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
    private final ResourceBundle bundle;
    
    /**
     * Pointer to the document.
     */
    private final Document currentDocument;
    
    /**
     * Pointer to the actor.
     */
    private final Story currentStory;
    
    /**
     * Actor choice combo box
     */
    private final ComboBox actorField;
    
    /**
     * Intention text field.
     */
    private final TextArea intentionField;
    
    /**
     * Expectation text field.
     */
    private final TextArea expectationField;
    
    /**
     * Constructor for actor dialog.
     * 
     * @param rb application resource bundle
     * @param document open document
     * @param story chosen story
     */
    public StoryDialog(ResourceBundle rb, Document document, Story story) {
        bundle = rb;
        currentDocument = document;
        currentStory = story;
        
        actorField = new ComboBox();
        actorField.getItems().addAll(currentDocument.getActors());
        actorField.getSelectionModel().select(currentStory.getActor());
        
        intentionField = new TextArea(currentStory.getIntention());
        intentionField.setWrapText(true);

        expectationField = new TextArea(currentStory.getExpectation());
        intentionField.setWrapText(true);
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
                applyToStory();
            }

            return null;
        });
        
        dialog.showAndWait();
    }
    
    /**
     * Ensures story exists in document.
     * 
     * When the story has been added to the document, we will return early.
     * 
     * If the story is not in the document then it's added.
     */
    private void addStoryToDocument() {
        if (currentDocument.hasStory(currentStory)) {
            return;
        }
        
        currentDocument.addStory(currentStory);
    }
    
    /**
     * Applies changes to story.
     * 
     * First we check to ensure the actor exists in the document.
     * 
     * Then the story attributes are set from the form.
     */
    private void applyToStory() {
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
     * it in a VBox wrapper.
     * 
     * @param label label node for the attribute
     * @param value node for presenting and editing attribute
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
     * Actor attribute builder.
     * 
     * Builds VBox with the name label and text field for managing actor.
     * 
     * @return actor attribute
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
     * Builds VBox with the name label and text field for managing intention.
     * 
     * @return intention attribute
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
     * Builds VBox with the name label and text field for managing expectation.
     * 
     * @return expectation attribute
     */
    private VBox expectationAttribute() {
        return attributeFor(
                new Label(bundle.getString("stories.dialogs.story.expectation")),
                expectationField
        );
    }
}
