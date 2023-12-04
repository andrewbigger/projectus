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
    
    private Integer adjustment;
    
    /**
     * Total number of totalPoints in projection.
     */
    private Integer totalPoints;
    
    private Integer pointsPerSprint;
    
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
    
    public Integer getTotalPoints() {
        return totalPoints;
    }
    
    public Integer getPointsPerSprint() {
        return adjust(pointsPerSprint, adjustment);
    }
    
    public Integer getSprints() {    
        return getTotalPoints() / getPointsPerSprint();
    }
    
    public Integer getWeeks() {
        return getSprints() * prefs.getSprintLength();
    }

    private Integer adjust(Integer points, Integer adjustment) {
        boolean positive = true;
        int rounds = adjustment;
        
        if (adjustment < 0) {
            positive = false;
            rounds = -adjustment;
        }
        
        int adj = points;
        int deviation = points / 4;
        
        for (int i = 0; i < rounds; i++) {

            if (positive == true) {
                adj += deviation;
            } else {
                adj -= deviation;
            }
            
            deviation = adj / 4;
        }
        
        return adj;
    }

}
