package com.algo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchController {

    private static final int BAR_SIZE = 100; // Use square blocks
    private static final int GAP_SIZE = 40; // Gap for arrows
    private StackPane[] bars;

    @FXML
    private HBox barContainer;

    @FXML
    private TextField numElementsField;

    @FXML
    private TextField inputField;

    @FXML
    private TextField searchField;

    @FXML
    private Label foundLabel;

    @FXML
    private void handleBinarySearch() {
        initializeBars(); // Initialize the bar visualization
        binarySearch();   // Perform the search with animation
    }

    private void initializeBars() {
        barContainer.getChildren().clear();
        foundLabel.setText(""); // Clear the found label
        String numElementsText = numElementsField.getText();
        String input = inputField.getText();
        if (numElementsText.isEmpty() || input.isEmpty()) {
            showAlert("Input Error", "Please provide the number of elements and a space-separated list of numbers.");
            return;
        }
        try {
            int numElements = Integer.parseInt(numElementsText.trim());
            String[] inputArray = input.split("\\s+");
            if (inputArray.length != numElements) {
                showAlert("Input Error", "The number of elements does not match the provided list.");
                return;
            }
            bars = new StackPane[numElements];
            for (int i = 0; i < numElements; i++) {
                double size = BAR_SIZE;
                Rectangle rectangle = new Rectangle(size, size, Color.DARKVIOLET);
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

    private void binarySearch() {
        List<KeyFrame> keyFrames = new ArrayList<>();
        Duration duration = Duration.ZERO;
        Duration stepDuration = Duration.seconds(2); // Slow down the simulation

        int n = bars.length;
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = Integer.parseInt(((Label) bars[i].getChildren().get(1)).getText());
        }

        int target;
        try {
            target = Integer.parseInt(searchField.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Ensure the search input is a valid number.");
            return;
        }

        int left = 0;
        int right = n - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // Highlight the current start, end, and middle elements
            int finalLeft = left;
            int finalRight = right;
            int finalMid = mid;
            duration = duration.add(stepDuration);
            keyFrames.add(new KeyFrame(duration, e -> highlightBars(finalLeft, finalRight, finalMid)));

            if (values[mid] == target) {
                // Target found
                duration = duration.add(stepDuration);
                keyFrames.add(new KeyFrame(duration, e -> highlightFound(finalMid)));
                break;
            } else if (values[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        // Play the timeline animation
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(keyFrames);
        timeline.setOnFinished(e -> resetBarColors());
        timeline.play();
    }

    private void highlightBars(int left, int right, int mid) {
        resetBarColors();
        boolean isOverlap = (left == right);
        addArrow(left, Color.BLUE, "up", isOverlap);  // Start element: blue arrow above
        addArrow(right, Color.RED, "up1", isOverlap); // End element: red arrow above
        addArrow(mid, Color.GREEN, "down", false);  // Middle element: green arrow on the block
    }

    private void addArrow(int index, Color color, String position, boolean isOverlap) {
        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(new Double[]{
            0.0, 0.0,
            10.0, 20.0,
            -10.0, 20.0 });
        arrow.setFill(color);
    
        if (position.equals("up")) {
            arrow.setRotate(180);
            bars[index].getChildren().add(arrow);
            arrow.setTranslateY(-GAP_SIZE); // Gap above the bar
            if (isOverlap) {
                arrow.setTranslateX(-20); // Shift left to avoid overlap
            }
        }else if (position.equals("up1")) {
            arrow.setRotate(180);
            bars[index].getChildren().add(arrow);
            arrow.setTranslateY(-GAP_SIZE); // Gap above the bar
            if (isOverlap) {
                arrow.setTranslateX(0); // Shift left to avoid overlap
            }
        } else if (position.equals("down")) {
            bars[index].getChildren().add(arrow);
            arrow.setTranslateY(GAP_SIZE); // Gap below the bar
            if (isOverlap) {
                arrow.setTranslateX(10); // Shift right to avoid overlap
            }
        } else if (position.equals("mid")) {
            bars[index].getChildren().add(arrow);
            arrow.setTranslateY(0); // Centered directly on the bar
        }
    }

    private void highlightFound(int index) {
        resetBarColors();
        Label foundLabel = new Label("Found");
        foundLabel.setTextFill(Color.GREEN);
        foundLabel.setStyle("-fx-font-size: 100px;"); // Set the font size using CSS
        bars[index].getChildren().add(foundLabel); // Highlight the found element with "Found"
        this.foundLabel.setText("It has been found at index = " + index); // Update the found label
    }

    private void resetBarColors() {
        for (StackPane bar : bars) {
            ((Rectangle) bar.getChildren().get(0)).setFill(Color.DARKVIOLET);
            bar.getChildren().removeIf(node -> node instanceof Polygon || (node instanceof Label && "Found".equals(((Label) node).getText()))); 
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