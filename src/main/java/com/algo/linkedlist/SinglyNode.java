package com.algo.linkedlist;

import javafx.util.Pair;
import javafx.scene.paint.Color;

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
    protected Color ValCol;
    protected Color NextCol;

    public SinglyNode() {
        this.next = null;
    }

    public SinglyNode(int value, double NodeValTopLeftCornerX, double NodeValTopLeftCornerY) {
        this.value = value;
        this.next = null;
        nodeWidth = canvas.getWidth() / 5;
        nodeHeight = canvas.getHeight() / 5;
        valWidth = nextWidth = nodeWidth / 2;
        this.NodeValTopLeftCornerX = NodeValTopLeftCornerX;
        this.NodeValTopLeftCornerY = NodeValTopLeftCornerY;
        NodeNxtTopLeftCornerX = NodeValTopLeftCornerX + nodeWidth/2;
        NodeNxtTopLeftCornerY = NodeValTopLeftCornerY;
        NextPointOutX = NodeValTopLeftCornerX + nodeWidth;
        NextPointOutY = NodeValTopLeftCornerY + nodeHeight / 2;
        NextPointInY = NodeValTopLeftCornerY + nodeHeight / 2;
        NextPointInX = NodeValTopLeftCornerX;
        ValCol = Color.YELLOW;
        NextCol = Color.YELLOWGREEN;
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

    public Pair<Double, Double> getAllWidth() {
        return new Pair<>(valWidth, nextWidth);
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
        double shift = canvas.getWidth() / 4;
        NodeValTopLeftCornerX += shift;
        NodeNxtTopLeftCornerX += shift;
        NextPointOutX += shift;
        NextPointInX += shift;
    }

    public void shiftLeft()
    {
        double shift = canvas.getWidth() / 4;
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