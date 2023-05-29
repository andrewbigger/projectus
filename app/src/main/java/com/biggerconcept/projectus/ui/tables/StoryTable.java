package com.biggerconcept.projectus.ui.tables;

import com.biggerconcept.projectus.domain.Story;
import com.biggerconcept.projectus.domain.Task;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
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
     * @param rb application resource bundle
     * @param stories list of currentStories
     */
    public StoryTable(
            ResourceBundle rb,
            ArrayList<Story> stories
    ) {
        bundle = rb;
        currentStories = stories;
    }
    
    /**
     * Builds story table view.
     * 
     * @param view story table view
     */
    public void build(TableView view) {
        view.setItems(FXCollections.observableArrayList(currentStories)
        );
        
        view.setPlaceholder(
                new Label(
                        bundle.getString("stories.table.empty")
                )
        );
        
        view.getColumns().setAll(
                idCol(),
                actorCol(),
                intentionCol(),
                expectationCol()
        );
    }
    
    /**
     * ID attribute column builder.
     * 
     * @return ID column
     */
    private TableColumn idCol() {
        TableColumn<Task, String> idCol = new TableColumn<>(
               bundle.getString("stories.table.id")
       );
        
        idCol.setSortable(SORTABLE);
        idCol.setMinWidth(ID_COL_MIN_WIDTH);
        
        idCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(
                    String.valueOf(currentStories.indexOf(data.getValue()) + 1)
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
        
        actorCol.setSortable(SORTABLE);
        actorCol.setMinWidth(ACTOR_COL_MIN_WIDTH);
        
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
        
        intentCol.setSortable(SORTABLE);
        intentCol.setMinWidth(INTENTION_COL_MIN_WIDTH);
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
                bundle.getString("stories.table.intention")
        );
        
        expectationCol.setSortable(SORTABLE);
        expectationCol.setMinWidth(EXPECTATION_COL_MIN_WIDTH);
        expectationCol.setCellValueFactory(
                new PropertyValueFactory("expectation")
        );
        
        return expectationCol;
    }
}
