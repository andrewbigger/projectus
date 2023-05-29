package com.biggerconcept.projectus;

import com.biggerconcept.projectus.domain.Actor;
import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.projectus.domain.Story;
import com.biggerconcept.projectus.exceptions.NoChoiceMadeException;
import com.biggerconcept.projectus.ui.dialogs.ActorDialog;
import com.biggerconcept.projectus.ui.dialogs.ErrorAlert;
import com.biggerconcept.projectus.ui.dialogs.StoryDialog;
import com.biggerconcept.projectus.ui.dialogs.YesNoPrompt;
import com.biggerconcept.projectus.ui.tables.StoryTable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * Controller for the stories window.
 * 
 * @author Andrew Bigger
 */
public class StoriesController implements Initializable {
    /**
     * Resource bundle for stories window.
     */
    private ResourceBundle bundle;
    
    /**
     * Current document.
     */
    private Document currentDocument;
    
    /**
     * Initializer for the stories window.
     * 
     * @param url
     * @param rb
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
     * @param actors 
     */
    public void setDocument(Document doc) {
        currentDocument = doc;
        mapDocumentToWindow();
    }
    
    /**
     * Add actor toolbar button.
     */
    @FXML
    public Button addActorButton;
    
    /**
     * Remove actor toolbar button.
     */
    @FXML
    public Button removeActorButton;
    
    /**
     * Edit actor toolbar button.
     */
    @FXML
    public Button editActorButton;
    
    /**
     * Add story toolbar button.
     */
    @FXML
    public Button addStoryButton;
    
    /**
     * Remove story toolbar button.
     */
    @FXML
    public Button removeStoryButton;
    
    /**
     * Edit story toolbar button.
     */
    @FXML
    public Button editStoryButton;
    
    /**
     * List view for story actors.
     */
    @FXML
    public ListView actorListView;
    
    /**
     * Table view for stories.
     */
    @FXML
    public TableView storyTableView;
    
    /**
     * Returns the stories window stage.
     * 
     * The stories window stage is the window with the add story button.
     * 
     * @return 
     */
    private Stage storiesStage() {
        Stage stage = (Stage) addActorButton.getScene().getWindow();
        return stage;
    }
    
    /**
     * Maps document to window.
     */
    private void mapDocumentToWindow() {
        actorListView.getItems().clear();
        
        for (Actor a : currentDocument.getActors()) {
            actorListView.getItems().add(a);
        }
        
        StoryTable storiesTable = new StoryTable(
                bundle,
                currentDocument.getStories()
        );
        
        storiesTable.build(storyTableView);
    }
    
    @FXML
    private void handleAddActor() {
        try {
            ActorDialog newActor = new ActorDialog(
                    bundle,
                    currentDocument,
                    new Actor()
            );
            
            newActor.show(storiesStage());
            mapDocumentToWindow();
        } catch (Exception e) {
            ErrorAlert.show(bundle, bundle.getString("errors.addActor"), e);
        }
    }
    
    @FXML
    private void handleRemoveActor() {
        try {
            Actor item = currentDocument
                    .getActors()
                    .get(
                            actorListView
                                .getSelectionModel()
                                .getSelectedIndex()
                    );
            
            ButtonType answer = YesNoPrompt.show(
                    AlertType.CONFIRMATION,
                    bundle.getString("stories.dialogs.removeActor.title"),
                    bundle.getString("stories.dialogs.removeActor.description")
            );
            
            if (answer == ButtonType.YES) {
                currentDocument.removeActor(item);
            }
            
            mapDocumentToWindow();
            
        } catch (ArrayIndexOutOfBoundsException oob) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(bundle, bundle.getString("errors.removeActor"), e);
        }
    }
    
    @FXML
    private void handleEditActor() {
        try {
            Actor item = currentDocument
                    .getActors()
                    .get(
                            actorListView
                                .getSelectionModel()
                                .getSelectedIndex()
                    );
            
            if (item == null) {
                throw new NoChoiceMadeException();
            }
            
            ActorDialog newActor = new ActorDialog(
                    bundle,
                    currentDocument,
                    item
            );
            
            newActor.show(storiesStage());
            mapDocumentToWindow();
            
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(bundle, bundle.getString("errors.editActor"), e);
        }
    }
    
    @FXML
    private void handleAddStory() {
        try {
            StoryDialog newStory = new StoryDialog(
                    bundle,
                    currentDocument,
                    new Story()
            );
            
            newStory.show(storiesStage());
            mapDocumentToWindow();
        } catch (Exception e) {
            ErrorAlert.show(bundle, bundle.getString("errors.addStory"), e);
        } 
    }
    
    @FXML
    private void handleRemoveStory() {
        try {
            ObservableList<Story> items = storyTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            ButtonType answer = YesNoPrompt.show(
                    AlertType.CONFIRMATION,
                    bundle.getString("stories.dialogs.removeStory.title"),
                    bundle.getString("stories.dialogs.removeStory.description")
            );
            
            if (answer == ButtonType.YES) {
                for (Story s: items) {
                    currentDocument.removeStory(s);
                }
            }
            
            mapDocumentToWindow();
            
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(bundle, bundle.getString("errors.removeActor"), e);
        }
    }
    
    @FXML
    private void handleEditStory() {
        try {
             ObservableList<Story> items = storyTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            StoryDialog manageStory = new StoryDialog(
                    bundle,
                    currentDocument,
                    items.get(0)
            );
            
            manageStory.show(storiesStage());
            
            mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(bundle, bundle.getString("errors.editStory"), e);
        }
    }
}
