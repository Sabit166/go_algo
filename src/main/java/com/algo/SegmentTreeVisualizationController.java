package com.algo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SegmentTreeVisualizationController extends Application {

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;

    @FXML
    private TextField BuildInput;

    int N = 100000;
    int[] numbers = new int[N];
    int[] segment_tree = new int[4 * N];

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadFXML("segment_tree_visualizer");
        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.setTitle("SEGMENT TREE VISUALIZER");
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SegmentTreeVisualizationController.class.getResource("/com/algo/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
    }

    @FXML
    private void HandleBuild() {
        String input = BuildInput.getText();
        if (!input.matches("[0-9 ]+")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter only digits separated by spaces.");
            alert.showAndWait();
            return;
        }

        String[] inputArray = input.split("\\s+");
        numbers = new int[inputArray.length];
        for (int i = 0; i < inputArray.length; i++) {
            numbers[i] = Integer.parseInt(inputArray[i]);
        }

        double canvas_width = canvas.getWidth();
        build_segment_tree(1, 0, numbers.length - 1, canvas_width / 2, canvas_width);
    }

    @FXML
    private void HandleQuerry() {
        // Implement the logic for HandleQuerry
    }

    @FXML
    private void HandleUpdate() {
        // Implement the logic for HandleUpdate
    }

    void build_segment_tree(int node, int start, int end, double canvas_start_point, double canvas_width) {
        if (start == end) {
            form_node(node, start, canvas_start_point, canvas_width);
        } else {
            int mid = (start + end) / 2;
            build_segment_tree(2 * node, start, mid, canvas_start_point, (canvas_start_point + canvas_width) / 2);
            build_segment_tree(2 * node + 1, mid + 1, end, (canvas_start_point + canvas_width) / 2, canvas_width);
            segment_tree[node] = segment_tree[2 * node] + segment_tree[2 * node + 1];
        }
    }

    void form_node(int node, int start, double canvas_start_point, double canvas_width) {
        segment_tree[node] = numbers[start];
        double width_point = (canvas_start_point + canvas_width) / 2;
        double tree_height = Math.ceil(Math.log(numbers.length) / Math.log(2));
        double height_index = canvas.getHeight() / tree_height;

        new Thread(() -> {
            for (double opacity = 0.0; opacity <= 1.0; opacity += 0.1) {
                final double currentOpacity = opacity;
                javafx.application.Platform.runLater(() -> {
                    gc.setGlobalAlpha(currentOpacity);
                    gc.setFill(Color.BLUE);
                    gc.fillOval(width_point - 10, height_index * node - 10, 20, 20); // Draw a circle with radius 10
                    gc.setFill(Color.GREY);
                    gc.fillText(String.valueOf(segment_tree[node]), width_point - 5, height_index * node + 5); // Draw the value inside the circle
                });
                try {
                    Thread.sleep(100); // Delay for animation effect
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> gc.setGlobalAlpha(1.0)); // Reset opacity to 1.0
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}