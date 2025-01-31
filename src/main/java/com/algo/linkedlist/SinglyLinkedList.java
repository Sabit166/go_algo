package com.algo.linkedlist;

import javafx.scene.canvas.Canvas;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javafx.scene.control.Alert;

public class SinglyLinkedList extends LinkedListVisualizationController {
    protected int nodeNumber;
    private SinglyNode head;
    private LinkedList<SinglyNode> nodes = new LinkedList<>();
    protected Map<Pair<Double, Double>, Pair<Double, Double>> map = new HashMap<>();

    LinkedListVisualizationController linkedListVisualizationController = new LinkedListVisualizationController();
    Canvas canvas = linkedListVisualizationController.canvas;

    public SinglyLinkedList() {
        this.head = null;
        nodeNumber = 0;
    }

    // Push to the front
    public void pushFront(int value) {
        nodeNumber++;
        if (nodeNumber > 4) {
            alert("Error", "Cannot add more than 4 nodes");
            return;
        }
        SinglyNode newNode = new SinglyNode(value, 50 + nodeNumber * 50, canvas.getHeight() * 0.75);
        // newNode.setNext(head);
        // head = newNode;
        nodes.addFirst(newNode);
        newNode.setNext(head);
        head = newNode;
    }

    // Push to the back

    public void pushBack(int value) {
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

    // Insert at the end
    // public void insertAtEnd(int value) {
    // nodeNumber++;
    // if(nodeNumber > 4) {
    // alert("Error", "Cannot add more than 4 nodes");
    // return;
    // }
    // SinglyNode newNode = new SinglyNode(value);
    // if (head == null) {
    // head = newNode;
    // return;
    // }
    // SinglyNode temp = head;
    // while (temp.getNext() != null) {
    // temp = temp.getNext();
    // }
    // temp.setNext(newNode);
    // }

    // Pop from the front
    public void popFront() {
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
    public void popBack() {
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
    public void insertAt(int index, int value, double x, double y) {
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
    protected double NodeTopLeftCornerX;
    protected double NodeTopLeftCornerY;
    protected double nodeWidth;
    protected double nodeHeight;
    protected double NextPointOutX;
    protected double NextPointOutY;
    protected double NextPointInX;
    protected double NextPointInY;

    public SinglyNode() {
        this.next = null;
        // this.value = 0;
        // nodeWidth = 50;
        // nodeHeight = 50;
    }

    public SinglyNode(int value, double NodeTopLeftCornerX, double NodeTopLeftCornerY) {
        this.value = value;
        this.next = null;
        nodeWidth = canvas.getWidth() / 5;
        nodeHeight = canvas.getHeight() / 5;
        this.NodeTopLeftCornerX = NodeTopLeftCornerX;
        this.NodeTopLeftCornerY = NodeTopLeftCornerY;
        NextPointOutX = NodeTopLeftCornerX + nodeWidth;
        NextPointOutY = NodeTopLeftCornerY + nodeHeight / 2;
        NextPointInY = NodeTopLeftCornerY + nodeHeight / 2;
        NextPointInX = NodeTopLeftCornerX;

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
}