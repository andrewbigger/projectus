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
    /**
     * Sortable constant.
     * 
     * This should be false as reordering tasks is not supported in table
     * view.
     */
    public static final boolean SORTABLE = false;
    
    /**
     * Width of ID column in epics table.
     */
    public static final int ID_COL_MIN_WIDTH = 100;
    
    /**
     * Width of name column in epics table.
     */
    public static final int NAME_COL_MIN_WIDTH = 500;
    
    /**
     * Application resource bundle.
     */
    private ResourceBundle bundle;
    
    /**
     * List of epics for the table.
     */
    private ArrayList<Epic> epics;
    
    /**
     * Constructor for epics table view model.
     * 
     * @param rb
     * @param e 
     */
    public EpicsTable(ResourceBundle rb, ArrayList<Epic> e) {
        bundle = rb;
        epics = e;
    }
    
    /**
     * Builds epic table view.
     * 
     * This sets the items of the given view to an observable list of epics.
     * 
     * This also sets the empty table state string.
     * 
     * The column builder functions are invoked here to build columns for
     * each visible attribute.
     * 
     * @param view 
     */
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
    
    /**
     * ID attribute column builder.
     * 
     * ID refers to a number assigned by the order of the epic.
     * 
     * Constructs a column for the display of epic IDs.
     * 
     * The cell factory builds an epic number for each epic.
     * 
     * @return 
     */
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
    
    /**
     * Name attribute column builder.
     * 
     * Constructs a column for the display of epic names.
     * 
     * The value retrieves the name property of each epic instance in the 
     * table.
     * 
     * @return 
     */
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
