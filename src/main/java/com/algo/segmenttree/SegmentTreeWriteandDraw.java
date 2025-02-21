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
    double writingHeight;
    double writingBreadth;
    double linesDistance;

    public SegmentTreeWriteandDraw(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        try {
            buildLines = Files.readAllLines(Paths.get("src/main/java/com/algo/segmenttree/BuildOperation.txt"));
            // System.out.println("BuildOperation Lines:");
            // for (String line : buildLines) {
            //     System.out.println(line);
            // }
            queryLines = Files.readAllLines(Paths.get("src/main/java/com/algo/segmenttree/QueryOperation.txt"));
            // for (String line : queryLines) {
            //     System.out.println(line);
            // }
            updateLines = Files.readAllLines(Paths.get("src/main/java/com/algo/segmenttree/UpdateOperation.txt"));
            // for (String line : updateLines) {
            //     System.out.println(line);
            // }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
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

    void write(String key)
    {
        writingHeight = 25;
        writingBreadth = canvas.getWidth() * 0.6 - 10;
        linesDistance = 40;
        gc.clearRect(writingBreadth, 0, canvas.getWidth(), canvas.getHeight());
        List<String> lines = null;
        if(key == "build") lines = buildLines;
        if(key == "query") lines = queryLines;
        if(key == "update") lines = updateLines;
        
        for(String line: lines)
            {
                gc.setFill(Color.BLACK);
                gc.setFont(new Font(30));
                gc.fillText(line, writingBreadth, writingHeight);
                System.out.println(line);
                writingHeight += linesDistance;
            }
    }
}
