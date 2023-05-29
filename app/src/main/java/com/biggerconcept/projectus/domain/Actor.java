package com.biggerconcept.projectus.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Representation of an actor in a story.
 * 
 * @author Andrew Bigger
 */
public class Actor {
    /**
     * Name of actor
     */
    @JsonInclude(Include.NON_NULL)
    private String name;
    
    /**
     * Default constructor
     */
    public Actor() {
        this.name = "";
    }
    
    /**
     * Constructor for an actor with a name.
     * 
     * @param name name of actor
     */
    public Actor(String name) {
        this.name = name;
    }
    
    /**
     * Getter for actor name.
     * 
     * @return actor name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Setter for actor name.
     * 
     * @param value value to set for actor name
     */
    public void setName(String value) {
        name = value;
    }
    
    /**
     * Override for toString method.
     * 
     * @return string representation of actor (name)
     */
    @Override
    public String toString() {
        return name;
    }
    
}
