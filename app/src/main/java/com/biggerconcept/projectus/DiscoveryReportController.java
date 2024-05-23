package com.biggerconcept.projectus;

import com.biggerconcept.appengine.exceptions.NoChoiceMadeException;
import com.biggerconcept.appengine.reports.Formats;
import com.biggerconcept.appengine.reports.Formats.Format;
import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.appengine.serializers.documents.Docx;
import com.biggerconcept.appengine.serializers.documents.Markdown;
import com.biggerconcept.appengine.ui.dialogs.ErrorAlert;
import com.biggerconcept.appengine.ui.dialogs.MessageBox;
import com.biggerconcept.appengine.ui.dialogs.OpenFileDialog;
import com.biggerconcept.appengine.ui.dialogs.OpenFolderDialog;
import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.serializers.DiscoveryDocumentSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * Discovery report controller
 * 
 * @author Andrew Bigger
 */
public class DiscoveryReportController implements Initializable {
    /**
     * Resource bundle for preferences window.
     */
    private ResourceBundle bundle;
    
    /**
     * Document domain model.
     */
    private Document currentDocument;
    
    /**
     * Document domain model.
     */
    private Epic currentEpic;
    
    /**
     * Report name text field
     */
    @FXML
    public TextField reportNameTextField;
    
    /**
     * Report format combo box
     */
    @FXML
    public ComboBox reportFormatComboBox;
    
    /**
     * Save location text field
     */
    @FXML
    public TextField reportSaveLocationTextField;
    
    /**
     * Browse save location button
     */
    @FXML
    public Button browseSaveLocationButton;
    
    /**
     * Custom template checkbox
     */
    @FXML
    public CheckBox customTemplateCheckBox;
    
    /**
     * Template file text field
     */
    @FXML
    public TextField templateFileTextField;
    
    /**
     * Browse for template button
     */
    @FXML
    public Button browseTemplateButton;
    
    /**
     * Template file VBOX
     */
    @FXML
    public VBox templateFileVBox;
    
    /**
     * Save button
     */
    @FXML
    public Button saveButton;
    
    /**
     * Initialize-er for the report window
     * 
     * @param url URL for preferences FXML
     * @param rb application resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = rb;
        templateFileVBox.setVisible(false);
        reportFormatComboBox.getItems().clear();
        reportFormatComboBox.getItems().addAll(Format.values());
        reportFormatComboBox.getSelectionModel()
                .select(Formats.defaultFormat);
        customTemplateCheckBox.setDisable(true);
    }
    
    /**
     * Setter for open document.
     * 
     * @param doc pointer to document
     */
    public void setDocument(Document doc) {
        currentDocument = doc;
    }
    
    /**
     * Setter for active epic
     * 
     * @param epic open epic
     */
    public void setEpic(Epic epic) {
        currentEpic = epic;
        reportNameTextField.setText(defaultName());
    }
    
    /**
     * Dialog window.
     * 
     * Accesses the parent scene of the save button.
     * 
     * @return dialog window
     */
    private Stage window() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        return stage;
    }
    
    /**
     * Handles browsing for a save location.
     */
    @FXML
    private void handleSaveLocationBrowse() {
        try {
            File f = OpenFolderDialog.show(
                bundle.getString("dialogs.save.title"),
                window()
            );
            
            if (f == null) {
                throw new NoChoiceMadeException();
            }
            
            reportSaveLocationTextField.setText(f.getAbsolutePath());
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(
                    bundle,
                    bundle.getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Handles browsing for a template file
     */
    @FXML
    private void handleTemplateFileBrowse() {
        try {
            File f = OpenFileDialog.show(
                    bundle.getString("dialogs.open.title"),
                    window(),
                    new FileChooser.ExtensionFilter(
                        "Template File",
                        Arrays.asList("dot", "dotx")
                    )
            );
            
            if (f == null) {
                throw new NoChoiceMadeException();
            }
            
            templateFileTextField.setText(f.getAbsolutePath());
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(
                    bundle,
                    bundle.getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Toggles custom template controls.
     * 
     * When custom template checkbox is selected, the template
     * chooser controls are shown. When it is de-selected, the
     * custom template is reset and the controls are hidden.
     */
    @FXML
    private void handleToggleCustomTemplate() {
        if (customTemplateCheckBox.isSelected()) {
            templateFileVBox.setVisible(true);
        } else {
            templateFileVBox.setVisible(false);
            templateFileTextField.setText("");
        }
    }
    
    /**
     * Enables custom template controls when the format allows
     * for a custom template.
     * 
     * Markdown does not require a template, so when that format is
     * selected, the custom template checkbox is disabled until
     * any other format is selected.
     */
    @FXML
    private void handleToggleCustomTemplateControl() {
        if (getFormat() == Format.MD) {
            customTemplateCheckBox.setSelected(false);
            handleToggleCustomTemplate();
            customTemplateCheckBox.setDisable(true);
        } else {
            customTemplateCheckBox.setSelected(false);
            handleToggleCustomTemplate();
            customTemplateCheckBox.setDisable(false);
        }
    }
    
    /**
     * Handles the cancel dialog action
     */
    @FXML
    private void handleCancel() {
        try {
            window().close();
        } catch (Exception e) {
            ErrorAlert.show(
                    bundle,
                    bundle.getString("errors.generic"),
                    e
            );
        }
        
    }
    
    /**
     * Handles save report.
     * 
     * If there are not enough details to render the report
     * a warning dialog is shown and the function will return early.
     * 
     * Otherwise the template is resolved, and the discovery document
     * serializer is setup for discovery document serialization.
     * 
     * The document is saved and then the dialog is closed.
     */
    @FXML
    private void handleSave() {
        try {
            if (hasName() == false) {
                MessageBox.show(
                        AlertType.WARNING, 
                        bundle.getString(
                                "reports.discovery.errors.noGen.title"
                        ),
                        bundle.getString(
                                "reports.discovery.errors.noGen.missingName"
                        )
                );
                return;
            }
            
            if (hasSaveLocation() == false) {
                MessageBox.show(
                        AlertType.WARNING, 
                        bundle.getString(
                                "reports.discovery.errors.noGen.title"
                        ),
                        bundle.getString(
                                "reports.discovery.errors.noGen.missingLocation"
                        )
                );
                return;
            }
            
            XWPFDocument template = getTemplateDocument();
            Doc doc;
            File f = getSaveLocationFile();
            
            if (getFormat() == Format.DOCX) {
                doc = new Docx(f, template);
            } else {
                doc = new Markdown(f);
            }
            
            DiscoveryDocumentSerializer dds = new DiscoveryDocumentSerializer(
                    bundle,
                    currentDocument.getPreferences(),
                    currentEpic,
                    f,
                    doc
            );
            
            dds.save();
            window().close();
        } catch (Exception e) {
            ErrorAlert.show(bundle, bundle.getString("errors.saveFile"), e);
        }
    }
    
    /**
     * Builds a default name for a discovery document.
     * 
     * This should be derived from the epic identifier and name. However
     * in the event the current epic is not set, we'll use a generic default
     * string from the application bundle.
     * 
     * @return epic document name
     */
    private String defaultName() {
        if (currentEpic == null) {
            return bundle.getString("reports.discovery.title");
        }
        
        return String.valueOf(currentEpic.getIdentifier()) 
                + " " 
                + currentEpic.getName();
    }
    
    /**
     * Gets and trims the set document name.
     * 
     * @return document name
     */
    private String getName() {
        return reportNameTextField.getText().trim();
    }
    
    /**
     * Returns the selected document format.
     * 
     * @return document format
     */
    private Format getFormat() {
        return (Format) reportFormatComboBox
                .getSelectionModel()
                .getSelectedItem();
    }
    
    /**
     * Gets the save location path as a string.
     * 
     * @return save loation path
     */
    private String getSaveLocation() {
        return reportSaveLocationTextField.getText();
    }
    
    /**
     * Constructs and returns a file for the save directory.
     * 
     * @return save directory
     */
    private File getSaveLocationDirectory() {
        return new File(getSaveLocation());
    }
    
    /**
     * Constructs and returns a file for the target report file
     * 
     * This is a union of the save location directory, the name
     * of the document and the format.
     * 
     * @return target report file
     */
    private File getSaveLocationFile() {
        String path = getSaveLocationDirectory().getAbsolutePath()
                        + File.separatorChar 
                        + getName() 
                        + "."
                        + getFormat().toString().toLowerCase();
        
        File f = new File(path);
        
        return f;
    }
    
    /**
     * Returns the template file path as a string.
     * 
     * @return template file path
     */
    private String getTemplate() {
        return templateFileTextField.getText();
    }
    
    /**
     * Resolves the template document.
     * 
     * If the path is not set, the application default template will be
     * returned from the application bundle.
     * 
     * If a template is found, that is read into an XWPF document
     * for use in the serializer.
     * 
     * @return template document
     * @throws IOException when unable to read the template
     */
    private XWPFDocument getTemplateDocument() throws IOException {
        if ("".equals(getTemplate())) {
            return currentDocument.getPreferences().defaultTemplate();
        }
        
        InputStream template = new FileInputStream(getTemplate());
        
        return new XWPFDocument(template);
    }
    
    /**
     * Returns true when a save location is set on the form, and it
     * is accessible for writing documents.
     * 
     * @return whether save location is available
     */
    private boolean hasSaveLocation() {
        if (getSaveLocation() == "") {
            return false;
        }
        
        if (getSaveLocationDirectory().canWrite() == false) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Returns true when the report name has been set.
     * 
     * @return whether the report name has been set.
     */
    private boolean hasName() {
        return getName() != "";
    }
}
