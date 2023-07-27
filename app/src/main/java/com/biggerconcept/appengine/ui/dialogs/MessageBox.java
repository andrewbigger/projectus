package com.biggerconcept.appengine.ui.dialogs;

import com.biggerconcept.projectus.exceptions.NoChoiceMadeException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Standard message box dialog.
 * 
 * @author Andrew Bigger
 */
public class MessageBox {
    /**
     * Creates and displays a message box.
     * 
     * @param type prompt type, controls icon rendered in dialog
     * @param header header text for dialog
     * @param message message text for user
     * 
     */
    public static void show(
            Alert.AlertType type,
            String header, 
            String message
    ) {
        
        Alert confirm = new Alert(
                type,
                message,
                ButtonType.OK);
        
        confirm.setHeaderText(header);
        confirm.showAndWait();
    }
}
