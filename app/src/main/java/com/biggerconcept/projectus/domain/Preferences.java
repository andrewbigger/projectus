package com.biggerconcept.projectus.domain;

import static com.biggerconcept.projectus.domain.Size.DEFAULT_L_TASK_SIZE;
import static com.biggerconcept.projectus.domain.Size.DEFAULT_M_TASK_SIZE;
import static com.biggerconcept.projectus.domain.Size.DEFAULT_S_TASK_SIZE;
import static com.biggerconcept.projectus.domain.Size.DEFAULT_XL_TASK_SIZE;
import static com.biggerconcept.projectus.domain.Size.DEFAULT_XS_TASK_SIZE;
import static com.biggerconcept.projectus.domain.Size.TaskSize.L;
import static com.biggerconcept.projectus.domain.Size.TaskSize.M;
import static com.biggerconcept.projectus.domain.Size.TaskSize.S;
import static com.biggerconcept.projectus.domain.Size.TaskSize.XL;
import static com.biggerconcept.projectus.domain.Size.TaskSize.XS;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Application preferences.
 * 
 * @author Andrew Bigger
 */
public class Preferences {
        
   /**
    * Returns estimate for given task size.
    * 
    * @param size
    * @return 
    */
    public int estimateFor(Size.TaskSize size) {
        switch(size) {
            case XS:
                return extraSmallTaskSize;
            case S:
                return smallTaskSize;
            case M:
                return mediumTaskSize;
            case L:
                return largeTaskSize;
            case XL:
                return extraLargeTaskSize;
            default:
                return 0;
        }
    }
    
    /**
     * Set size for an extra small task.
     */
    @JsonInclude(Include.NON_NULL)
    private int extraSmallTaskSize;
    
    /**
     * Set size for a small task.
     */
    @JsonInclude(Include.NON_NULL)
    private int smallTaskSize;
    
    /**
     * Set size for a medium task.
     */
    @JsonInclude(Include.NON_NULL)
    private int mediumTaskSize;
    
    /**
     * Set size for a large task.
     */
    @JsonInclude(Include.NON_NULL)
    private int largeTaskSize;
    
    /**
     * Set size for an extra large task.
     */
    @JsonInclude(Include.NON_NULL)
    private int extraLargeTaskSize;
    
    /**
     * Builds a set of default preferences.
     * 
     * @return 
     */
    public static Preferences defaultPreferences() {
        Preferences p = new Preferences();
        
        p.extraSmallTaskSize = DEFAULT_XS_TASK_SIZE;
        p.smallTaskSize = DEFAULT_S_TASK_SIZE;
        p.mediumTaskSize = DEFAULT_M_TASK_SIZE;
        p.largeTaskSize = DEFAULT_L_TASK_SIZE;
        p.extraLargeTaskSize = DEFAULT_XL_TASK_SIZE;
        
        return p;
    }
    
    /**
     * Getter for extra small task size setting.
     * 
     * @return 
     */
    public int getExtraSmallTaskSize() {
        return extraSmallTaskSize;
    }
    
    /**
     * Getter for small task size setting.
     * 
     * @return 
     */
    public int getSmallTaskSize() {
        return smallTaskSize;
    }
    
    /**
     * Getter for medium task size setting.
     * 
     * @return 
     */
    public int getMediumTaskSize() {
        return mediumTaskSize;
    }
    
    /**
     * Getter for large task size setting.
     * 
     * @return 
     */
    public int getLargeTaskSize() {
        return largeTaskSize;
    }
    
    /**
     * Getter for extra large task size setting.
     * 
     * @return 
     */
    public int getExtraLargeTaskSize() {
        return extraLargeTaskSize;
    }
    
    /**
     * String setter for extra small task size setting.
     * 
     * Parses string to integer before calling the integer based setter.
     * 
     * @param value 
     */
    public void setExtraSmallSize(String value) {
        setExtraSmallSize(
                Integer.parseInt(value)
        );
    }
    
    /**
     * Setter for extra small task size setting.
     * 
     * @param value 
     */
    public void setExtraSmallSize(int value) {
        extraSmallTaskSize = value;
    }
    
    /**
     * Setter for small task size setting.
     * 
     * Parses string to integer before calling the integer based setter.
     * 
     * @param value
     */
    public void setSmallSize(String value) {
        setSmallSize(
            Integer.parseInt(value)
        );
    }
    
    /**
     * Setter for small task size setting.
     * 
     * @param value 
     */
    public void setSmallSize(int value) {
        smallTaskSize = value;
    }
    
     /**
     * Setter for medium task size setting.
     * 
     * Parses string to integer before calling the integer based setter.
     * 
     * @param value
     */
    public void setMediumSize(String value) {
        setMediumSize(
            Integer.parseInt(value)
        );
    }
   
    /**
     * Setter for medium task size setting.
     * 
     * @param value 
     */
    public void setMediumSize(int value) {
        mediumTaskSize = value;
    }
    
    /**
     * Setter for large task size setting.
     * 
     * Parses string to integer before calling the integer based setter.
     * 
     * @param value
     */
    public void setLargeSize(String value) {
        setLargeSize(
            Integer.parseInt(value)
        );
    }
    
    /**
     * Setter for large task size setting.
     * 
     * @param value 
     */
    public void setLargeSize(int value) {
        largeTaskSize = value;
    }
    
    /**
     * Setter for extra large task size setting.
     * 
     * Parses string to integer before calling the integer based setter.
     * 
     * @param value
     */
    public void setExtraLargeSize(String value) {
        setExtraLargeSize(
            Integer.parseInt(value)
        );
    }
    
    /**
     * Setter for extra large task size setting.
     * 
     * @param value 
     */
    public void setExtraLargeSize(int value) {
        extraLargeTaskSize = value;
    }
    
}
