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
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmsController {

    private static final int BAR_WIDTH = 30;
    private static final int MAX_HEIGHT = 300;
    private StackPane[] bars;

    @FXML
    private HBox barContainer;

    @FXML
    private TextField inputField;

    @FXML
    private void handleSelectionSort() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Selection Sort");
        alert.setHeaderText(null);
        alert.setContentText("Selection Sort button clicked!");
        alert.showAndWait();

        initializeBars(); // Initialize the bar visualization
        selectionSort();  // Perform the sorting with animation
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

    private void selectionSort() {
        // Maintain a logical array for values
        double[] heights = new double[bars.length];
        for (int i = 0; i < bars.length; i++) {
            heights[i] = ((Rectangle) bars[i].getChildren().get(0)).getHeight();
        }
    
        List<KeyFrame> keyFrames = new ArrayList<>();
        Duration duration = Duration.ZERO;
        Duration stepDuration = Duration.seconds(1);
    
        int n = bars.length;
    
        // One by one move the boundary of the unsorted subarray
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
    
            // Debug: Log outer loop details
            System.out.println("Outer loop i: " + i + ", minIndex: " + minIndex);
    
            // Highlight the starting boundary
            int finalI = i;
            keyFrames.add(new KeyFrame(duration, e -> highlightBars(finalI, finalI)));
    
            // Find the minimum element in the unsorted part
            for (int j = i + 1; j < n; j++) {
                int finalJ = j;
    
                // Highlight the comparison
                duration = duration.add(stepDuration);
                int currentMinIndex = minIndex;
                keyFrames.add(new KeyFrame(duration, e -> highlightBars(currentMinIndex, finalJ)));
    
                // Update the minimum index
                if (heights[j] < heights[minIndex]) {
                    minIndex = j;
    
                    // Debug: Log minimum update
                    System.out.println("New minIndex found at " + minIndex);
    
                    // Highlight the new minimum index
                    int updatedMinIndex = minIndex;
                    keyFrames.add(new KeyFrame(duration, e -> highlightBars(updatedMinIndex, finalJ)));
                }
            }
    
            // Swap elements in the logical array
            if (minIndex != i) {
                double temp = heights[i];
                heights[i] = heights[minIndex];
                heights[minIndex] = temp;
    
                // Debug: Log swapping
                System.out.println("Swapping: " + heights[minIndex] + " with " + heights[i]);
    
                // Swap elements visually
                int finalMinIndex = minIndex;
                duration = duration.add(stepDuration);
                keyFrames.add(new KeyFrame(duration, e -> {
                    swapBars(finalI, finalMinIndex);
                    resetBarColors();
                }));
            } else {
                // No swap needed, just reset colors
                duration = duration.add(stepDuration);
                keyFrames.add(new KeyFrame(duration, e -> resetBarColors()));
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
            // Debug: Log the heights before the swap
            System.out.println("Before swap: Bar1 height = " + ((Rectangle) bars[index1].getChildren().get(0)).getHeight() +
                               ", Bar2 height = " + ((Rectangle) bars[index2].getChildren().get(0)).getHeight());
    
            // Swap the heights of the rectangles
            double tempHeight = ((Rectangle) bars[index1].getChildren().get(0)).getHeight();
            ((Rectangle) bars[index1].getChildren().get(0)).setHeight(((Rectangle) bars[index2].getChildren().get(0)).getHeight());
            ((Rectangle) bars[index2].getChildren().get(0)).setHeight(tempHeight);
    
            // Swap the labels
            String tempLabel = ((Label) bars[index1].getChildren().get(1)).getText();
            ((Label) bars[index1].getChildren().get(1)).setText(((Label) bars[index2].getChildren().get(1)).getText());
            ((Label) bars[index2].getChildren().get(1)).setText(tempLabel);
    
            // Debug: Log the heights after the swap
            System.out.println("After swap: Bar1 height = " + ((Rectangle) bars[index1].getChildren().get(0)).getHeight() +
                               ", Bar2 height = " + ((Rectangle) bars[index2].getChildren().get(0)).getHeight());
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