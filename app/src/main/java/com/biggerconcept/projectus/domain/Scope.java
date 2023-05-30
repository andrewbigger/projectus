package com.biggerconcept.projectus.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;

/**
 * Representation of epic scope.
 * 
 * @author Andrew Bigger
 */
public class Scope {
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
        this.include = new ArrayList<>();
        this.exclude = new ArrayList<>();
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
