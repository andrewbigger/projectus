package com.biggerconcept.appengine.ui.tables;

import javafx.scene.control.TableColumn;

/**
 * Standard table column
 * 
 * @author Andrew Bigger
 */
public class StandardTableColumn {
    /**
     * Default sortable state of columns.
     */
    public static final boolean DEFAULT_SORTABLE = false;
    
    /**
     * Default column minimum width.
     */
    public static final double DEFAULT_COLUMN_MIN_WIDTH = 150;
    
    /**
     * Apply default standard settings to column.
     * 
     * @param col 
     */
    public static void apply(TableColumn col) {
        StandardTableColumn.apply(col,
                DEFAULT_SORTABLE,
                DEFAULT_COLUMN_MIN_WIDTH
        );
    }
    
    /**
     * Applies standard settings to column with default sortable setting.
     * 
     * @param col
     * @param sortable 
     */
    public static void apply(TableColumn col, boolean sortable) {
        StandardTableColumn.apply(col, sortable, DEFAULT_COLUMN_MIN_WIDTH);
    }
    
    /**
     * Applies standard settings to column with default minimum width setting.
     * 
     * @param col
     * @param minWidth 
     */
    public static void apply(TableColumn col, double minWidth) {
        StandardTableColumn.apply(col, DEFAULT_SORTABLE, minWidth);
    }
    
    /**
     * Applies standard settings to columns.
     * 
     * @param col column to apply changes
     * @param sortable whether the column is sortable
     * @param minWidth minimum width of column
     */
    public static void apply(
            TableColumn col,
            boolean sortable,
            double minWidth
    ) {
        col.setSortable(sortable);
        col.setMinWidth(minWidth);
    }
}
