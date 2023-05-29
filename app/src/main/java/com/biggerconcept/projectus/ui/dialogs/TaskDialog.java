package com.biggerconcept.projectus.ui.dialogs;

import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Size.TaskSize;
import com.biggerconcept.projectus.domain.Task;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Task management dialog.
 * 
 * @author Andrew Bigger
 */
public class TaskDialog {
    /**
     * Resource bundle for dialog.
     */
    private ResourceBundle bundle;
    
    /**
     * Pointer to the epic that the task belongs to.
     */
    private Epic epic;
    
    /**
     * Task currently being managed by the dialog.
     */
    private Task currentTask;
    
    /**
     * Task name text field.
     */
    private TextField nameField;
    
    /**
     * Task size combo box.
     */
    private ComboBox sizeField;
    
    /**
     * Task description text area.
     */
    private TextArea descriptionField;
    
    /**
     * Task acceptance criteria text area.
     */
    private TextArea acceptanceCriteriaField;
    
    /**
     * Constructor for task dialog
     * 
     * @param b
     * @param e
     * @param t 
     */
    public TaskDialog(ResourceBundle b, Epic e, Task t) {
        bundle = b;
        epic = e;
        currentTask = t;
        
        nameField = new TextField(currentTask.getName());
        
        sizeField = new ComboBox();
        sizeField.getItems().addAll(TaskSize.values());
        sizeField.getSelectionModel().select(currentTask.getSize());
        
        descriptionField = new TextArea(currentTask.getDescription());
        acceptanceCriteriaField = new TextArea(
                currentTask.getAcceptanceCriteria()
        );
    }
    
    /**
     * Shows task manager dialog on the given stage.
     * 
     * This calls the attribute constructor private functions to build a form
     * for editing tasks.
     * 
     * When the apply button is clicked, the callback will create the task
     * in the epic (if it has not already been created) and then apply the 
     * values from the dialog to the task.
     * 
     * @param stage 
     */
    public void show(Stage stage) {
        Dialog<String> dialog = new Dialog<>();
        
        dialog.setTitle(bundle.getString("epic.tasks.dialogs.manage.title"));
        
        ButtonType apply = new ButtonType(
                bundle.getString("epic.tasks.dialogs.manage.actions.save"),
                ButtonData.APPLY
        );
        
        ButtonType cancel = new ButtonType(
                bundle.getString("epic.tasks.dialogs.manage.actions.cancel"),
                ButtonData.CANCEL_CLOSE
        );
        
        VBox wrapper = new VBox();
        wrapper.setSpacing(10);
        
        wrapper.getChildren().addAll(
                nameAttribute(),
                sizeAttribute(),
                descriptionAttribute(),
                acceptanceCriteriaAttribute()
        );
        
        dialog.getDialogPane().setContent(wrapper);
        
        dialog.getDialogPane().getButtonTypes().add(apply);
        dialog.getDialogPane().getButtonTypes().add(cancel);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == apply) {
                applyToTask();
            }
            
            return null;
        });
        
        dialog.showAndWait(); 
    }
    
    /**
     * Ensures that the task exists in the epic.
     * 
     * If the task exists in the epic, then no task will be added.
     * 
     * However, if the task does not exist, then the current task will be
     * added to that epic.
     */
    private void addTaskToEpic() {
        if (epic.hasTask(currentTask)) {
            return;
        }
        
        epic.addTask(currentTask);
    }
    
    /**
     * Applies form to current task.
     * 
     * First we ensure that the task exists in the epic.
     * 
     * Then the contents of the fields are retrieved from the dialog and applied
     * to the current task object.
     */
    private void applyToTask() {
        addTaskToEpic();

        currentTask.setName(nameField.getText());
        currentTask.setSize(
                (TaskSize) sizeField.getSelectionModel().getSelectedItem()
        );

        currentTask.setDescription(descriptionField.getText());
        currentTask.setAcceptanceCriteria(acceptanceCriteriaField.getText());
    }
    
    /**
     * Attribute builder function.
     * 
     * This takes a label and a control for modifying the attribute and 
     * returns it in a vbox wrapper.
     * 
     * @param label
     * @param value
     * @return 
     */
    private VBox attributeFor(Label label, Node value) {
        VBox wrapper = new VBox();
        
        wrapper.setSpacing(5);
        wrapper.getChildren().addAll(label, value);
        
        return wrapper;
    }
    
    /**
     * Name attribute builder.
     * 
     * Builds a vbox with the name label and text field for managing a task
     * name.
     * 
     * @return 
     */
    private VBox nameAttribute() {        
        return attributeFor(
                new Label(bundle.getString("epic.tasks.dialogs.manage.name")),
                nameField
        );
    }
    
    /**
     * Size attribute builder.
     * 
     * Builds a vbox with the size label and a combo box for selecting task
     * size.
     * 
     * @return 
     */
    private VBox sizeAttribute() {
        return attributeFor(
                new Label(bundle.getString("epic.tasks.dialogs.manage.size")),
                sizeField
        );
    }
    
    /**
     * Description attribute builder.
     * 
     * Builds a vbox with the description label and text area for managing
     * task description.
     * 
     * @return 
     */
    private VBox descriptionAttribute() {
        return attributeFor(
                new Label(
                        bundle.getString(
                                "epic.tasks.dialogs.manage.description"
                        )
                ),
                descriptionField
        );
    }
    
    /**
     * Acceptance criteria buiilder.
     * 
     * Builds a vbox with the acceptance criteria label and text area for 
     * managing task acceptance criteria.
     * 
     * @return 
     */
    private VBox acceptanceCriteriaAttribute() {
        return attributeFor(
                new Label(
                        bundle.getString(
                                "epic.tasks.dialogs.manage.acceptanceCriteria"
                        )
                ),
                acceptanceCriteriaField
        );
    }

}
