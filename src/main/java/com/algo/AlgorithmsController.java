package com.algo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class AlgorithmsController {
    @FXML
    private ImageView pippo;

    @FXML
    private void initialize() {
        pippo.setOnMouseEntered(event -> {
            pippo.setScaleX(1.1);
            pippo.setScaleY(1.1);
            //pippo.setEffect(new javafx.scene.effect.ColorAdjust(0, 0, 0.3, 0));
        });

        pippo.setOnMouseExited(event -> {
            pippo.setScaleX(1.0);
            pippo.setScaleY(1.0);
            pippo.setEffect(null);
        });
    }

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
        stage.setScene(scene);
        if(fxml != "visualization_setup")stage.setFullScreen(true);
        stage.setTitle(fxml.replace("_", " ").toUpperCase());
        stage.show();
    }
}