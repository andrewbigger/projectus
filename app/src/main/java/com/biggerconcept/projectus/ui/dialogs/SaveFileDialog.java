package com.biggerconcept.projectus.ui.dialogs;

import com.biggerconcept.projectus.exceptions.NoChoiceMadeException;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Standard save file dialog.
 * 
 * @author Andrew Bigger
 */
public class SaveFileDialog {
    /**
     * Creates and displays a file save dialog.
     * 
     * This dialog will be shown on the given stage, and when closed, the 
     * choice the user made will be returned to the caller.
     * 
     * If no choice is made, then a NoChoiceMadeException will be thrown.
     * 
     * @param prompt save file prompt, description of dialog purpose
     * @param toolboxStage parent window to show dialog from
     * @param ext save file dialog extension filter
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
        FileChooser fileSaveChoice = new FileChooser();
        
        fileSaveChoice.setTitle(prompt);
        fileSaveChoice.setSelectedExtensionFilter(ext);
        
        File choice = fileSaveChoice.showSaveDialog(toolboxStage);
        
        if (choice == null) throw new NoChoiceMadeException();
        
        return choice;
    }

    public static File show() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
