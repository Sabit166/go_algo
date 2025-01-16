package com.algo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class VisualizationSetupController {

    @FXML
    private void handleDataStructures() {
        // Logic to navigate to Data Structures
        // For now, just show an alert
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Data Structures");
        alert.setHeaderText(null);
        alert.setContentText("Data Structures button clicked!");
        alert.showAndWait();
    }

    @FXML
    private void handleAlgorithms() {
        // Logic to navigate to Algorithms
        // For now, just show an alert
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Algorithms");
        alert.setHeaderText(null);
        alert.setContentText("Algorithms button clicked!");
        alert.showAndWait();
    }
}
