package com.algo;

import javafx.util.Pair;
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

class Segment_Tree_Nodes {
    int value;
    double x, y;

    Segment_Tree_Nodes() {

    }

    Segment_Tree_Nodes(int value, double x, double y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }
}

public class SegmentTreeVisualizationController extends Application {

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;

    @FXML
    private TextField BuildInput;

    int N = 100000;
    int[] numbers = new int[N];
    Segment_Tree_Nodes[] segment_tree = new Segment_Tree_Nodes[4 * N];

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadFXML("segment_tree_visualizer");
        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.setTitle("SEGMENT TREE VISUALIZER");
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                SegmentTreeVisualizationController.class.getResource("/com/algo/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        // Initialize the segment_tree array with Segment_Tree_Nodes instances
        for (int i = 0; i < segment_tree.length; i++) {
            segment_tree[i] = new Segment_Tree_Nodes();
        }
    }

    @FXML
    private void HandleBuild() {
        // Clear the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

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
        build_segment_tree(1, 0, numbers.length - 1, 0, canvas_width);
        build_lines(1, 0, numbers.length - 1);
        build_circle(1, 0, numbers.length - 1);
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
            segment_tree[node].value = numbers[start];
        } else {
            int mid = (start + end) / 2;
            build_segment_tree(2 * node, start, mid, canvas_start_point, (canvas_start_point + canvas_width) / 2);
            build_segment_tree(2 * node + 1, mid + 1, end, (canvas_start_point + canvas_width) / 2, canvas_width);
            segment_tree[node].value = segment_tree[2 * node].value + segment_tree[2 * node + 1].value;
        }
        System.out.println("Building tree: node=" + node + ", range=[" + start + ", " + end + "]");
        // for(int i=0;i<100000L;i++)
        build_helper(node, canvas_start_point, canvas_width);
    }

    void build_helper(int node, double canvas_start_point, double canvas_width) {
        double tree_height = Math.ceil(Math.log(numbers.length) / Math.log(2));
        double width_point = (canvas_start_point + canvas_width) / 2;
        int level = (int) (Math.log(node) / Math.log(2)); // Determine the level of the node
        double y = level * (canvas.getHeight() / tree_height) * 0.5 + 50; // Adjust y position
        segment_tree[node].x = width_point - 15;
        segment_tree[node].y = y - 15;

        // Platform.runLater(() -> {
        // gc.setGlobalAlpha(1.0);
        // gc.setFill(Color.GRAY);
        // gc.fillOval(width_point - 15, y - 15, 30, 30); // Adjust circle position and
        // increase radius
        // gc.setFill(Color.WHITE);
        // gc.fillText(String.valueOf(segment_tree[node].value), width_point - 5, y +
        // 5); // Draw the value
        // });
    }

    Pair<Double, Double> build_lines(int node, int start, int end) {
        if (start == end) {
            //build_helper(segment_tree[node].x, segment_tree[node].y, segment_tree[node].value, node);
            return new Pair<>(segment_tree[node].x, segment_tree[node].y);
        }

        int mid = (start + end) / 2;
        Pair<Double, Double> left = build_lines(2 * node, start, mid);
        Pair<Double, Double> right = build_lines(2 * node + 1, mid + 1, end);

        double x = segment_tree[node].x;
        double y = segment_tree[node].y;

        build_helper(x, y, left.getKey(), left.getValue());
        build_helper(x, y, right.getKey(), right.getValue());

        return new Pair<>(x, y);
    }

    void build_circle(int node, int start, int end) {
        if (start == end) {
            build_helper(segment_tree[node].x, segment_tree[node].y, segment_tree[node].value, node);
            return;
        }

        int mid = (start + end) / 2;
        build_circle(2 * node, start, mid);
        build_circle(2 * node + 1, mid + 1, end);

        double x = segment_tree[node].x;
        double y = segment_tree[node].y;

        build_helper(x, y, segment_tree[node].value, node);
    }

    void build_helper(double x, double y, int value, int node) {
        Platform.runLater(() -> {
            gc.setGlobalAlpha(1.0);
            gc.setFill(Color.GRAY);
            gc.fillOval(x - 15, y - 15, 50, 50); // Adjust circle position and increase radius
            gc.setFill(Color.WHITE);
            gc.fillText(String.valueOf(value), x + 3 , y + 6); // Draw the value
            gc.setFill(Color.BLACK);
            gc.fillText(String.valueOf(node), x + 3, y + 46); // Draw the value
        });
    }

    void build_helper(double x1, double y1, double x2, double y2) {
        Platform.runLater(() -> {
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeLine(x1, y1, x2, y2);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
