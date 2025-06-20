package com.biggerconcept.projectus.ui.tables;

import com.biggerconcept.projectus.State;
import com.biggerconcept.sdk.ui.tables.StandardTable;
import com.biggerconcept.sdk.ui.tables.StandardTableColumn;
import com.biggerconcept.projectus.domain.Story;
import com.biggerconcept.projectus.domain.Task;
import com.biggerconcept.projectus.ui.dialogs.StoryDialog;
import com.biggerconcept.projectus.ui.dialogs.TaskDialog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * View model for the currentStories table.
 * 
 * @author Andrew Bigger
 */
public class StoryTable {
    /**
     * Sortable constant.
     * 
     * This should be false as stories are presented in user specified order.
     * view.
     */
    public static final boolean SORTABLE = false;
    
    /**
     * Default width of ID column.
     */
    public static final int ID_COL_MIN_WIDTH = 100;
    
    /**
     * Default width of actor column.
     */
    public static final int ACTOR_COL_MIN_WIDTH = 150;
    
    /**
     * Default width of intention column.
     */
    public static final int INTENTION_COL_MIN_WIDTH = 200;
    
    /**
     * Default width of expectation column.
     */
    public static final int EXPECTATION_COL_MIN_WIDTH = 200;
    
    /**
     * Application state
     */
    private final State state;
    
    /**
     * Application resource bundle.
     */
    private final ResourceBundle bundle;
    
    /**
     * List of currentStories.
     */
    private final ArrayList<Story> currentStories;
    
    /**
     * Constructor for the currentStories table view model.
     * 
     * @param state application state
     * @param rb application resource bundle
     * @param stories list of currentStories
     */
    public StoryTable(
            State state,
            ResourceBundle rb,
            ArrayList<Story> stories
    ) {
        this.state = state;
        bundle = rb;
        currentStories = stories;
    }
    
    /**
     * Binds data to story table view.
     * 
     * @param view story table view
     * @param allowDialog allow dialog to open
     */
    public void bind(TableView view, boolean allowDialog) {
        StandardTable.bind(
                view,
                bundle.getString("stories.table.empty"),
                FXCollections.observableArrayList(currentStories),
                Arrays.asList(
                    idCol(),
                    actorCol(),
                    intentionCol(),
                    expectationCol()
                ),
                false
        );
        
        view.setOnMousePressed(event -> {
            if (
                    allowDialog == true && 
                    event.isPrimaryButtonDown() && 
                    event.getClickCount() == 2
            ) {
                try {
                    Story selected = (Story) view
                            .getSelectionModel()
                            .getSelectedItem();

                    StoryDialog manageStory = new StoryDialog(
                        state.bundle(),
                        state.getOpenDocument(),
                        selected
                    );

                    manageStory.show(state.mainController().window());
                } catch (Exception ex) {
                    // ignore
                    System.out.println(ex.getStackTrace());
                }
            }
        });
    }
    
    /**
     * ID attribute column builder.
     * 
     * @return ID column
     */
    private TableColumn idCol() {
        TableColumn<Story, String> idCol = new TableColumn<>(
               bundle.getString("stories.table.id")
       );
        
        StandardTableColumn.apply(idCol, ID_COL_MIN_WIDTH);
        
        idCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(
                    "S" + 
                    String.valueOf(data.getValue().getIdentifier())
            );
        });
        
        return idCol;
    }
    
    /**
     * Actor attribute column builder.
     * 
     * @return actor column
     */
    private TableColumn actorCol() {
        TableColumn<Story, String> actorCol = new TableColumn<>(
               bundle.getString("stories.table.actor")
       );
        
        StandardTableColumn.apply(actorCol, ACTOR_COL_MIN_WIDTH);
        
        actorCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(
                    data.getValue().getActor().getName()
            );
        });
        
        return actorCol;
    }
    
    /**
    * Intention attribute column builder.
    * 
    * @return intention column
    */
   private TableColumn intentionCol() {
        TableColumn<Story, String> intentCol = new TableColumn<>(
                bundle.getString("stories.table.intention")
        );
        
        StandardTableColumn.apply(intentCol, INTENTION_COL_MIN_WIDTH);
        intentCol.setCellValueFactory(new PropertyValueFactory("intention"));
        
        return intentCol;
    }
   
   /**
    * Expectation attribute column builder.
    * 
    * @return expectation column
    */
   private TableColumn expectationCol() {
        TableColumn<Story, String> expectationCol = new TableColumn<>(
                bundle.getString("stories.table.expectation")
        );
        
        StandardTableColumn.apply(expectationCol, EXPECTATION_COL_MIN_WIDTH);

        expectationCol.setCellValueFactory(
                new PropertyValueFactory("expectation")
        );
        
        return expectationCol;
    }
}
