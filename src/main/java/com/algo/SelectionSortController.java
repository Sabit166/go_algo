// filepath: /c:/Users/Alif Ul Haque/Desktop/project_java/go_algo/src/main/java/com/algo/SelectionSortController.java
package com.algo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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

public class SelectionSortController extends Application{

    private static Scene scene;
    private static final int BAR_WIDTH = 100; // Increased width
    private static final int MAX_HEIGHT = 400; // Increased height
    private StackPane[] bars;

    @FXML
    private HBox barContainer;

    @FXML
    private TextField numElementsField;

    @FXML
    private TextField elementsField;

    @FXML
    private void handleSelectionSort() {
        initializeBars(); // Initialize the bar visualization
        selectionSort();  // Perform the sorting with animation
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
                double height = 100; // Set a constant height for all bars
                Rectangle rectangle = new Rectangle(BAR_WIDTH, height, Color.DARKVIOLET);
                Label label = new Label(elementsArray[i].trim());
                label.setTextFill(Color.BLACK);
                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(rectangle, label);
                bars[i] = stackPane;
                barContainer.getChildren().add(bars[i]);
            }
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Ensure the size and all elements are valid numbers.");
        }
    }

    private void selectionSort() {
        List<KeyFrame> keyFrames = new ArrayList<>();
        Duration duration = Duration.ZERO;
        Duration stepDuration = Duration.seconds(1.5);

        int n = bars.length;
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = Integer.parseInt(((Label) bars[i].getChildren().get(1)).getText());
        }

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                int finalJ = j;
                int finalMinIndex = minIndex;

                // Highlight the bars being compared
                duration = duration.add(stepDuration);
                keyFrames.add(new KeyFrame(duration, e -> highlightBars(finalMinIndex, finalJ)));

                if (values[j] < values[minIndex]) {
                    minIndex = j;
                }
            }

            // Swap values
            int temp = values[minIndex];
            values[minIndex] = values[i];
            values[i] = temp;

            // Swap bars visually
            int finalI = i;
            int finalMinIndex1 = minIndex;
            duration = duration.add(stepDuration);
            keyFrames.add(new KeyFrame(duration, e -> swapBars(finalI, finalMinIndex1)));
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

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("selection_sort"));
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle("Selection Sort");
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