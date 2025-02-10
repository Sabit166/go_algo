package com.algo.linkedlist;

import javafx.util.Pair;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.canvas.Canvas;

public class SinglyNode extends LinkedListVisualizationController {
    protected String value;
    protected double NodeValTopLeftCornerX;
    protected double NodeValTopLeftCornerY;
    protected double NodeNxtTopLeftCornerX;
    protected double NodeNxtTopLeftCornerY;
    protected double ValueDisplayX;
    protected double ValueDisplayY;
    protected double nodeWidth;
    protected double nodeHeight;
    protected double valWidth;
    protected double nextWidth;
    protected double NextPointOutX;
    protected double NextPointOutY;
    protected double NextPointInX;
    protected double NextPointInY;
    protected double shift;
    protected double pointerLength;
    protected LinearGradient ValGradient;
    protected LinearGradient NextGradient;
    protected LinearGradient BorderGradient;
    protected Color NextCol;

    public SinglyNode() {
    }

    public SinglyNode(Canvas canvas, int value) {
        this.value = String.valueOf(value);
        nodeWidth = canvas.getWidth() / 5;
        nodeHeight = canvas.getHeight()/2;
        shift = canvas.getWidth() / 4;
        pointerLength = shift - nodeWidth;
        valWidth = nextWidth = nodeWidth / 2;
        NodeValTopLeftCornerX = 25;
        NodeValTopLeftCornerY = nodeHeight / 2;
        NodeNxtTopLeftCornerX = NodeValTopLeftCornerX + nodeWidth / 2;
        NodeNxtTopLeftCornerY = NodeValTopLeftCornerY;
        NextPointOutX = NodeValTopLeftCornerX + nodeWidth;
        NextPointOutY = NodeValTopLeftCornerY + nodeHeight / 2;
        NextPointInY = NodeValTopLeftCornerY + nodeHeight / 2;
        NextPointInX = NodeValTopLeftCornerX;
        ValueDisplayX = NodeValTopLeftCornerX + valWidth / 5;
        ValueDisplayY = NodeValTopLeftCornerY + nodeHeight / 2;

        ValGradient = new LinearGradient(
                NodeValTopLeftCornerX, NodeValTopLeftCornerY,
                NodeValTopLeftCornerX + valWidth, NodeValTopLeftCornerY + nodeHeight,
                false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.CYAN), new Stop(1, Color.NAVY));

        NextGradient = new LinearGradient(
                NodeNxtTopLeftCornerX, NodeNxtTopLeftCornerY,
                NodeNxtTopLeftCornerX + valWidth, NodeNxtTopLeftCornerY + nodeHeight,
                false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.PURPLE), new Stop(1, Color.DARKVIOLET));
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        // System.out.println("Value: " + value);
        // System.out.println("NodeValTopLeftCornerX: " + NodeValTopLeftCornerX);
        // System.out.println("NodeValTopLeftCornerY: " + NodeValTopLeftCornerY);
        // System.out.println("NodeNxtTopLeftCornerX: " + NodeNxtTopLeftCornerX);
        // System.out.println("NodeNxtTopLeftCornerY: " + NodeNxtTopLeftCornerY);
        // System.out.println("NodeWidth: " + nodeWidth);
        // System.out.println("NodeHeight: " + nodeHeight);
        // System.out.println("ValWidth: " + valWidth);
        // System.out.println("NextWidth: " + nextWidth);
        // System.out.println("NextPointOutX: " + NextPointOutX);
        // System.out.println("NextPointOutY: " + NextPointOutY);
        // System.out.println("NextPointInX: " + NextPointInX);
        // System.out.println("NextPointInY: " + NextPointInY);
        // System.out.println("Shift: " + shift);
    }

    public Pair<Double, Double> getNodeValTopLeftCorner() {
        return new Pair<>(NodeValTopLeftCornerX, NodeValTopLeftCornerY);
    }

    public Pair<Double, Double> getNodeNxtTopLeftCorner() {
        return new Pair<>(NodeNxtTopLeftCornerX, NodeNxtTopLeftCornerY);
    }

    public Pair<Double, Double> getNodeDimensions() {
        return new Pair<>(nodeWidth, nodeHeight);
    }

    public double getNodeWidth() {
        return nodeWidth;
    }

    public double getNodeHeight() {
        return nodeHeight;
    }

    public double getValWidth() {
        return valWidth;
    }

    public double getNextWidth() {
        return nextWidth;
    }

    public String getValue() {
        return value;
    }

    public Pair<Double, Double> getNextPointOut() {
        return new Pair<>(NextPointOutX, NextPointOutY);
    }

    public Pair<Double, Double> getNextPointIn() {
        return new Pair<>(NextPointInX, NextPointInY);
    }

    public Pair<Double, Double> getValueDisplay() {
        return new Pair<>(ValueDisplayX, ValueDisplayY);
    }

    public LinearGradient getValGradient() {
        return ValGradient;
    }

    public LinearGradient getNextGradient() {
        return NextGradient;
    }

    public LinearGradient getBorderGradient() {
        return BorderGradient;
    }

    void makeHead()
    {
        NextGradient = ValGradient = new LinearGradient(
            NodeValTopLeftCornerX, NodeValTopLeftCornerY,
            NodeValTopLeftCornerX + valWidth, NodeValTopLeftCornerY + nodeHeight,
            false, CycleMethod.NO_CYCLE,
            new Stop(0, Color.CYAN), new Stop(1, Color.NAVY));
    }

    void makeTemp()
    {
        NextGradient = ValGradient = new LinearGradient(
            NodeNxtTopLeftCornerX, NodeNxtTopLeftCornerY,
            NodeNxtTopLeftCornerX + valWidth, NodeNxtTopLeftCornerY + nodeHeight,
            false, CycleMethod.NO_CYCLE,
            new Stop(0, Color.PURPLE), new Stop(1, Color.DARKVIOLET));
    }

    void setNormalGradient()
    {
        NextGradient = new LinearGradient(
            NodeNxtTopLeftCornerX, NodeNxtTopLeftCornerY,
            NodeNxtTopLeftCornerX + valWidth, NodeNxtTopLeftCornerY + nodeHeight,
            false, CycleMethod.NO_CYCLE,
            new Stop(0, Color.PURPLE), new Stop(1, Color.DARKVIOLET));
            
        ValGradient = new LinearGradient(
            NodeValTopLeftCornerX, NodeValTopLeftCornerY,
            NodeValTopLeftCornerX + valWidth, NodeValTopLeftCornerY + nodeHeight,
            false, CycleMethod.NO_CYCLE,
            new Stop(0, Color.CYAN), new Stop(1, Color.NAVY));
    }


    public void shiftRight() {
        // map.put(new Pair<>(NextPointOutX + shift, NextPointOutY), new
        // Pair<>(NextPointInX + shift, NextPointInY));
        NodeValTopLeftCornerX += shift;
        NodeNxtTopLeftCornerX += shift;
        NextPointOutX += shift;
        NextPointInX += shift;
    }

    public void shiftLeft() {
        NodeValTopLeftCornerX -= shift;
        NodeNxtTopLeftCornerX -= shift;
        NextPointOutX -= shift;
        NextPointInX -= shift;
    }
}