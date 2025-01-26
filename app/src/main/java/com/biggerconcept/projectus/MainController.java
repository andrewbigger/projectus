package com.biggerconcept.projectus;

import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Risk;
import com.biggerconcept.projectus.domain.Scope;
import com.biggerconcept.projectus.domain.Story;
import com.biggerconcept.projectus.domain.Task;
import com.biggerconcept.projectus.exceptions.DuplicateItemException;
import com.biggerconcept.appengine.exceptions.NoChoiceMadeException;
import com.biggerconcept.appengine.platform.OperatingSystem;
import com.biggerconcept.appengine.reports.IReport;
import com.biggerconcept.appengine.reports.ui.menus.ReportMenuBuilder;
import com.biggerconcept.projectus.helpers.Date;
import com.biggerconcept.appengine.ui.dialogs.ErrorAlert;
import com.biggerconcept.appengine.ui.dialogs.OpenFileDialog;
import com.biggerconcept.projectus.ui.dialogs.RiskChooserDialog;
import com.biggerconcept.appengine.ui.dialogs.SaveFileDialog;
import com.biggerconcept.projectus.ui.dialogs.StoryChooserDialog;
import com.biggerconcept.projectus.ui.dialogs.TaskDialog;
import com.biggerconcept.appengine.ui.dialogs.YesNoPrompt;
import com.biggerconcept.projectus.ui.tables.EpicsTable;
import com.biggerconcept.projectus.ui.tables.RiskTable;
import com.biggerconcept.projectus.ui.tables.StoryTable;
import com.biggerconcept.projectus.ui.tables.TasksTable;
import com.biggerconcept.appengine.ui.windows.StandardWindow;
import com.biggerconcept.projectus.domain.Status;
import com.biggerconcept.projectus.helpers.Compare;
import com.biggerconcept.projectus.ui.dialogs.EpicChooserDialog;
import com.biggerconcept.projectus.ui.dialogs.EpicDialog;
import com.biggerconcept.projectus.ui.dialogs.ScopeDialog;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Controller for the main view.
 * 
 * @author Andrew Bigger
 */
public class MainController implements Initializable {
    /**
     * Application State
     */
    private State state;

    /**
     * Extension filter for files.
     */
    private ExtensionFilter fileExtFilter;
    
    /**
     * List of open windows.
     */
    private ArrayList<Stage> openWindows;
    
    /**
     * Application menu.
     */
    @FXML
    public MenuBar mainMenu;
    
    /**
     * Application exit menu item.
     */
    @FXML
    public MenuItem quitMenuItem;
    
    /**
     * Open file toolbar button.
     * 
     * When clicked, this should prompt the user to choose a file.
     */
    @FXML
    public Button openFileButton;
    
    /**
     * Save file toolbar button.
     * 
     * When clicked, this serializes the current state of the app to file.
     */
    @FXML
    public Button saveFileButton;
    
    /**
     * Toolbar button for creating a new file.
     * 
     * When clicked this will clear the current object and replace it with
     * a new one.
     */
    @FXML
    public Button newFileButton;
    
    /**
     * Project title text box.
     * 
     */
    @FXML
    public TextField projectNameTextField;
    
    /**
     * Project start date picker.
     */
    @FXML
    public DatePicker projectStartDatePicker;
    
    /**
     * Project end date picker.
     */
    @FXML
    public DatePicker projectEndDatePicker;
    
    /**
     * Add epic to project button.
     */
    @FXML
    public Button addEpicButton;
    
    /**
     * Remove epic from project button.
     */
    @FXML
    public Button removeEpicButton;
    
    /**
     * Move epic priority up within project button.
     */
    @FXML
    public Button moveEpicUpButton;
    
    /**
     * Move epic priority down within project button.
     */
    @FXML
    public Button moveEpicDownButton;
    
    /**
     * View of epics
     */
    @FXML
    public TableView epicsTableView;
    
    /**
     * Selected epic panel.
     */
    @FXML
    public TabPane selectedEpicPanel;
    
    /**
     * Selected epic name text field.
     */
    @FXML
    public TextField epicNameTextField;
    
    /**
     * Selected epic tasks table view.
     */
    @FXML
    public TableView tasksTableView;
    
    /**
     * Epic stories table view.
     */
    @FXML
    public TableView storiesTableView;
    
    /**
     * Epic risks table view.
     */
    @FXML
    public TableView risksTableView;
    
    /**
     * Add task to epic button.
     */
    @FXML
    public Button addTaskButton;
    
    /**
     * Remove task from epic button.
     */
    @FXML
    public Button removeTaskButton;
    
    /**
     * Move task up button.
     */
    @FXML
    public Button moveTaskUpButton;
    
    /**
     * Move task down button.
     */
    @FXML
    public Button moveTaskDownButton;
    
    /**
     * Edit selected task button.
     */
    @FXML
    public Button editTaskButton;
    
    /**
     * Defined summary progress bar.
     */
    @FXML
    public ProgressBar definedSummaryProgressBar;
    
    /**
     * Defined scope progress bar.
     */
    @FXML
    public ProgressBar definedScopeProgressBar;
    
    /**
     * Defined status progress bar.
     */
    @FXML
    public ProgressBar definedStatusProgressBar;
    
    /**
     * Defined status count label.
     */
    @FXML
    public Label definedTaskCountLabel;
    
    /**
     * Sized status progress bar.
     */
    @FXML
    public ProgressBar sizedStatusProgressBar;
    
    /**
     * Sized status count label.
     */
    @FXML
    public Label sizedCountLabel;
    
    /**
     * Described status progress bar.
     */
    @FXML
    public ProgressBar describedStatusProgressBar;
    
    /**
     * Described status count label.
     */
    @FXML
    public Label describedCountLabel;
    
    /**
     * Total progress bar.
     */
    @FXML
    public ProgressBar epicTaskProgressBar;
    
    /**
     * Complete task count label.
     */
    @FXML
    public Label completeTaskCountLabel;
    
    /**
     * Total task count label.
     */
    @FXML
    public Label totalTaskCountLabel;
    
    /**
     * Epic points progress bar.
     */
    @FXML
    public ProgressBar epicPointsProgressBar;
    
    /**
     * Completed point count label.
     */
    @FXML
    public Label epicCompletedPointsLabel;
    
    /**
     * Total point count label.
     */
    @FXML
    public Label epicTotalPointsLabel;
    
    /**
     * Scope include list view.
     */
    @FXML
    public ListView includeScopeListView;
    
    /**
     * Scope exclude list view.
     */
    @FXML
    public ListView excludeScopeListView;
    
    /**
     * Summary text area.
     */
    @FXML
    public TextArea epicSummaryTextArea;
    
    /**
     * Project overall status panel.
     */
    @FXML
    public VBox statusPanel;
    
    /**
     * Label to display total number of weeks available in project.
     */
    @FXML
    public Label totalWeeks;
    
    /**
     * Label to display total number of sprints available in project.
     */
    @FXML
    public Label totalSprints;
    
    /**
     * Label to display total number of weeks elapsed in project.
     */
    @FXML
    public Label weeksElapsed;
    
    /**
     * Label to display total number of sprints elapsed in project.
    */
    @FXML
    public Label sprintsElapsed;
    
    /**
     * Label for total number of points defined in project.
     */
    @FXML
    public Label totalPoints;
    
    /**
     * Label for completed points.
     */
    @FXML
    public Label completedPoints;
    
    /**
     * Label to show average number of points completed in a sprint.
     */
    @FXML
    public Label velocity;
    
    /**
     * Label to show status summary.
     */
    @FXML
    public Label tracking;
    
    /**
     * Container for week information.
     */
    @FXML
    public HBox weekSummary;
    
    /**
     * Progress bar for overall sprint progress.
     */
    @FXML
    public ProgressBar sprintProgress;
    
    /**
     * Progress bar for overall point progress.
     */
    @FXML
    public ProgressBar pointProgress;
    
    /**
     * Progress bar for overall commitment.
     */
    @FXML
    public ProgressBar commitmentProgress;
    
    /**
     * Label for commitment points
     */
    @FXML
    public Label commitmentPointsLabel;
    
    /**
     * Label for available commitment.
     */
    @FXML
    public Label availableCommitmentLabel;
    
    /**
     * Panel for commitment status.
     */
    @FXML
    public VBox commitmentPanel;
    
    /**
     * Story manager toolbar button
     */
    @FXML
    public Button manageStoriesButton;
    
    /**
     * Risk manager toolbar button
     */
    @FXML
    public Button manageRisksButton;
    
    /**
     * Move epic button
     */
    @FXML
    public Button moveEpicButton;
    
    /**
     * Move task button
     */
    @FXML
    public Button moveTaskButton;
    
    /**
     * Epic outlook button
     */
    @FXML
    public Button outlookButton;
    
    /**
     * Add included scope button
     */
    @FXML
    public Button addInclScopeButton;
    
    /**
     * Remove included scope button
     */
    @FXML
    public Button removeInclScopeButton;
    
    /**
     * Add excluded scope button
     */
    @FXML
    public Button addExclScopeButton;
    
    /**
     * Remove excluded scope button
     */
    @FXML
    public Button removeExclScopeButton;
    
    /**
     * Add story link button
     */
    @FXML
    public Button addStoryLinkButton;
    
    /**
     * Remove story link button
     */
    @FXML
    public Button removeStoryLinkButton;
    
    /**
     * Move story link up button
     */
    @FXML
    public Button moveStoryUpButton;
    
    /**
     * Move story link down button
     */
    @FXML
    public Button moveStoryDownButton;
    
    /**
     * Add risk link button
     */
    @FXML
    public Button addRiskLinkButton;
    
    /**
     * Remove risk link button
     */
    @FXML
    public Button removeRiskLinkButton;
    
    /**
     * Move risk link up button
     */
    @FXML
    public Button moveRiskUpButton;
    
    /**
     * Move risk link down button
     */
    @FXML
    public Button moveRiskDownButton;
    
    /**
     * Discovery report generator button.
     */
    @FXML
    public Button discoveryReportButton;
    
    /**
     * Reports menu button
     */
    @FXML
    public MenuButton reportsMenuButton;
    
    /**
     * Initializes the main window.
     * 
     * @param url URL of main FXML
     * @param rb application resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        state = new State(this, rb);
        
        fileExtFilter = new ExtensionFilter(
                "JSON File",
                Arrays.asList("json")
        );
        
        epicsTableView.setPlaceholder(
               new Label(
                       state.bundle().getString("project.table.empty")
               )
       );

        selectedEpicPanel.setVisible(false);
        statusPanel.setVisible(false);
        weekSummary.setVisible(false);
        commitmentPanel.setVisible(false);
        
        if (OperatingSystem.isMac()) {
            mainMenu.useSystemMenuBarProperty().set(true);
            quitMenuItem.visibleProperty().set(false);
        }
        
        openWindows = new ArrayList<Stage>();
        
        applyTooltips();
    }
    
    /**
     * Returns state.
     * 
     * @return application state
     */
    public State getState() {
        return state;
    }
    
    /**
     * Assembles window title.
     * 
     * @return 
     */
    private String buildWindowTitle() {
        String title = state.getOpenDocument().getTitle();
        
        if (title == null || title.trim() == "") {
            title = state.bundle().getString("document.defaultTitle");
        }
        
        return title + " - " + state.bundle().getString("application.name");
    }
    
    /**
     * Sets window title to main stage.
     */
    private void setWindowTitle() {
        window().setTitle(buildWindowTitle());
    }

    /**
     * Creates and adds tool tips to UI controls.
     */
    private void applyTooltips() {
        openFileButton.setTooltip(
                new Tooltip(state.bundle().getString("toolbar.open.tooltip"))
        );
        saveFileButton.setTooltip(
                new Tooltip(state.bundle().getString("toolbar.save.tooltip"))
        );
        newFileButton.setTooltip(
                new Tooltip(state.bundle().getString("toolbar.new.tooltip"))
        );
        addEpicButton.setTooltip(
                new Tooltip(state.bundle().getString("toolbar.add.tooltip"))
        );
        removeEpicButton.setTooltip(
                new Tooltip(state.bundle().getString("toolbar.remove.tooltip"))
        );
        moveEpicUpButton.setTooltip(
                new Tooltip(state.bundle().getString("toolbar.moveUp.tooltip"))
        );
        moveEpicDownButton.setTooltip(
                new Tooltip(state.bundle().getString("toolbar.moveDown.tooltip"))
        );
        manageStoriesButton.setTooltip(
                new Tooltip(state.bundle().getString("toolbar.storyManager.tooltip"))
        );
        manageRisksButton.setTooltip(
                new Tooltip(state.bundle().getString("toolbar.riskManager.tooltip"))
        );
        moveEpicButton.setTooltip(
                new Tooltip(state.bundle().getString("toolbar.moveEpic.tooltip"))
        );
        outlookButton.setTooltip(
                new Tooltip(state.bundle().getString("toolbar.outlook.tooltip"))
        );
        discoveryReportButton.setTooltip(
                new Tooltip(state.bundle().getString("toolbar.discovery.tooltip"))
        );
        addTaskButton.setTooltip(
                new Tooltip(state.bundle().getString("epic.toolbar.add.tooltip"))
        );
        removeTaskButton.setTooltip(
                new Tooltip(state.bundle().getString("epic.toolbar.remove.tooltip"))
        );
        moveTaskUpButton.setTooltip(
                new Tooltip(state.bundle().getString("epic.toolbar.moveUp.tooltip"))
        );
        moveTaskDownButton.setTooltip(
                new Tooltip(state.bundle().getString("epic.toolbar.moveDown.tooltip"))
        );
        editTaskButton.setTooltip(
                new Tooltip(state.bundle().getString("epic.toolbar.edit.tooltip"))
        );
        moveTaskButton.setTooltip(
                new Tooltip(state.bundle().getString("epic.toolbar.moveTask.tooltip"))
        );
        addInclScopeButton.setTooltip(
                new Tooltip(state.bundle().getString("epic.tabs.scope.include.add.tooltip"))
        );
        removeInclScopeButton.setTooltip(
                new Tooltip(state.bundle().getString("epic.tabs.scope.include.remove.tooltip"))
        );
        addExclScopeButton.setTooltip(
                new Tooltip(state.bundle().getString("epic.tabs.scope.exclude.add.tooltip"))
        );
        removeExclScopeButton.setTooltip(
                new Tooltip(state.bundle().getString("epic.tabs.scope.exclude.remove.tooltip"))
        );
        addStoryLinkButton.setTooltip(
                new Tooltip(state.bundle().getString("epic.tabs.story.add.tooltip"))
        );
        removeStoryLinkButton.setTooltip(
                new Tooltip(state.bundle().getString("epic.tabs.story.remove.tooltip"))
        );
        moveStoryUpButton.setTooltip(
                new Tooltip(state.bundle().getString("epic.tabs.story.moveUp.tooltip"))
        );
        moveStoryDownButton.setTooltip(
                new Tooltip(state.bundle().getString("epic.tabs.story.moveDown.tooltip"))
        );
        addRiskLinkButton.setTooltip(
                new Tooltip(state.bundle().getString("epic.tabs.risks.add.tooltip"))
        );
        removeRiskLinkButton.setTooltip(
                new Tooltip(state.bundle().getString("epic.tabs.risks.remove.tooltip"))
        );
        moveRiskUpButton.setTooltip(
                new Tooltip(state.bundle().getString("epic.tabs.risks.moveUp.tooltip"))
        );
        moveRiskDownButton.setTooltip(
                new Tooltip(state.bundle().getString("epic.tabs.risks.moveDown.tooltip"))
        );
        definedSummaryProgressBar.setTooltip(
                new Tooltip(state.bundle().getString("epic.tabs.overview.progress.summary.tooltip"))
        );
        definedScopeProgressBar.setTooltip(
                new Tooltip(state.bundle().getString("epic.tabs.overview.progress.scope.tooltip"))
        );
        definedStatusProgressBar.setTooltip(
                new Tooltip(state.bundle().getString("epic.tabs.overview.progress.descriptions.tooltip"))
        );
        sizedStatusProgressBar.setTooltip(
                new Tooltip(state.bundle().getString("epic.tabs.overview.progress.sized.tooltip"))
        );
        describedStatusProgressBar.setTooltip(
                new Tooltip(state.bundle().getString("epic.tabs.overview.progress.described.tooltip"))
        );
    }
    
    /**
     * Returns the main window.
     * 
     * The main window stage is the window that the new file button control is
     * in. 
     * 
     * @return controller window
     */
    private Stage window() {
        return (Stage) newFileButton.getScene().getWindow();
    }
    
    /**
     * Maps given document to window.
     */
    public void mapDocumentToWindow() {
        state.getOpenDocument().rebuildIdentifiers();
        
        setWindowTitle();
        mapProjectDetailsToWindow();
        mapEpicsToWindow();
        mapSelectedEpicToWindow();
        mapStatusToWindow();
        mapReportsToWindow();
    }
    
    private void mapStatusToWindow() {
        Status status = state.getOpenDocument().status();
        
        if (status.hasDates() && status.hasStarted() == true) {
            statusPanel.setVisible(true);
            weekSummary.setVisible(true);
            
            totalWeeks.setText(
                    String.valueOf(status.totalWeeks())
            );

            totalSprints.setText(
                    String.valueOf(status.totalSprints())
            );

            weeksElapsed.setText(
                    String.valueOf(status.weeksElapsed())
            );

            sprintsElapsed.setText(
                    String.valueOf(status.sprintsElapsed())
            );

            totalPoints.setText(
                    String.valueOf(status.totalPoints())
            );
            
            completedPoints.setText(
                    String.valueOf(status.completedPoints())
            );
            
            velocity.setText(
                    String.valueOf(status.pointsPerSprint())
            );
            
            tracking.setText(String.valueOf(status.summary()));
            sprintProgress.setProgress(status.sprintProgress());
            pointProgress.setProgress(status.pointProgress());
        } else {
            statusPanel.setVisible(false);
            weekSummary.setVisible(false);
        }
        
        if (status.totalPoints() > 0) {
            commitmentPanel.setVisible(true);
            
            commitmentProgress.setProgress(status.availablePointsProgress());

            if (status.isOverCommitted()) {
                commitmentProgress.getStyleClass().add("overcommitment");
            } else {
                commitmentProgress.getStyleClass().remove("overcommitment");
            }

            commitmentPointsLabel.setText(
                    String.valueOf(status.totalPoints())
            );

            availableCommitmentLabel.setText(
                    String.valueOf(status.availablePoints())
            );
        } else {
            commitmentPanel.setVisible(false);
        }
    }
    
    private void mapProjectDetailsToWindow() {
        String title = state.getOpenDocument().getTitle();
        String enteredTitle = projectNameTextField.getText();
        
        if (Compare.notEmptyIsDifferent(title, enteredTitle)) {
            projectNameTextField.setText(title);
        }
        
        long start = state.getOpenDocument().getStart();
        projectStartDatePicker.setValue(Date.fromEpoch(start));
        
        long end = state.getOpenDocument().getEnd();
        projectEndDatePicker.setValue(Date.fromEpoch(end));
    }
    
    private void mapEpicsToWindow() {
        if (state.getOpenDocument().getEpics() == null) {
            epicsTableView.getItems().clear();
            
            return;
        }
        EpicsTable epicsTable = new EpicsTable(
                state.bundle(),
                state.getOpenDocument().getPreferences(),
                state.getOpenDocument().getEpics()
        );
        
        epicsTable.bind(epicsTableView);
    }
    
    private void mapSelectedEpicToWindow() {
        mapEpicOverviewToWindow();
        mapEpicScopeToWindow();
        mapEpicStoriesToWindow();
        mapEpicTasksToWindow();
        mapEpicRisksToWindow();

        if (state.getOpenEpic() != null) {
            selectedEpicPanel.setVisible(true);
        } else {
            selectedEpicPanel.setVisible(false);
        }
    }
    
    private void mapEpicOverviewToWindow() {
        if (state.getOpenEpic() == null) {
            epicNameTextField.setText("");
            epicSummaryTextArea.setText("");
            
            return;
        }
        
        // overview tab
        // epic name text field
        epicNameTextField.setText(state.getOpenEpic().getName());

        // epic summary text field
        epicSummaryTextArea.setWrapText(true);
        epicSummaryTextArea.setText(state.getOpenEpic().getSummary());

        mapEpicOverviewStatusToWindow();
    }
    
    private void mapEpicOverviewStatusToWindow() {
        if (state.getOpenEpic() == null) {
            definedSummaryProgressBar.setProgress(0);
            definedScopeProgressBar.setProgress(0);
            definedStatusProgressBar.setProgress(0);
            sizedCountLabel.setText("");
            sizedStatusProgressBar.setProgress(0);
            describedCountLabel.setText("");
            describedStatusProgressBar.setProgress(0);
            epicTaskProgressBar.setProgress(0);
            completeTaskCountLabel.setText("");
            totalTaskCountLabel.setText("");
            epicPointsProgressBar.setProgress(0);
            epicCompletedPointsLabel.setText("");
            epicTotalPointsLabel.setText("");
            
            return;
        }
        
        // status section
        // defined task status
        definedSummaryProgressBar.setProgress(
                state.getOpenEpic().calculateSummaryProgress()
        );

        definedScopeProgressBar.setProgress(
                state.getOpenEpic().calculateScopeProgress()
        );

        definedStatusProgressBar.setProgress(
                state.getOpenEpic().calculateDefinitionProgress()
        );

        // sized task status
        sizedCountLabel.setText(
                String.valueOf(
                        state.getOpenEpic().calculateSizedCount()
                )
        );

        sizedStatusProgressBar.setProgress(
                state.getOpenEpic().calculateSizedProgress()
        );

        // described task status
        describedCountLabel.setText(
                String.valueOf(
                        state.getOpenEpic().calculateDescribedCount()
                )
        );

        describedStatusProgressBar.setProgress(
                state.getOpenEpic().calculateDescribedProgress()
        );

        // progress section
        // task progress

        epicTaskProgressBar.setProgress(
                state.getOpenEpic().calculateCompleteProgress()
        );

        completeTaskCountLabel.setText(
                String.valueOf(
                        state.getOpenEpic().calculateCompleteCount()
                )
        );

        totalTaskCountLabel.setText(
                String.valueOf(
                        state.getOpenEpic().calculateTaskCount()
                )
        );

        // point progress
        epicPointsProgressBar.setProgress(
                state.getOpenEpic().calculateCompletePointProgress(
                        state.getOpenDocument().getPreferences()
                )
        );

        epicCompletedPointsLabel.setText(
                String.valueOf(
                        state.getOpenEpic().calculateCompletePointCount(
                                state.getOpenDocument().getPreferences()
                        )
                )
        );

        epicTotalPointsLabel.setText(
                String.valueOf(
                        state.getOpenEpic().getSize(
                                state.getOpenDocument().getPreferences()
                        )
                )
        );
    }
    
    private void mapEpicScopeToWindow() {
        if (state.getOpenEpic() == null) {
            includeScopeListView.getItems().clear();
            excludeScopeListView.getItems().clear();
            
            return;
        }
        
        // scope tab
        includeScopeListView.getItems().clear();

        for (String in : state.getOpenEpic().getScope().getIncluded()) {
            includeScopeListView.getItems().add(in);
        }

        excludeScopeListView.getItems().clear();
        
        for (String out : state.getOpenEpic().getScope().getExcluded()) {
            excludeScopeListView.getItems().add(out);
        }

    }
    
    private void mapEpicStoriesToWindow() {
        if (state.getOpenEpic() == null) {
            return;
        }

        // stories tab
        // stories table
        StoryTable storiesTable = new StoryTable(
                state.bundle(),
                state.getOpenEpic().getDocumentStories()
        );

        storiesTable.bind(storiesTableView);
    }
    
    private void mapEpicTasksToWindow() {
        if (state.getOpenEpic() == null) {
            return;
        }

        // tasks tab
        // tasks table
        TasksTable tasksTable = new TasksTable(
                state.bundle(),
                state.getOpenEpic().getTasks(),
                state.getOpenDocument().getPreferences(),
                state.getOpenEpic().getIdentifier()
        );

        tasksTable.bind(tasksTableView);
    }
    
    private void mapEpicRisksToWindow() {
        if (state.getOpenEpic() == null) {
            return;
        }
        
        // risks tab
        // risks table
        RiskTable risksTable = new RiskTable(
                state.bundle(),
                state.getOpenEpic().getDocumentRisks()
        );

        risksTable.bind(risksTableView);
    }
    
    private void mapReportsToWindow() {
        ArrayList<IReport> reports = state
                .getOpenDocument()
                .getPreferences()
                .getReports();
        
        ReportMenuBuilder.build(
                state.bundle(),
                reportsMenuButton, 
                reports,
                state.getReportContent()
        );
    }
    
    /**
     * Maps window content to current document object for serialization.
     */
    public void mapWindowToDocument() {  
        state.getOpenDocument().setTitle(projectNameTextField.getText());
        
        state.getOpenDocument().setStart(
                Date.toEpoch(projectStartDatePicker.getValue())
        );
        
        state.getOpenDocument().setEnd(Date.toEpoch(projectEndDatePicker.getValue()));
        
        if (state.getOpenEpic() != null) {
            state.getOpenEpic().setName(epicNameTextField.getText());
            state.getOpenEpic().setSummary(epicSummaryTextArea.getText());
            
            Scope s = new Scope();
            
            for (Object in : includeScopeListView.getItems()) {
                s.getIncluded().add(in.toString());
            }
            
            for (Object out : excludeScopeListView.getItems()) {
                s.getExcluded().add(out.toString());
            }

            state.getOpenEpic().setScope(s);
        }
    }
    
    /**
     * Closes dependent windows.
     * 
     * Any exception thrown is ignored.
     */
    private void closeAllDependentWindows() {
        for (Stage s : openWindows) {
            try {
                s.close();
            } catch (Exception e) {
                // ignore
            }
        }
        
        openWindows.clear();
    }
    
    /**
     * Creates new Projectus window.
     */
    @FXML
    private void handleNewWindow() {
        try {
             FXMLLoader loader = StandardWindow.load(
                    this,
                    state.bundle(),
                    "/fxml/Main.fxml"
            );
            
            Parent window = (Parent) loader.load();
            
            Stage stage = StandardWindow.setup(
                    window,
                    state.bundle().getString("application.name"),
                    "/fxml/Application.css",
                    false,
                    StageStyle.DECORATED,
                    true
            );
            
            stage.show();
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.generic"), e);
        }
    }
    
    /**
     * Creates a new document.
     * 
     * This will replace the state.getOpenDocument() with a new one, having the effect
     * of creating a new locale file.
     */
    @FXML
    private void handleCreateNewDocument() {
        try {
            closeAllDependentWindows();
            
            state.reset();
            state.mapDocumentToWindow();
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.new"), e);
        }
    }
    
    /**
     * Opens a document.
     * 
     * Shows open file dialog to allow user to choose a file.
     * 
     * If there is a choice then the document is deserialized and loaded
     * into memory.
     * 
     * If there is an error deserializing the document a error alert will be
     * shown.
     * 
     * When no choice is made, nothing will be done.
     */
    @FXML
    private void handleOpenDocument() {
        try {
            File documentFile = OpenFileDialog.show(state.bundle().getString("dialogs.open.title"),
                    window(),
                    fileExtFilter
            );
            
            openDocument(documentFile);
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (IOException e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.open"), e);
        }
    }
    
    /**
     * Opens document
     * 
     * @param documentFile file to open
     * @throws IOException when unable to open document
     */
    public void openDocument(File documentFile) throws IOException {
        closeAllDependentWindows();

        state.setOpenDocument(Document.load(documentFile));
        state.setOpenEpic(null);
        state.mapDocumentToWindow();
    }
    
    /**
     * Saves open document.
     * 
     * When the file is not set, a save file dialog is shown. If a location is
     * not picked, then nothing will happen.
     * 
     * The document will then be saved to disk.
     */
    @FXML
    private void handleSaveDocument() {
        try {
            state.mapWindowToDocument();

            if (state.getOpenDocument().getFile() == null) {
                File f = SaveFileDialog.show(state.bundle().getString("dialogs.save.title"),
                        window(),
                        fileExtFilter
                );

                if (f == null) {
                    return;
                }

                state.getOpenDocument().setFile(f);
            }
            
            state.getOpenDocument().rebuildIdentifiers();
            state.getOpenDocument().save();
            state.mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (IOException e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.saveFile"), e);
        }
    }
    
    /**
     * Adds epic to project.
     */
    @FXML
    private void handleAddEpic() {
        try {
             EpicDialog addEpic = new EpicDialog(
                    state.bundle(),
                    state.getOpenDocument(),
                    new Epic()
            );
            
            addEpic.show(window());
            state.mapDocumentToWindow();
        } catch(Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.addEpic"), e);
        }
    }
    
    /**
     * Removes selected epic from project.
     */
    @FXML
    private void handleRemoveEpic() {
        try {
            ObservableList<Epic> items = epicsTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            ButtonType answer = YesNoPrompt.show(
                    AlertType.CONFIRMATION,
                    state.bundle().getString("epic.dialogs.remove.title"),
                    state.bundle().getString("epic.dialogs.remove.description")
            );
            
            if (answer == ButtonType.YES) {
                for (Epic e: items) {
                    state.getOpenDocument().removeEpic(e);
                }
            }

            state.setOpenEpic(null);
            state.mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.removeEpic"), e);
        }
        
    }
    
    /**
     * Moves selected epic up the list.
     */
    @FXML
    private void handleMoveEpicUp() {
        try {
            ObservableList<Epic> items = epicsTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            int selectedIndex = epicsTableView
                    .getItems()
                    .indexOf(items.get(0));
            
            int targetIndex = selectedIndex - 1;
            
            if (targetIndex < 0) {
                throw new NoChoiceMadeException();
            }
            
            Collections.swap(
                    state.getOpenDocument().getEpics(),
                    selectedIndex,
                    targetIndex
            );
            
            state.mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.moveEpic"), e);
        }
    }
    
    /**
     * Moves selected epic down the list.
     */
    @FXML
    private void handleMoveEpicDown() {
        try {
            ObservableList<Epic> items = epicsTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            int selectedIndex = epicsTableView
                    .getItems()
                    .indexOf(items.get(0));
            
            int targetIndex = selectedIndex + 1;
            
            if (targetIndex > epicsTableView.getItems().size() - 1) {
                throw new NoChoiceMadeException();
            }
            
            Collections.swap(
                    state.getOpenDocument().getEpics(),
                    selectedIndex,
                    targetIndex
            );
            
            state.mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.moveEpic"), e);
        }
    }
    
    /**
     * Moves epic to another document.
     */
    @FXML
    private void handleMoveEpic() {
        try {
            ObservableList<Epic> items = epicsTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            File documentFile = OpenFileDialog.show(
                    state.bundle().getString("dialogs.moveEpic.chooseProject"),
                    window(),
                    fileExtFilter
            );
            
            ButtonType answer = YesNoPrompt.show(
                    AlertType.CONFIRMATION,
                    state.bundle().getString("dialogs.moveEpic.title"),
                    state.bundle().getString("dialogs.moveEpic.description")
            );
            
            if (answer == ButtonType.NO) {
                throw new NoChoiceMadeException();
            }

            Document chosenDocument = Document.load(documentFile);
            
            for (Epic e : items) {                
                for (Story s : e.getDocumentStories()) {
                    if (chosenDocument.findActor(s.getActor().getId()) == null) {
                        chosenDocument.addActor(s.getActor());
                    }

                    if (chosenDocument.hasStory(s) == false) {
                        chosenDocument.addStory(s);
                    }
                }
                
                for (Risk r : e.getDocumentRisks()) {
                    if (chosenDocument.hasRisk(r) == false) {
                        chosenDocument.addRisk(r);
                    }
                }
                
                state.getOpenDocument().removeEpic(e);
                chosenDocument.addEpic(e);
            }
            
            state.getOpenDocument().save();
            chosenDocument.save();
            
            state.mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.moveEpic"), e);
        }
    }
    
    /**
     * Shows manage stories window.
     */
    @FXML
    private void handleOpenOutlook() {
        try {            
            ObservableList<Epic> items = epicsTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            FXMLLoader loader = StandardWindow.load(
                    this,
                    state.bundle(),
                    "/fxml/Outlook.fxml"
            );
            
            Parent outlookPane = (Parent) loader.load();
            
            OutlookController controller = (OutlookController) loader
                .getController();
            
            controller.setPreferences(state.getOpenDocument().getPreferences());
            controller.setEpic(items.get(0));

            Stage stage = StandardWindow.setup(
                    outlookPane,
                    state.getOpenEpic().getName(),
                    "/fxml/Application.css",
                    StageStyle.UTILITY
            );
            
            stage.setResizable(false);
            
            openWindows.add(stage);
            stage.showAndWait();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
             ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.outlook.open"),
                    e
            );
        }
    }
    
    /**
     * Handler for epic selection.
     * 
     * When triggered, the selection is checked to see if an epic has been
     * selected. If an epic has been selected, then the current epic pointer
     * is updated to the selected epic, this results in the epic being shown
     * in the right hand side panel.
     * 
     * When no epic is chosen, the right hand side panel is hidden.
     */
    @FXML
    private void handleEpicSelect() {
        try {
            ObservableList<Epic> items = epicsTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                state.setOpenEpic(null);
            } else {
                state.mapWindowToDocument();
                state.setOpenEpic(items.get(0));
            }
            
            state.mapDocumentToWindow();
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.selectEpic"), e);
        }
    }

    /**
     * Handler for the creation of a new epic task.
     * 
     * This constructs a new add task dialog with an empty task.
     * 
     * The task dialog will handle whether or not to save the task into the
     * project. The document is then mapped back to the window.
     */
    @FXML
    private void handleAddTask() {
        try {
            ArrayList<Task> empty = new ArrayList<>();
            empty.add(new Task());
            
            TaskDialog addTask = new TaskDialog(
                    state.bundle(),
                    state.getOpenEpic(),
                    empty,
                    false
            );
            
            addTask.show(window());
            state.mapDocumentToWindow();
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.addTask"), e);
        }
    }
    
    /**
     * Handler for removing an epic task.
     * 
     * This gets the selected item. If nothing is selected then the function
     * will exit.
     * 
     * If a task is selected, the user will be asked whether they want to 
     * delete it.
     * 
     * If the user responds yes, then the task is removed, and the document
     * is mapped to the window.
     */
    @FXML
    private void handleRemoveTask() {
        try {
            ObservableList<Task> items = tasksTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            ButtonType answer = YesNoPrompt.show(
                    AlertType.CONFIRMATION,
                    state.bundle().getString("epic.tasks.dialogs.remove.title"),
                    state.bundle().getString("epic.tasks.dialogs.remove.description")
            );
            
            if (answer == ButtonType.YES) {
                for (Task t: items) {
                    state.getOpenEpic().removeTask(t);
                }
            }
            
            state.mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.removeTask"), e);
        }
    }
    
    /**
     * Handler for moving task up one position in the list.
     * 
     * When invoked, any selected item will be moved up and the document
     * mapped to window.
     * 
     * If nothing is selected, then no change should be made.
     * 
    */
    @FXML
    private void handleMoveTaskUp() {
        try {
            ObservableList<Task> items = tasksTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            int selectedIndex = tasksTableView
                    .getItems()
                    .indexOf(items.get(0));
            
            int targetIndex = selectedIndex - 1;
            
            if (targetIndex < 0) {
                throw new NoChoiceMadeException();
            }
            
            Collections.swap(
                    state.getOpenEpic().getTasks(),
                    selectedIndex,
                    targetIndex
            );
            
            state.mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.moveTask"), e);
        }
    }
    
    /**
     * Handler for moving a task down one position in the list.
     * 
     * When invoked, this will move the task down one position.
     * 
     * If nothing is selected, then no change is made.
     */
    @FXML
    private void handleMoveTaskDown() {
        try {
            ObservableList<Task> items = tasksTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            int selectedIndex = tasksTableView
                    .getItems()
                    .indexOf(items.get(0));
            
            int targetIndex = selectedIndex + 1;
            
            if (targetIndex > tasksTableView.getItems().size() - 1) {
                throw new NoChoiceMadeException();
            }
            
            Collections.swap(
                    state.getOpenEpic().getTasks(),
                    selectedIndex,
                    targetIndex
            );
            
            state.mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.moveTask"), e);
        }
    }
    
    /**
     * Retrieves selected task and then launches manage task dialog.
     */
    @FXML
    private void handleEditTask() {
        try {
            ObservableList<Task> items = tasksTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            TaskDialog manageTask = new TaskDialog(
                    state.bundle(),
                    state.getOpenEpic(),
                    items,
                    items.size() > 1
            );
            
            manageTask.show(window());
            
            state.mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.editTask"), e);
        }
    }
    
    /**
     * Moves task to another epic.
     */
    @FXML
    private void handleMoveTask() {
        try {
            ObservableList<Task> items = tasksTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            EpicChooserDialog pickEpic = new EpicChooserDialog(
                    state.bundle(),
                    state.getOpenDocument().getEpics()
            );
            
            Epic chosenEpic = pickEpic.show(window());
            
            for (Task t : items) {
                state.getOpenEpic().removeTask(t);
                chosenEpic.addTask(t);
            }
            
            state.mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.moveTask"), e);
        }
    }
    
    @FXML
    private void handleAddStoryLink() {
        try {
            StoryChooserDialog pickStory = new StoryChooserDialog(
                    state.bundle(),
                    state.getOpenDocument().getStories()
            );
            
            Story chosenStory = pickStory.show(window());
            
            state.getOpenEpic().addStory(chosenStory);
            
            state.mapDocumentToWindow();
        } catch (DuplicateItemException | NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.addStory"), e);
        }
    }
    
    @FXML
    private void handleRemoveStoryLink() {
        try {
            ObservableList<Story> items = storiesTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            ButtonType answer = YesNoPrompt.show(
                    AlertType.CONFIRMATION,
                    state.bundle().getString("epic.stories.dialogs.remove.title"),
                    state.bundle().getString("epic.stories.dialogs.remove.description")
            );
            
            if (answer == ButtonType.YES) {
                for (Story s: items) {
                    state.getOpenEpic().removeStory(s);
                }
            }
            
            state.mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.removeStory"), e);
        }
    }
    
    @FXML
    private void handleMoveStoryUp() {
        try {
            ObservableList<Story> items = storiesTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            int selectedIndex = storiesTableView
                    .getItems()
                    .indexOf(items.get(0));
            
            int targetIndex = selectedIndex - 1;
            
            if (targetIndex < 0) {
                throw new NoChoiceMadeException();
            }
            
            Collections.swap(
                    state.getOpenEpic().getStories(),
                    selectedIndex,
                    targetIndex
            );
            
            state.mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.moveStory"), e);
        }
    }
    
    @FXML
    private void handleMoveStoryDown() {
        try {
            ObservableList<Story> items = storiesTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            int selectedIndex = storiesTableView
                    .getItems()
                    .indexOf(items.get(0));
            
            int targetIndex = selectedIndex + 1;
            
            if (targetIndex > storiesTableView.getItems().size() - 1) {
                throw new NoChoiceMadeException();
            }
            
            Collections.swap(
                    state.getOpenEpic().getStories(),
                    selectedIndex,
                    targetIndex
            );
            
            state.mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.moveStory"), e);
        }
    }
    
    @FXML
    private void handleAddRiskLink() {
        try {
            RiskChooserDialog pickRisk = new RiskChooserDialog(
                    state.bundle(),
                    state.getOpenDocument().getRisks()
            );
            
            Risk chosenRisk = pickRisk.show(window());
            
            state.getOpenEpic().addRisk(chosenRisk);
            
            state.mapDocumentToWindow();
        } catch (DuplicateItemException | NoChoiceMadeException ncm) {
            // do nothing
        }
    }
    
    @FXML
    private void handleRemoveRiskLink() {
        try {
            ObservableList<Risk> items = risksTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            ButtonType answer = YesNoPrompt.show(
                    AlertType.CONFIRMATION,
                    state.bundle().getString("epic.risks.dialogs.remove.title"),
                    state.bundle().getString("epic.risks.dialogs.remove.description")
            );
            
            if (answer == ButtonType.YES) {
                for (Risk r: items) {
                    state.getOpenEpic().removeRisk(r);
                }
            }
            
            state.mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.remoeRisk"), e);
        }
    }
    
    @FXML
    private void handleMoveRiskUp() {
        try {
            ObservableList<Risk> items = risksTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            int selectedIndex = risksTableView
                    .getItems()
                    .indexOf(items.get(0));
            
            int targetIndex = selectedIndex - 1;
            
            if (targetIndex < 0) {
                throw new NoChoiceMadeException();
            }
            
            Collections.swap(
                    state.getOpenEpic().getRisks(),
                    selectedIndex,
                    targetIndex
            );
            
            state.mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.moveRisk"), e);
        }
    }
    
    @FXML
    private void handleMoveRiskDown() {
        try {
            ObservableList<Risk> items = risksTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            int selectedIndex = risksTableView
                    .getItems()
                    .indexOf(items.get(0));
            
            int targetIndex = selectedIndex + 1;
            
            if (targetIndex > risksTableView.getItems().size() - 1) {
                throw new NoChoiceMadeException();
            }
            
            Collections.swap(
                    state.getOpenEpic().getRisks(),
                    selectedIndex,
                    targetIndex
            );
            
            state.mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.moveRisk"), e);
        }
    }
    
    /**
     * Shows preference dialog.The contents of the dialog are loaded from the 
     * pane FXML.
     * 
     * A call to setup dialog ensures that the dialog is configured 
     * appropriately before it is shown.
     * 
     */
    @FXML
    private void handleOpenPreferencesDialog() {
        try {
            FXMLLoader loader = StandardWindow.load(
                    this,
                    state.bundle(),
                    "/fxml/Preferences.fxml"
            );
            
            Parent preferencePane = (Parent) loader.load();
        
            PreferencesController controller = (PreferencesController) loader
                .getController();
        
            controller.setState(state);
            
            Stage stage = StandardWindow.setup(
                    preferencePane,
                    state.bundle().getString("dialogs.preferences.title"),
                    null,
                    false,
                    StageStyle.DECORATED,
                    false
            );
            
            openWindows.add(stage);
            stage.showAndWait();
            
            state.getOpenDocument().rebuildIdentifiers();
            state.mapDocumentToWindow();
        } catch (IOException e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.preferences.open"),
                    e
            );
        }
    }
    
    /**
     * Shows manage stories window.
     */
    @FXML
    private void handleManageStories() {
        try {
            FXMLLoader loader = StandardWindow.load(
                    this,
                    state.bundle(),
                    "/fxml/Stories.fxml"
            );
            
            Parent storiesPane = (Parent) loader.load();
            
            StoriesController controller = (StoriesController) loader
                    .getController();
            
            controller.setDocument(state.getOpenDocument());
            
            Stage stage = StandardWindow.setup(
                    storiesPane,
                    state.bundle().getString("dialogs.stories.title"),
                    "/fxml/Application.css",
                    StageStyle.DECORATED
            );
            
            openWindows.add(stage);
            stage.showAndWait();
        } catch (Exception e) {
             ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.stories.open"),
                    e
            );
        }
    }
    
    /**
     * Shows manage risks window.
     */
    @FXML
    private void handleManageRisks() {
        try {
            FXMLLoader loader = StandardWindow.load(
                    this,
                    state.bundle(),
                    "/fxml/Risks.fxml"
            );
            
            Parent risksPane = (Parent) loader.load();
            
            RisksController controller = (RisksController) loader
                    .getController();
            
            controller.setDocument(state.getOpenDocument());
            
            Stage stage = StandardWindow.setup(
                    risksPane,
                    state.bundle().getString("risks.dialogs.risk.title"),
                    "/fxml/Application.css",
                    StageStyle.DECORATED
            );
            
            openWindows.add(stage);
            stage.showAndWait();
        } catch (Exception e) {
             ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.risks.open"),
                    e
            );
        }
    }
    
    @FXML
    private void handleAddScope() {
        try {
            ScopeDialog addScope = new ScopeDialog(
                    state.bundle(),
                    state.getOpenEpic().getScope().getIncluded(),
                    ""
            );
            
            addScope.show(window());
            state.mapDocumentToWindow();
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.scope.add"),
                    e
            );
        }
    }
    
    @FXML
    private void handleRemoveScope() {
        try {
            String item = (String) includeScopeListView
                    .getSelectionModel()
                    .getSelectedItem();
            
            if (item == null) {
                throw new NoChoiceMadeException();
            }
            
            ButtonType answer = YesNoPrompt.show(
                    AlertType.CONFIRMATION,
                    state.bundle().getString(
                            "epic.dialogs.scope.included.remove.title"
                    ),
                    state.bundle().getString(
                            "epic.dialogs.scope.included.remove.description"
                    )
            );
            
            if (answer == ButtonType.YES) {
                state.getOpenEpic().getScope().getIncluded().remove(item);
            }
            
            state.mapDocumentToWindow();
            
        } catch(NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.scope.remove"),
                    e
            );
        }
    }
    
    @FXML
    private void handleAddExcludedScope() {
        try {
            ScopeDialog addExcludedScope = new ScopeDialog(
                    state.bundle(),
                    state.getOpenEpic().getScope().getExcluded(),
                    ""
            );
            
            addExcludedScope.show(window());
            state.mapDocumentToWindow();
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.scope.add"),
                    e
            );
        }
    }
    
    @FXML
    private void handleRemoveExcludedScope() {
        try {
            String item = (String) excludeScopeListView
                    .getSelectionModel()
                    .getSelectedItem();
            
            if (item == null) {
                throw new NoChoiceMadeException();
            }
            
            ButtonType answer = YesNoPrompt.show(
                    AlertType.CONFIRMATION,
                    state.bundle().getString(
                            "epic.dialogs.scope.excluded.remove.title"
                    ),
                    state.bundle().getString(
                            "epic.dialogs.scope.excluded.remove.description"
                    )
            );
            
            if (answer == ButtonType.YES) {
                state.getOpenEpic().getScope().getExcluded().remove(item);
            }
            
            state.mapDocumentToWindow();
            
        } catch(NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.scope.remove"),
                    e
            );
        }
    }
    
    @FXML
    private void handleOpenDiscoveryReportDialog() {
        try {
            if (state.getOpenEpic() == null) {
                throw new NoChoiceMadeException();
            }
            
            FXMLLoader loader = StandardWindow.load(
                    this,
                    state.bundle(),
                    "/fxml/DiscoveryReport.fxml"
            );
            
            Parent reportPane = (Parent) loader.load();
        
            DiscoveryReportController controller = 
                    (DiscoveryReportController) loader.getController();
        
            controller.setDocument(state.getOpenDocument());
            controller.setEpic(state.getOpenEpic());
            
            String title = state.bundle().getString("reports.discovery.title")
                    + " ["
                    + state.getOpenEpic().getName()
                    + "]";
            
            Stage stage = StandardWindow.setup(
                    reportPane,
                    title,
                    null,
                    false,
                    StageStyle.DECORATED,
                    false
                    
            );
            
            stage.showAndWait();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Opens about application dialog box.
     */
    @FXML
    private void handleOpenAboutDialog() {
        try {
            Stage aboutStage = new Stage();
        
            Parent aboutPane = FXMLLoader.load(
                getClass().getResource("/fxml/About.fxml"), state.bundle()
            );

            aboutStage.setAlwaysOnTop(true);
            aboutStage.setScene(new Scene(aboutPane));
            aboutStage.initStyle(StageStyle.UTILITY);
            aboutStage.resizableProperty().setValue(false);

            aboutStage.setTitle(
                    state.bundle().getString("application.about.windowTitle")
            );
            
            openWindows.add(aboutStage);
            aboutStage.showAndWait();
        } catch (IOException e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.about.open"), e);
        }
    }
    
    /**
     * Opens help website in default browser.
     */
    @FXML
    private void handleViewHelp() {
        try {
            OperatingSystem.goToUrl(App.HELP_URL);
        } catch (Exception e) {
            ErrorAlert.show(state.bundle(), state.bundle().getString("errors.help.open"), e);
        }
    }
    
    /**
     * Exits application.
     */
    @FXML
    private void handleApplicationExit() {
        System.exit(0);
    }

}
