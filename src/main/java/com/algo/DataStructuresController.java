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

public class DataStructuresController {

    @FXML
    private void handleLinkedList() {
        // Logic for Linked List
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Linked List");
        alert.setHeaderText(null);
        alert.setContentText("Linked List button clicked!");
        alert.showAndWait();
    }

    @FXML
    private void handleStack(ActionEvent event) throws IOException {
        // Load the Stack Visualization screen
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/stack_visualization.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Stack Visualization");
        stage.show();
    }

    @FXML
    private void handleQueue() {
        // Logic for Queue
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Queue");
        alert.setHeaderText(null);
        alert.setContentText("Queue button clicked!");
        alert.showAndWait();
    }

    @FXML
    private void handleSegmentTree() {
        // Logic for Segment Tree
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Segment Tree");
        alert.setHeaderText(null);
        alert.setContentText("Segment Tree button clicked!");
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
