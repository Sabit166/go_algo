package com.algo;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private void handleStartVisualization(ActionEvent event) throws IOException {
        // Load the visualization setup screen
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/visualization_setup.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/algo/images and stylesheets/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Visualization Setup");
        stage.show();
    }

    @FXML
    private void handleHelpInstructions() {
        // Logic to display usage instructions
        // For now, just show an alert
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Help/Instructions");
        alert.setHeaderText(null);
        alert.setContentText("Help/Instructions button clicked!");
        alert.showAndWait();
    }

    @FXML
    private void handleExit(ActionEvent event) {
        // Logic to close the application
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}