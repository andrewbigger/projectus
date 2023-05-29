package com.biggerconcept.projectus.ui.tables;

import com.biggerconcept.projectus.domain.Preferences;
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
 * View model for tasks table.
 * 
 * @author Andrew Bigger
 */
public class TasksTable {
    /**
     * Sortable constant.
     * 
     * This should be false as reordering tasks are presented in
     * user specified order.
     */
   public static final boolean SORTABLE = false;
   
   /**
    * Default width of ID column.
    */
   public static final int ID_COL_MIN_WIDTH = 100;
   
   /**
    * Default width of name column.
    */
   public static final int NAME_COL_MIN_WIDTH = 350;
   
   /**
    * Default width of size column.
    */
   public static final int SIZE_COL_MIN_WIDTH = 80;
   
   /**
    * Default width of estimate column.
    */
   public static final int ESTIMATE_COL_MIN_WIDTH = 80;
   
   /**
    * Application resource bundle.
    */
   private ResourceBundle bundle;
   
   /**
    * Parent epic number.
    */
   private int epicID;
   
   /**
    * List of epic tasks.
    */
   private ArrayList<Task> tasks;
   
   /**
    * Document preferences.
    */
   private Preferences preferences;
   
   /**
    * Constructor for tasks table view model.
    * 
    * @param rb
    * @param t
    * @param p 
    */
   public TasksTable(
           ResourceBundle rb,
           ArrayList<Task> t,
           Preferences p,
           int e
   ) {
       bundle = rb;
       tasks = t;
       preferences = p;
       epicID = e;
   }
   
   /**
    * Builds tasks table view.
    * 
    * This sets the items of the tasks view to the list of epic tasks.
    * 
    * This also sets the empty state string.
    * 
    * The column builder functions are invoked here to build columns for eac
    * visible attribute.
    * 
    * @param view 
    */
   public void build(TableView view) {
       view.setItems(
               FXCollections.observableArrayList(tasks)
       );
       
       view.setPlaceholder(
               new Label(
                       bundle.getString("epic.tasks.table.empty")
               )
       );

       view.getColumns().setAll(idCol(), nameCol(), sizeCol(), estimateCol());
   }
   
   /**
    * ID attribute column builder.
    * 
    * ID refers to a number assigned by order of the task in the epic.
    * 
    * Constructs a column for the display of task IDs.
    * 
    * The cell factory builds a task number for each task.
    * 
    * @return 
    */
   private TableColumn idCol() {
       TableColumn<Task, String> idCol = new TableColumn<>(
               bundle.getString("epic.tasks.table.id")
       );
        
        idCol.setSortable(SORTABLE);
        idCol.setMinWidth(ID_COL_MIN_WIDTH);
        
        idCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(
                    epicID + "." + String.valueOf(
                            tasks.indexOf(data.getValue()) + 1)
            );
        });
        
        return idCol;
   }
   
   /**
    * Name attribute column builder.
    * 
    * Constructs a column for the task name.
    * 
    * The cell factory retrieves the name property from each instance in the
    * table.
    * 
    * @return 
    */
   private TableColumn nameCol() {
        TableColumn<Task, String> nameCol = new TableColumn<>(
                bundle.getString("epic.tasks.table.name")
        );
        
        nameCol.setSortable(SORTABLE);
        nameCol.setMinWidth(NAME_COL_MIN_WIDTH);
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        
        return nameCol;
    }
   
   /**
    * Size attribute column builder.
    * 
    * Constructs a column for the task size.
    * 
    * The cell factory retrieves the size property from each instance in the
    * table.
    * 
    * @return 
    */
   private TableColumn sizeCol() {
        TableColumn<Task, String> sizeCol = new TableColumn<>(
                bundle.getString("epic.tasks.table.size")
        );
        
        sizeCol.setSortable(SORTABLE);
        sizeCol.setMinWidth(SIZE_COL_MIN_WIDTH);
        sizeCol.setCellValueFactory(new PropertyValueFactory("size"));
        
        return sizeCol;
    }
   
   /**
    * Estimate attribute column builder.
    * 
    * Constructs a column for the task estimate.
    * 
    * The cell factory retrieves the estimate from the preferences.
    * 
    * @return 
    */
   private TableColumn estimateCol() {
       TableColumn<Task, String> estimateCol = new TableColumn<>(
               bundle.getString("epic.tasks.table.estimate")
       );
        
        estimateCol.setSortable(SORTABLE);
        estimateCol.setMinWidth(ESTIMATE_COL_MIN_WIDTH);
        
        estimateCol.setCellValueFactory(data -> {
            
            return new SimpleStringProperty(
                    String.valueOf(
                        preferences.estimateFor(data.getValue().getSize())
                    )
            );
        });
        
        return estimateCol;
   }
}
