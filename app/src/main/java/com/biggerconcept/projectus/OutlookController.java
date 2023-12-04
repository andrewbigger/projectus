package com.biggerconcept.projectus;

import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Outlook;
import com.biggerconcept.projectus.domain.Preferences;
import com.biggerconcept.projectus.domain.Sprint;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the epic outlook window.
 * 
 * @author Andrew Bigger
 */
public class OutlookController implements Initializable {
    /**
     * Resource bundle for outlook window
     */
    private ResourceBundle bundle;
    
    /**
     * Currently selected epic.
     */
    private Epic currentEpic;
    
    /**
     * Document preferences.
     */
    private Preferences prefs;
    
    /**
     * Outlook
     */
    private Outlook currentOutlook;
    
    /**
     * Init for the outlook window.
     * 
     * @param url URL for outlook FXML
     * @param rb application resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = rb;
    }
    
    public void setPreferences(Preferences prefs) {
        this.prefs = prefs;
    }
    
    /**
     * Sets epic for outlook.
     * 
     * @param epic selected epic
     */
    public void setEpic(Epic epic) {
        currentEpic = epic;
        
        if (currentOutlook == null) {
            currentOutlook = currentEpic.getOutlook();
        }
        
        currentOutlook.setEpic(currentEpic);
        currentOutlook.setPrefs(prefs);
        currentOutlook.calculate();
        
        mapOutlookToWindow();
    }

    /**
     * Epic name label
     */
    @FXML
    private Label nameLabel;
    
    /**
     * Buffer points text field
     */
    @FXML
    private TextField bufferTextBox;
    
    /**
     * Estimate value label
     */
    @FXML
    private Label estimateValueLabel;
    
    /**
     * Estimate buffer value
     */
    @FXML
    private Label estimateBufferValueLabel;
    
    /**
     * O + 3 points label
     */
    @FXML
    private Label oPlusThreePointsLabel;
         
    /**
     * O + 3 sprints label
     */
    @FXML
    private Label oPlusThreeSprintsLabel;
         
    /**
     * O + 3 weeks label
     */
    @FXML
    private Label oPlusThreeWeeksLabel;
    
    /**
     * O + 2 points label
     */
    @FXML
    private Label oPlusTwoPointsLabel;
    
    /**
     * O + 2 sprints label
     */
    @FXML
    private Label oPlusTwoSprintsLabel;

    /**
     * O + 2 weeks label
     */
    @FXML
    private Label oPlusTwoWeeksLabel;
            
    /**
     * O + 1 points label
     */
    @FXML
    private Label oPlusOnePointsLabel;
          
    /**
     * O + 1 sprints label
     */
    @FXML
    private Label oPlusOneSprintsLabel;
          
    /**
     * O + 3 weeks label
     */
    @FXML
    private Label oPlusOneWeeksLabel;
            
    /**
     * O points label
     */
    @FXML
    private Label oPointsLabel;
    
    /**
     * O sprints label
     */
    @FXML
    private Label oSprintsLabel;
          
    /**
     * O weeks label
     */
    @FXML
    private Label oWeeksLabel;
           
    /**
     * O - 1 points label
     */
    @FXML
    private Label oMinusOnePointsLabel;
         
    /**
     * O - 1 sprints label
     */
    @FXML
    private Label oMinusOneSprintsLabel;
            
    /**
     * O - 1 weeks label
     */
    @FXML
    private Label oMinusOneWeeksLabel;
            
    /**
     * O - 2 points label
     */
    @FXML
    private Label oMinusTwoPointsLabel;
            
    /**
     * O - 2 sprints label
     */
    @FXML
    private Label oMinusTwoSprintsLabel;
            
    /**
     * O - 2 weeks label
     */
    @FXML
    private Label oMinusTwoWeeksLabel;
            
    /**
     * O - 3 points label
     */
    @FXML
    private Label oMinusThreePointsLabel;
         
    /**
     * O - 3 sprints label
     */
    @FXML
    private Label oMinusThreeSprintsLabel;
           
    /**
     * O - 3 weeks label
     */
    @FXML
    private Label oMinusThreeWeeksLabel;
    
    /**
     * Points per sprint field
     */
    @FXML
    private TextField pointsPerSprintTextField;
    
    /**
     * Reference sprint one name field
     */
    @FXML
    private TextField pastSprintOneNameTextField;
    
    /**
     * Reference sprint one points field
     */
    @FXML
    private TextField pastSprintOnePointsTextField;
    
    /**
     * Reference sprint two name field
     */
    @FXML
    private TextField pastSprintTwoNameTextField;
    
    /**
     * Reference sprint two points field
     */
    @FXML
    private TextField pastSprintTwoPointsTextField;
    
    /**
     * Reference sprint three name field
     */
    @FXML
    private TextField pastSprintThreeNameTextField;
    
    /**
     * Reference sprint three points field
     */
    @FXML
    private TextField pastSprintThreePointsTextField;
    
    /**
     * Reference sprint four name field
     */
    @FXML
    private TextField pastSprintFourNameTextField;
    
    /**
     * Reference sprint four points field
     */
    @FXML
    private TextField pastSprintFourPointsTextField;
    
    /**
     * Average points label
     */
    @FXML
    private Label averagePointsLabel;
    
    /**
     * Completed points value label
     */
    @FXML
    private Label completedPointsLabel;
    
    /**
     * Exclude completed points checkbox
     */
    @FXML
    private CheckBox excludeCompletedPointsCheckbox;

    /**
     * Returns pointer to the outlook window.
     * 
     * @return window stage
     */
    private Stage window() {
        Stage window = (Stage) nameLabel.getScene().getWindow();
        return window;
    }
    
    /**
     * Callback to handle change
     * 
     * This will trigger a mapping call to map the window
     * to the outlook.
     * 
     * The outlook is then recalculated and presented back
     * into the window.
     */
    @FXML
    private void handleChange() {
        mapWindowToOutlook();
        
        currentOutlook.calculate(
                excludeCompletedPointsCheckbox.selectedProperty().get()
        );
        
        currentEpic.setOutlook(currentOutlook);
        mapOutlookToWindow();
    }
    
    /**
     * Maps outlook to window controls
     */
    private void mapOutlookToWindow() {
        nameLabel.setText(currentEpic.getName());

        bufferTextBox.setText(
                String.valueOf(currentOutlook.getBuffer())
        );
        
        estimateValueLabel.setText(
                String.valueOf(currentOutlook.getEstimatePoints())
        );
        
        estimateBufferValueLabel.setText(
                String.valueOf(currentOutlook.getEstimateWithBuffer())
        );
        
        mapSprintsToPanel();
        
        pointsPerSprintTextField.setText(
                String.valueOf(currentOutlook.getPointsPerSprint())
        );
        
        averagePointsLabel.setText(
                String.valueOf(currentOutlook.getAveragePoints())
        );
        
        mapOutlookToTable();
        
        pointsPerSprintTextField.getStyleClass().clear();
        pointsPerSprintTextField.getStyleClass().add(pointsClass());
        
        completedPointsLabel.setText(
                String.valueOf(currentEpic.calculateCompletePointCount(prefs))
        );
    }
    
    /**
     * Maps window controls back to outlook.
     */
    private void mapWindowToOutlook() {
        currentOutlook.setBuffer(
                Integer.valueOf(bufferTextBox.getText())
        );
        
        currentOutlook.setPointsPerSprint(
                Integer.valueOf(pointsPerSprintTextField.getText())
        );
        
        currentOutlook.setSprintOne(
                new Sprint(
                        pastSprintOneNameTextField.getText(),
                        Integer.valueOf(
                                pastSprintOnePointsTextField.getText()
                        )
                )
        );
        
        currentOutlook.setSprintTwo(
                new Sprint(
                        pastSprintTwoNameTextField.getText(),
                        Integer.valueOf(
                                pastSprintTwoPointsTextField.getText()
                        )
                )
        );
        
        currentOutlook.setSprintThree(
                new Sprint(
                        pastSprintThreeNameTextField.getText(),
                        Integer.valueOf(
                                pastSprintThreePointsTextField.getText()
                        )
                )
        );
        
        currentOutlook.setSprintFour(
                new Sprint(
                        pastSprintFourNameTextField.getText(),
                        Integer.valueOf(
                                pastSprintFourPointsTextField.getText()
                        )
                )
        );
        
    }
    
    /**
     * Maps sprint data to the sprint panel
     */
    private void mapSprintsToPanel() {
        Sprint sprintOne = currentOutlook.getSprintOne();
        Sprint sprintTwo = currentOutlook.getSprintTwo();
        Sprint sprintThree = currentOutlook.getSprintThree();
        Sprint sprintFour = currentOutlook.getSprintFour();
        
        pastSprintOneNameTextField.setText(sprintOne.getName());
        pastSprintOnePointsTextField.setText(
                String.valueOf(sprintOne.getCompletedPoints())
        );
        
        pastSprintTwoNameTextField.setText(sprintTwo.getName());
        pastSprintTwoPointsTextField.setText(
                String.valueOf(sprintTwo.getCompletedPoints())
        );
        
        pastSprintThreeNameTextField.setText(sprintThree.getName());
        pastSprintThreePointsTextField.setText(
                String.valueOf(sprintThree.getCompletedPoints())
        );
        
        pastSprintFourNameTextField.setText(sprintFour.getName());
        pastSprintFourPointsTextField.setText(
                String.valueOf(sprintFour.getCompletedPoints())
        );
    }
    
    /**
     * Maps projection data to outlook table.
     */
    private void mapOutlookToTable() {
        oPlusThreePointsLabel.setText(
                String.valueOf(currentOutlook.getOPlusThree().getPointsPerSprint())
        );
        
        oPlusThreeSprintsLabel.setText(
                String.valueOf(currentOutlook.getOPlusThree().getSprints())
        );
        
        oPlusThreeWeeksLabel.setText(
                String.valueOf(currentOutlook.getOPlusThree().getWeeks())
        );
        
        oPlusTwoPointsLabel.setText(
                String.valueOf(currentOutlook.getOPlusTwo().getPointsPerSprint())
        );
        
        oPlusTwoSprintsLabel.setText(
                String.valueOf(currentOutlook.getOPlusTwo().getSprints())
        );
        
        oPlusTwoWeeksLabel.setText(
                String.valueOf(currentOutlook.getOPlusTwo().getWeeks())
        );
        
        oPlusOnePointsLabel.setText(
                String.valueOf(currentOutlook.getOPlusOne().getPointsPerSprint())
        );
        
        oPlusOneSprintsLabel.setText(
                String.valueOf(currentOutlook.getOPlusOne().getSprints())
        );
        
        oPlusOneWeeksLabel.setText(
                String.valueOf(currentOutlook.getOPlusOne().getWeeks())
        );
        
        oPointsLabel.setText(
                String.valueOf(currentOutlook.getO().getPointsPerSprint())
        );
        
        oSprintsLabel.setText(
                String.valueOf(currentOutlook.getO().getSprints())
        );
        
        oWeeksLabel.setText(
                String.valueOf(currentOutlook.getO().getWeeks())
        );
        
        oMinusOnePointsLabel.setText(
                String.valueOf(currentOutlook.getOMinusOne().getPointsPerSprint())
        );
        
        oMinusOneSprintsLabel.setText(
                String.valueOf(currentOutlook.getOMinusOne().getSprints())
        );
        
        oMinusOneWeeksLabel.setText(
                String.valueOf(currentOutlook.getOMinusOne().getWeeks())
        );
        
        oMinusTwoPointsLabel.setText(
                String.valueOf(currentOutlook.getOMinusTwo().getPointsPerSprint())
        );
        
        oMinusTwoSprintsLabel.setText(
                String.valueOf(currentOutlook.getOMinusTwo().getSprints())
        );
        
        oMinusTwoWeeksLabel.setText(
                String.valueOf(currentOutlook.getOMinusTwo().getWeeks())
        );
        
        oMinusThreePointsLabel.setText(
                String.valueOf(currentOutlook.getOMinusThree().getPointsPerSprint())
        );
        
        oMinusThreeSprintsLabel.setText(
                String.valueOf(currentOutlook.getOMinusThree().getSprints())
        );
        
        oMinusThreeWeeksLabel.setText(
                String.valueOf(currentOutlook.getOMinusThree().getWeeks())
        );
    }
    
    /**
     * Determines display class for points field.
     * 
     * @return css class for points field
     */
    private String pointsClass() {
        Integer pointsPerSprint = Integer.valueOf(
                pointsPerSprintTextField.getText()
        );
        
        if (isOptimistic(pointsPerSprint, currentOutlook)) {
            return "bg-red";
        } else if (isPessimistic(pointsPerSprint, currentOutlook)) {
            return "bg-green";
        }
        
        return "bg-amber";
    }
    
    /**
     * Determines whether points is optimistic to the outlook.
     * 
     * @param pointsPerSprint entered number of points per sprint.
     * @param outlook current epic outlook
     * @return whether the points are optimistic
     */
    private boolean isOptimistic(Integer pointsPerSprint, Outlook outlook) {
        return pointsPerSprint > outlook.getEstimatePoints();
    }
    
    /**
     * Determines whether points is pessimistic to the outlook.
     * 
     * @param pointsPerSprint entered number of points per sprint.
     * @param outlook current epic outlook
     * @return whether the points are pessimistic
     */
    private boolean isPessimistic(Integer pointsPerSprint, Outlook outlook) {
        return pointsPerSprint < (
                outlook.getEstimateWithBuffer() - (outlook.deviation() * 2)
        );
    }
}
