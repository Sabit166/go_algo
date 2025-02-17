package com.algo.segmenttree;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.canvas.Canvas;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

public class SegmentTreeWriteandDraw extends SegmentTreeVisualizationHelper {
    List<String> buildLines;
    List<String> queryLines;
    List<String> updateLines;

    public SegmentTreeWriteandDraw(Canvas canvas, int[] numbers) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        try {
            buildLines = Files.readAllLines(Paths.get("BuildOperation"));
            System.out.println("BuildOperation Lines:");
            for (String line : buildLines) {
                System.out.println(line);
            }
            queryLines = Files.readAllLines(Paths.get("QueryOperation"));
            updateLines = Files.readAllLines(Paths.get("UpdateOperation"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    void writeBuild()
    {

    }

    void writeQuery()
    {

    }

    void writeUpdate()
    {

    }
}
