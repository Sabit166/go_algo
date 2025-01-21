package com.algo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class VisualizationSetupController {

    @FXML
    private void handleDataStructures(ActionEvent event) throws IOException {
        // Load the Data Structures screen
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/data_structures.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Data Structures");
        stage.show();
    }

    @FXML
    private void handleAlgorithms(ActionEvent event) throws IOException {
        // Load the Algorithms screen
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/algorithms.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.setTitle("Algorithms");
        stage.show();
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        // Load the main menu screen
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/main_menu.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Data Structure Visualizer");
        stage.show();
    }
}
