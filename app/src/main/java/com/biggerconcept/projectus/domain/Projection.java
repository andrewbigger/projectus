package com.biggerconcept.projectus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * In memory representation of a projection.
 * 
 * @author Andrew Bigger
 */
public class Projection {
    /**
     * Document preferences.
     */
    private Preferences prefs;
    
    /**
     * Number of deviations to adjust the estimate by
     */
    private int adjustment;
    
    /**
     * Total number of totalPoints in projection.
     */
    private int totalPoints;
    
    /**
     * Configured points per sprint.
     */
    private int pointsPerSprint;
    
    /**
     * Constructor for projection
     * 
     * @param totalPoints total number of points in epic
     * @param pointsPerSprint points per sprint
     * @param adjustment number of deviations to adjust estimates
     * @param prefs document preferences
     */
    public Projection(
            int totalPoints,
            int pointsPerSprint,
            int adjustment, 
            Preferences prefs
    ) {
        this.totalPoints = totalPoints;
        
        // Points per sprint must be positive
        // this will guard against a dived by zero error
        // Default to deviation slice
        if (pointsPerSprint == 0) {
            this.pointsPerSprint = totalPoints / Outlook.DEVIATION_SLICE;
        } else {
            this.pointsPerSprint = pointsPerSprint;
        }
        
        this.adjustment = adjustment;
        this.prefs = prefs;        
    }
    
    /**
     * Default constructor.
     */
    public Projection() {
        this.totalPoints = 0;
        this.pointsPerSprint = 0;
        this.adjustment = 0;
        this.prefs = null;
    }
    
    @JsonIgnore
    public String getName() {
        return "O" + String.valueOf(adjustment);
    }
    
    /**
     * Getter for total number of points.
     * 
     * @return total number of points.
     */
    public int getTotalPoints() {
        return totalPoints;
    }
    
    /**
     * Getter for points per sprint.
     * 
     * @return points per sprint
     */
    public int getPointsPerSprint() {
        return adjust(pointsPerSprint);
    }
    
    /**
     * Getter for number of sprints to complete epic.
     * 
     * @return estimate of number of sprints
     */
    public int getSprints() {    
        return getTotalPoints() / getPointsPerSprint();
    }
    
    /**
     * Getter for number of weeks.
     * 
     * @return estimate for number of weeks.
     */
    public int getWeeks() {
        return getSprints() * prefs.getSprintLength();
    }

    /**
     * Adjusts number of points based on projection adjustment.
     * 
     * For each deviation, a deviation of points is added or
     * removed depending on whether the adjustment is positive or
     * negative.
     * 
     * @param points points to adjust
     * @return adjusted projected estimate
     */
    private int adjust(int points) {
        boolean positive = true;
        int rounds = adjustment;
        
        if (adjustment < 0) {
            positive = false;
            rounds = -adjustment;
        }
        
        int adjustedPoints = points;
        int deviation = adjustedPoints / Outlook.DEVIATION_SLICE;
        
        for (int i = 0; i < rounds; i++) {
            if (positive == true) {
                adjustedPoints += deviation;
            } else {
                adjustedPoints -= deviation;
            }
            
            deviation = adjustedPoints / Outlook.DEVIATION_SLICE;
        }
        
        return adjustedPoints;
    }

}
