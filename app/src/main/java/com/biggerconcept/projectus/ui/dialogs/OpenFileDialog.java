package com.biggerconcept.projectus.ui.dialogs;

import com.biggerconcept.projectus.exceptions.NoChoiceMadeException;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Standard open file dialog.
 * 
 * @author Andrew Bigger
 */
public class OpenFileDialog {
    /**
     * Creates and displays a file choice dialog.
     * 
     * This dialog will be shown on the given stage, and when closed, the
     * choice the user made will be returned to the caller.
     * 
     * If no choice is made, then a NoChoiceMadeException will be thrown.
     * 
     * @param prompt open file prompt, a description of prompt purpose
     * @param toolboxStage parent window to show dialog from
     * @param ext extension filter for dialog
     * 
     * @return chosen file
     * 
     * @throws NoChoiceMadeException when user makes no choice or cancels
     */
    public static File show(
            String prompt, 
            Stage toolboxStage,
            ExtensionFilter ext
    ) 
            throws NoChoiceMadeException {
        FileChooser fileChoice = new FileChooser();
        
        fileChoice.setTitle(prompt);
        fileChoice.setSelectedExtensionFilter(ext);
        
        File choice = fileChoice.showOpenDialog(toolboxStage);
        
        if (choice == null) throw new NoChoiceMadeException();
        
        return choice;
    }
}
