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
    * @param size task size
    * 
    * @return estimate as number
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
     * Start number for epics.
     */
    @JsonInclude(Include.NON_NULL)
    private int epicStartNumber;
    
    /**
     * Size of sprint in weeks.
     */
    @JsonInclude(Include.NON_NULL)
    private int sprintLength;
    
    /**
     * Default epic start number.
     */
    private static final int DEFAULT_EPIC_START_NUMBER = 1;
    
    /**
     * Default sprint size number.
     */
    private static final int DEFAULT_SPRINT_LENGTH = 2;
    
    /**
     * Builds a set of default preferences.
     * 
     * @return default preferences
     */
    public static Preferences defaultPreferences() {
        Preferences p = new Preferences();
        
        p.epicStartNumber = DEFAULT_EPIC_START_NUMBER;
        
        p.extraSmallTaskSize = DEFAULT_XS_TASK_SIZE;
        p.smallTaskSize = DEFAULT_S_TASK_SIZE;
        p.mediumTaskSize = DEFAULT_M_TASK_SIZE;
        p.largeTaskSize = DEFAULT_L_TASK_SIZE;
        p.extraLargeTaskSize = DEFAULT_XL_TASK_SIZE;
        p.sprintLength = DEFAULT_SPRINT_LENGTH;
        
        return p;
    }
    
    /**
     * Getter of epic start number.
     * 
     * @return epic start number
     */
    public int getEpicStartNumber() {
        return epicStartNumber;
    }
    
    /**
     * Getter for extra small task size setting.
     * 
     * @return extra small task size
     */
    public int getExtraSmallTaskSize() {
        return extraSmallTaskSize;
    }
    
    /**
     * Getter for small task size setting.
     * 
     * @return small task size
     */
    public int getSmallTaskSize() {
        return smallTaskSize;
    }
    
    /**
     * Getter for medium task size setting.
     * 
     * @return medium task size
     */
    public int getMediumTaskSize() {
        return mediumTaskSize;
    }
    
    /**
     * Getter for large task size setting.
     * 
     * @return large task size
     */
    public int getLargeTaskSize() {
        return largeTaskSize;
    }
    
    /**
     * Getter for extra large task size setting.
     * 
     * @return extra large task size
     */
    public int getExtraLargeTaskSize() {
        return extraLargeTaskSize;
    }
    
    /**
     * Getter for sprint length.
     * 
     * @return sprint length
     */
    public int getSprintLength() {
        return sprintLength;
    }
    
    /**
     * String setter for epic start number.
     * 
     * @param value string representation of start number.
     */
    public void setEpicStartNumber(String value) {
        setEpicStartNumber(
                Integer.parseInt(value)
        );
    }
    
    /**
     * Setter for epic start number.
     * 
     * This is the number to start counting epics from.
     * 
     * @param value value to set start epic number to
     */
    public void setEpicStartNumber(int value) {
        epicStartNumber = value;
    }
    
    /**
     * String setter for extra small task size setting.
     * 
     * Parses string to integer before calling the integer based setter.
     * 
     * @param value string integer value to set as extra small size
     */
    public void setExtraSmallSize(String value) {
        setExtraSmallSize(
                Integer.parseInt(value)
        );
    }
    
    /**
     * Setter for extra small task size setting.
     * 
     * @param value integer value to set as extra small size
     */
    public void setExtraSmallSize(int value) {
        extraSmallTaskSize = value;
    }
    
    /**
     * Setter for small task size setting.
     * 
     * Parses string to integer before calling the integer based setter.
     * 
     * @param value string integer value to set as small size
     */
    public void setSmallSize(String value) {
        setSmallSize(
            Integer.parseInt(value)
        );
    }
    
    /**
     * Setter for small task size setting.
     * 
     * @param value integer value to set as small size
     */
    public void setSmallSize(int value) {
        smallTaskSize = value;
    }
    
     /**
     * Setter for medium task size setting.
     * 
     * Parses string to integer before calling the integer based setter.
     * 
     * @param value string integer value to set as medium size
     */
    public void setMediumSize(String value) {
        setMediumSize(
            Integer.parseInt(value)
        );
    }
   
    /**
     * Setter for medium task size setting.
     * 
     * @param value integer value to set as medium size
     */
    public void setMediumSize(int value) {
        mediumTaskSize = value;
    }
    
    /**
     * Setter for large task size setting.
     * 
     * Parses string to integer before calling the integer based setter.
     * 
     * @param value string value to set as large size
     */
    public void setLargeSize(String value) {
        setLargeSize(
            Integer.parseInt(value)
        );
    }
    
    /**
     * Setter for large task size setting.
     * 
     * @param value integer value to set as large size
     */
    public void setLargeSize(int value) {
        largeTaskSize = value;
    }
    
    /**
     * Setter for extra large task size setting.
     * 
     * Parses string to integer before calling the integer based setter.
     * 
     * @param value string value to set as extra large size
     */
    public void setExtraLargeSize(String value) {
        setExtraLargeSize(
            Integer.parseInt(value)
        );
    }
    
    /**
     * Setter for extra large task size setting.
     * 
     * @param value integer value to set as extra large size
     */
    public void setExtraLargeSize(int value) {
        extraLargeTaskSize = value;
    }
    
    /**
     * Setter for sprint length.
     * 
     * @param value integer value to set as sprint length
     */
    public void setSprintLength(int value) {
        sprintLength = value;
    }
    
    /**
     * String based setter for sprint length.
     * 
     * @param value 
     */
    public void setSprintLength(String value) {
        setSprintLength(
                Integer.parseInt(value)
        );
    }
    
}
