package com.biggerconcept.appengine.ui.dialogs;

import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * A standard alert box for controller errors
 * 
 * @author Andrew Bigger
 */
public class ErrorAlert {
    /**
     * Shows an error alert from a given exception.The action gives the alert the ability to display context about
 what controller action has failed.
     * 
     * The alert text will be the exception message.
     * 
     * @param bundle application resource bundle
     * @param message error message to show
     * @param ex exception that caused message
     */
    public static void show(
            ResourceBundle bundle,
            String message,
            Exception ex
    ) {
       show(
               bundle.getString("errors.alert.title"), 
               message, 
               ex.getMessage()
       );
    }
    
    /**
     * Shows an error alert.
     * 
     * This sets the title, header text and content to the given
     * values.
     * 
     * @param title title of error alert dialog
     * @param header header text of error alert dialog
     * @param content body text of error alert dialog
     */
    public static void show(
            String title,
            String header,
            String content
    ) {
        Alert alert = new Alert(AlertType.ERROR);

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
}
