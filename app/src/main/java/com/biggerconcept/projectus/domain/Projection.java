package com.biggerconcept.projectus.domain;

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
    private Integer adjustment;
    
    /**
     * Total number of totalPoints in projection.
     */
    private Integer totalPoints;
    
    /**
     * Configured points per sprint.
     */
    private Integer pointsPerSprint;
    
    /**
     * Constructor for projection
     * 
     * @param totalPoints total number of points in epic
     * @param pointsPerSprint points per sprint
     * @param adjustment number of deviations to adjust estimates
     * @param prefs document preferences
     */
    public Projection(
            Integer totalPoints,
            Integer pointsPerSprint,
            Integer adjustment, 
            Preferences prefs
    ) {
        this.totalPoints = totalPoints;
        this.pointsPerSprint = pointsPerSprint;
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
    
    /**
     * Getter for total number of points.
     * 
     * @return total number of points.
     */
    public Integer getTotalPoints() {
        return totalPoints;
    }
    
    /**
     * Getter for points per sprint.
     * 
     * @return points per sprint
     */
    public Integer getPointsPerSprint() {
        return adjust(pointsPerSprint);
    }
    
    /**
     * Getter for number of sprints to complete epic.
     * 
     * @return estimate of number of sprints
     */
    public Integer getSprints() {    
        return getTotalPoints() / getPointsPerSprint();
    }
    
    /**
     * Getter for number of weeks.
     * 
     * @return estimate for number of weeks.
     */
    public Integer getWeeks() {
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
    private Integer adjust(Integer points) {
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
