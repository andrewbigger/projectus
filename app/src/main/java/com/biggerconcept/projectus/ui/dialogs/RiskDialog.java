package com.biggerconcept.projectus.ui.dialogs;

import com.biggerconcept.appengine.ui.dialogs.StandardDialog;
import com.biggerconcept.projectus.domain.Risk;
import com.biggerconcept.projectus.domain.Risk.RiskImpact;
import com.biggerconcept.projectus.domain.Risk.RiskLikelihood;
import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.projectus.domain.Risk.RiskStatus;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Risk management dialog.
 * 
 * @author Andrew Bigger
 */
public class RiskDialog {
    /**
     * Resource bundle for dialog.
     */
    private final ResourceBundle bundle;
    
    /**
     * Pointer to the document.
     */
    private final Document currentDocument;
    
    /**
     * Pointer to the risk.
     */
    private final Risk currentRisk;
    
    /**
     * Name text field.
     */
    private final TextField nameField;
    
    /**
     * Likelihood combo box.
     */
    private final ComboBox likelihoodField;
    
    /**
     * Impact combo box.
     */
    private final ComboBox impactField;
    
    /**
     * Status combo box.
     */
    private final ComboBox statusField;
    
    /**
     * Constructor for risk dialog.
     * 
     * @param rb application resource bundle
     * @param document open document
     * @param risk chosen risk
     */
    public RiskDialog(ResourceBundle rb, Document document, Risk risk) {
        bundle = rb;
        currentDocument = document;
        currentRisk = risk;

        nameField = new TextField(currentRisk.getName());

        likelihoodField = new ComboBox();
        likelihoodField.getItems().addAll(RiskLikelihood.values());
        likelihoodField.getSelectionModel().select(currentRisk.getLikelihood());
      
        impactField = new ComboBox();
        impactField.getItems().addAll(RiskImpact.values());
        impactField.getSelectionModel().select(currentRisk.getImpact());
        
        statusField = new ComboBox();
        statusField.getItems().addAll(RiskStatus.values());
        statusField.getSelectionModel().select(currentRisk.getStatus());
    }
    
    public void show(Stage stage) {
        List<Node> attributes = Arrays.asList(
                nameAttribute(),
                likelihoodAttribute(),
                impactAttribute(),
                statusAttribute()
        );
        
        ButtonType apply = StandardDialog.applyAction(
                bundle.getString(
                       "risks.dialogs.risk.actions.save" 
                )
        );
        
        List<ButtonType> actions = Arrays.asList(
                StandardDialog.cancelAction(
                        bundle.getString(
                                "risks.dialogs.risk.actions.cancel"
                        )
                ),
                apply
        );
        
        Dialog<String> dialog = StandardDialog.dialog(
            bundle.getString("stories.dialogs.risk.title"),
            attributes,
            actions
        );
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == apply) {
                applyToRisk();
            }

            return null;
        });
        
        dialog.showAndWait();
    }
    
    /**
     * Ensures risk exists in document.
     * 
     * When the risk has been added to the document, we will return early.
     * 
     * If the risk is not in the document then it's added.
     */
    private void addRiskToDocument() {
        if (currentDocument.hasRisk(currentRisk)) {
            return;
        }
        
        currentDocument.addRisk(currentRisk);
    }
    
    /**
     * Applies changes to risk.
     * 
     * First we check to ensure the actor exists in the document.
     * 
     * Then the risk attributes are set from the form.
     */
    private void applyToRisk() {
        addRiskToDocument();

        currentRisk.setName(nameField.getText());
        currentRisk.setImpact(
          (RiskImpact) impactField.getSelectionModel().getSelectedItem()
        );

        currentRisk.setLikelihood(
          (RiskLikelihood) likelihoodField.getSelectionModel().getSelectedItem()
        );
    }
    
    /**
     * Name attribute builder.
     * 
     * Builds VBox with the name label and text field for managing name.
     * 
     * @return name attribute
     */
    private VBox nameAttribute() {
        return StandardDialog.attribute(
                new Label(bundle.getString("risks.dialogs.risk.name")),
                nameField
        );
    }
    
    /**
     * Likelihood attribute builder.
     * 
     * Builds VBox with the likelihood label and combo box for managing 
     * likelihood.
     * 
     * @return likelihood attribute
     */
    private VBox likelihoodAttribute() {
        return StandardDialog.attribute(
                new Label(bundle.getString("risks.dialogs.risk.likelihood")),
                likelihoodField
        );
    }
    
    /**
     * Impact attribute builder.
     * 
     * Builds VBox with the impact label and combo box for managing impact.
     * 
     * @return impact attribute
     */
    private VBox impactAttribute() {
        return StandardDialog.attribute(
                new Label(bundle.getString("risks.dialogs.risk.impact")),
                impactField
        );
    }
    
    /**
     * Status attribute builder.
     * 
     * Builds VBox with the status label and combo box for managing status.
     * 
     * @return status attribute
     */
    private VBox statusAttribute() {
        return StandardDialog.attribute(
                new Label(bundle.getString("risks.dialogs.risk.status")),
                statusField
        );
    }
}
