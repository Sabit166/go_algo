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
import javafx.application.Application;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Stack;

public class StackVisualizationController extends Application{

    @FXML
    private ComboBox<String> dataTypeComboBox;
    @FXML
    private TextField inputField;
    @FXML
    private HBox stackContainer;

    private static Scene scene;
    private Stack<Object> stack = new Stack<>();

    @FXML
    private void initialize() {
        dataTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            stack.clear();
            updateStackVisualization();
        });

        inputField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    handlePush();
                    break;
                default:
                    break;
            }
        });
    }

    @FXML
    private void handlePush() {
        if (stack.size() >= 15) {
            showAlert("Error", "Stack is full. Maximum 15 elements allowed.");
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

        stack.push(value);
        updateStackVisualization();
        inputField.clear();
    }

    @FXML
    private void handlePop() {
        if (stack.isEmpty()) {
            showAlert("Error", "Stack is empty.");
            return;
        }

        stack.pop();
        updateStackVisualization();
    }

    @FXML
    private void handleTop() {
        if (stack.isEmpty()) {
            showAlert("Error", "Stack is empty.");
            return;
        }

        Object topElement = stack.peek();
        showAlert("Top Element", "Top element is: " + topElement);
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/data_structures.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle("Data Structures");
        stage.show();
    }

    private void updateStackVisualization() {
        stackContainer.getChildren().clear();
        for (Object element : stack) {
            Label label = new Label(element.toString());
            label.setStyle("-fx-border-color: black; -fx-padding: 5;");
            stackContainer.getChildren().add(label);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("stack_visualization"));
        scene.getStylesheets()
                .add(getClass().getResource("/com/algo/images and stylesheets/stylestackqueue.css").toExternalForm());
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle("Stack Visualization");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/algo/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
