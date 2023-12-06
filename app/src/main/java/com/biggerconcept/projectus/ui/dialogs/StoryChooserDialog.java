package com.biggerconcept.projectus.ui.dialogs;

import com.biggerconcept.appengine.ui.dialogs.StandardDialog;
import com.biggerconcept.projectus.domain.Story;
import com.biggerconcept.appengine.exceptions.NoChoiceMadeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
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
    private final ListView storiesListView;
    
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
            storiesListView.getItems().add(buildStoryName(s));
            
            pos += 1;
        }
    }
    /**
     * Shows story picker dialog.
     * 
     * @param stage window to show dialog in
     * 
     * @return chosen story
     * 
     * @throws NoChoiceMadeException when no choice is made
     */
    public Story show(Stage stage) throws NoChoiceMadeException {
        List<Node> attributes = Arrays.asList(
                storyListAttribute()
        );
        
        ButtonType apply = StandardDialog.applyAction(
                bundle.getString(
                       "dialogs.storyChooser.actions.choose" 
                )
        );
        
        List<ButtonType> actions = Arrays.asList(
                StandardDialog.cancelAction(
                        bundle.getString(
                                "dialogs.storyChooser.actions.cancel"
                        )
                ),
                apply
        );
        
        Dialog<String> dialog = StandardDialog.dialog(
            bundle.getString("dialogs.storyChooser.title"),
            attributes,
            actions,
            apply
        );
        
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
     * Story list attribute builder.
     * 
     * Builds a VBox with the name label and text field for managing a task
     * name.
     * 
     * @return name attribute
     */
    private VBox storyListAttribute() {        
        return StandardDialog.attribute(
                new Label(bundle.getString("dialogs.storyChooser.description")),
                storiesListView
        );
    }
    
    /**
     * Builds story name for presentation.
     * 
     * @param s story to render name for
     * 
     * @return story name as a one line string
     */
    private String buildStoryName(Story s) {
        String name = "";
            
        name += "S";
        name += String.valueOf(s.getIdentifier());
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
