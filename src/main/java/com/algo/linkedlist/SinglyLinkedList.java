package com.algo.linkedlist;

import javafx.scene.canvas.Canvas;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;


public class SinglyLinkedList extends LinkedListVisualizationController {
    protected int nodeNumber;
    private SinglyNode head;
    private LinkedList<SinglyNode> nodes = new LinkedList<>();
    protected Map<Pair<Double, Double>, Pair<Double, Double>> map = new HashMap<>();
    private ArrayList<stage> stages;// = new ArrayList<>();

    LinkedListVisualizationController linkedListVisualizationController = new LinkedListVisualizationController();
    Canvas canvas = linkedListVisualizationController.canvas;

    public SinglyLinkedList() {
        this.head = null;
        nodeNumber = 0;
    }

    // Push to the front
    public ArrayList<stage> pushFront(int value) {
        nodeNumber++;
        if (nodeNumber > 4) {
            alert("Error", "Cannot add more than 4 nodes");
            return null;
        }
        SinglyNode newNode = new SinglyNode(value, 50 + nodeNumber * 50, canvas.getHeight() * 0.75);
        // newNode.setNext(head);
        // head = newNode;
        nodes.addFirst(newNode);
    }

    // Push to the back

    public ArrayList<stage> pushBack(int value) {
        nodeNumber++;
        if (nodeNumber > 4) {
            alert("Error", "Cannot add more than 4 nodes");
            return;
        }
        SinglyNode newNode = new SinglyNode(value, 50 + nodeNumber * 50, canvas.getHeight() * 0.75);
        if (head == null) {
            head = newNode;
            return;
        }
        SinglyNode temp = head;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        temp.setNext(newNode);
        nodes.addLast(newNode);
    }


    // Pop from the front
    public ArrayList<stage> popFront() {
        nodeNumber--;
        if (nodeNumber < 0) {
            alert("Error", "There are no nodes to delete");
            return;
        }
        if (!head.equals(nodes.getFirst())) {
            alert("Error", "Cannot delete node from the front");
            return;
        }
        SinglyNode temp = head;
        head = head.getNext();
        temp = null;
        nodes.removeFirst();

    }

    // Pop from the back
    public ArrayList<stage> popBack() {
        nodeNumber--;
        if (nodeNumber < 0) {
            alert("Error", "There are no nodes to delete");
            return;
        }
        if (head.getNext() == null) {
            head = null;
            nodes.removeLast();
            return;
        }
        SinglyNode temp = head;
        while (temp.getNext().getNext() != null) {
            temp = temp.getNext();
        }
        temp.setNext(null);
        nodes.removeLast();
    }

    // Insert at a specific position
    public ArrayList<stage> insertAt(int index, int value) {
        SinglyNode newNode = new SinglyNode(value, 50 + nodeNumber * 50, canvas.getHeight() * 0.75);
        if (index == 0) {
            newNode.setNext(head);
            head = newNode;
            return;
        }
        SinglyNode temp = head;
        for (int i = 0; i < index - 1; i++) {
            if (temp.getNext() == null) {
                return;
            }
            temp = temp.getNext();
        }
        newNode.setNext(temp.getNext());
        temp.setNext(newNode);
        nodes.add(index, newNode);
    }

    // Delete at a specific position
    public ArrayList<stage> deleteAt(int index) {
        if (head == null) {
            return null;
        }
        if (index == 0) {
            SinglyNode temp = head;
            head = head.getNext();
            temp = null;
            return;
        }
        SinglyNode temp = head;
        for (int i = 0; i < index - 1; i++) {
            if (temp.getNext() == null) {
                return;
            }
            temp = temp.getNext();
        }
        SinglyNode delNode = temp.getNext();
        temp.setNext(delNode.getNext());
        delNode = null;
        nodes.remove(index);
    }

    // Print the list
    public void printList() {
        SinglyNode temp = head;
        while (temp != null) {
            System.out.print(temp.getValue() + " ");
            temp = temp.getNext();
        }
        System.out.println();
    }

    void drawList() {

    }

    void alert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

class SinglyNode extends LinkedListVisualizationController {
    protected int value;
    private SinglyNode next;
    protected double NodeValTopLeftCornerX;
    protected double NodeValTopLeftCornerY;
    protected double NodeNxtTopLeftCornerX;
    protected double NodeNxtTopLeftCornerY;
    protected double nodeWidth;
    protected double nodeHeight;
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

    public Pair<Double, Double> getNextPointOut() {
        return new Pair<>(NextPointOutX, NextPointOutY);
    }

    public Pair<Double, Double> getNextPointIn() {
        return new Pair<>(NextPointInX, NextPointInY);
    }

    public Pair<Color, Color> getColors() {
        return new Pair<>(ValCol, NextCol);
    }
}