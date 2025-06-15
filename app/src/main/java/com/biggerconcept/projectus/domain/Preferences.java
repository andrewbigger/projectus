package com.biggerconcept.projectus.domain;

import com.biggerconcept.projectus.App;
import com.biggerconcept.sdk.reports.IReport;
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
import com.biggerconcept.projectus.reports.Report;
import com.biggerconcept.sdk.preferences.Config;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

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
    
    @JsonInclude(Include.NON_NULL)
    @JsonDeserialize(contentAs=Report.class)
    private ArrayList<IReport> reports;
    
    /**
     * Application settings
     */
    @JsonIgnore
    private Config applicationSettings;
    
    /**
     * Default epic start number.
     */
    private static final int DEFAULT_EPIC_START_NUMBER = 1;
    
    /**
     * Default sprint size number.
     */
    private static final int DEFAULT_SPRINT_LENGTH = 2;
    
    /**
     * Example sprint for reference sprint one
     */
    private static final Sprint DEFAULT_REF_SPRINT_ONE = new Sprint("1", 20);
    
    /**
     * Example sprint for reference sprint two
     */
    private static final Sprint DEFAULT_REF_SPRINT_TWO = new Sprint("2", 18);
    
    /**
     * Example sprint for reference sprint three
     */
    private static final Sprint DEFAULT_REF_SPRINT_THREE = new Sprint("3", 22);
    
    /**
     * Example sprint for reference sprint four
     */
    private static final Sprint DEFAULT_REF_SPRINT_FOUR = new Sprint("4", 12);
    
    /**
     * Default estimate buffer
     */
    private static final int DEFAULT_ESTIMATE_BUFFER = 10;
    
    /**
     * Reference sprint one
     */
    @JsonInclude(Include.NON_NULL)
    private Sprint refSprintOne;
    
    /**
     * Reference sprint two
     */
    @JsonInclude(Include.NON_NULL)
    private Sprint refSprintTwo;
    
    /**
     * Reference sprint three
     */
    @JsonInclude(Include.NON_NULL)
    private Sprint refSprintThree;
    
    /**
     * Reference sprint four
     */
    @JsonInclude(Include.NON_NULL)
    private Sprint refSprintFour;
    
    /**
     * Estimate buffer
     */
    @JsonInclude(Include.NON_NULL)
    private Integer estimateBuffer;
    
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
        p.refSprintOne = DEFAULT_REF_SPRINT_ONE;
        p.refSprintTwo = DEFAULT_REF_SPRINT_TWO;
        p.refSprintThree = DEFAULT_REF_SPRINT_THREE;
        p.refSprintFour = DEFAULT_REF_SPRINT_FOUR;
        p.estimateBuffer = DEFAULT_ESTIMATE_BUFFER;
        
        return p;
    }
    
    /**
     * Default constructor.
     */
    public Preferences() {
        this.applicationSettings = App.config();
    }
    
    /**
     * Getter for application settings
     * 
     * @return application settings
     */
    public Config getApplicationSettings() {
        if (applicationSettings == null) {
            applicationSettings = App.config();
        }
        
        return applicationSettings;
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
     * Getter for reference sprint one.
     * 
     * @return reference sprint
     */
    public Sprint getRefSprintOne() {
        if (refSprintOne == null) {
            return DEFAULT_REF_SPRINT_ONE;
        }
        
        return refSprintOne;
    }
    
    /**
     * Getter for reference sprint two.
     * 
     * @return reference sprint
     */
    public Sprint getRefSprintTwo() {
        if (refSprintTwo == null) {
            return DEFAULT_REF_SPRINT_TWO;
        }
        
        return refSprintTwo;
    }
    
    /**
     * Getter for reference sprint three.
     * 
     * @return reference sprint
     */
    public Sprint getRefSprintThree() {
        if (refSprintThree == null) {
            return DEFAULT_REF_SPRINT_THREE;
        }
        
        return refSprintThree;
    }
    
    /**
     * Getter for reference sprint four.
     * 
     * @return reference sprint
     */
    public Sprint getRefSprintFour() {
        if (refSprintFour == null) {
            return DEFAULT_REF_SPRINT_FOUR;
        }
        
        return refSprintFour;
    }
    
    /**
     * Getter for estimate buffer.
     * 
     * @return estimate buffer
     */
    public int getEstimateBuffer() {
        if (estimateBuffer == null) {
            return DEFAULT_ESTIMATE_BUFFER;
        }
        
        return estimateBuffer;
    }
    
    /**
     * Getter for reports
     * 
     * @return reports
     */
    public ArrayList<IReport> getReports() {
        if (reports == null) {
            reports = new ArrayList<>();
        }
        
        return reports;
    }
    
    /**
     * Returns total number of completed points in reference
     * sprints.
     * 
     * @return points
     */
    public int countDeliveredPoints() {
        return getRefSprintOne().getCompletedPoints() +
                getRefSprintTwo().getCompletedPoints() +
                getRefSprintThree().getCompletedPoints() +
                getRefSprintFour().getCompletedPoints();
    }
    
    /**
     * Returns average number of completed points in reference
     * sprints.
     * 
     * @return average points
     */
    public int averageDeliveredPoints() {
        return countDeliveredPoints() / 4;
    }
    
    /**
     * Returns number of available points in specified number
     * of sprints
     * 
     * @param sprints sprints to count for
     * @return number of available points.
     */
    public int calculateAvailablePointsInSprints(int sprints) {
        return averageDeliveredPoints() * sprints;
    }
    
    /**
     * Returns number of available points in a sprint.
     * 
     * @return points per sprint
     */
    public int calculateAvailablePointsPerSprint() {
        return calculateAvailablePointsInSprints(1);
    }
    
    /**
     * Setter for application settings
     * 
     * @return application settings
     */
    public Config setApplicationSettings() {
        return applicationSettings;
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
     * @param value sprint length value
     */
    public void setSprintLength(String value) {
        setSprintLength(
                Integer.parseInt(value)
        );
    }
    
    /**
     * Setter for reference sprint one.
     * 
     * @param value new sprint
     */
    public void setRefSprintOne(Sprint value) {
        refSprintOne = value;
    }
    
    /**
     * Setter for reference sprint two.
     * 
     * @param value new sprint
     */
    public void setRefSprintTwo(Sprint value) {
        refSprintTwo = value;
    }
    
    /**
     * Setter for reference sprint three.
     * 
     * @param value new sprint
     */
    public void setRefSprintThree(Sprint value) {
        refSprintThree = value;
    }
    
    /**
     * Setter for reference sprint four.
     * 
     * @param value new sprint
     */
    public void setRefSprintFour(Sprint value) {
        refSprintFour = value;
    }
    
    /**
     * Setter for estimate buffer
     * 
     * @param value new estimate buffer
     */
    public void setEstimateBuffer(int value) {
        estimateBuffer = value;
    }
    
    /**
     * Setter for reports
     * 
     * @param value new reports array
     */
    public void setReports(ArrayList<IReport> value) {
        reports = value;
    }

    /**
     * Returns default template
     * 
     * @return default template
     * 
     * @throws IOException when unable to read default template
     */
    public XWPFDocument defaultTemplate() throws IOException {
        InputStream template = getClass()
                .getResourceAsStream("/docs/docx.dotx");
        
        return new XWPFDocument(
                template
        );
    }
}
