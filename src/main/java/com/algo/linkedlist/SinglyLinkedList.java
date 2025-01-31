package com.algo.linkedlist;

import javafx.scene.canvas.Canvas;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SinglyLinkedList {
    private SinglyNode head;
    private LinkedList<SinglyNode> nodes = new LinkedList<>();
    protected Map<Pair<Double, Double>, Pair<Double, Double>> map = new HashMap<>();


    LinkedListVisualizationController linkedListVisualizationController = new LinkedListVisualizationController();
    Canvas canvas = linkedListVisualizationController.canvas;

    public SinglyLinkedList() {
        this.head = null;
    }

    // Push to the front
    public void pushFront(int value) {
        SinglyNode newNode = new SinglyNode(value, x, y);
        newNode.setNext(head);
        head = newNode;
    }

    // Push to the back

    public void pushBack(int value) {
        SinglyNode newNode = new SinglyNode(value, x, y);
        if (head == null) {
            head = newNode;
            return;
        }
        SinglyNode temp = head;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        temp.setNext(newNode);
    }

    // Insert at the end
    public void insertAtEnd(int value) {
        SinglyNode newNode = new SinglyNode(value, x, y);
        if (head == null) {
            head = newNode;
            return;
        }
        SinglyNode temp = head;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        temp.setNext(newNode);
    }

    // Pop from the front
    public void popFront() {
        if (head != null) {
            SinglyNode temp = head;
            head = head.getNext();
            temp = null;
        }
    }

    // Pop from the back
    public void popBack() {
        if (head == null) {
            return;
        }
        if (head.getNext() == null) {
            head = null;
            return;
        }
        SinglyNode temp = head;
        while (temp.getNext().getNext() != null) {
            temp = temp.getNext();
        }
        temp.setNext(null);
    }

    // Insert at a specific position
    public void insertAt(int index, int value, double x, double y) {
        SinglyNode newNode = new SinglyNode(value, x, y);
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
    }

    // Delete at a specific position
    public void deleteAt(int index) {
        if (head == null) {
            return;
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

    void drawList()
    {

    }
}

class SinglyNode {
    protected int value;
    private SinglyNode next;
    protected double NodeTopRightCornerX;
    protected double NodeTopRightCornerY;
    protected double nodeWidth = 50;
    protected double nodeHeight = 50;
    protected double NextPointOutX;
    protected double NextPointOutY;
    protected double NextPointInX;
    protected double NextPointInY;

    public SinglyNode() {
        this.next = null;
        this.value = 0;
        this.x = 0;
        this.y = 0;
    }

    public SinglyNode(int value, double x, double y) {
        this.value = value;
        this.x = x;
        this.y = y;
        this.next = null;
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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setValue(int value) {
        this.value = value;
    }
}