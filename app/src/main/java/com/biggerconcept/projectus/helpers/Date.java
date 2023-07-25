package com.biggerconcept.projectus.helpers;

import com.biggerconcept.projectus.exceptions.DateChronologyException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
    
    /**
     * Calculates number of weeks between a start and end date.
     * 
     * When the start date is after the end date, a date chronology exception
     * will be thrown to the caller.
     * 
     * @param start date to start from
     * @param end date to end at
     * @return
     * @throws DateChronologyException 
     */
    public static long weeksBetween(LocalDate start, LocalDate end) 
            throws DateChronologyException {
        if (toEpoch(start) > toEpoch(end)) {
            throw new DateChronologyException();
        }
        
        return ChronoUnit.WEEKS.between(start, end);
    }
    
    /**
     * Calculates number of weeks between a start epoch timestamp and end
     * epoch timestamp.
     * 
     * When the start date is after the end date, a date chronology exception
     * will be thrown to the caller.
     * 
     * @param startEpoch start timestamp
     * @param endEpoch end timestamp
     * @return
     * @throws DateChronologyException 
     */
    public static long weeksBetween(long startEpoch, long endEpoch) 
            throws DateChronologyException {
        return weeksBetween(fromEpoch(startEpoch), fromEpoch(endEpoch));
    }
    
    /**
     * Returns today as epoch day.
     * 
     * @return 
     */
    public static long nowEpochDay() {
        return java.time.LocalDate.now().toEpochDay();
    }
}
