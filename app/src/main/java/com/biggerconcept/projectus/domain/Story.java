package com.biggerconcept.projectus.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Representation of a user story.
 * 
 * @author Andrew Bigger
 */
public class Story {
    /**
     * Reference to story actor.
     */
    @JsonInclude(Include.NON_NULL)
    private Actor actor;
    
    /**
     * Story intention.
     */
    @JsonInclude(Include.NON_NULL)
    private String intention;
    
    /**
     * Story expectation.
     */
    @JsonInclude(Include.NON_NULL)
    private String expectation;
    
    /**
     * Default constructor for story.
     */
    public Story() {
        this.actor = null;
        this.intention = "";
        this.expectation = "";
    }
    
    /**
     * Constructor for story.
     * 
     * @param actor 
     */
    public Story(Actor actor) {
        this.actor = actor;
        this.intention = "";
        this.expectation = "";
    }
    
    /**
     * Constructor for story.
     * 
     * @param actor
     * @param intention
     * @param expectation 
     */
    public Story(Actor actor, String intention, String expectation) {
        this.actor = actor;
        this.intention = intention;
        this.expectation = expectation;
    }
    
    /**
     * Getter for actor.
     * 
     * @return 
     */
    public Actor getActor() {
        return actor;
    }
    
    /**
     * Getter for intention.
     * 
     * @return 
     */
    public String getIntention() {
        return intention;
    }
    
    /**
     * Getter for expectation.
     * 
     * @return 
     */
    public String getExpectation() {
        return expectation;
    }
    
    /**
     * Setter for actor.
     * 
     * @param value 
     */
    public void setActor(Actor value) {
        actor = value;
    }
    
    /**
     * Setter for intention.
     * 
     * @param value 
     */
    public void setIntention(String value) {
        intention = value;
    }
    
    /**
     * Setter for expectation.
     * 
     * @param value 
     */
    public void setExpectation(String value) {
        expectation = value;
    }
}
