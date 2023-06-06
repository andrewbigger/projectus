package com.biggerconcept.projectus.ui.dialogs;

import com.biggerconcept.appengine.ui.dialogs.StandardDialog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Scope management dialog.
 * 
 * @author Andrew Bigger
 */
public class ScopeDialog {
    /**
     * Resource bundle for dialog.
     */
    private final ResourceBundle bundle;
    
    /**
     * Pointer to scope collection.
     */
    private final ArrayList<String> scopeCollection;
    
    /**
     * Pointer to the actor.
     */
    private String currentScope;
    
    /**
     * Scope text area.
     */
    private final TextField scopeField;
    
    /**
     * Constructor for scope dialog.
     * 
     * @param rb resource bundle for application
     * @param collection scope collection
     * @param scope scope for dialog
     */
    public ScopeDialog(
            ResourceBundle rb,
            ArrayList<String> collection,
            String scope
    ) {
        bundle = rb;
        scopeCollection = collection;
        currentScope = scope;
        
        scopeField = new TextField(scope);
    }
    
    /**
     * Shows scope dialog on specified stage.
     * 
     * @param stage parent window to show dialog from
     */
    public void show(Stage stage) {
        List<Node> attributes = Arrays.asList(
                nameAttribute()
        );
        
        ButtonType apply = StandardDialog.applyAction(
                bundle.getString(
                       "epic.dialogs.scope.actions.save" 
                )
        );
        
        List<ButtonType> actions = Arrays.asList(
                StandardDialog.cancelAction(
                        bundle.getString(
                                "epic.dialogs.scope.actions.cancel"
                        )
                ),
                apply
        );
        
        Dialog<String> dialog = StandardDialog.dialog(
            bundle.getString("epic.dialogs.scope.title"),
            attributes,
            actions,
            apply
        );
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == apply) {
                applyToScope();
            }

            return null;
        });
        
        dialog.showAndWait();
    }
    
    /**
     * Applies scope to collection.
     * 
     * If the scope exists then it is removed from the list.
     * 
     * Then the scope is appended.
     */
    private void applyToScope() {
        currentScope = scopeField.getText();
        
        if (scopeCollection.contains(currentScope)) {
            scopeCollection.remove(currentScope);
        }
        
        scopeCollection.add(currentScope);
    }
    
    /**
     * Name attribute builder.
     * 
     * Builds VBox with the name label and text field for managing epic name.
     * 
     * @return name attribute
     */
    private VBox nameAttribute() {
        return StandardDialog.attribute(
                new Label(bundle.getString("epic.dialogs.scope.manage.scope")
                ),
                scopeField
        );
    }
}
