package com.biggerconcept.projectus;

import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.exceptions.NoChoiceMadeException;
import com.biggerconcept.projectus.platform.OperatingSystem;
import com.biggerconcept.projectus.ui.Date;
import com.biggerconcept.projectus.ui.dialogs.ErrorAlert;
import com.biggerconcept.projectus.ui.dialogs.OpenFileDialog;
import com.biggerconcept.projectus.ui.dialogs.SaveFileDialog;
import com.biggerconcept.projectus.ui.dialogs.TextPrompt;
import com.biggerconcept.projectus.ui.dialogs.YesNoPrompt;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
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
     * The document being edited in the main window.
     */
    private Document currentDocument;
    
    /**
     * Extension filter for files.
     */
    private ExtensionFilter fileExtFilter;
    
    /**
     * Resource bundle for application.
     */
    private ResourceBundle bundle;
    
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
     * Initializes the main window.
     * 
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = rb;

        currentDocument = new Document();
        fileExtFilter = new ExtensionFilter(
                "JSON File",
                Arrays.asList("json")
        );
        
        if (OperatingSystem.isMac()) {
            mainMenu.useSystemMenuBarProperty().set(true);
            quitMenuItem.visibleProperty().set(false);
        }

        applyTooltips();
    }

    /**
     * Creates and adds tool tips to UI controls.
     */
    private void applyTooltips() {
        openFileButton.setTooltip(
                new Tooltip(bundle.getString("toolbar.open.tooltip"))
        );
        saveFileButton.setTooltip(
                new Tooltip(bundle.getString("toolbar.save.tooltip"))
        );
        newFileButton.setTooltip(
                new Tooltip(bundle.getString("toolbar.new.tooltip"))
        );
    }
    
    /**
     * Returns the main window stage.
     * 
     * The main window stage is the window that the new file button control is
     * in. 
     * 
     * @return 
     */
    private Stage mainStage() {
        Stage stage = (Stage) newFileButton.getScene().getWindow();
        return stage;
    }
    
    /**
     * Maps given document to window.
     * 
     * @param doc 
     */
    private void mapDocumentToWindow(Document doc) {
        applyPreferencesToWindow();
        
        projectNameTextField.setText(doc.getTitle());
        projectStartDatePicker.setValue(Date.fromEpoch(doc.getStart()));
        projectEndDatePicker.setValue(Date.fromEpoch(doc.getEnd()));
        
        epicsTableView.setItems(
            FXCollections.observableArrayList(currentDocument.getEpics())
        );
        
        // TODO: String
        TableColumn<Epic, String> idCol = new TableColumn<>("#");
        TableColumn<Epic, String> nameCol = new TableColumn<>("Name");
        
        idCol.setSortable(false);
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(
                    String.valueOf(
                            currentDocument
                                    .getEpics()
                                    .indexOf(data.getValue()) + 1)
            );
        });
        
        nameCol.setSortable(false);
        nameCol.setMinWidth(500);
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        
        epicsTableView.getColumns().setAll(idCol, nameCol);
    }
    
    /**
     * Applies document settings to window.
     */
    private void applyPreferencesToWindow() {
        // TODO: apply preferences
    }
    
    /**
     * Maps window content to current document object for serialization.
     * 
     * @return 
     */
    private void mapWindowToDocument() {      
        currentDocument.setTitle(projectNameTextField.getText());
        
        currentDocument.setStart(
                Date.toEpoch(projectStartDatePicker.getValue())
        );
        
        currentDocument.setEnd(Date.toEpoch(projectEndDatePicker.getValue()));
    }
    
    /**
     * Creates a new document.
     * 
     * This will replace the currentDocument with a new one, having the effect
     * of creating a new locale file.
     */
    @FXML
    private void handleCreateNewDocument() {
        try {
            currentDocument = new Document();
            mapDocumentToWindow(currentDocument);
        } catch (Exception e) {
            ErrorAlert.show(bundle, bundle.getString("errors.new"), e);
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
            File documentFile = OpenFileDialog.show(
                    bundle.getString("dialogs.open.title"),
                    mainStage(),
                    fileExtFilter
            );

            currentDocument = Document.load(documentFile);
            mapDocumentToWindow(currentDocument);
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (IOException e) {
            ErrorAlert.show(bundle, bundle.getString("errors.open"), e);
        }
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
            mapWindowToDocument();

            if (currentDocument.getFile() == null) {
                File f = SaveFileDialog.show(
                        bundle.getString("dialogs.save.title"),
                        mainStage(),
                        fileExtFilter
                );

                if (f == null) {
                    return;
                }

                currentDocument.setFile(f);
            }

            currentDocument.save();

        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (IOException e) {
            ErrorAlert.show(bundle, bundle.getString("errors.saveFile"), e);
        }
    }
    
    /**
     * Adds epic to project.
     */
    @FXML
    private void handleAddEpic() {
        try {
            // TODO: Add strings:
            String name = TextPrompt.show(
                    "Add epic",
                    "Please enter the name of the epic"
            );
            
            currentDocument.createEpic(name);
            mapDocumentToWindow(currentDocument);

        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch(Exception e) {
            // TODO: String
            ErrorAlert.show(bundle, bundle.getString("errors.generic"), e);
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
                    "Remove selected epics",
                    "Are you sure you want to remove the selected epic?"
            );
            
            if (answer == ButtonType.YES) {
                for (Epic e: items) {
                    currentDocument.removeEpic(e);
                }
            }
            
            mapDocumentToWindow(currentDocument);
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            // TODO: String
            ErrorAlert.show(bundle, bundle.getString("errors.generic"), e);
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
                    currentDocument.getEpics(),
                    selectedIndex,
                    targetIndex
            );
            
            mapDocumentToWindow(currentDocument);
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            // TODO: String
            ErrorAlert.show(bundle, bundle.getString("errors.generic"), e);
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
                    currentDocument.getEpics(),
                    selectedIndex,
                    targetIndex
            );
            
            mapDocumentToWindow(currentDocument);
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            // TODO: String
            ErrorAlert.show(bundle, bundle.getString("errors.generic"), e);
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
            URL location = getClass().getResource("/fxml/Preferences.fxml");
            FXMLLoader loader = new FXMLLoader();
        
            loader.setLocation(location);
            loader.setResources(bundle);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
        
            Parent preferencePane = (Parent) loader.load();
        
            PreferencesController controller = (PreferencesController) loader
                .getController();
        
            controller.setDocument(currentDocument);
        
            Stage stage = new Stage();
        
            stage.setAlwaysOnTop(true);
            stage.setScene(new Scene(preferencePane));
            stage.setTitle(bundle.getString("dialogs.preferences.title"));
            stage.initStyle(StageStyle.UTILITY);
            stage.resizableProperty().setValue(false);
            
            stage.showAndWait();
        } catch (IOException e) {
            //TODO: Add strings
            ErrorAlert.show(bundle, bundle.getString("errors.generic"), e);
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
                getClass().getResource("/fxml/About.fxml"), bundle
            );

            aboutStage.setAlwaysOnTop(true);
            aboutStage.setScene(new Scene(aboutPane));
            aboutStage.initStyle(StageStyle.UTILITY);
            aboutStage.resizableProperty().setValue(false);

            aboutStage.setTitle(
                    bundle.getString("application.about.windowTitle")
            );
            
            aboutStage.showAndWait();
            
            applyPreferencesToWindow();
        } catch (IOException e) {
            //TODO: Add string
            ErrorAlert.show(bundle, bundle.getString("errors.generic"), e);
        }
    }
    
    /**
     * Opens help website in default browser.
     * 
     */
    @FXML
    private void handleViewHelp() {
        try {
            OperatingSystem.goToUrl(App.HELP_URL);
        } catch (Exception e) {
            //TODO: Add string
            ErrorAlert.show(bundle, bundle.getString("errors.generic"), e);
        }
    }
    
    /**
     * Exits application.
     * 
     * @param event
     */
    @FXML
    private void handleApplicationExit() {
        System.exit(0);
    }

}
