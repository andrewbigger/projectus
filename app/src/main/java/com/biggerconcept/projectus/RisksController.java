package com.biggerconcept.projectus;

import com.biggerconcept.projectus.domain.Risk;
import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.sdk.exceptions.NoChoiceMadeException;
import com.biggerconcept.sdk.platform.OperatingSystem;
import com.biggerconcept.projectus.ui.dialogs.RiskDialog;
import com.biggerconcept.sdk.ui.dialogs.ErrorAlert;
import com.biggerconcept.sdk.ui.dialogs.YesNoPrompt;
import com.biggerconcept.projectus.domain.Risk.RiskImpact;
import com.biggerconcept.projectus.domain.Risk.RiskLikelihood;
import com.biggerconcept.projectus.domain.Risk.RiskStatus;
import com.biggerconcept.projectus.ui.tables.RiskTable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the risks window.
 * 
 * @author Andrew Bigger
 */
public class RisksController implements Initializable {
    /**
     * Application State
     */
    private State state;
    
    /**
     * Resource bundle for stories window.
     */
    private ResourceBundle bundle;
    
    /**
     * Current document.
     */
    private Document currentDocument;
    
    /**
     * Currently selected risk.
     */
    private Risk currentRisk;
    
    /**
     * Application menu.
     */
    @FXML
    public MenuBar mainMenu;
    
    /**
     * Application exit menu item.
     */
    @FXML
    public MenuItem quitMenuItem;
    
    /**
     * Initialize-er for the stories window.
     * 
     * @param url URL for stories FXML
     * @param rb application resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = rb;
        
        risksPanel.setVisible(false);
        
        riskLikelihoodComboBox.getItems().addAll(RiskLikelihood.values());
        riskImpactComboBox.getItems().addAll(RiskImpact.values());
        riskStatusComboBox.getItems().addAll(RiskStatus.values());
        
        if (OperatingSystem.isMac()) {
            mainMenu.useSystemMenuBarProperty().set(true);
            quitMenuItem.visibleProperty().set(false);
        }
        
        
        applyTooltips();
    }
    
    /**
     * Returns the window.
     * 
     * @return window
     */
    private Stage window() {
        return (Stage) mainMenu.getScene().getWindow();
    }
    
    /**
     * Applies tool tips to the controls of the stories window.
     */
    private void applyTooltips() {
        // TODO: tooltips
    }
    
    /**
     * Sets controller current document.
     * 
     * @param state application state
     */
    public void setState(State state) {
        this.state = state;
        currentDocument = state.getOpenDocument();
        mapDocumentToWindow();
    }
    
    /**
     * Add risk toolbar button.
     */
    @FXML
    public Button addRiskButton;
    
    /**
     * Remove risk toolbar button.
     */
    @FXML
    public Button removeRiskButton;
    
    /**
     * Edit risk toolbar button.
     */
    @FXML
    public Button editRiskButton;

    /**
     * Table view for risks.
     */
    @FXML
    public TableView riskTableView;
    
    /**
     * Edit risk panel.
     */
    @FXML
    private VBox risksPanel;
    
    /**
     * Risk name text field.
     */
    @FXML
    private TextField riskNameTextField;
    
    /**
     * Risk likelihood combo box.
     */
    @FXML
    private ComboBox riskLikelihoodComboBox;
    
    /**
     * Risk impact combo box.
     */
    @FXML
    private ComboBox riskImpactComboBox;
    
    /**
     * Risk status combo box.
     */
    @FXML
    private ComboBox riskStatusComboBox;
    
    /**
     * Risk detail text area.
     */
    @FXML
    private TextArea riskDetailTextArea;
    
    /**
     * Risk mitigation strategy text area.
     */
    @FXML
    private TextArea riskMitigationTextArea;
    
    /**
     * Apply risk button.
     */
    @FXML
    private Button applyRiskButton;

    /**
     * Maps document to window.
     */
    private void mapDocumentToWindow() {
        RiskTable riskTable = new RiskTable(
                state,
                bundle,
                currentDocument.getRisks()
        );
        
        riskTable.bind(riskTableView, false);
        
        if (currentRisk != null) {
            risksPanel.setVisible(true);

            riskNameTextField.setText(currentRisk.getName());
            
            riskLikelihoodComboBox
                    .getSelectionModel()
                    .select(currentRisk.getLikelihood());
            
            riskImpactComboBox
                    .getSelectionModel()
                    .select(currentRisk.getImpact());
            
            riskStatusComboBox
                    .getSelectionModel()
                    .select(currentRisk.getStatus());
            
            riskDetailTextArea.setText(currentRisk.getDetail());
            riskMitigationTextArea.setText(
                    currentRisk.getMitigationStrategy()
            );
            
        } else {
            risksPanel.setVisible(false);
        }
    }
    
    /**
     * Maps window to current document.
     */
    private void mapWindowToDocument() {
        if (currentRisk == null) {
            return;
        }
        
        currentRisk.setName(riskNameTextField.getText());
        currentRisk.setLikelihood(
                (RiskLikelihood) riskLikelihoodComboBox
                        .getSelectionModel()
                        .getSelectedItem()
        );
        
        currentRisk.setImpact(
                (RiskImpact) riskImpactComboBox
                        .getSelectionModel()
                        .getSelectedItem()
        );
        
        currentRisk.setStatus(
                (RiskStatus) riskStatusComboBox
                        .getSelectionModel()
                        .getSelectedItem()
        );
        
        currentRisk.setDetail(riskDetailTextArea.getText());
        currentRisk.setMitigationStrategy(riskMitigationTextArea.getText());
    
        mapDocumentToWindow();
    }
    
    /**
     * Handles adding a risk to the document.
     */
    @FXML
    private void handleAddRisk() {
        try {
            RiskDialog newRisk = new RiskDialog(
                    bundle,
                    currentDocument,
                    new Risk()
            );
            
            newRisk.show(window());
            mapDocumentToWindow();
        } catch (Exception e) {
            ErrorAlert.show(bundle, bundle.getString("errors.addRisk"), e);
        } 
    }
    
    /**
     * Handles removing a risk from the document.
     */
    @FXML
    private void handleRemoveRisk() {
        try {
            ObservableList<Risk> items = riskTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            ButtonType answer = YesNoPrompt.show(
                    AlertType.CONFIRMATION,
                    bundle.getString("stories.dialogs.removeRisk.title"),
                    bundle.getString("stories.dialogs.removeRisk.description")
            );
            
            if (answer == ButtonType.YES) {
                for (Risk s: items) {
                    currentDocument.removeRisk(s);
                }
            }
            
            mapDocumentToWindow();
            
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(bundle, bundle.getString("errors.removeActor"), e);
        }
    }
    
    /**
     * Handles editing a risk within a document.
     */
    @FXML
    private void handleEditRisk() {
        try {
             ObservableList<Risk> items = riskTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            RiskDialog manageRisk = new RiskDialog(
                    bundle,
                    currentDocument,
                    items.get(0)
            );
            
            manageRisk.show(window());
            
            mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(bundle, bundle.getString("errors.editRisk"), e);
        }
    }
    
    /**
     * Handles choice of risk from risk table.
     */
    @FXML
    private void handleChooseRisk() {
        try {
            ObservableList<Risk> items = riskTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                currentRisk = null;
            } else {
                currentRisk = items.get(0);
            }
            
            mapDocumentToWindow();
        } catch (Exception e) {
            ErrorAlert.show(bundle, bundle.getString("errors.generic"), e);
        }
    }
    
    /**
     * Handles update to risk.
     */
    @FXML
    private void handleChangeRisk() {
        try {
            mapWindowToDocument();
        } catch(Exception e) {
            ErrorAlert.show(bundle, bundle.getString("errors.editRisk"), e);
        }
    }
    
    /**
     * Opens help website in default browser.
     */
    @FXML
    private void handleViewHelp() {
        try {
            OperatingSystem.goToUrl(App.HELP_URL);
        } catch (Exception e) {
            ErrorAlert.show(bundle, bundle.getString("errors.help.open"), e);
        }
}
    
     /**
     * Exits application.
     */
    @FXML
    private void handleApplicationExit() {
        System.exit(0);
    }
    
    @FXML
    private void handleWindowClose() {
        window().close();
    }

}
