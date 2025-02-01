package com.algo.linkedlist;

import javafx.util.Pair;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javafx.scene.canvas.Canvas;

public class stage extends LinkedListVisualizationController
{
    LinkedList<SinglyNode> nodes;
    Map<Pair<Double, Double>, Pair<Double, Double>> map;
    public stage()
    {
        this.nodes = new LinkedList<>();
        this.map = new HashMap<>();
    }
    public stage(Canvas canvas, LinkedList<SinglyNode> nodes, Map<Pair<Double, Double>, Pair<Double, Double>> map)
    {
        this.nodes = nodes;
        this.map = map;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    void draw(stage st)
    {
        if(st == null)
            return;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawNodes(st.nodes);
        drawPointers(st.map);
    }

    void drawNodes(LinkedList<SinglyNode> node)
    {
        for (SinglyNode nd : node) {
            Pair<Double, Double> p1 = nd.getNodeValTopLeftCorner();
            Pair<Double, Double> p2 = nd.getNodeNxtTopLeftCorner();
            Double x1 = nd.getValWidth();
            Double x2 = nd.getNextWidth();

            gc.setFill(nd.getColors().getKey());
            gc.fillRect(p1.getKey(), p1.getValue(), x1, nd.getNodeHeight());
            gc.setFill(nd.getColors().getValue());
            gc.fillRect(p2.getKey(), p2.getValue(), x2, nd.getNodeHeight());
            System.out.println("Node drawn at: (" + p1.getKey() + ", " + p1.getValue() + ") and node value: " + nd.getValue());
        }
    }

    void drawPointers(Map<Pair<Double, Double>, Pair<Double, Double>> mp)
    {
        Double x1, y1, x2, y2;
        if(mp == null)
            return;
        for (Map.Entry<Pair<Double, Double>, Pair<Double, Double>> entry : mp.entrySet()) {
            Pair<Double, Double> p1 = entry.getKey();
            Pair<Double, Double> p2 = entry.getValue();
            x1 = p1.getKey();
            y1 = p1.getValue();
            x2 = p2.getKey();
            y2 = p2.getValue();
            gc.strokeLine(x1, y1, x2, y2);
        }
    }
}