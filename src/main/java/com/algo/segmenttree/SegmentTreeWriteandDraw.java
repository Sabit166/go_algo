package com.algo.segmenttree;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.canvas.Canvas;

public class SegmentTreeWriteandDraw extends SegmentTreeVisualizationHelper {

    public SegmentTreeWriteandDraw(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    void build_helper(SegmentTreeNodes node, Color color) {

        double x = node.x;
        double y = node.y;
        int value = node.value;
        int l = node.start;
        int r = node.end;

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
