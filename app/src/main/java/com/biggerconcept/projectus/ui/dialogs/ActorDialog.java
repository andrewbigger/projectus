package com.biggerconcept.projectus.ui.dialogs;

import com.biggerconcept.projectus.domain.Actor;
import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.projectus.exceptions.NoChoiceMadeException;
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
    private ResourceBundle bundle;
    
    /**
     * Pointer to the document.
     */
    private Document currentDocument;
    
    /**
     * Pointer to the actor.
     */
    private Actor currentActor;
    
    /**
     * Actor name text area.
     */
    private TextField nameField;
    
    /**
     * Constructor for actor dialog.
     * 
     * @param b
     * @param d
     * @param a
     */
    public ActorDialog(ResourceBundle b, Document d, Actor a) {
        bundle = b;
        currentDocument = d;
        currentActor = a;
        
        nameField = new TextField(currentActor.getName());
    }
    
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
    
    private void addActorToDocument() {
        if (currentDocument.hasActor(currentActor)) {
            return;
        }
        
        currentDocument.addActor(currentActor);
    }
    
    private void applyToActor() {
        addActorToDocument();
        
        currentActor.setName(nameField.getText());
    }
    
    /**
     * Attribute builder function.
     * 
     * Takes a label and control for modifying the attribute and returns
     * it in a vbox wrapper.
     * 
     * @param label
     * @param value
     * @return 
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
     * Builds vbox with the name label and text field for managing actor name.
     * 
     * @return 
     */
    private VBox nameAttribute() {
        return attributeFor(
                new Label(bundle.getString("stories.dialogs.actor.name")),
                nameField
        );
    }
}
