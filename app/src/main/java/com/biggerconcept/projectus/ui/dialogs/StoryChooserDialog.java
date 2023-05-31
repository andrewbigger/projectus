package com.biggerconcept.projectus.ui.dialogs;

import com.biggerconcept.projectus.domain.Story;
import com.biggerconcept.projectus.exceptions.NoChoiceMadeException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Story picker dialog.
 * 
 * @author Andrew Bigger
 */
public class StoryChooserDialog {
    /**
     * Resource bundle for dialog.
     */
    private final ResourceBundle bundle;
    
    /**
     * Stories to choose from.
     */
    private final ArrayList<Story> stories;
    
    /**
     * List view of stories.
     */
    private ListView storiesListView;
    
    /**
     * Chosen story to return to caller.
     */
    private Story chosenStory;
        
    public StoryChooserDialog(ResourceBundle rb, ArrayList<Story> storiesList) {
        bundle = rb;
        stories = storiesList;
        
        storiesListView = new ListView();
        
        int pos = 0;
        for (Story s : storiesList) {
            storiesListView.getItems().add(buildStoryName(pos, s));
            
            pos += 1;
        }
    }
    /**
     * Shows story picker dialog.
     * 
     * @param stage
     * @return
     * @throws NoChoiceMadeException 
     */
    public Story show(Stage stage) throws NoChoiceMadeException {
        Dialog<String> dialog = new Dialog<>();
        
        dialog.setTitle(bundle.getString("dialogs.storyChooser.title"));
        
        ButtonType apply = new ButtonType(
                bundle.getString("dialogs.storyChooser.actions.choose"),
                ButtonData.APPLY
        );
        
        ButtonType cancel = new ButtonType(
                bundle.getString("dialogs.storyChooser.actions.cancel"),
                ButtonData.CANCEL_CLOSE
        );
        
        VBox wrapper = new VBox();
        wrapper.setSpacing(10);
        
        wrapper.getChildren().addAll(storyListAttribute());
        
        dialog.getDialogPane().setContent(wrapper);
        dialog.getDialogPane().getButtonTypes().add(apply);
        dialog.getDialogPane().getButtonTypes().add(cancel);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == apply) {
                int i = storiesListView.getSelectionModel().getSelectedIndex();
                chosenStory = stories.get(i);
            }
            
            return null;
        });
        
        dialog.showAndWait();
        
        if (chosenStory == null) {
            throw new NoChoiceMadeException();
        }
        
        return chosenStory;
    }
    
    /**
     * Attribute builder function.
     * 
     * This takes a label and a control for modifying the attribute and 
     * returns it in a VBox wrapper.
     * 
     * @param label attribute label node
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
     * Story list attribute builder.
     * 
     * Builds a VBox with the name label and text field for managing a task
     * name.
     * 
     * @return name attribute
     */
    private VBox storyListAttribute() {        
        return attributeFor(
                new Label(bundle.getString("dialogs.storyChooser.description")),
                storiesListView
        );
    }
    
    /**
     * Builds story name for presentation.
     * 
     * @param pos
     * @param s
     * @return 
     */
    private String buildStoryName(int pos, Story s) {
        String name = "";
            
        name += "S";
        name += String.valueOf(pos + 1);
        name += " ";
        name += bundle.getString("stories.table.actor");
        name += " ";
        name += s.getActor().getName();
        name += " ";
        name += bundle.getString("stories.table.intention");
        name += " ";
        name += s.getIntention();
        name += " ";
        name += bundle.getString("stories.table.expectation");
        name += " ";
        name += s.getExpectation();
        
        return name;
    }
}
