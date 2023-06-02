package com.biggerconcept.appengine.ui.dialogs;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Standard dialog builder.
 * 
 * @author Andrew Bigger
 */
public class StandardDialog {
    /**
     * Default width of standard dialog. 
     */
    public static final double DEFAULT_WIDTH = 500;
    
    /**
     * Default padding within standard dialog.
     */
    public static final double DEFAULT_PADDING = 10;
    
    /**
     * Default margin between attributes of standard dialog.
     */
    public static final double DEFAULT_MARGIN = 5;
    
    /**
     * Empty dialog builder.
     * 
     * Returns a dialog with a specified title.
     * 
     * @param title
     * @return 
     */
    public static Dialog<String> dialog(
            String title
    ) {
        return dialog(
                title,
                DEFAULT_WIDTH,
                new ArrayList<>(),
                new ArrayList<>(),
                null
        );
    }
    
    /**
     * Default width dialog builder.
     * 
     * Builds a dialog with standard width.
     * 
     * @param title
     * @param nodes
     * @param actions
     * @param defaultAction
     * @return 
     */
    public static Dialog<String> dialog(
            String title,
            List<Node> nodes,
            List<ButtonType> actions,
            ButtonType defaultAction
    ) {
        return dialog(
                title,
                DEFAULT_WIDTH,
                nodes,
                actions,
                defaultAction
        );
    }
    
    /**
     * Standard dialog builder.
     * 
     * The title of the dialog is set to the given string. The width of the 
     * dialog is set to the given value.
     * 
     * All nodes and actions are added to the dialog.
     * 
     * Dialog object is returned to caller.
     * 
     * @param title
     * @param width
     * @param nodes
     * @param actions
     * @param defaultAction
     * 
     * @return 
     */
    public static Dialog<String> dialog(
            String title,
            double width,
            List<Node> nodes,
            List<ButtonType> actions,
            ButtonType defaultAction
    ) {
        javafx.scene.control.Dialog<String> dialog = 
                new javafx.scene.control.Dialog<>();
        
        dialog.setTitle(title);
        dialog.setWidth(width);
        
        DialogPane pane = dialog.getDialogPane();
        
        pane.setContent(StandardDialog.wrap(nodes));
        
        for (ButtonType action : actions) {
            pane.getButtonTypes().add(action);
        }
        
        if (defaultAction != null) {
            Button d = (Button) pane.lookupButton(defaultAction);
            if (d != null) {
                d.setDefaultButton(true);
            }
        }
        
        return dialog;
    }
    
    /**
     * Returns empty dialog control wrapper with standard padding.
     * 
     * @return 
     */
    public static VBox wrap() {
        return wrap(
                DEFAULT_PADDING,
                DEFAULT_WIDTH,
                new ArrayList<>()
        );
    }
 
    /**
     * Wraps given nodes in VBox with default padding.
     * 
     * @param nodes nodes for VBox
     * @return 
     */
    public static VBox wrap(
            List<Node> nodes
    ) {
        return wrap(
                DEFAULT_PADDING,
                DEFAULT_WIDTH,
                nodes
        );
    }
    
    /**
     * Wraps given nodes in VBox with specified padding.
     * 
     * @param padding padding for VBox
     * @param width min width for VBox
     * @param nodes nodes for VBox
     * 
     * @return 
     */
    public static VBox wrap(
            double padding,
            double width,
            List<Node> nodes
    ) {
        VBox wrapper = new VBox();
        
        wrapper.setMinWidth(width);
        wrapper.setSpacing(padding);
        wrapper.getChildren().addAll(nodes);
        
        return wrapper;
    }
    
    /**
     * Builds apply button for a dialog.
     * 
     * Uses the given title to label the button. Button data is set to
     * APPLY.
     * 
     * @param title for apply button
     * @return 
     */
    public static ButtonType applyAction(String title) {
        return action(
                title,
                ButtonData.APPLY
        );
    }
    
    /**
     * Builds cancel button for a dialog.
     * 
     * Uses the given title to label the button. Button data is set to 
     * CANCEL_CLOSE.
     * 
     * @param title for cancel button
     * 
     * @return cancel button type
     */
    public static ButtonType cancelAction(String title) {
        return action(
                title,
                ButtonData.CANCEL_CLOSE
        );
    }
    
    /**
     * Builds action button for a dialog.
     * 
     * Accepts the a title to label the button. Button data is set to
     * given button data.
     * 
     * @param title for button
     * @param data for button
     * 
     * @return 
     */
    public static ButtonType action(String title, ButtonData data) {
        return new ButtonType(
                title,
                data
        );
    }
    
    /**
     * Attribute with label builder function.
     * 
     * Takes a label and control for modifying the attribute and returns
     * it in a VBox wrapper.
     * 
     * @param label label node for the attribute
     * @param control node for presenting and editing attribute
     * 
     * @return attribute wrapped in VBox
     */
    public static VBox attribute(Label label, Node control) {
        VBox wrapper = new VBox();
        
        wrapper.setSpacing(DEFAULT_MARGIN);
        wrapper.getChildren().addAll(label, control);
        
        return wrapper;
    }
    
    /**
     * Attribute builder function.
     * 
     * Takes a label and control for modifying the attribute and returns
     * it in a VBox wrapper.
     * 
     * @param control node for presenting and editing attribute
     * 
     * @return attribute wrapped in VBox
     */
    public static VBox attribute(Node control) {
        VBox wrapper = new VBox();
        
        wrapper.setSpacing(DEFAULT_MARGIN);
        wrapper.getChildren().addAll(control);
        
        return wrapper;
    }
}
