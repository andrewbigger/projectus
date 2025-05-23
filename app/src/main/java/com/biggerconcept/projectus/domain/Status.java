package com.biggerconcept.projectus.domain;

import com.biggerconcept.projectus.exceptions.DateChronologyException;
import com.biggerconcept.projectus.helpers.Date;

/**
 * Class for deriving overall project status.
 * 
 * @author Andrew Bigger
 */
public class Status {
    /**
     * ENUM of project tracking status.
     */
    public static enum Tracking {
        AHEAD,
        ON_TRACK,
        AT_RISK,
        COMPLETE,
    }
    
    /**
     * Project document.
     */
    private Document doc;
    
    /**
     * Constructor for status.
     * 
     * @param doc document
     */
    public Status(Document doc) {
        this.doc = doc;
    }
    
    /**
     * Returns true if start and end dates have been specified in the
     * document.
     * 
     * @return whether dates have been set.
     */
    public boolean hasDates() {
        if (doc.getStart() == 0 || doc.getEnd() == 0) {
            return false;
        }

        return true;
    }
    
    /**
     * Returns true when the current time is greater than the project start
     * time.
     * 
     * @return whether the project has started
     */
    public boolean hasStarted() {
        if (doc.getStart() < Date.nowEpochDay()) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Returns true if the project has concluded
     * 
     * @return whether the project has concluded
     */
    public boolean hasConcluded() {
        if (doc.getEnd() < Date.nowEpochDay()) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Returns total number of weeks between the start and end date.
     * 
     * If the dates have not been set then zero will be returned.
     * 
     * Similarly if the end date is before the start date, then zero will be
     * returned.
     * 
     * @return total number of weeks
     */
    public long totalWeeks() {
        if (hasDates() == false) {
            return 0;
        }
        
        long weeks;
        
        try {
            weeks = Date.weeksBetween(doc.getStart(), doc.getEnd());
        } catch(DateChronologyException ex) {
            weeks = 0;
        }
        
        return weeks;
    }
    
    /**
     * Returns total number of weeks elapsed, difference between current time
     * and start date.
     * 
     * If the project hasn't started, 0 will be returned.
     * 
     * @return weeks elapsed
     */
    public long weeksElapsed() {
        if (hasStarted() == false) {
            return 0;
        }
        
        long elapsed;
        
        try {
            elapsed = Date.weeksBetween(doc.getStart(), Date.nowEpochDay());
        } catch (DateChronologyException ex) {
            elapsed = 0;
        }
        
        return elapsed;
    }
    
    /**
     * Returns total number of sprints between start and end date.
     * 
     * If the dates or sprint length has not been set in the document, then
     * zero will be returned.
     * 
     * If the total number of weeks between the start and end date is zero, then
     * zero will be returned.
     * 
     * @return total sprints
     */
    public long totalSprints() {
        if (hasDates() == false || sprintLength() == 0) {
            return 0;
        }
        
        long weeks = totalWeeks();
        
        if (weeks == 0) {
            return 0;
        }
        
        return weeks / sprintLength();
    }
    
    /**
     * Returns number of sprints elapsed between start date and current date.
     * 
     * if the dates or sprint length have not been set in the document then
     * zero will be returned.
     * 
     * @return sprints elapsed
     */
    public long sprintsElapsed() {
        if (hasDates() == false || sprintLength() == 0) {
            return 0;
        }
        
        long weeks = weeksElapsed();
        
        if (weeks == 0) {
            return 0;
        }
        
        return weeksElapsed() / sprintLength();
    }
    
    /**
     * Returns total number of points in the project.
     * 
     * @return total points
     */
    public int totalPoints() {
        int total = 0;
        
        for (Epic e : doc.getEpics()) {
            total += e.getSize(doc.getPreferences());
        }
        return total;
    }
    
    /**
     * Returns number of completed points.
     * 
     * @return completed point total
     */
    public int completedPoints() {
        int completed = 0;
        
        for (Epic e : doc.getEpics()) {
            completed += e.calculateCompletePointCount(doc.getPreferences());
        }
        
        return completed;
    }
    
    /**
     * Returns number of points completed per sprint.
     * 
     * @return points per sprint
     */
    public int pointsPerSprint() {
        int completed = completedPoints();
        long sprints = sprintsElapsed();
        
        if (completed == 0 || sprints == 0) {
            return 0;
        }
        
        return (int) (completed / sprints);
    }
    
    /**
     * Returns number of available points based on
     * average number of delivered points in reference
     * sprints.
     * 
     * @return available points
     */
    public int availablePoints() {       
        return (int) totalSprints() * 
                doc.getPreferences().averageDeliveredPoints();
    }
    
    /**
     * Returns total points as a proportion of available points.
     * 
     * @return available points progress
     */
    public double availablePointsProgress() {
        return (double) totalPoints() / availablePoints();
    }
    
    /**
     * Returns true if project is over committed.
     * 
     * @return over committed
     */
    public boolean isOverCommitted() {
        return totalPoints() > availablePoints();
    }
    
    /**
     * Returns elapsed sprints as a proportion of total sprints.
     * 
     * @return elapsed sprints progress
     */
    public double sprintProgress() {
        return (double) sprintsElapsed() / totalSprints();
    }
    
    /**
     * Returns completed points as a proportion of total points.
     * 
     * @return point progress
     */
    public double pointProgress() {
        return (double) completedPoints() / totalPoints();
    }
    
    /**
     * Returns ideal number of points per sprint required to 
     * complete the project by the due date.
     * 
     * @return ideal points per sprint
     */
    public int idealPointsPerSprint() {
        int total = totalPoints();
        long sprints = totalSprints();
        
        if (total == 0 || sprints == 0) {
            return 0;
        }
        
        return (int) (total / sprints);
    }
    
    /**
     * Returns status summary.
     * 
     * If the points are complete, the summary is that the
     * project is complete.
     * 
     * When the number of points completed is above the calculated
     * ideal, then the AHEAD summary is returned.
     * 
     * When the actual is on the ideal, ON_TRACK is returned.
     * 
     * When the actual is behind the ideal, then AT_RISK is returned.
     * 
     * @return summary
     */
    public Tracking summary() {
        if (completedPoints() == totalPoints()) {
            return Tracking.COMPLETE;
        }
        
        int actual = pointsPerSprint();
        int ideal = idealPointsPerSprint();
        
        if (actual > ideal) {
            return Tracking.AHEAD;
        }
        
        if (actual == ideal) {
            return Tracking.ON_TRACK;
        }
        
        return Tracking.AT_RISK;
    }
    
    /**
     * Retrieves sprint length from preferences.
     * 
     * @return sprint length
     */
    private int sprintLength() {
        return doc.getPreferences().getSprintLength();
    }
}
