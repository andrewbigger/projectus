package com.biggerconcept.projectus.ui.dialogs;

import com.biggerconcept.sdk.ui.dialogs.StandardDialog;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.sdk.exceptions.NoChoiceMadeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Epic picker dialog.
 * 
 * @author Andrew Bigger
 */
public class EpicChooserDialog {
    /**
     * Resource bundle for dialog.
     */
    private final ResourceBundle bundle;
    
    /**
     * Epics to choose from.
     */
    private final ArrayList<Epic> epics;
    
    /**
     * List view of epics.
     */
    private ListView epicsListView;
    
    /**
     * Chosen epic to return to caller.
     */
    private Epic chosenEpic;
        
    public EpicChooserDialog(ResourceBundle rb, ArrayList<Epic> epicsList) {
        bundle = rb;
        epics = epicsList;
        
        epicsListView = new ListView();
        
        for (Epic e : epicsList) {
            epicsListView.getItems().add(e.getName());
        }
    }
    /**
     * Shows epic picker dialog.
     * 
     * @param stage window to show dialog in
     * 
     * @return chosen risk
     * 
     * @throws NoChoiceMadeException when no choice is made
     */
    public Epic show(Stage stage) throws NoChoiceMadeException {
        List<Node> attributes = Arrays.asList(
                epicListAttribute()
        );
        
        ButtonType apply = StandardDialog.applyAction(
                bundle.getString(
                    "dialogs.epicChooser.actions.choose"
                )
        );
        
        List<ButtonType> actions = Arrays.asList(
                StandardDialog.cancelAction(
                        bundle.getString(
                                "dialogs.epicChooser.actions.cancel"
                        )
                ),
                apply
        );
        
        Dialog<String> dialog = StandardDialog.dialog(
                bundle.getString("dialogs.epicChooser.title"),
                attributes,
                actions,
                apply
        );
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == apply) {
                int i = epicsListView.getSelectionModel().getSelectedIndex();
                chosenEpic = epics.get(i);
            }
            
            return null;
        });
        
        dialog.showAndWait();
        
        if (chosenEpic == null) {
            throw new NoChoiceMadeException();
        }
        
        return chosenEpic;
    }

     /**
     * Epic list attribute builder.
     * 
     * @return name attribute
     */
    private VBox epicListAttribute() {        
        return StandardDialog.attribute(new Label(
                    bundle.getString("dialogs.epicChooser.description")
                ),
                epicsListView
        );
    }
    
}
