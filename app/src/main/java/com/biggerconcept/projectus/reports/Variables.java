package com.biggerconcept.projectus.reports;

import com.biggerconcept.projectus.State;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Outlook;
import com.biggerconcept.projectus.domain.Preferences;
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
    
    /**
     * Returns start epic number as a variable
     * 
     * @param state application state
     * 
     * @return start epic number
     */
    public static String startEpicNumber(State state) {
        try {
            return String.valueOf(
                    state
                            .getOpenDocument()
                            .getPreferences()
                            .getEpicStartNumber()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns extra small task size number as a variable
     * 
     * @param state application state
     * 
     * @return extra small task size
     */
    public static String xsTaskSize(State state) {
        try {
            return String.valueOf(
                    state
                            .getOpenDocument()
                            .getPreferences()
                            .getExtraSmallTaskSize()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns small task size number as a variable
     * 
     * @param state application state
     * 
     * @return small task size
     */
    public static String sTaskSize(State state) {
        try {
            return String.valueOf(
                    state
                            .getOpenDocument()
                            .getPreferences()
                            .getSmallTaskSize()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns medium task size number as a variable
     * 
     * @param state application state
     * 
     * @return medium task size
     */
    public static String mTaskSize(State state) {
        try {
            return String.valueOf(
                    state
                            .getOpenDocument()
                            .getPreferences()
                            .getMediumTaskSize()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns large task size number as a variable
     * 
     * @param state application state
     * 
     * @return large task size
     */
    public static String lTaskSize(State state) {
        try {
            return String.valueOf(
                    state
                            .getOpenDocument()
                            .getPreferences()
                            .getLargeTaskSize()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns extra large task size number as a variable
     * 
     * @param state application state
     * 
     * @return extra large task size
     */
    public static String xlTaskSize(State state) {
        try {
            return String.valueOf(
                    state
                            .getOpenDocument()
                            .getPreferences()
                            .getExtraLargeTaskSize()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns sprint length number as a variable
     * 
     * @param state application state
     * 
     * @return sprint length
     */
    public static String sprintLength(State state) {
        try {
            return String.valueOf(
                    state
                            .getOpenDocument()
                            .getPreferences()
                            .getSprintLength()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns estimate buffer as a variable
     * 
     * @param state application state
     * 
     * @return estimate buffer
     */
    public static String estimateBuffer(State state) {
        try {
            return String.valueOf(
                    state
                            .getOpenDocument()
                            .getPreferences()
                            .getSprintLength()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns epic count as a variable
     * 
     * @param state application state
     * 
     * @return epic count
     */
    public static String epicCount(State state) {
        try {
            return String.valueOf(
                    state
                        .getOpenDocument()
                        .getEpics()
                        .size()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns selected epic name as string for use as a variable
     * 
     * @param state application statue
     * 
     * @return selected epic name
     */
    public static String selectedEpicName(State state) {
        try {
            return state.getOpenEpic().getName();
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns selected epic identifier as string for use as a variable
     * 
     * @param state application statue
     * 
     * @return selected epic identifier
     */
    public static String selectedEpicIdentifier(State state) {
        try {
            return String.valueOf(state.getOpenEpic().getIdentifier());
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns selected epic sized card count as string for use as a variable
     * 
     * @param state application statue
     * 
     * @return selected epic sized card count
     */
    public static String selectedEpicSizedTaskCount(State state) {
        try {
            return String.valueOf(
                    state
                        .getOpenEpic()
                        .calculateSizedCount()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns selected epic task count as string for use as a variable
     * 
     * @param state application statue
     * 
     * @return selected epic task count
     */
    public static String selectedEpicTaskCount(State state) {
        try {
            return String.valueOf(
                    state
                        .getOpenEpic()
                        .getTasks()
                        .size()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns selected epic complete task count as string for use as a variable
     * 
     * @param state application statue
     * 
     * @return selected epic complete task count
     */
    public static String selectedEpicCompleteTaskCount(State state) {
        try {
            return String.valueOf(
                    state
                        .getOpenEpic()
                        .calculateCompleteCount()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns selected epic total points as string for use as a variable
     * 
     * @param state application statue
     * 
     * @return selected epic total points count
     */
    public static String selectedEpicTotalPoints(State state) {
        try {
            return String.valueOf(
                    state
                        .getOpenEpic()
                        .getSize(state.getOpenDocument().getPreferences())
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns selected epic total points as string for use as a variable
     * 
     * @param state application statue
     * 
     * @return selected epic total points count
     */
    public static String selectedEpicCompletePoints(State state) {
        try {
            return String.valueOf(
                    state
                        .getOpenEpic()
                        .calculateCompletePointCount(
                                state.getOpenDocument().getPreferences()
                        )
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns selected epic estimate points as string for use as a variable
     * 
     * @param state application statue
     * 
     * @return selected epic estimate points count
     */
    public static String selectedEpicEstimatePoints(State state) {
        try {
            return String.valueOf(
                    state
                        .getOpenEpic()
                        .getSize(state.getOpenDocument().getPreferences())
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns selected epic status as string for use as a variable
     * 
     * @param state application statue
     * 
     * @return selected epic status
     */
    public static String selectedEpicStatus(State state) {
        try {
            return String.valueOf(
                    state
                        .getOpenEpic()
                        .calculateStatus()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns selected epic risk count as string for use as a variable
     * 
     * @param state application statue
     * 
     * @return selected epic risk count
     */
    public static String selectedEpicRiskCount(State state) {
        try {
            return String.valueOf(
                    state
                        .getOpenEpic()
                        .getRisks()
                        .size()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns selected epic risk count as string for use as a variable
     * 
     * @param state application statue
     * 
     * @return selected epic story count
     */
    public static String selectedEpicStoryCount(State state) {
        try {
            return String.valueOf(
                    state
                        .getOpenEpic()
                        .getStories()
                        .size()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns selected epic outlook buffer as string for use as a variable
     * 
     * @param state application statue
     * 
     * @return selected epic outlook buffer points
     */
    public static String selectedEpicOutlookBuffer(State state) {
        try {
            Preferences prefs = state.getOpenDocument().getPreferences();
            Epic openEpic = state.getOpenEpic();
            
            Outlook outlook = state
                        .getOpenEpic()
                        .getOutlook();
            
            outlook.calculate(prefs, openEpic, false);
            
            return String.valueOf(outlook.getBuffer());
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns selected epic outlook buffer excluding completed points as 
     * string for use as a variable
     * 
     * @param state application statue
     * 
     * @return selected epic outlook buffer points
     */
    public static String selectedEpicOutlookBufferExclCompletedPoints(
            State state
    ) {
        try {
            Preferences prefs = state.getOpenDocument().getPreferences();
            Epic openEpic = state.getOpenEpic();
            
            Outlook outlook = state
                        .getOpenEpic()
                        .getOutlook();
            
            outlook.calculate(prefs, openEpic, true);
            
            return String.valueOf(outlook.getBuffer());
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns selected epic outlook estimated points including buffer points as 
     * string for use as a variable
     * 
     * @param state application statue
     * 
     * @return selected epic outlook buffer points
     */
    public static String selectedEpicOutlookEstimatePointsWithBuffer(
            State state
    ) {
        try {
            Preferences prefs = state.getOpenDocument().getPreferences();
            Epic openEpic = state.getOpenEpic();
            
            Outlook outlook = state
                        .getOpenEpic()
                        .getOutlook();
            
            outlook.calculate(prefs, openEpic, true);
            
            return String.valueOf(outlook.getEstimateWithBuffer());
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns points per sprint for outlook projection.
     * 
     * @param state application statue
     * @param deviation projection deviation
     * @param exclCompletedPoints exclude completed points
     * 
     * @return points per sprint for given deviation
     */
    public static String selectedEpicOutlookPointsPerSprint(
            State state,
            int deviation,
            boolean exclCompletedPoints
    ) {
        try {
            Preferences prefs = state.getOpenDocument().getPreferences();
            Epic openEpic = state.getOpenEpic();
            
            Outlook outlook = state
                        .getOpenEpic()
                        .getOutlook();
            
            outlook.calculate(prefs, openEpic, exclCompletedPoints);
            
            return String.valueOf(
                    outlook.findProjection(deviation).getPointsPerSprint()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns sprints for outlook projection.
     * 
     * @param state application statue
     * @param deviation projection deviation
     * @param exclCompletedPoints exclude completed points
     * 
     * @return sprints for given deviation
     */
    public static String selectedEpicOutlookSprints(
            State state,
            int deviation,
            boolean exclCompletedPoints
    ) {
        try {
            Preferences prefs = state.getOpenDocument().getPreferences();
            Epic openEpic = state.getOpenEpic();
            
            Outlook outlook = state
                        .getOpenEpic()
                        .getOutlook();
            
            outlook.calculate(prefs, openEpic, exclCompletedPoints);
            
            return String.valueOf(
                    outlook.findProjection(deviation).getSprints()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns weeks for outlook projection.
     * 
     * @param state application statue
     * @param deviation projection deviation
     * @param exclCompletedPoints exclude completed points
     * 
     * @return sprints for given deviation
     */
    public static String selectedEpicOutlookWeeks(
            State state,
            int deviation,
            boolean exclCompletedPoints
    ) {
        try {
            Preferences prefs = state.getOpenDocument().getPreferences();
            Epic openEpic = state.getOpenEpic();
            
            Outlook outlook = state
                        .getOpenEpic()
                        .getOutlook();
            
            outlook.calculate(prefs, openEpic, exclCompletedPoints);
            
            return String.valueOf(
                    outlook.findProjection(deviation).getWeeks()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
}
