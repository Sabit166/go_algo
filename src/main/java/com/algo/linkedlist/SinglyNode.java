package com.algo.linkedlist;

import javafx.util.Pair;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.Map;
import javafx.scene.canvas.Canvas;

public class SinglyNode extends LinkedListVisualizationController {
    protected int value;
    private SinglyNode next;
    protected double NodeValTopLeftCornerX;
    protected double NodeValTopLeftCornerY;
    protected double NodeNxtTopLeftCornerX;
    protected double NodeNxtTopLeftCornerY;
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
    protected LinearGradient PointerGradient;
    protected Color NextCol;
    protected Rectangle valueBox;
    protected Rectangle nextBox;
    protected Line nextPointer;

    public SinglyNode() {
        this.next = null;
    }

    public SinglyNode(Canvas canvas, int value) {
        this.value = value;
        this.next = null;
        nodeWidth = canvas.getWidth() / 5;
        nodeHeight = canvas.getHeight();
        shift = canvas.getWidth() / 4;
        pointerLength = shift - nodeWidth;
        valWidth = nextWidth = nodeWidth / 2;
        NodeValTopLeftCornerX = 25;
        NodeValTopLeftCornerY = nodeHeight / 2;
        NodeNxtTopLeftCornerX = NodeValTopLeftCornerX + nodeWidth / 2;
        NodeNxtTopLeftCornerY = NodeValTopLeftCornerY;
        NextPointOutX = NodeValTopLeftCornerX + nodeWidth;
        NextPointOutY = NodeValTopLeftCornerY + nodeHeight / 4;
        NextPointInY = NodeValTopLeftCornerY + nodeHeight / 4;
        NextPointInX = NodeValTopLeftCornerX;
        valueBox = new Rectangle(NodeValTopLeftCornerX, NodeValTopLeftCornerY, valWidth, nodeHeight);
        // valueBox.setStyle("-fx-border-color: black");
        nextBox = new Rectangle(NodeNxtTopLeftCornerX, NodeNxtTopLeftCornerY, nextWidth, nodeHeight);
        // nextBox.setStyle("-fx-border-color: black");
        nextPointer = new Line(NextPointOutX, NextPointOutY, NextPointOutX + pointerLength, NextPointOutY);
        // nextPointer.setStyle("-fx-stroke: black");

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

        PointerGradient = new LinearGradient(
                NodeNxtTopLeftCornerX, NodeNxtTopLeftCornerY,
                NodeNxtTopLeftCornerX + valWidth, NodeNxtTopLeftCornerY + nodeHeight,
                false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.BLACK), new Stop(1, Color.DARKGRAY));
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        System.out.println("Value: " + value);
        System.out.println("NodeValTopLeftCornerX: " + NodeValTopLeftCornerX);
        System.out.println("NodeValTopLeftCornerY: " + NodeValTopLeftCornerY);
        System.out.println("NodeNxtTopLeftCornerX: " + NodeNxtTopLeftCornerX);
        System.out.println("NodeNxtTopLeftCornerY: " + NodeNxtTopLeftCornerY);
        System.out.println("NodeWidth: " + nodeWidth);
        System.out.println("NodeHeight: " + nodeHeight);
        System.out.println("ValWidth: " + valWidth);
        System.out.println("NextWidth: " + nextWidth);
        System.out.println("NextPointOutX: " + NextPointOutX);
        System.out.println("NextPointOutY: " + NextPointOutY);
        System.out.println("NextPointInX: " + NextPointInX);
        System.out.println("NextPointInY: " + NextPointInY);
        System.out.println("Shift: " + shift);
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

    public int getValue() {
        return value;
    }

    public Pair<Double, Double> getNextPointOut() {
        return new Pair<>(NextPointOutX, NextPointOutY);
    }

    public Pair<Double, Double> getNextPointIn() {
        return new Pair<>(NextPointInX, NextPointInY);
    }

    public LinearGradient getValGradient() {
        return ValGradient;
    }

    public LinearGradient getNextGradient() {
        return NextGradient;
    }

    public LinearGradient getPointerGradient() {
        return PointerGradient;
    }

    void drawValBox() {

    }

    void drawNextBox() {

    }

    void drawNextPointer() {

    }

    public void shiftRight() {
        // map.put(new Pair<>(NextPointOutX + shift, NextPointOutY), new
        // Pair<>(NextPointInX + shift, NextPointInY));
        NodeValTopLeftCornerX += shift;
        NodeNxtTopLeftCornerX += shift;
        NextPointOutX += shift;
        NextPointInX += shift;
        System.out.println("Shifted right at point: (" + NodeValTopLeftCornerX + ", " + NodeValTopLeftCornerY + ")");
    }

    public void shiftLeft() {
        NodeValTopLeftCornerX -= shift;
        NodeNxtTopLeftCornerX -= shift;
        NextPointOutX -= shift;
        NextPointInX -= shift;
    }
}