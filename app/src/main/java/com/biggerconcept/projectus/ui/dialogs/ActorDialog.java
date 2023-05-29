package com.biggerconcept.projectus.ui.dialogs;

import com.biggerconcept.projectus.domain.Actor;
import com.biggerconcept.projectus.domain.Document;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
        Dialog<String> dialog = new Dialog<>();
        
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle(bundle.getString("stories.dialogs.actor.title"));
        
        ButtonType apply = new ButtonType(
                bundle.getString("stories.dialogs.actor.actions.save"),
                ButtonData.APPLY
        );
        
        ButtonType cancel = new ButtonType(
                bundle.getString("stories.dialogs.actor.actions.cancel"),
                ButtonData.CANCEL_CLOSE
        );
        
        VBox wrapper = new VBox();
        wrapper.setSpacing(10);
        
        wrapper.getChildren().addAll(
                nameAttribute()
        );
        
        dialog.getDialogPane().setContent(wrapper);
        dialog.getDialogPane().getButtonTypes().add(apply);
        dialog.getDialogPane().getButtonTypes().add(cancel);
        
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
     * Attribute builder function.
     * 
     * Takes a label and control for modifying the attribute and returns
     * it in a VBox wrapper.
     * 
     * @param label label node for attribute
     * @param value field node for editing attribute
     * 
     * @return attribute wrapped in VBox
     */
    private VBox attributeFor(Label label, Node value) {
        VBox wrapper = new VBox();
        
        wrapper.setSpacing(5);
        wrapper.getChildren().addAll(label, value);
        
        return wrapper;
    }
    
    /**
     * Name attribute builder.
     * 
     * Builds VBox with the name label and text field for managing actor name.
     * 
     * @return name attribute
     */
    private VBox nameAttribute() {
        return attributeFor(
                new Label(bundle.getString("stories.dialogs.actor.name")),
                nameField
        );
    }
}
