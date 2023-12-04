package com.biggerconcept.projectus;

import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.projectus.domain.Preferences;
import com.biggerconcept.appengine.ui.dialogs.ErrorAlert;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Sprint;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

/**
 * Controller for the preferences window.
 * 
 * @author Andrew Bigger
 */
public class PreferencesController implements Initializable {
    /**
     * Resource bundle for preferences window.
     */
    private ResourceBundle bundle;
    
    /**
     * Document domain model.
     */
    private Document currentDocument;
    
    /**
     * Document preferences
     */
    private Preferences currentPreferences;

    /**
     * Initialize-er for the preference window
     * 
     * @param url URL for preferences FXML
     * @param rb application resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = rb;

        applyTooltips();
        
    }
    
    /**
     * Applies tool tips to the controls of the preferences window.
     */
    private void applyTooltips() {
        cancelPreferencesButton.setTooltip(
            new Tooltip(bundle.getString(
                    "dialogs.preferences.actions.cancel.tooltip"
                )
            )
        );
        savePreferencesButton.setTooltip(
            new Tooltip(bundle.getString(
                    "dialogs.preferences.actions.save.tooltip"
                )
            )
        );
    }
    
    /**
     * Sets document pointer for the preference window.
     * 
     * @param doc open document
     */
    public void setDocument(Document doc) {
        this.currentDocument = doc;
        this.currentPreferences = currentDocument.getPreferences();
        mapPreferencesToWindow();
    }
    
    /**
     * Epic start number text field.
     */
    @FXML
    public TextField epicStartNumberTextField;
    
    /**
     * Extra small size text field.
     */
    @FXML
    public TextField extraSmallSizeTextField;
    
    /**
     * Small size text field.
     */
    @FXML
    public TextField smallSizeTextField;
    
    /**
     * Medium size text field.
     */
    @FXML
    public TextField mediumSizeTextField;
    
    /**
     * Large size text field.
     */
    @FXML
    public TextField largeSizeTextField;
    
    /**
     * Extra large size text field.
     */
    @FXML
    public TextField extraLargeSizeTextField;
    
    /**
     * Sprint size text field.
     */
    @FXML
    public TextField sprintSizeTextField;
    
    /**
     * Cancel preferences button.
     */
    @FXML
    public Button cancelPreferencesButton;

    /**
     * Save changes button.
     */
    @FXML
    public Button savePreferencesButton;
    
    /**
     * Reference sprint 1 name text field
     */
    @FXML
    public TextField refSprintOneNameTextField;
    
    /**
     * Reference sprint 1 points text field
     */
    @FXML
    public TextField refSprintOneCompletedPointsTextField;
    
    /**
     * Reference sprint 2 name text field
     */
    @FXML
    public TextField refSprintTwoNameTextField;
    
    /**
     * Reference sprint 2 points text field
     */
    @FXML
    public TextField refSprintTwoCompletedPointsTextField;
    
    /**
     * Reference sprint 3 name text field
     */
    @FXML
    public TextField refSprintThreeNameTextField;
    
    /**
     * Reference sprint 3 points text field
     */
    @FXML
    public TextField refSprintThreeCompletedPointsTextField;
    
    /**
     * Reference sprint 4 name text field
     */
    @FXML
    public TextField refSprintFourNameTextField;
    
    /**
     * Reference sprint 4 points text field
     */
    @FXML
    public TextField refSprintFourCompletedPointsTextField;
    
    /**
     * Estimate buffer setting text field
     */
    @FXML
    public TextField estimateBufferTextField;
    
    /**
     * Returns the preference window stage.
     * 
     * The pref window stage is the window that the save button control is
     * in. 
     * 
     * @return controller window
     */
    private Stage preferenceStage() {
        Stage stage = (Stage) savePreferencesButton.getScene().getWindow();
        return stage;
    }
    
    /**
     * Maps given document to window.
     * 
     * @param doc open document
     */
    private void mapPreferencesToWindow() {
        mapEpicPreferencesToWindow();
        mapSprintPreferencesToWindow();
        mapEstimatePreferencesToWindow();
    }
    
    /**
     * Maps epic size preferences to window.
     */
    private void mapEpicPreferencesToWindow() {
        epicStartNumberTextField.setText(
                String.valueOf(currentPreferences.getEpicStartNumber())
        );
        extraSmallSizeTextField.setText(
                String.valueOf(currentPreferences.getExtraSmallTaskSize())
        );
        smallSizeTextField.setText(
                String.valueOf(currentPreferences.getSmallTaskSize())
        );
        mediumSizeTextField.setText(
                String.valueOf(currentPreferences.getMediumTaskSize())
        );
        largeSizeTextField.setText(
                String.valueOf(currentPreferences.getLargeTaskSize())
        );
        extraLargeSizeTextField.setText(
                String.valueOf(currentPreferences.getExtraLargeTaskSize())
        );
    }
    
    /**
     * Maps sprint preferences to window
     */
    private void mapSprintPreferencesToWindow() {
        sprintSizeTextField.setText(
                String.valueOf(currentPreferences.getSprintLength())
        );
    }
    
    /**
     * Maps estimate and reference sprints to window.
     */
    private void mapEstimatePreferencesToWindow() {
        estimateBufferTextField.setText(
                String.valueOf(currentPreferences.getEstimateBuffer())
        );
        
        mapReferenceSprintsToWindow();
    }
    
    /**
     * Maps reference sprints to window.
     */
    private void mapReferenceSprintsToWindow() {
        refSprintOneNameTextField.setText(
                currentPreferences.getRefSprintOne().getName()
        );
        refSprintOneCompletedPointsTextField.setText(
                String.valueOf(
                        currentPreferences
                                .getRefSprintOne()
                                .getCompletedPoints()
                )
        );
        refSprintTwoNameTextField.setText(
                currentPreferences.getRefSprintTwo().getName()
        );
        refSprintTwoCompletedPointsTextField.setText(
                String.valueOf(
                        currentPreferences
                                .getRefSprintTwo()
                                .getCompletedPoints()
                )
        );
        refSprintThreeNameTextField.setText(
                currentPreferences.getRefSprintThree().getName()
        );
        refSprintThreeCompletedPointsTextField.setText(
                String.valueOf(
                        currentPreferences
                                .getRefSprintThree()
                                .getCompletedPoints()
                )
        );
        refSprintFourNameTextField.setText(
                currentPreferences.getRefSprintFour().getName()
        );
        refSprintFourCompletedPointsTextField.setText(
                String.valueOf(
                        currentPreferences
                            .getRefSprintFour()
                            .getCompletedPoints()
                )
        );
    }
    
    /**
     * Maps window content to new document object for serialization.
     * 
     * @return built preference object
     */
    private void mapWindowToPreferences() {
        mapWindowToEpicPreferences();
        mapWindowToSprintPreferences();
        mapWindowToEstimatePreferences();
    }
    
    /**
     * Maps window to epic preferences.
     */
     private void mapWindowToEpicPreferences() {
        currentPreferences.setEpicStartNumber(
                epicStartNumberTextField.getText()
        );
        currentPreferences.setExtraSmallSize(extraSmallSizeTextField.getText());
        currentPreferences.setSmallSize(smallSizeTextField.getText());
        currentPreferences.setMediumSize(mediumSizeTextField.getText());
        currentPreferences.setLargeSize(largeSizeTextField.getText());
        currentPreferences.setExtraLargeSize(extraLargeSizeTextField.getText());
    }
    
     /**
      * Maps window to sprint preferences
      */
    private void mapWindowToSprintPreferences() {
        currentPreferences.setSprintLength(sprintSizeTextField.getText());
    }
    
    /**
     * Maps window to estimate preferences.
     */
    private void mapWindowToEstimatePreferences() {
        currentPreferences.setEstimateBuffer(
                Integer.valueOf(estimateBufferTextField.getText())
        );
        
        mapWindowToReferenceSprintPreferences();
    }
    
    /**
     * Maps reference sprints to estimate preferences.
     */
    private void mapWindowToReferenceSprintPreferences() {
        currentPreferences.setRefSprintOne(
                new Sprint(
                        refSprintOneNameTextField.getText(),
                        Integer.valueOf(
                                refSprintOneCompletedPointsTextField.getText()
                        )
                )
        );
        
        currentPreferences.setRefSprintTwo(
                new Sprint(
                        refSprintTwoNameTextField.getText(),
                        Integer.valueOf(
                                refSprintTwoCompletedPointsTextField.getText()
                        )
                )
        );
        
        currentPreferences.setRefSprintThree(
                new Sprint(
                        refSprintThreeNameTextField.getText(),
                        Integer.valueOf(
                                refSprintThreeCompletedPointsTextField.getText()
                        )
                )
        );
        
        currentPreferences.setRefSprintFour(
                new Sprint(
                        refSprintFourNameTextField.getText(),
                        Integer.valueOf(
                                refSprintFourCompletedPointsTextField.getText()
                        )
                )
        );
    }
    
        
    /**
     * Applies default reference sprint to all epic outlooks.
     */
    @FXML
    private void handleApplyAllReferenceSprints() {
        try {
            mapWindowToReferenceSprintPreferences();
        
            for (Epic e : currentDocument.getEpics()) {
                e.getOutlook().setSprintOne(currentPreferences.getRefSprintOne());
                e.getOutlook().setSprintTwo(currentPreferences.getRefSprintTwo());
                e.getOutlook().setSprintThree(currentPreferences.getRefSprintThree());
                e.getOutlook().setSprintFour(currentPreferences.getRefSprintFour());
            }
        } catch (Exception e) {
            ErrorAlert.show(
                    bundle,
                    bundle.getString("errors.preferences.applyReferenceSprints"),
                    e
            );
        }
    }
    
    /**
     * Cancels the modification of preferences.
     */
    @FXML
    private void handleCancelPreferences() {
        try {
            preferenceStage().close();
        } catch (Exception e) {
            ErrorAlert.show(
                    bundle,
                    bundle.getString("errors.preferences.cancel"),
                    e
            );
        }
    }
    
    /**
     * Saves preference changes to document preferences.
     */
    @FXML
    private void handleSavePreferences() {
        try {
            mapWindowToPreferences();
            preferenceStage().close();
        } catch (Exception e) {
            ErrorAlert.show(
                    bundle,
                    bundle.getString("errors.preferences.save"),
                    e
            );
        }
    }

}
