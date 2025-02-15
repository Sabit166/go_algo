package com.algo.segmenttree;

import javafx.util.Pair;
import javafx.application.Application;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.io.IOException;

public class SegmentTreeVisualizationController extends Application {

    @FXML
    protected Canvas canvas;
    protected GraphicsContext gc;

    @FXML
    private TextField BuildInput;

    @FXML
    private TextField QuerryInput;

    @FXML
    private TextField UpdateInput;

    SegmentTreeVisualizationHelper helper;
    protected int[] numbers;
    protected final SegmentTreeNodes[] segment_tree = new SegmentTreeNodes[4 * 16]; // Initialize the segment tree


    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadFXML("segment_tree_visualizer");
        Scene scene = new Scene(root);
        stage.setFullScreen(true);
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
        helper = new SegmentTreeVisualizationHelper(canvas);
        // Initialize the segment_tree array with Segment_Tree_Nodes instances
        for (int i = 0; i < segment_tree.length; i++) {
            segment_tree[i] = new SegmentTreeNodes();
        }
    }

    void reset() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < segment_tree.length; i++) {
            segment_tree[i] = new SegmentTreeNodes();
        }
        helper = new SegmentTreeVisualizationHelper(canvas);
    }

    @FXML
    private void HandleBuild() {
        // Clear the canvas
        reset();

        String input = BuildInput.getText();
        if (input.isEmpty()) {
            alert("Please enter some digits.");
            return;
        }
        if (!input.matches("[0-9 ]+")) {
            alert("Please enter only digits separated by spaces.");
            return;
        }

        String[] inputArray = input.split("\\s+");

        if (inputArray.length > 16) {
            alert("Please enter at most " + 16 + " digits.");
            return;
        }
        numbers = new int[inputArray.length];

        numbers = new int[inputArray.length];
        for (int i = 0; i < inputArray.length; i++) {
            numbers[i] = Integer.parseInt(inputArray[i]);
        }

        double canvas_width = canvas.getWidth();
        build_segment_tree(1, 0, numbers.length - 1, 0, canvas_width);
        build_lines(1, 0, numbers.length - 1);
        build_circle(1, 0, numbers.length - 1);
        // build_circle();
    }

    @FXML
    private void HandleQuerry() {
        String input = QuerryInput.getText();
        if (!input.matches("[0-9 ]+")) { // Match two numbers separated by one or more spaces, allowing leading/trailing
            alert("Please enter only digits.");
            return;
        }

        String[] inputArray = input.split("\\s+");
        for (int i = 0; i < inputArray.length; i++) {
            System.out.println(inputArray[i]);
        }
        if (inputArray.length != 2) {
            alert("Please enter only two digits.");
            return;
        }
        int l = Integer.parseInt(inputArray[0]);
        int r = Integer.parseInt(inputArray[1]);

        if (l < 0 || l >= numbers.length || r < 0 || r >= numbers.length) {
            alert("Please enter valid indices.");
            return;
        }

        instant_build_circle(1, 0, numbers.length - 1);
        int result = query_segment_tree(1, 0, numbers.length - 1, l, r);
        prompt("The sum of the range [" + l + ", " + r + "] is: " + result);
    }

    @FXML
    private void HandleUpdate() {
        String input = UpdateInput.getText(); // Trim leading/trailing spaces
        if (!input.matches("[0-9 ]+")) { // Match two numbers separated by one or more spaces
            alert("Please enter an index and a value separated by a space.");
            return;
        }

        String[] inputArray = input.split("\\s+"); // Split on one or more spaces
        if (inputArray.length != 2) {
            alert("Please enter only two digits.");
            return;
        }
        int index = Integer.parseInt(inputArray[0]);
        int value = Integer.parseInt(inputArray[1]);

        if (index < 0 || index >= numbers.length) {
            alert("Please enter a valid index.");
            return;
        }

        instant_build_circle(1, 0, numbers.length - 1);
        update_segment_tree(1, 0, numbers.length - 1, index, value);
        prompt("The value at index [" + index + "] has been updated to: " + value);
    }

    void alert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    void prompt(String message) {
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, canvas.getHeight()  - 40, canvas.getWidth(), 40);
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(2);
        gc.strokeRect(0, canvas.getHeight()  - 40, canvas.getWidth(), 40);
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gc.fillText(message, 10, canvas.getHeight() - 15);
    }

    int query_segment_tree(int node, int start, int end, int l, int r) {
        if (r < start || end < l) {
            return 0; // Out of range
        }
        PauseTransition pause = new PauseTransition(Duration.seconds(node));

        if (l <= start && end <= r) {
            pause.setOnFinished(e -> {
                build_helper(node, Color.WHITESMOKE);
            });
            pause.play();
            return segment_tree[node].value; // Current segment is completely within range
        }
        int mid = (start + end) / 2;
        int left_sum = query_segment_tree(2 * node, start, mid, l, r);
        int right_sum = query_segment_tree(2 * node + 1, mid + 1, end, l, r);
        pause.setOnFinished(e -> {
            build_helper(node, Color.WHITESMOKE);
        });
        pause.play();
        // build_helper(node, Color.WHITESMOKE);
        return left_sum + right_sum;
    }

    void update_segment_tree(int node, int start, int end, int idx, int value) {
        if (start == end) {
            numbers[idx] = value;
            segment_tree[node].value = value;
        } else {
            int mid = (start + end) / 2;
            if (start <= idx && idx <= mid) {
                update_segment_tree(2 * node, start, mid, idx, value);
            } else {
                update_segment_tree(2 * node + 1, mid + 1, end, idx, value);
            }
            segment_tree[node].value = segment_tree[2 * node].value + segment_tree[2 * node + 1].value;
        }
        PauseTransition pause = new PauseTransition(Duration.seconds(node));
        pause.setOnFinished(e -> {
            build_helper(node, Color.WHEAT);
        });
        pause.play();
    }

    void build_segment_tree(int node, int start, int end, double canvas_start_point, double canvas_width) {
        if (start == end) {
            // Leaf node
            build_helper(node, canvas_start_point, canvas_width, numbers[start], start, end);
        } else {
            int mid = (start + end) / 2;
            build_segment_tree(2 * node, start, mid, canvas_start_point, (canvas_start_point + canvas_width) / 2);
            build_segment_tree(2 * node + 1, mid + 1, end, (canvas_start_point + canvas_width) / 2, canvas_width);
            int n = segment_tree[2 * node].value + segment_tree[2 * node + 1].value;
            build_helper(node, canvas_start_point, canvas_width, n, start, end);
        }
    }

    void build_helper(int node, double canvas_start_point, double canvas_width, int value, int start, int end) {
        segment_tree[node].start = start;
        segment_tree[node].end = end;
        segment_tree[node].value = value;
        double tree_height = Math.ceil(Math.log(numbers.length) / Math.log(2));
        double width_point = (canvas_start_point + canvas_width) / 2;
        int level = (int) (Math.log(node) / Math.log(2)); // Determine the level of the node
        double y = level * (canvas.getHeight() / tree_height) * 0.75 + 50; // Adjust y position
        segment_tree[node].x = width_point - 15;
        segment_tree[node].y = y - 35;
    }

    Pair<Double, Double> build_lines(int node, int start, int end) {
        if (start == end) {
            // build_helper(segment_tree[node].x, segment_tree[node].y,
            // segment_tree[node].value, node);
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
        PauseTransition pause = new PauseTransition(Duration.seconds(node));

        if (start != end) {

            int mid = (start + end) / 2;
            build_circle(2 * node, start, mid);
            build_circle(2 * node + 1, mid + 1, end);
        }
        pause.setOnFinished(e -> {
            build_helper(node, Color.GRAY); // Draw the circle
        });
        pause.play();
    }

    void instant_build_circle(int node, int start, int end) {
        if (start != end) {

            int mid = (start + end) / 2;
            build_circle(2 * node, start, mid);
            build_circle(2 * node + 1, mid + 1, end);
        }
        build_helper(node, Color.GRAY); // Draw the circle
    }

    // private void build_circle() {
    // for (int i = numbers.length * 2; i > 0; i--) {
    // if (segment_tree[i].value == -1)
    // continue;
    // final int index = i; // Create a final copy of i
    // PauseTransition pause = new PauseTransition(Duration.seconds(numbers.length *
    // 2 - i)); // Stagger pauses
    // pause.setOnFinished(e -> {
    // build_helper(index, Color.GRAY); // Draw the circle
    // });
    // pause.play(); // Start the pause transition
    // }
    // }

    void build_helper(int node, Color color) {

        double x = segment_tree[node].x;
        double y = segment_tree[node].y;
        int value = segment_tree[node].value;
        int l = segment_tree[node].start;
        int r = segment_tree[node].end;

        gc.setFill(color);
        gc.fillOval(x - 15, y - 15, 50, 50); // Adjust circle position and increase radius
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeOval(x - 15, y - 15, 50, 50); // Add border to the circle
        gc.setFill(Color.rgb(0, 0, 0, 0.3)); // Set shadow color
        gc.fillOval(x - 10, y - 10, 50, 50); // Add shadow to the circle
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(20)); // Set font size to 20
        gc.fillText(String.valueOf(value), x + 3, y + 6); // Draw the value
        gc.setFill(Color.BLACK);
        gc.fillText("[" + l + "," + r + "]", x - 10, y + 50); // Draw the range

    }

    void build_helper(double x1, double y1, double x2, double y2) {

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.5);
        gc.strokeLine(x1, y1, x2, y2);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
