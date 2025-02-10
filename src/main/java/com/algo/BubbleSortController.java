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

    private static final int BAR_SIZE = 100; // Square size
    private StackPane[] bars;

    @FXML
    private HBox barContainer;

    @FXML
    private TextField numElementsField;

    @FXML
    private TextField elementsField;

    @FXML
    private void handleBubbleSort() {
        initializeBars(); // Initialize the bar visualization
        bubbleSort();     // Perform the sorting with animation
    }

    private void initializeBars() {
        barContainer.getChildren().clear();
        String numElementsInput = numElementsField.getText().trim();
        String elementsInput = elementsField.getText().trim();

        if (numElementsInput.isEmpty() || elementsInput.isEmpty()) {
            showAlert("Input Error", "Please provide the number of elements and the elements separated by spaces.");
            return;
        }

        try {
            int size = Integer.parseInt(numElementsInput);
            String[] elementsArray = elementsInput.split("\\s+");

            if (elementsArray.length != size) {
                showAlert("Input Error", "The number of elements does not match the specified size.");
                return;
            }

            bars = new StackPane[size];
            for (int i = 0; i < size; i++) {
                int value = Integer.parseInt(elementsArray[i].trim());
                Rectangle rectangle = new Rectangle(BAR_SIZE, BAR_SIZE, Color.DARKVIOLET);
                Label label = new Label(elementsArray[i].trim());
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
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = Integer.parseInt(((Label) bars[i].getChildren().get(1)).getText());
        }

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                int finalJ = j;
                int finalJ1 = j + 1;

                // Highlight the bars being compared
                duration = duration.add(stepDuration);
                keyFrames.add(new KeyFrame(duration, e -> highlightBars(finalJ, finalJ1)));

                if (values[j] > values[j + 1]) {
                    // Swap values
                    int temp = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = temp;

                    // Swap bars visually
                    duration = duration.add(stepDuration);
                    keyFrames.add(new KeyFrame(duration, e -> swapBars(finalJ, finalJ1)));
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
        String tempLabel = ((Label) bars[index1].getChildren().get(1)).getText();
        ((Label) bars[index1].getChildren().get(1)).setText(((Label) bars[index2].getChildren().get(1)).getText());
        ((Label) bars[index2].getChildren().get(1)).setText(tempLabel);
    }

    private void highlightBars(int index1, int index2) {
        resetBarColors();
        ((Rectangle) bars[index1].getChildren().get(0)).setFill(Color.GREEN); // Highlight the selected bar
        ((Rectangle) bars[index2].getChildren().get(0)).setFill(Color.RED);   // Highlight the comparison
    }

    private void resetBarColors() {
        for (StackPane bar : bars) {
            ((Rectangle) bar.getChildren().get(0)).setFill(Color.DARKVIOLET);
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
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/algorithms.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Algorithms");
        stage.show();
    }
}