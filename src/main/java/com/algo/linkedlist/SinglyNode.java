package com.algo.linkedlist;

import javafx.util.Pair;
import javafx.scene.paint.Color;
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
    protected Color ValCol;
    protected Color NextCol;

    public SinglyNode() {
        this.next = null;
    }

    public SinglyNode(Canvas canvas, int value) {
        this.value = value;
        this.next = null;
        nodeWidth = canvas.getWidth() / 5;
        nodeHeight = canvas.getHeight();
        shift = canvas.getWidth() / 4;
        valWidth = nextWidth = nodeWidth / 2;
        NodeValTopLeftCornerX = 25;
        NodeValTopLeftCornerY = nodeHeight / 2;
        NodeNxtTopLeftCornerX = NodeValTopLeftCornerX + nodeWidth/2;
        NodeNxtTopLeftCornerY = NodeValTopLeftCornerY;
        NextPointOutX = NodeValTopLeftCornerX + nodeWidth;
        NextPointOutY = NodeValTopLeftCornerY + nodeHeight / 2;
        NextPointInY = NodeValTopLeftCornerY + nodeHeight / 2;
        NextPointInX = NodeValTopLeftCornerX;
        ValCol = Color.YELLOW;
        NextCol = Color.YELLOWGREEN;
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

    public SinglyNode getNext() {
        return next;
    }

    public void setNext(SinglyNode next) {
        this.next = next;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public void setValCol(Color valCol) {
        this.ValCol = valCol;
    }

    public void setNextCol(Color nextCol) {
        this.NextCol = nextCol;
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

        public Pair<Double, Double> getNextPointOut() {
        return new Pair<>(NextPointOutX, NextPointOutY);
    }

    public Pair<Double, Double> getNextPointIn() {
        return new Pair<>(NextPointInX, NextPointInY);
    }

    public Pair<Color, Color> getColors() {
        return new Pair<>(ValCol, NextCol);
    }

    public void shiftRight()
    {
        NodeValTopLeftCornerX += shift;
        NodeNxtTopLeftCornerX += shift;
        NextPointOutX += shift;
        NextPointInX += shift;
        System.out.println("Shifted right at point: (" + NodeValTopLeftCornerX + ", " + NodeValTopLeftCornerY + ")");
    }

    public void shiftLeft()
    {
        NodeValTopLeftCornerX -= shift;
        NodeNxtTopLeftCornerX -= shift;
        NextPointOutX -= shift;
        NextPointInX -= shift;
    }

    public void indicateHead()
    {
        ValCol = Color.RED;
        NextCol = Color.RED;
    }

    public void setNormal()
    {
        ValCol = Color.YELLOW;
        NextCol = Color.YELLOWGREEN;
    }
}