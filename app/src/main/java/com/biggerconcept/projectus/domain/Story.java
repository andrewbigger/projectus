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
     * @param actor actor that the story belongs to
     */
    public Story(Actor actor) {
        this.actor = actor;
        this.intention = "";
        this.expectation = "";
    }
    
    /**
     * Constructor for story.
     * 
     * @param actor actor that the story belongs to
     * @param intention story intent string (i want to)
     * @param expectation  story expectation strings (and i expect)
     */
    public Story(Actor actor, String intention, String expectation) {
        this.actor = actor;
        this.intention = intention;
        this.expectation = expectation;
    }
    
    /**
     * Getter for actor.
     * 
     * @return story actor
     */
    public Actor getActor() {
        return actor;
    }
    
    /**
     * Getter for intention.
     * 
     * @return story intention
     */
    public String getIntention() {
        return intention;
    }
    
    /**
     * Getter for expectation.
     * 
     * @return story expectation
     */
    public String getExpectation() {
        return expectation;
    }
    
    /**
     * Setter for actor.
     * 
     * @param value story actor to set
     */
    public void setActor(Actor value) {
        actor = value;
    }
    
    /**
     * Setter for intention.
     * 
     * @param value intention value to set
     */
    public void setIntention(String value) {
        intention = value;
    }
    
    /**
     * Setter for expectation.
     * 
     * @param value expectation value to set
     */
    public void setExpectation(String value) {
        expectation = value;
    }
}
