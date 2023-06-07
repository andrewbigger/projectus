package com.biggerconcept.projectus.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Representation of epic scope.
 * 
 * @author Andrew Bigger
 */
public class Scope {
    /**
     * Scope ID.
     */
    @JsonInclude(Include.NON_NULL)
    private UUID id;
    
    /**
     * Holds items that are in scope.
     */
    @JsonInclude(Include.NON_NULL)
    private ArrayList<String> include;
    
    /**
     * Holds items that are not in scope.
     */
    @JsonInclude(Include.NON_NULL)
    private ArrayList<String> exclude;
    
    /**
     * Default constructor for scope.
     */
    public Scope() {
        this.id = UUID.randomUUID();
        this.include = new ArrayList<>();
        this.exclude = new ArrayList<>();
    }
    
    /**
     * Returns ID.
     * 
     * @return 
     */
    public UUID getId() {
        return id;
    }
    
    /**
     * Returns included scope.
     * 
     * @return scope
     */
    public ArrayList<String> getIncluded() {
        return include;
    }
    
    /**
     * Returns excluded scope.
     * 
     * @return out of scope
     */
    public ArrayList<String> getExcluded() {
        return exclude;
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
     * Sets included scope.
     * 
     * @param value value to set as scope
     */
    public void setIncluded(ArrayList<String> value) {
        include = value;
    }
    
    /**
     * Sets excluded scope.
     * 
     * @param value to set as out of scope
     */
    public void setExcluded(ArrayList<String> value) {
        exclude = value;
    }
}
