package com.biggerconcept.docs;

import com.biggerconcept.sdk.ui.Theme;
import com.biggerconcept.sdk.ui.helpers.Date;
import com.biggerconcept.sdk.snapporazi.DocImg;
import com.biggerconcept.projectus.App;
import com.biggerconcept.projectus.MainController;
import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.projectus.domain.Epic;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import static java.util.concurrent.TimeUnit.SECONDS;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

/**
 * Documentation images for Projectus.
 * 
 * @author Andrew Bigger
 */
public class ProjectusSnaps extends ApplicationTest {
    /**
     * Image camera
     */
    private DocImg camera;
    
    /**
     * Root node
     */
    private Parent root;
    
    /**
     * Window
     */
    private Scene scene;
    
    /**
     * Window controller
     */
    private MainController controller;
    
    /**
     * Setup for documentation images.
     * 
     * @param stage application window
     * 
     * @throws Exception when there is an error setting up scene
     */
    @Override
    public void start(Stage stage) throws Exception {
        camera = new DocImg("projectus", getClass());
        
        ResourceBundle bundle = ResourceBundle.getBundle("strings", App.EN_US);
        
        URL location = getClass().getResource("/fxml/Main.fxml");
        FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(location);
        loader.setResources(bundle);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        
        root = loader.load();
        controller = (MainController) loader.getController();

        scene = new Scene(root);
        Theme.apply(getClass(), scene);
        
        stage.setScene(scene);
        stage.setWidth(1500);
        stage.setHeight(800);
        stage.show();
        
        // Set date so that example is consistent.
        
        File exampleYml = new File(
                getClass()
                        .getResource("/example.projectus")
                        .getFile()
        );
        
        setWindow(exampleYml);
    }
    
    /**
     * Generates all documentation base images
     * 
     * @throws Exception when unable to perform action
     */
    @Test
    public void snap() throws Exception {
        snapEmptyWindow();
        snapWindow();
        snapEpicOverview();
    }
    
    /**
     * Capture empty window.
     * 
     * @throws Exception when unable to perform action.
     */
    @Test
    public void snapEmptyWindow() throws Exception {
        camera.setFeature("empty");
        camera.capture("window");
    }
    
    /**
     * Capture window with example locale.
     * 
     * @throws Exception when unable to perform action
     */
    @Test
    public void snapWindow() throws Exception {
       loadExample();
       
       camera.setFeature("open-document");
       camera.capture("window");
    }
    
    /**
     * Capture window with a project open.
     * 
     * @throws Exception when unable to perform the action.
     */
    @Test
    public void snapEpicOverview() throws Exception {
        loadExample();
        
        camera.setFeature("open-epic");
        selectProject(0);
        camera.capture("window");
    }
    
    /**
     * Loads example locale into window.
     */
    private void loadExample() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    File exampleYml = new File(
                            getClass()
                                    .getResource("/example.projectus")
                                    .getFile()
                    );

                    controller.openDocument(exampleYml);
                } catch (Exception ex) {
                    // do nothing
                }
            }
        });
            
        sleep(1, SECONDS);
    }
    
    /**
     * Selects an epic from the table view.
     * 
     * @param idx index of the epic to select.
     */
    private void selectProject(int idx) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.epicsTableView.getSelectionModel().select(idx);

                Epic selected = (Epic) controller
                        .epicsTableView
                        .getSelectionModel()
                        .getSelectedItem();
                
                controller.getState().setOpenEpic(selected);
                controller.mapDocumentToWindow();
            }
        });
    }
    
    /**
     * Sets project window.
     * 
     * This will set the start date as three weeks ago, and the end date
     * as two months from the current date.
     * 
     * This ensures that the example images are always consistent.
     * 
     * @param exampleFile file to adjust
     * 
     * @throws IOException when unable to edit the example yml.
     */
    private void setWindow(File exampleFile) throws IOException {
        LocalDate now = LocalDate.now();
        
        LocalDate threeWeeksAgo = now.minusWeeks(3);
        LocalDate twoMonthsFromNow = now.plusMonths(2);
        
        long startDate = Date.toEpoch(threeWeeksAgo);
        long endDate = Date.toEpoch(twoMonthsFromNow);
        
        Document doc = Document.load(exampleFile);
        
        doc.setStart(startDate);
        doc.setEnd(endDate);
        
        doc.save();
    }

}
