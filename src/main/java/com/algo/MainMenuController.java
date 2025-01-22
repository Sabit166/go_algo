package com.algo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class MainMenuController {

    @FXML
    private void handleStartVisualization(ActionEvent event) throws IOException {
        loadPage("visualization_setup", event);
    }

    @FXML
    private void handleHelpInstructions(ActionEvent event) {
        // Handle help/instructions
    }

    @FXML
    private void handleExit(ActionEvent event) {
        System.exit(0);
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