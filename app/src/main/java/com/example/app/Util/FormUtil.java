package com.example.app.Util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FormUtil {
    // Updated method to handle null controllerType parameter
    public static <T> T switchScene(Stage stage, String fxmlFileName, Class<T> controllerType) {
        try {
            FXMLLoader loader = new FXMLLoader(FormUtil.class.getResource("/Fxml/" + fxmlFileName));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            // Only try to cast if controllerType is not null
            if (controllerType != null) {
                return controllerType.cast(loader.getController());
            } else {
                // Just return null if no controller type was specified
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Consider using Logger instead
            return null;
        }
    }

}
