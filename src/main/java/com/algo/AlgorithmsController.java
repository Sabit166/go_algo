package com.algo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import java.io.IOException;

public class AlgorithmsController {

    @FXML
    private void handleBubbleSort() {
        // Logic for Bubble Sort
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Bubble Sort");
        alert.setHeaderText(null);
        alert.setContentText("Bubble Sort button clicked!");
        alert.showAndWait();
    }

    @FXML
    private void handleSelectionSort() {
        // Logic for Selection Sort
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Selection Sort");
        alert.setHeaderText(null);
        alert.setContentText("Selection Sort button clicked!");
        alert.showAndWait();
    }

    @FXML
    private void handleInsertionSort() {
        // Logic for Insertion Sort
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Insertion Sort");
        alert.setHeaderText(null);
        alert.setContentText("Insertion Sort button clicked!");
        alert.showAndWait();
    }

    @FXML
    private void handleBinarySearch() {
        // Logic for Binary Search
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Binary Search");
        alert.setHeaderText(null);
        alert.setContentText("Binary Search button clicked!");
        alert.showAndWait();
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        // Load the visualization setup screen
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/visualization_setup.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Visualization Setup");
        stage.show();
    }
}
