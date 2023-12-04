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

    @FXML
    private Label nameLabel;
    
    @FXML
    private TextField bufferTextBox;
    
    @FXML
    private Label estimateValueLabel;
    
    @FXML
    private Label estimateBufferValueLabel;
    
    @FXML
    private Label oPlusThreePointsLabel;
            
    @FXML
    private Label oPlusThreeSprintsLabel;
            
    @FXML
    private Label oPlusThreeWeeksLabel;
            
    @FXML
    private Label oPlusTwoPointsLabel;
    
    @FXML
    private Label oPlusTwoSprintsLabel;

    @FXML
    private Label oPlusTwoWeeksLabel;
            
    @FXML
    private Label oPlusOnePointsLabel;
            
    @FXML
    private Label oPlusOneSprintsLabel;
            
    @FXML
    private Label oPlusOneWeeksLabel;
            
    @FXML
    private Label oPointsLabel;
            
    @FXML
    private Label oSprintsLabel;
            
    @FXML
    private Label oWeeksLabel;
            
    @FXML
    private Label oMinusOnePointsLabel;
            
    @FXML
    private Label oMinusOneSprintsLabel;
            
    @FXML
    private Label oMinusOneWeeksLabel;
            
    @FXML
    private Label oMinusTwoPointsLabel;
            
    @FXML
    private Label oMinusTwoSprintsLabel;
            
    @FXML
    private Label oMinusTwoWeeksLabel;
            
    @FXML
    private Label oMinusThreePointsLabel;
            
    @FXML
    private Label oMinusThreeSprintsLabel;
           
    @FXML
    private Label oMinusThreeWeeksLabel;
    
    @FXML
    private TextField pointsPerSprintTextField;
    
    @FXML
    private TextField pastSprintOneNameTextField;
    
    @FXML
    private TextField pastSprintOnePointsTextField;
    
    @FXML
    private TextField pastSprintTwoNameTextField;
    
    @FXML
    private TextField pastSprintTwoPointsTextField;
    
    @FXML
    private TextField pastSprintThreeNameTextField;
    
    @FXML
    private TextField pastSprintThreePointsTextField;
    
    @FXML
    private TextField pastSprintFourNameTextField;
    
    @FXML
    private TextField pastSprintFourPointsTextField;
    
    @FXML
    private Label averagePointsLabel;
    
    @FXML
    private Label completedPointsLabel;
    
    @FXML
    private CheckBox excludeCompletedPointsCheckbox;

    private Stage window() {
        Stage window = (Stage) nameLabel.getScene().getWindow();
        return window;
    }
    
    @FXML
    private void handleChange() {
        mapWindowToOutlook();
        
        currentOutlook.calculate(
                excludeCompletedPointsCheckbox.selectedProperty().get()
        );
        
        currentEpic.setOutlook(currentOutlook);
        mapOutlookToWindow();
    }
    
    
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
    
    private boolean isOptimistic(Integer pointsPerSprint, Outlook outlook) {
        return pointsPerSprint > outlook.getEstimatePoints();
    }
    
    private boolean isPessimistic(Integer pointsPerSprint, Outlook outlook) {
        return pointsPerSprint < (
                outlook.getEstimateWithBuffer() - (outlook.deviation() * 2)
        );
    }
}
