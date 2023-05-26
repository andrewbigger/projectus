package com.biggerconcept.projectus.ui.dialogs;

import com.biggerconcept.projectus.exceptions.NoChoiceMadeException;
import javafx.scene.control.TextInputDialog;

/**
 * Standard text input prompt.
 * 
 * @author Andrew Bigger
 */
public class TextPrompt {
    /**
     * Prompts the user for text input.
     * 
     * @param title
     * @param question
     * @return
     * @throws NoChoiceMadeException 
     */
    public static String show(String title, String question) 
            throws NoChoiceMadeException {
        
        TextInputDialog prompt = new TextInputDialog();
        
        prompt.setTitle(title);
        prompt.setGraphic(null);
        prompt.setHeaderText(question);
        prompt.showAndWait();
        
        String result = prompt.getEditor().getText().trim();
        
        if ("".equals(result)) {
            throw new NoChoiceMadeException();
        }
        
        return result;
    }
}
