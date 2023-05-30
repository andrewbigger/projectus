package com.biggerconcept.projectus;

import com.biggerconcept.projectus.domain.Risk;
import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.projectus.exceptions.NoChoiceMadeException;
import com.biggerconcept.projectus.ui.dialogs.RiskDialog;
import com.biggerconcept.projectus.ui.dialogs.ErrorAlert;
import com.biggerconcept.projectus.ui.dialogs.YesNoPrompt;
import com.biggerconcept.projectus.ui.tables.RiskTable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * Controller for the risks window.
 * 
 * @author Andrew Bigger
 */
public class RisksController implements Initializable {
    /**
     * Resource bundle for stories window.
     */
    private ResourceBundle bundle;
    
    /**
     * Current document.
     */
    private Document currentDocument;
    
    /**
     * Initialize-er for the stories window.
     * 
     * @param url URL for stories FXML
     * @param rb application resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = rb;
        
        applyTooltips();
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
     * @param doc open document 
     */
    public void setDocument(Document doc) {
        currentDocument = doc;
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
     * Returns the risk window stage.
     * 
     * The risk window stage is the window with the add risk button.
     * 
     * @return controller window
     */
    private Stage storiesStage() {
        Stage stage = (Stage) addRiskButton.getScene().getWindow();
        return stage;
    }
    
    /**
     * Maps document to window.
     */
    private void mapDocumentToWindow() {
        RiskTable riskTable = new RiskTable(
                bundle,
                currentDocument.getRisks()
        );
        
        riskTable.build(riskTableView);
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
            
            newRisk.show(storiesStage());
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
            
            manageRisk.show(storiesStage());
            
            mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(bundle, bundle.getString("errors.editRisk"), e);
        }
    }
}
