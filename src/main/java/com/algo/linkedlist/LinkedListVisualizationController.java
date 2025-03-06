package com.algo.linkedlist;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
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
import java.util.List;

import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.util.Duration;

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
    private TextField popFrontValue;

    @FXML
    private TextField deleteAtIndex;

    private SinglyLinkedList singlyLinkedList;
    private ArrayList<stage> stages;
    private boolean isDoubly = false;

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        singlyLinkedList = new SinglyLinkedList(canvas);
    }

    @FXML
    void toSingle() {
        singlyLinkedList = new SinglyLinkedList(canvas);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        isDoubly = false;
    }

    @FXML
    void toDouble() {
        singlyLinkedList = new DoublyLinkedList(canvas);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        isDoubly = true;
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
            stages = singlyLinkedList.insertAt(index, valueText);
            display(stages);
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid numbers for index and value.");
        }
    }

    @FXML
    void handlePushBack() {
        String valueText = pushBackValue.getText();
        System.out.println(valueText);

        if (valueText.isEmpty()) {
            showAlert("Error", "Please enter a value.");
            return;
        }

        try {
            stages = singlyLinkedList.pushBack(valueText);
            display(stages);
            System.out.println("pushBack");
            pushBackValue.clear();
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
            stages = singlyLinkedList.pushFront(valueText);
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
    // void display(ArrayList<stage> stages) {
    // SequentialTransition sequentialTransition = new SequentialTransition();

    // for (int i = 0; i < stages.size(); i++) {
    // int index = i; // Needed for lambda scope
    // PauseTransition pause = new PauseTransition(Duration.seconds(1));
    // pause.setOnFinished(e -> {
    // Platform.runLater(() -> {
    // System.out.println("Drawing stage " + index + " at time " + (index + 1) + "
    // seconds");
    // stages.get(index).draw(stages.get(index));
    // });
    // });
    // sequentialTransition.getChildren().add(pause);
    // }

    // sequentialTransition.play();
    // }

    // void display(ArrayList<stage> stages) {
    // SequentialTransition sequentialTransition = new SequentialTransition();

    // for (int i = 0; i < stages.size(); i++) {
    // int index = i; // Needed for lambda scope
    // PauseTransition pause = new PauseTransition(Duration.seconds(index + 1));
    // pause.setOnFinished(e -> {
    // System.out.println("Drawing stage " + index + " at time " + (index + 1) + "
    // seconds");

    // // Use Platform.runLater to ensure UI updates are rendered incrementally
    // Platform.runLater(() -> {
    // // Clear the canvas before drawing the new stage
    // gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    // if (isDoubly)
    // stages.get(index).draw(stages.get(index), true);
    // else
    // stages.get(index).draw(stages.get(index));
    // });
    // });
    // sequentialTransition.getChildren().add(pause);
    // }
    // sequentialTransition.play();
    // }

    // private void showAlert(String title, String message) {
    // Alert alert = new Alert(Alert.AlertType.ERROR);
    // alert.setTitle(title);
    // alert.setHeaderText(null);
    // alert.setContentText(message);
    // alert.showAndWait();
    // }

    void display(ArrayList<stage> stages) {
        List<KeyFrame> keyFrames = new ArrayList<>();
        Duration duration = Duration.ZERO;
        Duration stepDuration = Duration.seconds(2); // Slow down the simulation

        for (int i = 0; i < stages.size(); i++) {
            int index = i; // Needed for lambda scope
            System.out.println("Drawing stage " + index + " at time " + (index + 1) + " seconds");
            duration = duration.add(stepDuration);
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            if (isDoubly)
            {
                keyFrames.add(new KeyFrame(duration, e -> stages.get(index).draw(stages.get(index), true)));
            }
            else
            {
                keyFrames.add(new KeyFrame(duration, e -> stages.get(index).draw(stages.get(index))));
            }
        }

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(keyFrames);
        //timeline.setOnFinished(e -> resetBarColors());
        timeline.play();
    }

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
