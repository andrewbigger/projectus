package com.biggerconcept.projectus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.UUID;

/**
 * Representation of an actor in a story.
 * 
 * @author Andrew Bigger
 */
public class Actor {
    /**
     * Actor ID.
     */
    @JsonInclude(Include.NON_NULL)
    private UUID id;
    
    /**
     * Name of actor.
     */
    @JsonInclude(Include.NON_NULL)
    private String name;
    
    /**
     * Pointer to parent document.
     */
    @JsonIgnore
    private Document parent;
    
    /**
     * Default constructor.
     */
    public Actor() {
        this.id = UUID.randomUUID();
        this.name = "";
    }
    
    /**
     * Constructor for an actor with a name.
     * 
     * @param name name of actor
     */
    public Actor(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
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
     * Setter for document parent pointer.
     * 
     * @param value 
     */
    public void setParent(Document value) {
        parent = value;
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
     * Setter for id.
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
