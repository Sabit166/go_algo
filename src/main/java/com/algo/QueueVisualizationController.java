package com.algo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class QueueVisualizationController {

    @FXML
    private ComboBox<String> dataTypeComboBox;
    @FXML
    private TextField inputField;
    @FXML
    private HBox queueContainer;

    private Queue<Object> queue = new LinkedList<>();

    @FXML
    private void initialize() {
        dataTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            queue.clear();
            updateQueueVisualization();
        });

        inputField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    handleEnqueue();
                    break;
                default:
                    break;
            }
        });
    }

    @FXML
    private void handleEnqueue() {
        if (queue.size() >= 15) {
            showAlert("Error", "Queue is full. Maximum 15 elements allowed.");
            return;
        }

        String dataType = dataTypeComboBox.getValue();
        String input = inputField.getText();

        if (dataType == null || input.isEmpty()) {
            showAlert("Error", "Please select a data type and enter a value.");
            return;
        }

        Object value;
        try {
            switch (dataType) {
                case "Integer":
                    value = Integer.parseInt(input);
                    break;
                case "Character":
                    if (input.length() != 1) throw new IllegalArgumentException();
                    value = input.charAt(0);
                    break;
                case "String":
                    value = input;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            showAlert("Error", "Invalid input for the selected data type.");
            return;
        }

        queue.add(value);
        updateQueueVisualization();
        inputField.clear();
    }

    @FXML
    private void handleDequeue() {
        if (queue.isEmpty()) {
            showAlert("Error", "Queue is empty.");
            return;
        }

        queue.poll();
        updateQueueVisualization();
    }

    @FXML
    private void handleFront() {
        if (queue.isEmpty()) {
            showAlert("Error", "Queue is empty.");
            return;
        }

        Object frontElement = queue.peek();
        showAlert("Front Element", "Front element is: " + frontElement);
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/data_structures.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Data Structures");
        stage.show();
    }

    private void updateQueueVisualization() {
        queueContainer.getChildren().clear();
        for (Object element : queue) {
            Label label = new Label(element.toString());
            label.setStyle("-fx-border-color: black; -fx-padding: 5;");
            queueContainer.getChildren().add(label);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
