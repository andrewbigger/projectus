package com.biggerconcept.projectus.helpers;

/**
 * Functions for comparing values.
 * 
 * @author Andrew Bigger
 */
public class Compare {
    /**
     * Ensures a given string is not empty and is different to
     * a comparison string.
     * 
     * When the string is empty, this will return false. When the
     * string is different to the compare value this will return false.
     * 
     * Otherwise this will return true.
     * 
     * @param value value to check
     * @param compare value to check against
     * 
     * @return result
     */
    public static boolean notEmptyIsDifferent(String value, String compare) {
        if (value.trim() == "") {
            return false;
        }
        
        if (value == compare) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Ensures a given long is not empty and is different to
     * a comparison long.
     * 
     * When the long is empty, this will return false. When the
     * long is different to the compare value this will return false.
     * 
     * Otherwise this will return true.
     * 
     * @param value value to check
     * @param compare value to check against
     * 
     * @return result
     */
    public static boolean notEmptyIsDifferent(long value, long compare) {
        if (value == 0) {
            return false;
        }
        
        if (value == compare) {
            return false;
        }
        
        return true;
    }
}
