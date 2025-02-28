package com.algo.linkedlist;

import javafx.util.Pair;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.canvas.Canvas;

public class DoublyNode extends SinglyNode {
    protected double prevWidth;
    private double NodePrevTopLeftCornerX;
    private double NodePrevTopLeftCornerY;
    private double PrevPointOutX;
    private double PrevPointOutY;
    private double PrevPointInX;
    private double PrevPointInY;
    protected LinearGradient PrevGradient;

    public DoublyNode() {
        super();
    }

    public DoublyNode(Canvas canvas, int value) {
        this.value = String.valueOf(value);
        nodeWidth = canvas.getWidth() / 5;
        nodeHeight = canvas.getHeight() / 2;
        shift = canvas.getWidth() / 4;
        pointerLength = shift - nodeWidth;
        prevWidth = valWidth = nextWidth = nodeWidth / 3;
        NodePrevTopLeftCornerX = 25;
        NodePrevTopLeftCornerY = nodeHeight / 2;
        NodeValTopLeftCornerX = NodePrevTopLeftCornerX + nodeWidth / 3;
        NodeValTopLeftCornerY = NodePrevTopLeftCornerY;
        NodeNxtTopLeftCornerX = NodeValTopLeftCornerX + nodeWidth / 3;
        NodeNxtTopLeftCornerY = NodeValTopLeftCornerY;
        NextPointOutX = NodePrevTopLeftCornerX + nodeWidth;
        NextPointOutY = NodePrevTopLeftCornerY + nodeHeight / 3;
        NextPointInY = NextPointOutY;
        NextPointInX = NodePrevTopLeftCornerX;
        PrevPointOutX = NodePrevTopLeftCornerX + nodeWidth;
        PrevPointOutY = NextPointOutY + nodeHeight / 3;
        PrevPointInY = PrevPointOutY;
        PrevPointInX = NodePrevTopLeftCornerX;
        ValueDisplayX = NodeValTopLeftCornerX + valWidth / 2;
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

        PrevGradient = new LinearGradient(
                NodePrevTopLeftCornerX, NodePrevTopLeftCornerY,
                NodePrevTopLeftCornerX + valWidth, NodePrevTopLeftCornerY + nodeHeight,
                false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.ORANGE), new Stop(1, Color.DARKRED));
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

    public Pair<Double, Double> getNodePrevTopLeftCorner() {
        return new Pair<>(NodePrevTopLeftCornerX, NodePrevTopLeftCornerY);
    }

    public Double getPrevWidth() {
        return prevWidth;
    }

    public Pair<Double, Double> getPrevPointOut() {
        return new Pair<>(PrevPointOutX, PrevPointOutY);
    }

    public Pair<Double, Double> getPrevPointIn() {
        return new Pair<>(PrevPointInX, PrevPointInY);
    }

    public LinearGradient getPrevGradient() {
        return PrevGradient;
    }

    void makeHead() {
        ValGradient = new LinearGradient(
                NodePrevTopLeftCornerX, NodePrevTopLeftCornerY,
                NodePrevTopLeftCornerX + valWidth, NodePrevTopLeftCornerY + nodeHeight,
                false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.ORANGE), new Stop(1, Color.DARKRED));

        NextGradient = new LinearGradient(
                NodePrevTopLeftCornerX, NodePrevTopLeftCornerY,
                NodePrevTopLeftCornerX + valWidth, NodePrevTopLeftCornerY + nodeHeight,
                false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.ORANGE), new Stop(1, Color.DARKRED));

        PrevGradient = new LinearGradient(
                NodePrevTopLeftCornerX, NodePrevTopLeftCornerY,
                NodePrevTopLeftCornerX + valWidth, NodePrevTopLeftCornerY + nodeHeight,
                false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.ORANGE), new Stop(1, Color.DARKRED));
    }

    void makeTail() {
        ValGradient = new LinearGradient(
                NodeNxtTopLeftCornerX, NodeNxtTopLeftCornerY,
                NodeNxtTopLeftCornerX + valWidth, NodeNxtTopLeftCornerY + nodeHeight,
                false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.PURPLE), new Stop(1, Color.DARKVIOLET));

        NextGradient = new LinearGradient(
                NodeNxtTopLeftCornerX, NodeNxtTopLeftCornerY,
                NodeNxtTopLeftCornerX + valWidth, NodeNxtTopLeftCornerY + nodeHeight,
                false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.PURPLE), new Stop(1, Color.DARKVIOLET));

        PrevGradient = new LinearGradient(
                NodeNxtTopLeftCornerX, NodeNxtTopLeftCornerY,
                NodeNxtTopLeftCornerX + valWidth, NodeNxtTopLeftCornerY + nodeHeight,
                false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.PURPLE), new Stop(1, Color.DARKVIOLET));
    }

    void makeTemp() {
        ValGradient = new LinearGradient(
                NodeValTopLeftCornerX, NodeValTopLeftCornerY,
                NodeValTopLeftCornerX + valWidth, NodeValTopLeftCornerY + nodeHeight,
                false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.CYAN), new Stop(1, Color.NAVY));

        NextGradient = new LinearGradient(
                NodeValTopLeftCornerX, NodeValTopLeftCornerY,
                NodeValTopLeftCornerX + valWidth, NodeValTopLeftCornerY + nodeHeight,
                false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.CYAN), new Stop(1, Color.NAVY));

        PrevGradient = new LinearGradient(
                NodeValTopLeftCornerX, NodeValTopLeftCornerY,
                NodeValTopLeftCornerX + valWidth, NodeValTopLeftCornerY + nodeHeight,
                false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.CYAN), new Stop(1, Color.NAVY));
    }

    void setNormalGradient() {
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

        PrevGradient = new LinearGradient(
                NodePrevTopLeftCornerX, NodePrevTopLeftCornerY,
                NodePrevTopLeftCornerX + valWidth, NodePrevTopLeftCornerY + nodeHeight,
                false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.ORANGE), new Stop(1, Color.DARKRED));
    }

    public void shiftRight() {
        NodeValTopLeftCornerX += shift;
        NodeNxtTopLeftCornerX += shift;
        NodePrevTopLeftCornerX += shift;
        NextPointOutX += shift;
        NextPointInX += shift;
        PrevPointOutX += shift;
        PrevPointInX += shift;
        ValueDisplayX += shift;
    }

    public void shiftLeft() {
        NodeValTopLeftCornerX -= shift;
        NodeNxtTopLeftCornerX -= shift;
        NodePrevTopLeftCornerX -= shift;
        NextPointOutX -= shift;
        NextPointInX -= shift;
        PrevPointOutX -= shift;
        PrevPointInX -= shift;
        ValueDisplayX -= shift;
    }
}
