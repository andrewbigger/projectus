package com.biggerconcept.projectus.reports;

import com.biggerconcept.projectus.State;

/**
 * Resolves timeline specific variables
 * 
 * @author Andrew Bigger
 */

public class Variables {
    /**
     * Retrieves velocity (average points per sprint) from the
     * open document for use as a report variable.
     * 
     * If the velocity is unable to be calculated, a blank string will
     * be returned.
     * 
     * @param state application state
     * 
     * @return velocity as a string
     */
//    public static String velocity(State state) {
//        try {
//            return String.valueOf(
//                state
//                    .getOpenDocument()
//                    .getPreferences()
//                    .calculateAveragePointsPerSprint()
//            );
//        } catch (Exception ex) {
//            return "";
//        }
//    }
}
