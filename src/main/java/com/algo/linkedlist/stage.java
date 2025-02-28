package com.algo.linkedlist;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class stage extends LinkedListVisualizationController
{
    LinkedList<SinglyNode> singlynodes;
    LinkedList<DoublyNode> doublynodes;
    Map<Pair<Double, Double>, Pair<Double, Double>> map;
    public stage()
    {
        this.singlynodes = new LinkedList<>();
        this.doublynodes = new LinkedList<>();
        this.map = new HashMap<>();
    }
    public stage(Canvas canvas, LinkedList<SinglyNode> singlynodes, Map<Pair<Double, Double>, Pair<Double, Double>> mp)
    {
        this.singlynodes = singlynodes;
        this.map = mp;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }
    public stage(Canvas canvas, LinkedList<DoublyNode> doublynodes, Map<Pair<Double, Double>, Pair<Double, Double>> mp, boolean isDoubly)
    {
        this.doublynodes = doublynodes;
        this.map = mp;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    void draw(stage st)
    {
        if(st == null)
            return;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // LinkedList<SinglyNode> node = st.nodes;
        // Map<Pair<Double, Double>, Pair<Double, Double>> mp = st.map;
        // for (SinglyNode nd : node) {
        //     Pair<Double, Double> p1 = nd.getNodeValTopLeftCorner();
        //     Pair<Double, Double> p2 = nd.getNodeNxtTopLeftCorner();
        //     Double x1 = nd.getValWidth();
        //     Double x2 = nd.getNextWidth();

        //     gc.setFill(nd.ValGradient);
        //     gc.fillRect(p1.getKey(), p1.getValue(), x1, nd.getNodeHeight());
        //     gc.setStroke(nd.PointerGradient);
        //     gc.setLineWidth(6.0);
        //     gc.strokeRect(p1.getKey(), p1.getValue(), x1, nd.getNodeHeight());

        //     gc.setFill(nd.NextGradient);
        //     gc.fillRect(p2.getKey(), p2.getValue(), x2, nd.getNodeHeight());
        //     gc.setStroke(nd.PointerGradient);
        //     gc.setLineWidth(6.0);
        //     gc.strokeRect(p2.getKey(), p2.getValue(), x2, nd.getNodeHeight());
        // }

        drawNodes(st.singlynodes);
        drawPointers(st.map);

        // Double x1, y1, x2, y2;
        // for (Map.Entry<Pair<Double, Double>, Pair<Double, Double>> entry : mp.entrySet()) {
        //     Pair<Double, Double> p1 = entry.getKey();
        //     Pair<Double, Double> p2 = entry.getValue();
        //     x1 = p1.getKey();
        //     y1 = p1.getValue();
        //     x2 = p2.getKey();
        //     y2 = p2.getValue();
        //     gc.setStroke(Color.BLACK);
        //     gc.setLineWidth(6.0);
        //     gc.strokeLine(x1, y1, x2, y2);
        // }
    } 

    void drawNodes(LinkedList<SinglyNode> node)
    {
        for (SinglyNode nd : node) {
            Pair<Double, Double> p1 = nd.getNodeValTopLeftCorner();
            Pair<Double, Double> p2 = nd.getNodeNxtTopLeftCorner();
            Pair<Double, Double> p3 = nd.getValueDisplay();
            Double x1 = nd.getValWidth();
            Double x2 = nd.getNextWidth();

            gc.setFill(nd.ValGradient);
            gc.fillRect(p1.getKey(), p1.getValue(), x1, nd.getNodeHeight());
            gc.setStroke(nd.BorderGradient);
            gc.setLineWidth(6.0);
            gc.strokeRect(p1.getKey(), p1.getValue(), x1, nd.getNodeHeight());

            gc.setFill(nd.NextGradient);
            gc.fillRect(p2.getKey(), p2.getValue(), x2, nd.getNodeHeight());
            gc.setStroke(nd.BorderGradient);
            gc.setLineWidth(6.0);
            gc.strokeRect(p2.getKey(), p2.getValue(), x2, nd.getNodeHeight());

            gc.setFill(Color.BLACK);
            gc.setFont(new Font(80)); 
            gc.fillText(nd.getValue(), p3.getKey(), p3.getValue());
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
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(6.0);
            gc.strokeLine(x1, y1, x2, y2);
        }
    }

    void draw(stage st, boolean isDoubly)
    {
        if(st == null)
            return;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // LinkedList<SinglyNode> node = st.nodes;
        // Map<Pair<Double, Double>, Pair<Double, Double>> mp = st.map;
        // for (SinglyNode nd : node) {
        //     Pair<Double, Double> p1 = nd.getNodeValTopLeftCorner();
        //     Pair<Double, Double> p2 = nd.getNodeNxtTopLeftCorner();
        //     Double x1 = nd.getValWidth();
        //     Double x2 = nd.getNextWidth();

        //     gc.setFill(nd.ValGradient);
        //     gc.fillRect(p1.getKey(), p1.getValue(), x1, nd.getNodeHeight());
        //     gc.setStroke(nd.PointerGradient);
        //     gc.setLineWidth(6.0);
        //     gc.strokeRect(p1.getKey(), p1.getValue(), x1, nd.getNodeHeight());

        //     gc.setFill(nd.NextGradient);
        //     gc.fillRect(p2.getKey(), p2.getValue(), x2, nd.getNodeHeight());
        //     gc.setStroke(nd.PointerGradient);
        //     gc.setLineWidth(6.0);
        //     gc.strokeRect(p2.getKey(), p2.getValue(), x2, nd.getNodeHeight());
        // }

        drawNodes(st.doublynodes, true);
        drawPointers(st.map);

        // Double x1, y1, x2, y2;
        // for (Map.Entry<Pair<Double, Double>, Pair<Double, Double>> entry : mp.entrySet()) {
        //     Pair<Double, Double> p1 = entry.getKey();
        //     Pair<Double, Double> p2 = entry.getValue();
        //     x1 = p1.getKey();
        //     y1 = p1.getValue();
        //     x2 = p2.getKey();
        //     y2 = p2.getValue();
        //     gc.setStroke(Color.BLACK);
        //     gc.setLineWidth(6.0);
        //     gc.strokeLine(x1, y1, x2, y2);
        // }
    } 

    void drawNodes(LinkedList<DoublyNode> node, boolean isDoubly)
    {
        for (DoublyNode nd : node) {
            Pair<Double, Double> p1 = nd.getNodeValTopLeftCorner();
            Pair<Double, Double> p2 = nd.getNodeNxtTopLeftCorner();
            Pair<Double, Double> p3 = nd.getNodePrevTopLeftCorner();
            Pair<Double, Double> p4 = nd.getValueDisplay();
            Double x1 = nd.getValWidth();
            Double x2 = nd.getNextWidth();
            Double x3 = nd.getPrevWidth();

            gc.setFill(nd.ValGradient);
            gc.fillRect(p1.getKey(), p1.getValue(), x1, nd.getNodeHeight());
            gc.setStroke(nd.BorderGradient);
            gc.setLineWidth(6.0);
            gc.strokeRect(p1.getKey(), p1.getValue(), x1, nd.getNodeHeight());

            gc.setFill(nd.NextGradient);
            gc.fillRect(p2.getKey(), p2.getValue(), x2, nd.getNodeHeight());
            gc.setStroke(nd.BorderGradient);
            gc.setLineWidth(6.0);
            gc.strokeRect(p2.getKey(), p2.getValue(), x2, nd.getNodeHeight());

            gc.setFill(nd.PrevGradient);
            gc.fillRect(p3.getKey(), p3.getValue(), x3, nd.getNodeHeight());
            gc.setStroke(nd.BorderGradient);
            gc.setLineWidth(6.0);
            gc.strokeRect(p3.getKey(), p3.getValue(), x3, nd.getNodeHeight());

            gc.setFill(Color.BLACK);
            gc.setFont(new Font(80)); 
            gc.fillText(nd.getValue(), p4.getKey(), p4.getValue());
        }
    }
}
