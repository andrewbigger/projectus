package com.biggerconcept.projectus;

import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.projectus.domain.Preferences;
import com.biggerconcept.appengine.ui.dialogs.ErrorAlert;
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
        mapPreferencesToWindow(currentDocument.getPreferences());
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
    private void mapPreferencesToWindow(Preferences prefs) {
        epicStartNumberTextField.setText(
                String.valueOf(prefs.getEpicStartNumber())
        );
        extraSmallSizeTextField.setText(
                String.valueOf(prefs.getExtraSmallTaskSize())
        );
        smallSizeTextField.setText(
                String.valueOf(prefs.getSmallTaskSize())
        );
        mediumSizeTextField.setText(
                String.valueOf(prefs.getMediumTaskSize())
        );
        largeSizeTextField.setText(
                String.valueOf(prefs.getLargeTaskSize())
        );
        extraLargeSizeTextField.setText(
                String.valueOf(prefs.getExtraLargeTaskSize())
        );
        sprintSizeTextField.setText(
                String.valueOf(prefs.getSprintLength())
        );
        refSprintOneNameTextField.setText(
                prefs.getRefSprintOne().getName()
        );
        refSprintOneCompletedPointsTextField.setText(
                String.valueOf(prefs.getRefSprintOne().getCompletedPoints())
        );
        refSprintTwoNameTextField.setText(
                prefs.getRefSprintTwo().getName()
        );
        refSprintTwoCompletedPointsTextField.setText(
                String.valueOf(prefs.getRefSprintTwo().getCompletedPoints())
        );
        refSprintThreeNameTextField.setText(
                prefs.getRefSprintThree().getName()
        );
        refSprintThreeCompletedPointsTextField.setText(
                String.valueOf(prefs.getRefSprintThree().getCompletedPoints())
        );
        refSprintFourNameTextField.setText(
                prefs.getRefSprintFour().getName()
        );
        refSprintFourCompletedPointsTextField.setText(
                String.valueOf(prefs.getRefSprintFour().getCompletedPoints())
        );
        estimateBufferTextField.setText(
                String.valueOf(prefs.getEstimateBuffer())
        );
    }
    
    /**
     * Maps window content to new document object for serialization.
     * 
     * @return built preference object
     */
    private Preferences mapWindowToPreferences() {
        Preferences p = new Preferences();
        
        p.setEpicStartNumber(epicStartNumberTextField.getText());
        p.setExtraSmallSize(extraSmallSizeTextField.getText());
        p.setSmallSize(smallSizeTextField.getText());
        p.setMediumSize(mediumSizeTextField.getText());
        p.setLargeSize(largeSizeTextField.getText());
        p.setExtraLargeSize(extraLargeSizeTextField.getText());
        p.setSprintLength(sprintSizeTextField.getText());
        p.setRefSprintOne(
                new Sprint(
                        refSprintOneNameTextField.getText(),
                        Integer.valueOf(
                                refSprintOneCompletedPointsTextField.getText()
                        )
                )
        );
        
        p.setRefSprintTwo(
                new Sprint(
                        refSprintTwoNameTextField.getText(),
                        Integer.valueOf(
                                refSprintTwoCompletedPointsTextField.getText()
                        )
                )
        );
        
        p.setRefSprintThree(
                new Sprint(
                        refSprintThreeNameTextField.getText(),
                        Integer.valueOf(
                                refSprintThreeCompletedPointsTextField.getText()
                        )
                )
        );
        
        p.setRefSprintFour(
                new Sprint(
                        refSprintFourNameTextField.getText(),
                        Integer.valueOf(
                                refSprintFourCompletedPointsTextField.getText()
                        )
                )
        );
        
        p.setEstimateBuffer(Integer.valueOf(estimateBufferTextField.getText()));
        
        return p;
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
            currentDocument.setPreferences(mapWindowToPreferences());
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
