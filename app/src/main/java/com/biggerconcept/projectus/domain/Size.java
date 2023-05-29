package com.biggerconcept.projectus.domain;

/**
 * Representation of task size.
 * 
 * @author Andrew Bigger
 */
public class Size {
    /**
     * Default no size task setting.
     */
    public static final int DEFAULT_ZERO_TASK_SIZE = 0;
    
     /**
     * Default extra small task size setting.
     */
    public static final int DEFAULT_XS_TASK_SIZE = 1;
    
    /**
     * Default small task size setting.
     */
    public static final int DEFAULT_S_TASK_SIZE = 3;
    
    /**
     * Default medium task size setting.
     */
    public static final int DEFAULT_M_TASK_SIZE = 5;
    
    /**
     * Default large task size setting.
     */
    public static final int DEFAULT_L_TASK_SIZE = 8;
    
    /**
     * Default extra large task size setting.
     */
    public static final int DEFAULT_XL_TASK_SIZE = 13;
    
    /**
     * ENUM of task sizes.
     */
    public static enum TaskSize {
        ZERO,
        XS,
        S,
        M,
        L,
        XL
    }
    
}
