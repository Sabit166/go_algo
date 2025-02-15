package com.algo.segmenttree;

import javafx.animation.PauseTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.util.Pair;

public class SegmentTreeVisualizationHelper extends SegmentTreeVisualizationController {

    public SegmentTreeVisualizationHelper() {
        
    }

    public SegmentTreeVisualizationHelper(Canvas canvas) {
        canvas = this.canvas;
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
}
