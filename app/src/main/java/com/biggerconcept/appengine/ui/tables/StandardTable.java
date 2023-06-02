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
     * @param view view to apply table to
     * @param placeholder placeholder text for empty state
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
     * @param view view to apply table to
     * @param placeholder placeholder text for empty state
     * @param data data for table
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
     * @param view view to apply table to
     * @param placeholder placeholder text for empty table
     * @param data data for table
     * @param columns columns for table
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
