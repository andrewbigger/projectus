package com.biggerconcept.projectus.ui;

import java.time.LocalDate;

/**
 * Date utility.
 * 
 * @author Andrew Bigger
 */
public class Date {
     /**
     * Converts epoch date to local date for presentation.
     * 
     * If the value is 0, null will be returned so we don't have projects
     * starting in 1970.
     * 
     * @param value epoch date value to convert
     * 
     * @return date as LocalDate
     */
    public static LocalDate fromEpoch(long value) {
        if (value == 0) {
            return null;
        }
        
        return java.time.LocalDate.ofEpochDay(value);
    }
    
    /**
     * Converts local date to epoch for serialization.
     * 
     * @param date local date value to convert
     * 
     * @return date as number of seconds from epoch
     */
    public static long toEpoch(LocalDate date) {
        if (date == null) {
            return 0;
        }

        return date.toEpochDay();
    }
}
