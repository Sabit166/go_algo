package com.algo.segmenttree;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    }

    @FXML
    private void HandleBuild() {
        // Clear the canvas
        reset();

        String input = BuildInput.getText();
        if (input.isEmpty()) {
            helper.alert("Please enter some digits.");
            return;
        }
        if (!input.matches("[0-9 ]+")) {
            helper.alert("Please enter only digits separated by spaces.");
            return;
        }

        String[] inputArray = input.split("\\s+");

        if (inputArray.length > 16) {
            helper.alert("Please enter at most " + 16 + " digits.");
            return;
        }
        numbers = new int[inputArray.length];
        

        numbers = new int[inputArray.length];
        for (int i = 0; i < inputArray.length; i++) {
            numbers[i] = Integer.parseInt(inputArray[i]);
        }
        helper = new SegmentTreeVisualizationHelper(canvas, numbers);

        double canvas_width = canvas.getWidth();
        helper.build_segment_tree(1, 0, numbers.length - 1, 0, canvas_width);
        helper.build_lines(1, 0, numbers.length - 1);
        helper.build_circle(1, 0, numbers.length - 1);
        // build_circle();
    }

    @FXML
    private void HandleQuerry() {
        String input = QuerryInput.getText();
        if (!input.matches("[0-9 ]+")) { // Match two numbers separated by one or more spaces, allowing leading/trailing
            helper.alert("Please enter only digits.");
            return;
        }

        String[] inputArray = input.split("\\s+");
        for (int i = 0; i < inputArray.length; i++) {
            System.out.println(inputArray[i]);
        }
        if (inputArray.length != 2) {
            helper.alert("Please enter only two digits.");
            return;
        }
        int l = Integer.parseInt(inputArray[0]);
        int r = Integer.parseInt(inputArray[1]);

        if (l < 0 || l >= numbers.length || r < 0 || r >= numbers.length) {
            helper.alert("Please enter valid indices.");
            return;
        }

        helper.instant_build_circle(1, 0, numbers.length - 1);
        int result = helper.query_segment_tree(1, 0, numbers.length - 1, l, r);
        helper.prompt("The sum of the range [" + l + ", " + r + "] is: " + result);
    }

    @FXML
    private void HandleUpdate() {
        String input = UpdateInput.getText(); // Trim leading/trailing spaces
        if (!input.matches("[0-9 ]+")) { // Match two numbers separated by one or more spaces
            helper.alert("Please enter an index and a value separated by a space.");
            return;
        }

        String[] inputArray = input.split("\\s+"); // Split on one or more spaces
        if (inputArray.length != 2) {
            helper.alert("Please enter only two digits.");
            return;
        }
        int index = Integer.parseInt(inputArray[0]);
        int value = Integer.parseInt(inputArray[1]);

        if (index < 0 || index >= numbers.length) {
            helper.alert("Please enter a valid index.");
            return;
        }

        helper.instant_build_circle(1, 0, numbers.length - 1);
        helper.update_segment_tree(1, 0, numbers.length - 1, index, value);
        helper.prompt("The value at index [" + index + "] has been updated to: " + value);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
