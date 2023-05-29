package com.biggerconcept.projectus.ui.tables;

import com.biggerconcept.projectus.domain.Epic;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * View model for epics table.
 * 
 * @author Andrew Bigger
 */
public class EpicsTable {
    public static final boolean SORTABLE = false;
    public static final int ID_COL_MIN_WIDTH = 100;
    public static final int NAME_COL_MIN_WIDTH = 500;
    
    private ResourceBundle bundle;
    private ArrayList<Epic> epics;
    
    public EpicsTable(ResourceBundle rb, ArrayList<Epic> e) {
        bundle = rb;
        epics = e;
    }
    
    public void build(TableView view) {        
        view.setItems(
                FXCollections.observableArrayList(epics)
        );
        
        view.setPlaceholder(
               new Label(
                       bundle.getString("project.table.empty")
               )
       );

        view.getColumns().setAll(idCol(), nameCol());
    }
    
    private TableColumn idCol() {               
        TableColumn<Epic, String> idCol = new TableColumn<>(
                bundle.getString("project.table.id")
        );
        
        idCol.setSortable(SORTABLE);
        idCol.setMinWidth(ID_COL_MIN_WIDTH);
        
        idCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(
                    String.valueOf(
                            epics.indexOf(data.getValue()) + 1)
            );
        });
        
        return idCol;
    }
    
    private TableColumn nameCol() {
        TableColumn<Epic, String> nameCol = new TableColumn<>(
                bundle.getString("project.table.name")
        );
        
        nameCol.setSortable(SORTABLE);
        nameCol.setMinWidth(NAME_COL_MIN_WIDTH);
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        
        return nameCol;
    }

}
