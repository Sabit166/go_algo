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
        loadPage("data_structures", event);
    }

    @FXML
    private void handleAlgorithms(ActionEvent event) throws IOException {
        loadPage("algorithms", event);
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        loadPage("main_menu", event);
    }

    private void loadPage(String fxml, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/" + fxml + ".fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(fxml.replace("_", " ").toUpperCase());
        stage.show();
    }
}