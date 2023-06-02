package com.biggerconcept.projectus.ui.dialogs;

import com.biggerconcept.appengine.ui.dialogs.StandardDialog;
import com.biggerconcept.projectus.domain.Risk;
import com.biggerconcept.projectus.exceptions.NoChoiceMadeException;
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
 * Risk picker dialog.
 * 
 * @author Andrew Bigger
 */
public class RiskChooserDialog {
    /**
     * Resource bundle for dialog.
     */
    private final ResourceBundle bundle;
    
    /**
     * Stories to choose from.
     */
    private final ArrayList<Risk> risks;
    
    /**
     * List view of risks.
     */
    private ListView risksListView;
    
    /**
     * Chosen risk to return to caller.
     */
    private Risk chosenRisk;
        
    public RiskChooserDialog(ResourceBundle rb, ArrayList<Risk> risksList) {
        bundle = rb;
        risks = risksList;
        
        risksListView = new ListView();
        
        int pos = 0;
        for (Risk r : risksList) {
            risksListView.getItems().add(buildRiskName(pos, r));
            
            pos += 1;
        }
    }
    /**
     * Shows risk picker dialog.
     * 
     * @param stage
     * @return
     * @throws NoChoiceMadeException 
     */
    public Risk show(Stage stage) throws NoChoiceMadeException {
        List<Node> attributes = Arrays.asList(
                riskListAttribute()
        );
        
        ButtonType apply = StandardDialog.applyAction(
                bundle.getString(
                    "dialogs.riskChooser.actions.choose"
                )
        );
        
        List<ButtonType> actions = Arrays.asList(
                StandardDialog.cancelAction(
                        bundle.getString(
                                "dialogs.riskChooser.actions.cancel"
                        )
                )
        );
        
        Dialog<String> dialog = StandardDialog.dialog(
                bundle.getString("dialogs.riskChooser.title"),
                attributes,
                actions,
                apply
        );
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == apply) {
                int i = risksListView.getSelectionModel().getSelectedIndex();
                chosenRisk = risks.get(i);
            }
            
            return null;
        });
        
        dialog.showAndWait();
        
        if (chosenRisk == null) {
            throw new NoChoiceMadeException();
        }
        
        return chosenRisk;
    }

     /**
     * Story list attribute builder.
     * 
     * Builds a VBox with the name label and text field for managing a task
     * name.
     * 
     * @return name attribute
     */
    private VBox riskListAttribute() {        
        return StandardDialog.attribute(
                new Label(
                    bundle.getString("dialogs.riskChooser.description")
                ),
                risksListView
        );
    }
    
    /**
     * Builds risk name for presentation.
     * 
     * @param pos
     * @param r
     * @return 
     */
    private String buildRiskName(int pos, Risk r) {
        String name = "";
            
        name += "R";
        name += String.valueOf(pos + 1);
        name += " ";
        name += bundle.getString("risks.table.name");
        name += " ";
        name += r.getName();
        
        return name;
    }
}
