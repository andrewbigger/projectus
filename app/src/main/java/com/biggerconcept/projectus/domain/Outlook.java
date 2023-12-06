package com.biggerconcept.projectus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * In memory representation of an epic outlook.
 * 
 * @author Andrew Bigger
 */
public class Outlook {
    /**
     * Projection builder function.
     */
    interface ProjectionBuilder {
        Projection build(int deviation);
    }
        
    /**
     * Deviation slice.
     */
    public static final int DEVIATION_SLICE = 4;
    /**
     * Pointer to parent epic.
     */
    private Epic parent;
    
    /**
     * Document preferences.
     */
    private Preferences prefs;
    
    /**
     * Buffer points to apply to estimates.
     */
    private int buffer;
    
    /**
     * Estimate points
     */
    private int estimatePoints;
    
    /**
     * Reference sprint one
     */
    private Sprint sprintOne;
    
    /**
     * Reference sprint two
     */
    private Sprint sprintTwo;
    
    /**
     * Reference sprint three
     */
    private Sprint sprintThree;
    
    /**
     * Reference sprint four
     */
    private Sprint sprintFour;
    
    /**
     * Projection three deviations more optimistic
     * than the estimate.
     */
    @JsonIgnore
    private Projection oPlusThree;
    
    /**
     * Projection two deviations more optimistic
     * than the estimate.
     */
    @JsonIgnore
    private Projection oPlusTwo;
    
    /**
     * Projection one deviation more optimistic
     * than the estimate.
     */
    @JsonIgnore
    private Projection oPlusOne;
    
    /**
     * Projection based on the estimate.
     */
    @JsonIgnore
    private Projection o;
    
    /**
     * Projection one deviation more pessimistic
     * than the estimate.
     */
    @JsonIgnore
    private Projection oMinusOne;
    
    /**
     * Projection two deviations more pessimistic
     * than the estimate.
     */
    @JsonIgnore
    private Projection oMinusTwo;
    
    /**
     * Projection three deviations more pessimistic
     * than the estimate.
     */
    @JsonIgnore
    private Projection oMinusThree;
    
    /**
     * Average points delivered in reference sprints.
     */
    private int averagePoints;
    
    /**
     * Points per sprint
     */
    private int pointsPerSprint;
    
    /**
     * Full constructor.
     * 
     * @param e parent epic
     * @param p document preferences
     */
    public Outlook(Epic e, Preferences p) {
        parent = e;
        prefs = p;
    }
    
    /**
     * Default constructor
     */
    public Outlook() {
        parent = null;
        prefs = null;
    }
    
    /**
     * Returns buffer points.
     * 
     * If the buffer is not set, then the default
     * buffer is retrieved from the preferences.
     * 
     * @return buffer points
     */
    public int getBuffer() {
        if (buffer == null) {
            return prefs.getEstimateBuffer();
        }
        
        return buffer;
    }
    
    /**
     * Returns estimate points.
     * 
     * @return estimate points
     */
    public int getEstimatePoints() {
        return estimatePoints;
    }
    
    /**
     * Returns estimate with set buffer.
     * 
     * @return estimate with buffer
     */
    @JsonIgnore
    public int getEstimateWithBuffer() {
        return getEstimatePoints() + getBuffer();
    }
    
    /**
     * Returns reference sprint one.
     * 
     * If reference sprint one is not set then
     * the default reference sprint is retrieved from
     * document preferences.
     * 
     * @return reference sprint
     */
    public Sprint getSprintOne() {
        if (sprintOne == null) {
            return prefs.getRefSprintOne();
        }
        
        return sprintOne;
    }
    
    /**
     * Returns reference sprint two.
     * 
     * If reference sprint two is not set then
     * the default reference sprint is retrieved from
     * document preferences.
     * 
     * @return reference sprint
     */
    public Sprint getSprintTwo() {
        if (sprintTwo == null) {
            return prefs.getRefSprintTwo();
        }
        
        return sprintTwo;
    }
    
    /**
     * Returns reference sprint three.
     * 
     * If reference sprint three is not set then
     * the default reference sprint is retrieved from
     * document preferences.
     * 
     * @return reference sprint
     */
    public Sprint getSprintThree() {
        if (sprintThree == null) {
            return prefs.getRefSprintThree();
        }
        
        return sprintThree;
    }
    
    /**
     * Returns reference sprint four.
     * 
     * If reference sprint four is not set then
     * the default reference sprint is retrieved from
     * document preferences.
     * 
     * @return reference sprint
     */
    public Sprint getSprintFour() {
        if (sprintFour == null) {
            return prefs.getRefSprintFour();
        }
        
        return sprintFour;
    }
    
    /**
     * Returns points per sprint.
     * 
     * If this is not set then the average points from
     * the reference sprints is returned by default.
     * 
     * @return points per sprint
     */
    public int getPointsPerSprint() {
        if (pointsPerSprint == null || pointsPerSprint == 0) {
            return getAveragePoints();
        }
        
        return pointsPerSprint;
    }
    
    /**
     * Returns the average number of points delivered.
     * 
     * If this is not set, then zero will be returned.
     * 
     * @return average points
     */
    public int getAveragePoints() {
        if (averagePoints == null) {
            return 0;
        }
        
        return averagePoints;
    }
    
    /**
     * Returns projection three deviations more
     * optimistic than the estimate.
     * 
     * @return projection
     */
    @JsonIgnore
    public Projection getOPlusThree() {
        return oPlusThree;
    }
    
    /**
     * Returns projection two deviations more
     * optimistic than the estimate.
     * 
     * @return projection
     */
    @JsonIgnore
    public Projection getOPlusTwo() {
        return oPlusTwo;
    }
    
    /**
     * Returns projection one deviations more
     * optimistic than the estimate.
     * 
     * @return projection
     */
    @JsonIgnore
    public Projection getOPlusOne() {
        return oPlusOne;
    }
    
    /**
     * Returns projection based on estimate.
     * 
     * @return projection
     */
    @JsonIgnore
    public Projection getO() {
        return o;
    }
    
    /**
     * Returns projection one deviation more
     * pessimistic than the estimate.
     * 
     * @return projection
     */
    @JsonIgnore
    public Projection getOMinusOne() {
        return oMinusOne;
    }
    
    /**
     * Returns projection two deviations more
     * pessimistic than the estimate.
     * 
     * @return projection
     */
    @JsonIgnore
    public Projection getOMinusTwo() {
        return oMinusTwo;
    }
    
    /**
     * Returns projection three deviations more
     * pessimistic than the estimate.
     * 
     * @return projection
     */
    @JsonIgnore
    public Projection getOMinusThree() {
        return oMinusThree;
    }
    
    /**
     * Default deviation.
     * 
     * This is includes the buffer.
     * 
     * @return deviation points
     */
    public int deviation() {
        return deviation(true);
    }
    
    /**
     * Returns number of points in a deviation.
     * 
     * The deviation is set as a quarter of the project size.
     * 
     * The buffer can be optionally included in the result.
     * 
     * @param inclBuffer whether or not to include the buffer
     * 
     * @return deviation points
     */
    public int deviation(boolean inclBuffer) {
        if (inclBuffer) {
            return getEstimateWithBuffer() / DEVIATION_SLICE;
        }
        
        return getEstimatePoints() / DEVIATION_SLICE;
    }
    
    /**
     * Setter for epic.
     * 
     * @param value epic value
     */
    public void setEpic(Epic value) {
        parent = value;
    }
    
    /**
     * Setter for preferences.
     * 
     * Preferences are necessary for point calculations.
     * 
     * @param value preferences to set
     */
    public void setPrefs(Preferences value) {
        prefs = value;
    }
    
    /**
     * Setter for buffer.
     * 
     * @param value buffer to set
     */
    public void setBuffer(int value) {
        buffer = value;
    }
    
    /**
     * Setter for reference sprint one.
     * 
     * @param value reference sprint
     */
    public void setSprintOne(Sprint value) {
        sprintOne = value;
    }
    
    /**
     * Setter for reference sprint two.
     * 
     * @param value reference sprint
     */
    public void setSprintTwo(Sprint value) {
        sprintTwo = value;
    }
    
    /**
     * Setter for reference sprint three.
     * 
     * @param value reference sprint
     */
    public void setSprintThree(Sprint value) {
        sprintThree = value;
    }
    
    /**
     * Setter for reference sprint four.
     * 
     * @param value reference sprint
     */
    public void setSprintFour(Sprint value) {
        sprintFour = value;
    }
    
    /**
     * Setter for points per sprint.
     * 
     * @param value points
     */
    public void setPointsPerSprint(int value) {
        pointsPerSprint = value;
    }
    
    /**
     * Default calculate callback.
     * 
     * This will calculate the outlook including the completed
     * task points.
     */
    public void calculate() {
        calculate(false);
    }
     
    /**
     * Calculate callback.
     * 
     * This will calculate the outlook. You can optionally include
     * completed task points.
     * 
     * This will:
     * 
     * - Calculate the total number of points
     * - Calculate the average number of delivered points from reference sprints
     * - Calculate projections
     * 
     * @param exclCompletedPoints whether to exclude completed task points.
     */
    public void calculate(boolean exclCompletedPoints) {
        calculateTotalPoints(exclCompletedPoints);
        calculateAverageDeliveredPointsFromReferenceSprints();
        calculateProjections();
    }
    
    /**
     * Calculates total number of points in the project.
     * 
     * This iterates over each task in the project and
     * adds the task points based on document preferences.
     * 
     * If the exclCompletedPoints is passed, any task that is
     * completed will be skipped in the calculation of points.
     * 
     * @param exclCompletedPoints whether to exclude completed tasks
     */
     private void calculateTotalPoints(boolean exclCompletedPoints) {
        estimatePoints = 0;
        
        for (Task t : parent.getTasks()) {
            if (t.isComplete() == true && exclCompletedPoints == true) {
                continue;
            }
            
            estimatePoints += prefs.estimateFor(t.getSize());
        }
    }
    
     /**
      * Determines average number of points delivered in the reference
      * sprints.
      * 
      * The average is stored on the averagePoints instance variable.
      */
    private void calculateAverageDeliveredPointsFromReferenceSprints() {
        averagePoints = sprintCompletedPoints() / 4;
    }
        
    /**
     * Calculates projections.
     * 
     * First we set up a projection builder function.
     * 
     * Then a projection is constructed for the estimated scenario.
     * 
     * Projections are constructed for three deviations in the
     * optimistic direction, and three deviations in the pessimistic
     * direction.
     */
    private void calculateProjections() {
        int estimate = getEstimateWithBuffer();
        
        ProjectionBuilder builder = (deviation) -> {
            return new Projection(
                    estimate,
                    getPointsPerSprint(),
                    deviation,
                    prefs
            );
        };
        
        o = builder.build(0);
        
        oPlusThree = builder.build(3);
        oPlusTwo = builder.build(2);
        oPlusOne = builder.build(1);
        
        oMinusOne = builder.build(-1);
        oMinusTwo = builder.build(-2);
        oMinusThree = builder.build(-3);
    }
    
    /**
     * Calculates total number of completed points for 
     * the reference sprints.
     * 
     * @return total number of completed points
     */
    private int sprintCompletedPoints() {
        return getSprintOne().getCompletedPoints() + 
                getSprintTwo().getCompletedPoints() + 
                getSprintThree().getCompletedPoints() +
                getSprintFour().getCompletedPoints();
    }
}
