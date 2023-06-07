package com.biggerconcept.projectus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.UUID;

/**
 * Representation of a user story.
 * 
 * @author Andrew Bigger
 */
public class Story {
    /**
     * Story ID.
     */
    @JsonInclude(Include.NON_NULL)
    private UUID id;
    
    /**
     * Story identifier.
     */
    @JsonInclude(Include.NON_NULL)
    private int identifier;
    
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
     * Parent document pointer.
     */
    @JsonIgnore
    private Document parent;
    
    /**
     * Default constructor for story.
     */
    public Story() {
        this.id = UUID.randomUUID();
        this.identifier = -1;
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
        this.id = UUID.randomUUID();
        this.identifier = -1;
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
        this.id = UUID.randomUUID();
        this.identifier = -1;
        this.actor = actor;
        this.intention = intention;
        this.expectation = expectation;
    }
    
    /**
     * Getter for ID.
     * 
     * @return 
     */
    public UUID getId() {
        return id;
    }
    
    /**
     * Getter for identifier.
     * 
     * @return 
     */
    public int getIdentifier() {
        return identifier;
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
     * Setter for parent document pointer.
     * 
     * @param value 
     */
    public void setParent(Document value) {
        parent = value;
    }
    
    /**
     * Setter for ID.
     * 
     * @param value 
     */
    public void setId(UUID value) {
        id = value;
    }
    
    /**
     * String based setter for ID.
     * 
     * @param value 
     */
    public void setId(String value) {
        id = UUID.fromString(value);
    }
    
    /**
     * Setter for identifier.
     * 
     * @param value 
     */
    public void setIdentifier(int value) {
        identifier = value;
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
    
    /**
     * Returns true if given story matches.
     * 
     * @param s story to search for
     * 
     * @return whether the story was found
     */
    public boolean match(Story s) {
        if (id == s.getId()) {
            return true;
        }
        
        return false;
    }
}
