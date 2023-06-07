package com.biggerconcept.projectus.ui.tables;

import com.biggerconcept.appengine.ui.tables.StandardTable;
import com.biggerconcept.appengine.ui.tables.StandardTableColumn;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Preferences;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * View model for currentEpics table.
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
     * Width of ID column in currentEpics table.
     */
    public static final int ID_COL_MIN_WIDTH = 100;
    
    /**
     * Width of name column in currentEpics table.
     */
    public static final int NAME_COL_MIN_WIDTH = 350;
    
    /**
     * Width of estimate column.
     */
    public static final int ESTIMATE_COL_MIN_WIDTH = 80;
    
    /**
     * Width of status column.
     */
    public static final int STATUS_COL_MIN_WIDTH = 110;
    
    /**
     * Application resource bundle.
     */
    private final ResourceBundle bundle;
    
    /**
     * Document documentPreferences.
     */
    private final Preferences documentPreferences;
    
    /**
     * List of currentEpics for the table.
     */
    private final ArrayList<Epic> currentEpics;
    
    /**
     * Constructor for currentEpics table view model.
     * 
     * @param rb application resource bundle
     * @param preferences document documentPreferences
     * @param epics epic list
     */
    public EpicsTable(
            ResourceBundle rb, 
            Preferences preferences, 
            ArrayList<Epic> epics
     ) {
        bundle = rb;
        documentPreferences = preferences;
        currentEpics = epics;
    }
    
    /**
     * Binds data to epic table view.
     * 
     * This sets the items of the given view to an observable list of epics.
     * 
     * This also sets the empty table state string.
     * 
     * The column builder functions are invoked here to build columns for
     * each visible attribute.
     * 
     * @param view table view for currentEpics
     */
    public void bind(TableView view) {
        StandardTable.bind(
                view,
                bundle.getString("project.table.empty"),
                FXCollections.observableArrayList(currentEpics),
                Arrays.asList(
                        idCol(), 
                        nameCol(), 
                        estimateCol(), 
                        statusCol()
                )
        );
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
     * @return ID column
     */
    private TableColumn idCol() {               
        TableColumn<Epic, String> idCol = new TableColumn<>(
                bundle.getString("project.table.id")
        );
        
        StandardTableColumn.apply(idCol, ID_COL_MIN_WIDTH);
        
        idCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(
                    String.valueOf(
                            documentPreferences.getEpicStartNumber() + 
                            data.getValue().getIdentifier() + 1
                    )
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
     * @return name column
     */
    private TableColumn nameCol() {
        TableColumn<Epic, String> nameCol = new TableColumn<>(
                bundle.getString("project.table.name")
        );
        
        StandardTableColumn.apply(nameCol, NAME_COL_MIN_WIDTH);
        
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        
        return nameCol;
    }
    
    /**
     * Estimate column builder.
     * 
     * Constructs a column for the display of epic estimate.
     * 
     * @return estimate column
     */
    private TableColumn estimateCol() {
        TableColumn<Epic, String> estimateCol = new TableColumn<>(
                bundle.getString("project.table.estimate")
        );
        
        StandardTableColumn.apply(estimateCol, ESTIMATE_COL_MIN_WIDTH);
        
        estimateCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(
                    String.valueOf(data.getValue().getSize(documentPreferences))
            );
        });
        
        return estimateCol;
    }
    
    /**
     * Status column builder.
     * 
     * @return status column
     */
    private TableColumn statusCol() {               
        TableColumn<Epic, String> statusCol = new TableColumn<>(
                bundle.getString("project.table.status")
        );
        
        StandardTableColumn.apply(statusCol, STATUS_COL_MIN_WIDTH);
        
        statusCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(
                    String.valueOf(
                            data.getValue().calculateStatus()
                    )
            );
        });
        
        return statusCol;
    }

}
