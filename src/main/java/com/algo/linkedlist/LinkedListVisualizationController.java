package com.algo.linkedlist;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.util.ArrayList;
import javafx.stage.Stage;

public class LinkedListVisualizationController extends Application {

    @FXML
    Canvas canvas;
    GraphicsContext gc;

    @FXML
    private TextField insertAtIndex;

    @FXML
    private TextField insertAtValue;

    @FXML
    private TextField pushBackValue;

    @FXML
    private TextField pushFrontValue;

    @FXML
    private TextField popBackValue;

    @FXML
    private TextField deleteAtIndex;

    private SinglyLinkedList singlyLinkedList;
    private ArrayList<stage> stages;

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        singlyLinkedList = new SinglyLinkedList(canvas);
        System.out.println("At initialization: "+ canvas.getWidth());
    }

    @FXML
    void toSingle() {
        singlyLinkedList = new SinglyLinkedList(canvas);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    void toDouble() {
        singlyLinkedList = new DoublyLinkedList(canvas);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    void handleInsertAt() {
        String indexText = insertAtIndex.getText();
        String valueText = insertAtValue.getText();

        if (indexText.isEmpty() || valueText.isEmpty()) {
            showAlert("Error", "Please enter both index and value.");
            return;
        }

        try {
            int index = Integer.parseInt(indexText);
            int value = Integer.parseInt(valueText);
            stages = singlyLinkedList.insertAt(index, value);
            display(stages);
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid numbers for index and value.");
        }
    }

    @FXML
    void handlePushBack() {
        String valueText = pushBackValue.getText();

        if (valueText.isEmpty()) {
            showAlert("Error", "Please enter a value.");
            return;
        }

        try {
            int value = Integer.parseInt(valueText);
            stages = singlyLinkedList.pushBack(value);
            display(stages);
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid number for value.");
        }
    }

    @FXML
    void handlePushFront() {
        String valueText = pushFrontValue.getText();

        if (valueText.isEmpty()) {
            showAlert("Error", "Please enter a value.");
            return;
        }

        try {
            int value = Integer.parseInt(valueText);
            stages = singlyLinkedList.pushFront(value);
            display(stages);
            pushFrontValue.clear();
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid number for value.");
        }
    }


    @FXML
    void handlePopBack() {
        stages = singlyLinkedList.popBack();
        display(stages);
    }

    @FXML
    void handlePopFront() {
        stages = singlyLinkedList.popFront();
        display(stages);
    }

    @FXML
    void handleDeleteAt() {
        String indexText = deleteAtIndex.getText();

        if (indexText.isEmpty()) {
            showAlert("Error", "Please enter an index.");
            return;
        }

        try {
            int index = Integer.parseInt(indexText);
            stages = singlyLinkedList.deleteAt(index);
            display(stages);
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid number for index.");
        }
    }

    void display(ArrayList<stage> stages) {
        for (stage s : stages) {
            s.draw(s);
        }
    }
    // void display(ArrayList<stage> stages) {
    //     new Thread(() -> {
    //         for (stage s : stages) {
    //             try {
    //                 javafx.application.Platform.runLater(() -> {
    //                     s.draw(s);
    //                 });
    //                 Thread.sleep(3000);
    //                 javafx.application.Platform.runLater(() -> {
    //                     gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    //                 });
    //             } catch (InterruptedException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }).start();
    // }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
            getClass().getResource("/com/algo/linkedlist_visualizer.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.setTitle("Linked List Visualization");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
