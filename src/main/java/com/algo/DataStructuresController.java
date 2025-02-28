package com.algo;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DataStructuresController {

    @FXML
    private void handleLinkedList(ActionEvent event) throws IOException {
        // Logic for Linked List
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/linkedlist_visualizer.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setFullScreen(true);
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
    private void handleQueue(ActionEvent event) throws IOException {
        // Load the Queue Visualization screen
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/queue_visualization.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Queue Visualization");
        stage.show();
    }

    @FXML
    private void handleSegmentTree(ActionEvent event) throws IOException {
        // Logic for Segment Tree
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/segment_tree_visualizer.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Segment Tree Visualization");
        stage.centerOnScreen();
        stage.show();
        stage.setFullScreen(true);
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        // Load the visualization setup screen
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/visualization_setup.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Visualization Setup");
        stage.centerOnScreen();
        stage.show();
    }
}