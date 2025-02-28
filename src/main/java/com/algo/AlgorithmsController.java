package com.algo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class AlgorithmsController {

    @FXML
    private void loadSelectionSort(ActionEvent event) throws IOException {
        loadPage("selection_sort", event);
    }

    @FXML
    private void loadBubbleSort(ActionEvent event) throws IOException {
        loadPage("bubble_sort", event);
    }

    @FXML
    private void loadBinarySearch(ActionEvent event) throws IOException {
        loadPage("binary_search", event);
    }

    @FXML
    private void loadBFS(ActionEvent event) throws IOException {
        loadPage("bfs", event);
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        loadPage("visualization_setup", event);
    }

    private void loadPage(String fxml, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/" + fxml + ".fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle(fxml.replace("_", " ").toUpperCase());
        stage.show();
    }
}