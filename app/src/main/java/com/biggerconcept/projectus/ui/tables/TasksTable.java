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
 * @author abigger
 */
public class TasksTable {
   public static final boolean SORTABLE = false;
   public static final int ID_COL_MIN_WIDTH = 100;
   public static final int NAME_COL_MIN_WIDTH = 350;
   public static final int SIZE_COL_MIN_WIDTH = 80;
   public static final int ESTIMATE_COL_MIN_WIDTH = 80;
   
   private ResourceBundle bundle;
   private ArrayList<Task> tasks;
   private Preferences preferences;
   
   public TasksTable(ResourceBundle rb, ArrayList<Task> t, Preferences p) {
       bundle = rb;
       tasks = t;
       preferences = p;
   }
   
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
   
   private TableColumn idCol() {
       TableColumn<Task, String> idCol = new TableColumn<>(
               bundle.getString("epic.tasks.table.id")
       );
        
        idCol.setSortable(SORTABLE);
        idCol.setMinWidth(ID_COL_MIN_WIDTH);
        
        idCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(
                    String.valueOf(
                            tasks.indexOf(data.getValue()) + 1)
            );
        });
        
        return idCol;
   }
   
   private TableColumn nameCol() {
        TableColumn<Task, String> nameCol = new TableColumn<>(
                bundle.getString("epic.tasks.table.name")
        );
        
        nameCol.setSortable(SORTABLE);
        nameCol.setMinWidth(NAME_COL_MIN_WIDTH);
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        
        return nameCol;
    }
   
   private TableColumn sizeCol() {
        TableColumn<Task, String> sizeCol = new TableColumn<>(
                bundle.getString("epic.tasks.table.size")
        );
        
        sizeCol.setSortable(SORTABLE);
        sizeCol.setMinWidth(SIZE_COL_MIN_WIDTH);
        sizeCol.setCellValueFactory(new PropertyValueFactory("size"));
        
        return sizeCol;
    }
   
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
