package com.icsd.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import javafx.scene.image.Image;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadFXML("MainFxml2");
        scene = new Scene(root);
        scene.getStylesheets().add(App.class.getResource("/com/icsd/main/view/mainfxml.css").toExternalForm());
        stage.setScene(scene);
        String imagePath = "/com/icsd/pictures/logo_64.png";
        InputStream inputStream = App.class.getResourceAsStream(imagePath);
        if (inputStream != null) {
            Image image = new Image(inputStream);
            stage.getIcons().add(image);

        } else {
            System.err.println("Image not found: " + imagePath);
        }

        stage.setTitle("BSTAttractions");

        stage.setResizable(false);
        stage.show();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/icsd/main/view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
