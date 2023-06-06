package com.biggerconcept.projectus.ui.dialogs;

import com.biggerconcept.appengine.ui.dialogs.StandardDialog;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Size.TaskSize;
import com.biggerconcept.projectus.domain.Task;
import com.biggerconcept.projectus.domain.Task.TaskStatus;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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
    private final ResourceBundle bundle;
    
    /**
     * Pointer to the parentEpic that the task belongs to.
     */
    private final Epic parentEpic;
    
    /**
     * Task currently being managed by the dialog.
     */
    private Task currentTask;
    
    /**
     * Task name text field.
     */
    private final TextField nameField;
    
    /**
     * Task size combo box.
     */
    private final ComboBox sizeField;
    
    /**
     * Task status combo box.
     */
    private final ComboBox statusField;
    
    /**
     * Task description text area.
     */
    private final TextArea descriptionField;
    
    /**
     * Task acceptance criteria text area.
     */
    private final TextArea acceptanceCriteriaField;
    
    /**
     * Constructor for task dialog
     * 
     * @param rb application resource bundle
     * @param epic parent parentEpic of chosen task
     * @param task chosen task
     */
    public TaskDialog(ResourceBundle rb, Epic epic, Task task) {
        bundle = rb;
        parentEpic = epic;
        currentTask = task;
        
        nameField = new TextField();
 
        sizeField = new ComboBox();
        sizeField.getItems().addAll(TaskSize.values());
               
        statusField = new ComboBox();
        statusField.getItems().addAll(TaskStatus.values());
        
        descriptionField = new TextArea();
        descriptionField.setWrapText(true);

        acceptanceCriteriaField = new TextArea();
        acceptanceCriteriaField.setWrapText(true);
        
        mapTaskToDialog();
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
     * @param stage parent window for dialog
     */
    public void show(Stage stage) {
        List<Node> attributes = Arrays.asList(
                taskNav(),
                nameAttribute(),
                sizeAttribute(),
                statusAttribute(),
                descriptionAttribute(),
                acceptanceCriteriaAttribute()
        );
        
        ButtonType apply = StandardDialog.applyAction(
                bundle.getString(
                       "epic.tasks.dialogs.manage.actions.save" 
                )
        );
        
        List<ButtonType> actions = Arrays.asList(
                StandardDialog.cancelAction(
                        bundle.getString(
                                "epic.tasks.dialogs.manage.actions.cancel"
                        )
                ),
                apply
        );
        
        Dialog<String> dialog = StandardDialog.dialog(
            bundle.getString("epic.tasks.dialogs.manage.title"),
            attributes,
            actions,
            apply
        );
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == apply) {
                applyToTask();
                return null;
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
        if (parentEpic.hasTask(currentTask)) {
            return;
        }
        
        parentEpic.addTask(currentTask);
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
        
        currentTask.setStatus(
                (TaskStatus) statusField.getSelectionModel().getSelectedItem()
        );

        currentTask.setDescription(descriptionField.getText());
        currentTask.setAcceptanceCriteria(acceptanceCriteriaField.getText());
    }
    
    /**
     * Name attribute builder.
     * 
     * Builds a VBox with the name label and text field for managing a task
     * name.
     * 
     * @return name attribute
     */
    private VBox nameAttribute() {        
        return StandardDialog.attribute(
                new Label(bundle.getString("epic.tasks.dialogs.manage.name")),
                nameField
        );
    }
    
    /**
     * Size attribute builder.
     * 
     * Builds a VBox with the size label and a combo box for selecting task
     * size.
     * 
     * @return size attribute
     */
    private VBox sizeAttribute() {
        return StandardDialog.attribute(
                new Label(bundle.getString("epic.tasks.dialogs.manage.size")),
                sizeField
        );
    }
    
    /**
     * Status attribute builder.
     * 
     * Builds a VBox with the size label and a combo box for selecting task
     * status.
     * 
     * @return size attribute
     */
    private VBox statusAttribute() {
        return StandardDialog.attribute(
                new Label(bundle.getString("epic.tasks.dialogs.manage.status")),
                statusField
        );
    }
    
    /**
     * Description attribute builder.
     * 
     * Builds a VBox with the description label and text area for managing
     * task description.
     * 
     * @return description attribute
     */
    private VBox descriptionAttribute() {
        return StandardDialog.attribute(
                new Label(
                        bundle.getString(
                                "epic.tasks.dialogs.manage.description"
                        )
                ),
                descriptionField
        );
    }
    
    /**
     * Acceptance criteria builder.
     * 
     * Builds a VBox with the acceptance criteria label and text area for 
     * managing task acceptance criteria.
     * 
     * @return acceptance criteria attribute
     */
    private VBox acceptanceCriteriaAttribute() {
        return StandardDialog.attribute(
                new Label(
                        bundle.getString(
                                "epic.tasks.dialogs.manage.acceptanceCriteria"
                        )
                ),
                acceptanceCriteriaField
        );
    }
    
    private BorderPane taskNav() {
        BorderPane wrapper = new BorderPane();
        
        wrapper.setLeft(prevTaskButton());
        wrapper.setRight(nextTaskButton());
        
        return wrapper;
    }
    
    /**
     * Builds previous action button.
     * 
     * The previous action will change the task to the previous task in the
     * dialog.
     * 
     * Button will be disabled if the parent epic does not include the
     * task.
     * 
     * @return 
     */
    private Button prevTaskButton() {
        Button prevTaskBtn = new Button();
        
        prevTaskBtn.setText(bundle.getString("dialogs.nav.previous"));
        prevTaskBtn.setOnAction((ActionEvent event) -> {
            currentTask = parentEpic.getPrevTask(currentTask);
            mapTaskToDialog();
        });
        
        if (parentEpic.hasTask(currentTask) == false) {
            prevTaskBtn.setDisable(true);
        }
        
        return prevTaskBtn;
    }
    
    /**
     * Builds next action button.
     * 
     * The next action will change the task to the next task in the dialog.
     * 
     * Button will be disabled if the parent epic does not include the
     * task.
     * 
     * @return 
     */
    private Button nextTaskButton() {
        Button nextTaskBtn = new Button();
        
        nextTaskBtn.setText(bundle.getString("dialogs.nav.next"));
        nextTaskBtn.setOnAction((ActionEvent event) -> {
            currentTask = parentEpic.getNextTask(currentTask);
            mapTaskToDialog();
        });
        
        if (parentEpic.hasTask(currentTask) == false) {
            nextTaskBtn.setDisable(true);
        } 
        
        return nextTaskBtn;
    }
        
    /**
     * Sets the value of controls to the value set in currentTask.
     */
    private void mapTaskToDialog() {        
        nameField.setText(currentTask.getName());
        sizeField.getSelectionModel().select(currentTask.getSize());
        statusField.getSelectionModel().select(currentTask.getStatus());
        descriptionField.setText(currentTask.getDescription());
        acceptanceCriteriaField.setText(currentTask.getAcceptanceCriteria());
    }
    
}
