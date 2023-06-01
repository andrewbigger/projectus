package com.biggerconcept.appengine.ui.window;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Window UI helper
 * 
 * @author Andrew Bigger
 */
public class StandardWindow {
    /**
     * Default window style.
     */
    public static final StageStyle DEFAULT_STYLE = StageStyle.DECORATED;
    
    /**
     * Default always on top directive.
     */
    public static final boolean DEFAULT_ON_TOP = false;
    
    /**
     * Default is re-sizable.
     */
    public static final boolean DEFAULT_RESIZABLE = true;
    
    
    /**
     * Loads FXML and returns loader to caller.
     * 
     * @param controller pointer to controller loading FXML
     * @param bundle application resource bundle
     * @param resource string path to FXML
     * 
     * @return FXML loader
     */
    public static FXMLLoader load(
            Object controller,
            ResourceBundle bundle,
            String resource
    ) {
        URL location = controller.getClass().getResource(resource);
        FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(location);
        loader.setResources(bundle);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        
        return loader;
    }
    
    /**
     * Window setup with minimal arguments.
     * 
     * Accepts a panel, title and style-sheet, and sets the stage for the
     * new window.
     * 
     * Additional window properties are set with class default constants.
     * 
     * @param panel node with window contents
     * @param title title for window
     * @param stylesheet path to style-sheet for window
     * 
     * @return configured window
     */
    public static Stage setup(Parent panel, String title, String stylesheet) {
        return setup(
                panel,
                title,
                stylesheet,
                DEFAULT_ON_TOP,
                DEFAULT_STYLE,
                DEFAULT_RESIZABLE
        );
    }
    
    /**
     * Window setup with border style argument.
     * 
     * Accepts a panel, title and style-sheet, and sets the stage for a new
     * window.
     * 
     * Additional window properties are set with class default constants.
     * 
     * @param panel node with window contents
     * @param title title for window
     * @param stylesheet path to style-sheet for window
     * @param style window border style
     * 
     * @return configured window
     */
    public static Stage setup(
            Parent panel,
            String title,
            String stylesheet,
            StageStyle style
    ) {
        return setup(
                panel,
                title,
                stylesheet,
                DEFAULT_ON_TOP,
                style,
                DEFAULT_RESIZABLE
        );
    }
    
    /**
     * Window setup.
     * 
     * Accepts a panel, title, style-sheet, on top, border style and re-sizable
     * argument and sets the stage for a new window.
     * 
     * @param panel node with window contents
     * @param title title for window
     * @param stylesheet path to style-sheet for window
     * @param onTop controls whether window is always on top
     * @param style window border style
     * @param resizable controls whether window is re-sizable.
     * 
     * @return configured window
     */
    public static Stage setup(
            Parent panel,
            String title,
            String stylesheet,
            boolean onTop,
            StageStyle style,
            boolean resizable
    ) {
        Stage stage = new Stage();
        
        stage.setAlwaysOnTop(onTop);
        
        Scene scene = new Scene(panel);
        
        if (stylesheet != null) {
            scene.getStylesheets().add(stylesheet);
        }
        
        stage.setScene(scene);
        stage.setTitle(title);
        stage.initStyle(style);
        stage.resizableProperty().setValue(resizable);
        
        return stage;
    }
    
}
