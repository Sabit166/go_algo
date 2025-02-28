package com.algo.segmenttree;

import javafx.animation.PauseTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import javafx.util.Pair;

public class SegmentTreeVisualizationHelper extends SegmentTreeVisualizationController {

    public SegmentTreeVisualizationHelper() {
        
    }

    public SegmentTreeVisualizationHelper(Canvas canvas, int[] numbers, SegmentTreeWriteandDraw draw) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.draw = draw;
        // Initialize the segment_tree array with Segment_Tree_Nodes instances
        for (int i = 0; i < segment_tree.length; i++) {
            segment_tree[i] = new SegmentTreeNodes();
        }
        this.numbers = numbers;
    }


    int query_segment_tree(int node, int start, int end, int l, int r) {
        if (r < start || end < l) {
            return 0; // Out of range
        }
        PauseTransition pause = new PauseTransition(Duration.seconds(node));

        if (l <= start && end <= r) {
            pause.setOnFinished(e -> {
                draw.build_helper(segment_tree[node], Color.WHITESMOKE);
            });
            pause.play();
            return segment_tree[node].value; // Current segment is completely within range
        }
        int mid = (start + end) / 2;
        int left_sum = query_segment_tree(2 * node, start, mid, l, r);
        int right_sum = query_segment_tree(2 * node + 1, mid + 1, end, l, r);
        pause.setOnFinished(e -> {
            draw.build_helper(segment_tree[node], Color.WHITESMOKE);
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
            draw.build_helper(segment_tree[node], Color.WHEAT);
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
        double y = level * (canvas.getHeight() / tree_height) * 0.75 + 25; // Adjust y position
        segment_tree[node].x = width_point - 15;
        segment_tree[node].y = y;
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

        draw.build_helper(x, y, left.getKey(), left.getValue());
        draw.build_helper(x, y, right.getKey(), right.getValue());

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
            draw.build_helper(segment_tree[node], Color.GRAY); // Draw the circle
        });
        pause.play();
    }

    void instant_build_circle(int node, int start, int end) {
        if (start != end) {

            int mid = (start + end) / 2;
            build_circle(2 * node, start, mid);
            build_circle(2 * node + 1, mid + 1, end);
        }
        draw.build_helper(segment_tree[node], Color.GRAY); // Draw the circle
    }

    void alert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    void prompt(String message, String timeComplexity) {
        double height = canvas.getHeight()  - 40;
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, height , canvas.getWidth(), 40);
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(2);
        gc.strokeRect(0, height, canvas.getWidth(), 40);
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gc.fillText(message, 50, height + 25);
        gc.fillText(timeComplexity, canvas.getWidth() - 400, height + 25);
    }
}
