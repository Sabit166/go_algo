// filepath: /c:/Users/Alif Ul Haque/Desktop/project_java/go_algo/src/main/java/com/algo/BubbleSortController.java
package com.algo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class BubbleSortController {

    private static final int BAR_WIDTH = 30;
    private static final int MAX_HEIGHT = 300;
    private StackPane[] bars;

    @FXML
    private HBox barContainer;

    @FXML
    private TextField inputField;

    @FXML
    private void handleBubbleSort() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Bubble Sort");
        alert.setHeaderText(null);
        alert.setContentText("Bubble Sort button clicked!");
        alert.showAndWait();

        initializeBars(); // Initialize the bar visualization
        bubbleSort();  // Perform the sorting with animation
    }

    private void initializeBars() {
        barContainer.getChildren().clear();
        String input = inputField.getText();
        if (input.isEmpty()) {
            showAlert("Input Error", "Please provide a comma-separated list of numbers.");
            return;
        }
        try {
            String[] inputArray = input.split(",");
            bars = new StackPane[inputArray.length];
            for (int i = 0; i < inputArray.length; i++) {
                double height = Double.parseDouble(inputArray[i].trim()) * 10;
                if (height > MAX_HEIGHT) {
                    showAlert("Input Error", "Please ensure numbers are small enough to fit the display.");
                    return;
                }
                Rectangle rectangle = new Rectangle(BAR_WIDTH, height, Color.SKYBLUE);
                Label label = new Label(inputArray[i].trim());
                label.setTextFill(Color.BLACK);
                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(rectangle, label);
                bars[i] = stackPane;
                barContainer.getChildren().add(bars[i]);
            }
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Ensure all inputs are valid numbers.");
        }
    }

    private void bubbleSort() {
        List<KeyFrame> keyFrames = new ArrayList<>();
        Duration duration = Duration.ZERO;
        Duration stepDuration = Duration.seconds(1);

        int n = bars.length;
        double[] heights = new double[n];
        for (int i = 0; i < n; i++) {
            heights[i] = ((Rectangle) bars[i].getChildren().get(0)).getHeight();
        }

        // Bubble Sort algorithm with animation
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                int finalJ = j;
                int nextJ = j + 1;

                // Highlight the comparison
                duration = duration.add(stepDuration);
                keyFrames.add(new KeyFrame(duration, e -> highlightBars(finalJ, nextJ)));

                // Swap if the element found is greater than the next element
                if (heights[j] > heights[j + 1]) {
                    double temp = heights[j];
                    heights[j] = heights[j + 1];
                    heights[j + 1] = temp;

                    // Swap elements visually
                    duration = duration.add(stepDuration);
                    keyFrames.add(new KeyFrame(duration, e -> {
                        swapBars(finalJ, nextJ);
                        resetBarColors();
                    }));
                }
            }
        }

        // Play the timeline animation
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(keyFrames);
        timeline.setOnFinished(e -> resetBarColors());
        timeline.play();
    }

    private void swapBars(int index1, int index2) {
        if (index1 != index2) {
            double tempHeight = ((Rectangle) bars[index1].getChildren().get(0)).getHeight();
            ((Rectangle) bars[index1].getChildren().get(0)).setHeight(((Rectangle) bars[index2].getChildren().get(0)).getHeight());
            ((Rectangle) bars[index2].getChildren().get(0)).setHeight(tempHeight);

            String tempLabel = ((Label) bars[index1].getChildren().get(1)).getText();
            ((Label) bars[index1].getChildren().get(1)).setText(((Label) bars[index2].getChildren().get(1)).getText());
            ((Label) bars[index2].getChildren().get(1)).setText(tempLabel);
        }
    }

    private void highlightBars(int index1, int index2) {
        resetBarColors();
        ((Rectangle) bars[index1].getChildren().get(0)).setFill(Color.GREEN); // Highlight the selected bar
        ((Rectangle) bars[index2].getChildren().get(0)).setFill(Color.RED);   // Highlight the comparison
    }

    private void resetBarColors() {
        for (StackPane bar : bars) {
            ((Rectangle) bar.getChildren().get(0)).setFill(Color.SKYBLUE);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/visualization_setup.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Visualization Setup");
        stage.show();
    }
}