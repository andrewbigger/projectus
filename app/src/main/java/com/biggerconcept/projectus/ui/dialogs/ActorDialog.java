package com.biggerconcept.projectus.ui.dialogs;

import com.biggerconcept.sdk.ui.dialogs.StandardDialog;
import com.biggerconcept.projectus.domain.Actor;
import com.biggerconcept.projectus.domain.Document;
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
 * Actor management dialog.
 * 
 * @author Andrew Bigger
 */
public class ActorDialog {
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
    private final Actor currentActor;
    
    /**
     * Actor name text area.
     */
    private final TextField nameField;
    
    /**
     * Constructor for actor dialog.
     * 
     * @param rb resource bundle for application
     * @param document open document
     * @param actor actor for dialog
     */
    public ActorDialog(ResourceBundle rb, Document document, Actor actor) {
        bundle = rb;
        currentDocument = document;
        currentActor = actor;
        
        nameField = new TextField(currentActor.getName());
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
                       "stories.dialogs.actor.actions.save" 
                )
        );
        
        List<ButtonType> actions = Arrays.asList(
                StandardDialog.cancelAction(
                        bundle.getString(
                                "stories.dialogs.actor.actions.cancel"
                        )
                ),
                apply
        );
        
        Dialog<String> dialog = StandardDialog.dialog(
            bundle.getString("stories.dialogs.actor.title"),
            attributes,
            actions,
            apply
        );
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == apply) {
                applyToActor();
            }

            return null;
        });
        
        dialog.showAndWait();
    }
    
    /**
     * Ensures actor is added to document.
     * 
     * First we check to see if the actor exists in the document, if it does
     * then we return early.
     * 
     * When the actor is not present, it is added to the open document.
     */
    private void addActorToDocument() {
        if (currentDocument.hasActor(currentActor)) {
            return;
        }
        
        currentDocument.addActor(currentActor);
    }
    
    /**
     * Applies form to actor.
     * 
     * First we ensure that the actor exists in the document, then we set the
     * actor's data from the form.
     */
    private void applyToActor() {
        addActorToDocument();
        
        currentActor.setName(nameField.getText());
    }
    
    /**
     * Name attribute builder.
     * 
     * Builds VBox with the name label and text field for managing actor name.
     * 
     * @return name attribute
     */
    private VBox nameAttribute() {
        return StandardDialog.attribute(
                new Label(bundle.getString("stories.dialogs.actor.name")),
                nameField
        );
    }
}
