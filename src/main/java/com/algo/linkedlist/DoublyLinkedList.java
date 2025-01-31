package com.algo.linkedlist;

import javafx.scene.canvas.Canvas;
import java.util.LinkedList;

public class DoublyLinkedList extends SinglyLinkedList {
    private doublyNode head;
    private LinkedList<doublyNode> nodes = new LinkedList<>();

    LinkedListVisualizationController linkedListVisualizationController = new LinkedListVisualizationController();
    Canvas canvas = linkedListVisualizationController.canvas;

    public DoublyLinkedList() {
        this.head = null;
    }

    // Push to the front
    public void pushFront(int value) {
        doublyNode newNode = new doublyNode(value);
        newNode.setNext(head);
        if (head != null) {
            head.setPrev(newNode);
        }
        head = newNode;
    }

    // Push to the back
    public void pushBack(int value) {
        doublyNode newNode = new doublyNode(value);
        if (head == null) {
            head = newNode;
            return;
        }
        doublyNode temp = head;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        temp.setNext(newNode);
        newNode.setPrev(temp);
    }

    // Pop from the front
    public void popFront() {
        if (head != null) {
            doublyNode temp = head;
            head = head.getNext();
            if (head != null) {
                head.setPrev(null);
            }
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
        doublyNode temp = head;
        while (temp.getNext().getNext() != null) {
            temp = temp.getNext();
        }
        temp.setNext(null);
    }

    // Print the list
    public void printList() {
        doublyNode temp = head;
        while (temp != null) {
            System.out.print(temp.getValue() + " ");
            temp = temp.getNext();
        }
        System.out.println();
    }
}

class doublyNode extends SinglyNode {
    private doublyNode next;
    private doublyNode prev;
    protected double PrevPointOutX;
    protected double PrevPointOutY;
    protected double PrevPointInX;
    protected double PrevPointInY;

    public doublyNode(int value) {
        this.value = value;
        this.next = null;
        this.prev = null;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    public doublyNode getNext() {
        return next;
    }

    public void setNext(doublyNode next) {
        this.next = next;
    }

    public doublyNode getPrev() {
        return prev;
    }

    public void setPrev(doublyNode prev) {
        this.prev = prev;
    }
}
