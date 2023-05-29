package com.biggerconcept.projectus.ui.windows;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author abigger
 */
public class Window {
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
    
    public static StageStyle DEFAULT_STYLE = StageStyle.DECORATED;
    public static boolean DEFAULT_ON_TOP = false;
    public static boolean DEFAULT_RESIZABLE = true;
    
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
