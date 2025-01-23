package com.algo;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main_menu"));
        scene.getStylesheets().add(getClass().getResource("/com/algo/images and stylesheets/style.css").toExternalForm());
        Image icon = new Image(getClass().getResourceAsStream("/com/algo/images and stylesheets/logo2.png"));
        stage.getIcons().add(icon);
        stage.setFullScreen(false);
        stage.setScene(scene);
        stage.setTitle("Go-Algo");
        stage.setResizable(false);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/algo/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}