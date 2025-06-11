package com.biggerconcept.projectus.ui.dialogs;

import com.biggerconcept.sdk.ui.dialogs.StandardDialog;
import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.projectus.domain.Epic;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Epic management dialog.
 * 
 * @author Andrew Bigger
 */
public class EpicDialog {
    /**
     * Resource bundle for dialog.
     */
    private final ResourceBundle bundle;
    
    /**
     * Pointer to the document.
     */
    private final Document currentDocument;
    
    /**
     * Pointer to the actor.
     */
    private final Epic currentEpic;
    
    /**
     * Epic name text area.
     */
    private final TextField nameField;
    
    /**
     * Constructor for epic dialog.
     * 
     * @param rb resource bundle for application
     * @param document open document
     * @param epic epic for dialog
     */
    public EpicDialog(ResourceBundle rb, Document document, Epic epic) {
        bundle = rb;
        currentDocument = document;
        currentEpic = epic;
        
        nameField = new TextField(currentEpic.getName());
    }
    
    /**
     * Shows actor dialog on specified stage.
     * 
     * @param stage parent window to show dialog from
     */
    public void show(Stage stage) {
        List<Node> attributes = Arrays.asList(
                nameAttribute()
        );
        
        ButtonType apply = StandardDialog.applyAction(
                bundle.getString(
                       "epic.dialogs.manage.actions.save" 
                )
        );
        
        List<ButtonType> actions = Arrays.asList(
                StandardDialog.cancelAction(
                        bundle.getString(
                                "epic.dialogs.manage.actions.cancel"
                        )
                ),
                apply
        );
        
        Dialog<String> dialog = StandardDialog.dialog(
            bundle.getString("epic.dialogs.manage.title"),
            attributes,
            actions,
            apply
        );
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == apply) {
                applyToEpic();
            }

            return null;
        });
        
        dialog.showAndWait();
    }
    
    /**
     * Ensures epic is added to document.
     * 
     * First we check to see if the epic exists in the document, if it does
     * then we return early.
     * 
     * When the epic is not present, it is added to the open document.
     */
    private void addEpicToDocument() {
        if (currentDocument.hasEpic(currentEpic)) {
            return;
        }
        
        currentDocument.addEpic(currentEpic);
    }
    
    /**
     * Applies form to actor.
     * 
     * First we ensure that the epic exists in the document, then we set the
     * epic's data from the form.
     */
    private void applyToEpic() {
        addEpicToDocument();
        
        currentEpic.setName(nameField.getText());
    }
    
    /**
     * Name attribute builder.
     * 
     * Builds VBox with the name label and text field for managing epic name.
     * 
     * @return name attribute
     */
    private VBox nameAttribute() {
        return StandardDialog.attribute(
                new Label(bundle.getString("epic.dialogs.manage.name")),
                nameField
        );
    }
}
