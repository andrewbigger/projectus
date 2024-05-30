package com.biggerconcept.projectus.reports;

import com.biggerconcept.projectus.State;
import com.biggerconcept.projectus.domain.Status;
import com.biggerconcept.projectus.domain.Status.Tracking;
import com.biggerconcept.projectus.helpers.Date;
import java.time.LocalDate;

/**
 * Resolves timeline specific variables
 * 
 * @author Andrew Bigger
 */

public class Variables {
    /**
     * Returns project name as a title
     * 
     * @param state application state
     * 
     * @return project name
     */
    public static String projectName(State state) {
        try {
            return state.getOpenDocument().getTitle();
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns project start date as a variable.
     * 
     * @param state application state
     * 
     * @return start date
     */
    public static String projectStartDate(State state) {
        try {
            LocalDate start = Date.fromEpoch(state.getOpenDocument().getStart());
            return start.toString();
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns project end date as a variable.
     * 
     * @param state application state
     * 
     * @return end date
     */
    public static String projectEndDate(State state) {
        try {
            LocalDate start = Date.fromEpoch(state.getOpenDocument().getStart());
            return start.toString();
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns weeks elapsed as a variable.
     * 
     * @param state application state
     * 
     * @return weeks elapsed
     */
    public static String weeksElapsed(State state) {
        try {
            Status status = state.getOpenDocument().status();
            
            if (status.hasStarted() == true) {
                return String.valueOf(status.weeksElapsed());
            } else {
                return "0";
            }
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns total number of weeks as a variable.
     * 
     * @param state application state
     * 
     * @return total weeks
     */
    public static String weeksTotal(State state) {
        try {
            Status status = state.getOpenDocument().status();
            
            if (status.hasStarted() == true) {
                return String.valueOf(status.totalWeeks());
            } else {
                return "0";
            }
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns number of weeks remaining as a variable.
     * 
     * @param state application state
     * 
     * @return weeks remaining
     */
    public static String weeksRemaining(State state) {
        try {
            Status status = state.getOpenDocument().status();
            
            long remaining = status.totalWeeks() - status.weeksElapsed();
            
            return String.valueOf(remaining);
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns sprints elapsed as a variable.
     * 
     * @param state application state
     * 
     * @return sprints elapsed
     */
    public static String sprintsElapsed(State state) {
        try {
            Status status = state.getOpenDocument().status();
            
            if (status.hasStarted() == true) {
                return String.valueOf(status.sprintsElapsed());
            } else {
                return "0";
            }
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns total number of sprints as a variable.
     * 
     * @param state application state
     * 
     * @return total sprints
     */
    public static String sprintsTotal(State state) {
        try {
            Status status = state.getOpenDocument().status();
            
            if (status.hasStarted() == true) {
                return String.valueOf(status.totalSprints());
            } else {
                return "0";
            }
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns number of sprints remaining as a variable.
     * 
     * @param state application state
     * 
     * @return sprints remaining
     */
    public static String sprintsRemaining(State state) {
        try {
            Status status = state.getOpenDocument().status();
            
            long remaining = status.totalSprints() - status.sprintsElapsed();
            
            return String.valueOf(remaining);
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns points completed as a variable.
     * 
     * @param state application state
     * 
     * @return points elapsed
     */
    public static String pointsCompleted(State state) {
        try {
            Status status = state.getOpenDocument().status();
            
            if (status.hasStarted() == true) {
                return String.valueOf(status.completedPoints());
            } else {
                return "0";
            }
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns total number of points as a variable.
     * 
     * @param state application state
     * 
     * @return total points
     */
    public static String pointsTotal(State state) {
        try {
            Status status = state.getOpenDocument().status();
            
            if (status.hasStarted() == true) {
                return String.valueOf(status.totalPoints());
            } else {
                return "0";
            }
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns number of points remaining as a variable.
     * 
     * @param state application state
     * 
     * @return points remaining
     */
    public static String pointsRemaining(State state) {
        try {
            Status status = state.getOpenDocument().status();
            
            long remaining = status.completedPoints() - status.totalPoints();
            
            return String.valueOf(remaining);
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns average number of points per sprint as a variable.
     * 
     * @param state application state
     * 
     * @return points remaining
     */
    public static String velocity(State state) {
        try {
            Status status = state.getOpenDocument().status();
            
            return String.valueOf(status.pointsPerSprint());
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns project tracking status as a variable
     * 
     * @param state application state
     * 
     * @return tracking status
     */
    public static String trackingStatus(State state) {
        Status status = state.getOpenDocument().status();
            
            if (status.hasStarted() == true) {
                return String.valueOf(status.summary());
            } else {
                return Tracking.ON_TRACK.toString();
            }
    }
    
    /**
     * Returns points committed as a variable.
     * 
     * @param state application state
     * 
     * @return points committed
     */
    public static String committedPoints(State state) {
        try {
            Status status = state.getOpenDocument().status();
            
            if (status.hasStarted() == true) {
                return String.valueOf(status.totalPoints());
            } else {
                return "0";
            }
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns total number of available points as a variable.
     * 
     * @param state application state
     * 
     * @return total points
     */
    public static String availablePoints(State state) {
        try {
            Status status = state.getOpenDocument().status();
            
            if (status.hasStarted() == true) {
                return String.valueOf(status.availablePoints());
            } else {
                return "0";
            }
        } catch (Exception ex) {
            return "";
        }
    }
    
}
