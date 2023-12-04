package com.biggerconcept.projectus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * In memory representation of an epic outlook.
 * 
 * @author Andrew Bigger
 */
public class Outlook {
    private Epic selectedEpic;
    private Preferences prefs;
    
    private Integer buffer;
    private Integer estimatePoints;
    
    private Sprint sprintOne;
    private Sprint sprintTwo;
    private Sprint sprintThree;
    private Sprint sprintFour;
    
    @JsonIgnore
    private Projection oPlusThree;
    
    @JsonIgnore
    private Projection oPlusTwo;
    
    @JsonIgnore
    private Projection oPlusOne;
    
    @JsonIgnore
    private Projection o;
    
    @JsonIgnore
    private Projection oMinusOne;
    
    @JsonIgnore
    private Projection oMinusTwo;
    
    @JsonIgnore
    private Projection oMinusThree;
    
    private Integer averagePoints;
    private Integer pointsPerSprint;
    
    public Outlook(Epic e, Preferences p) {
        selectedEpic = e;
        prefs = p;
    }
    
    public Outlook() {
        selectedEpic = null;
        prefs = null;
    }
    
    public Integer getBuffer() {
        if (buffer == null) {
            return prefs.getEstimateBuffer();
        }
        
        return buffer;
    }
    
    public Integer getEstimatePoints() {
        return estimatePoints;
    }
    
    @JsonIgnore
    public Integer getEstimateWithBuffer() {
        return getEstimatePoints() + getBuffer();
    }
    
    public Sprint getSprintOne() {
        if (sprintOne == null) {
            return prefs.getRefSprintOne();
        }
        
        return sprintOne;
    }
    
    public Sprint getSprintTwo() {
        if (sprintTwo == null) {
            return prefs.getRefSprintTwo();
        }
        
        return sprintTwo;
    }
    
    public Sprint getSprintThree() {
        if (sprintThree == null) {
            return prefs.getRefSprintThree();
        }
        
        return sprintThree;
    }
    
    public Sprint getSprintFour() {
        if (sprintFour == null) {
            return prefs.getRefSprintFour();
        }
        
        return sprintFour;
    }
    
    public Integer getPointsPerSprint() {
        if (pointsPerSprint == null) {
            return getAveragePoints();
        }
        
        return pointsPerSprint;
    }
    
    public Integer getAveragePoints() {
        if (averagePoints == null) {
            return 0;
        }
        
        return averagePoints;
    }
    
    @JsonIgnore
    public Projection getOPlusThree() {
        return oPlusThree;
    }
    
    @JsonIgnore
    public Projection getOPlusTwo() {
        return oPlusTwo;
    }
    
    @JsonIgnore
    public Projection getOPlusOne() {
        return oPlusOne;
    }
    
    @JsonIgnore
    public Projection getO() {
        return o;
    }
    
    @JsonIgnore
    public Projection getOMinusOne() {
        return oMinusOne;
    }
    
    @JsonIgnore
    public Projection getOMinusTwo() {
        return oMinusTwo;
    }
    
    @JsonIgnore
    public Projection getOMinusThree() {
        return oMinusThree;
    }
    
    public Integer deviation() {
        return getEstimateWithBuffer() / 4;
    }
    
    public boolean isOptimistic(Integer estimate) {
        return estimate > getEstimatePoints();
    }
    
    public boolean isPessimistic(Integer estimate) {
        return estimate < getEstimatePoints() - deviation();
    }
    
    public void setEpic(Epic value) {
        selectedEpic = value;
    }
    
    public void setPrefs(Preferences value) {
        prefs = value;
    }
    
    public void setBuffer(Integer value) {
        buffer = value;
    }
    
    public void setSprintOne(Sprint value) {
        sprintOne = value;
    }
    
    public void setSprintTwo(Sprint value) {
        sprintTwo = value;
    }
    
    public void setSprintThree(Sprint value) {
        sprintThree = value;
    }
    
    public void setSprintFour(Sprint value) {
        sprintFour = value;
    }
    
    public void setPointsPerSprint(Integer value) {
        pointsPerSprint = value;
    }
    
    public void calculate() {
        calculate(false);
    }
    
    public void calculate(boolean exclCompletedPoints) {
        estimatePoints = 0;
        
        for (Task t : selectedEpic.getTasks()) {
            if (t.isComplete() == true && exclCompletedPoints == true) {
                continue;
            }
            
            estimatePoints += prefs.estimateFor(t.getSize());
        }
        
        averagePoints = sprintCompletedPoints() / 4;
        
        int estimate = getEstimateWithBuffer();
        
//        if (exclCompletedPoints == true) {
//            estimate = getEstimatePoints();
//        }
        
        oPlusThree = new Projection(
                getEstimateWithBuffer(),
                getPointsPerSprint(),
                3,
                prefs
        );
        
        oPlusTwo = new Projection(
                estimate,
                getPointsPerSprint(),
                2,
                prefs
        );
        
        oPlusOne = new Projection(
                estimate,
                getPointsPerSprint(),
                1,
                prefs
        );
        
        o = new Projection(
                estimate,
                getPointsPerSprint(),
                0,
                prefs
        );
        
        oMinusOne = new Projection(
                estimate,
                getPointsPerSprint(),
                -1,
                prefs
        );
        
        oMinusTwo = new Projection(
                estimate,
                getPointsPerSprint(),
                -2,
                prefs
        );
        
        oMinusThree = new Projection(
                estimate,
                getPointsPerSprint(),
                -3,
                prefs
        );
    }
    
    private Integer sprintCompletedPoints() {
        return getSprintOne().getCompletedPoints() + 
                getSprintTwo().getCompletedPoints() + 
                getSprintThree().getCompletedPoints() +
                getSprintFour().getCompletedPoints();
    }
}
