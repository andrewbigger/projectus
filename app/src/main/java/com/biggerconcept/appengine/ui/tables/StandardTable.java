package com.biggerconcept.appengine.ui.tables;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Standard table
 * 
 * @author Andrew Bigger
 */
public class StandardTable {
    /**
     * Configures table view with empty state placeholder.
     * 
     * @param view
     * @param placeholder 
     */
    public static void apply(TableView view, String placeholder) {
        StandardTable.apply(
                view,
                placeholder,
                FXCollections.observableArrayList(new ArrayList<>()),
                new ArrayList<>()
        );
    }
    
    /**
     * Configures table view with placeholder and data.
     * 
     * @param view
     * @param placeholder
     * @param data 
     */
    public static void apply(
            TableView view,
            String placeholder,
            ObservableList data
    ) {
        StandardTable.apply(view, placeholder, data, new ArrayList<>());
    }
    
    /**
     * Configures the given table view with placeholder, data and columns.
     * 
     * @param view
     * @param placeholder
     * @param data
     * @param columns 
     */
    public static void apply(
            TableView view,
            String placeholder,
            ObservableList data,
            List<TableColumn> columns
            
    ) {
        view.setItems(data);
        view.setPlaceholder(new Label(placeholder));
        view.getColumns().setAll(columns);
    }
}
