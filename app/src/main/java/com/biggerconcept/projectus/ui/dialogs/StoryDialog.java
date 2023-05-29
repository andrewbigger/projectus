package com.biggerconcept.projectus.ui.dialogs;

import com.biggerconcept.projectus.domain.Actor;
import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.projectus.domain.Story;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Story management dialog.
 * 
 * @author Andrew Bigger
 */
public class StoryDialog {
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
    private Story currentStory;
    
    /**
     * Actor choice combo box
     */
    private ComboBox actorField;
    
    /**
     * Intention text field.
     */
    private TextArea intentionField;
    
    /**
     * Expecation text field.
     */
    private TextArea expectationField;
    
    /**
     * Constructor for actor dialog.
     * 
     * @param b
     * @param d
     * @param s
     */
    public StoryDialog(ResourceBundle b, Document d, Story s) {
        bundle = b;
        currentDocument = d;
        currentStory = s;
        
        actorField = new ComboBox();
        actorField.getItems().addAll(currentDocument.getActors());
        actorField.getSelectionModel().select(currentStory.getActor());
        
        intentionField = new TextArea(currentStory.getIntention());
        expectationField = new TextArea(currentStory.getExpectation());
    }
    
    public void show(Stage stage) {
        Dialog<String> dialog = new Dialog<>();
        dialog.initStyle(StageStyle.UTILITY);
        
        dialog.setTitle(bundle.getString("stories.dialogs.story.title"));
        
        ButtonType apply = new ButtonType(
                bundle.getString("stories.dialogs.story.actions.save"),
                ButtonData.APPLY
        );
        
        ButtonType cancel = new ButtonType(
                bundle.getString("stories.dialogs.story.actions.cancel"),
                ButtonData.CANCEL_CLOSE
        );
        
        VBox wrapper = new VBox();
        wrapper.setSpacing(10);
        
        wrapper.getChildren().addAll(
                actorAttribute(),
                intentionAttribute(),
                expectationAttribute()
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
    
    private void addStoryToDocument() {
        if (currentDocument.hasStory(currentStory)) {
            return;
        }
        
        currentDocument.addStory(currentStory);
    }
    
    private void applyToActor() {
        addStoryToDocument();
        
        currentStory.setActor(
               (Actor) actorField.getSelectionModel().getSelectedItem()
        );
        
        currentStory.setIntention(intentionField.getText());
        currentStory.setExpectation(expectationField.getText());
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
     * Actor attribute builder.
     * 
     * Builds vbox with the name label and text field for managing actor.
     * 
     * @return 
     */
    private VBox actorAttribute() {
        return attributeFor(
                new Label(bundle.getString("stories.dialogs.story.actor")),
                actorField
        );
    }
    
    /**
     * Intention attribute builder.
     * 
     * Builds vbox with the name label and text field for managing intention.
     * 
     * @return 
     */
    private VBox intentionAttribute() {
        return attributeFor(
                new Label(bundle.getString("stories.dialogs.story.intention")),
                intentionField
        );
    }
    
    /**
     * Expectation attribute builder.
     * 
     * Builds vbox with the name label and text field for managing expectation.
     * 
     * @return 
     */
    private VBox expectationAttribute() {
        return attributeFor(
                new Label(bundle.getString("stories.dialogs.story.expectation")),
                expectationField
        );
    }
}
