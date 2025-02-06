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
    private SinglyNode head;
    protected LinkedList<SinglyNode> nodes = new LinkedList<>();
    protected Map<Pair<Double, Double>, Pair<Double, Double>> map = new HashMap<>();
    protected ArrayList<stage> stages;// = new ArrayList<>();

    LinkedListVisualizationController linkedListVisualizationController = new LinkedListVisualizationController();
    Canvas canvas = linkedListVisualizationController.canvas;

    public SinglyLinkedList(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    // Push to the front
    public ArrayList<stage> pushFront(int value) {
        if (nodes.size() == 4) {
            alert("Error", "Cannot add more than 4 nodes");
            return null;
        }
        SinglyNode newNode = new SinglyNode(canvas, value);
        if (nodes.size() == 0) {
            stages = new ArrayList<>();
            nodes.addFirst(newNode);
            newNode.makeHead();
            //System.out.println("Nodes after adding first node: " + nodes);
            stages.add(new stage(canvas, nodes, null));
            return stages;
        }
        else
        {
            stages = new ArrayList<>();
            nodes.addFirst(newNode);
            //System.out.println("Nodes after adding new node: " + nodes);
            for (int i = 1; i < nodes.size(); i++) {
                nodes.get(i).shiftRight();
            }
            map.clear();
            for (int i = 1; i < nodes.size() - 1; i++) {
                SinglyNode node1 = nodes.get(i);
                SinglyNode node2 = nodes.get(i + 1);
                map.put(node1.getNextPointOut(), node2.getNextPointIn());
            }

            stages.add(new stage(canvas, nodes, map));
            map.put(nodes.get(0).getNextPointOut(), nodes.get(1).getNextPointIn());
            stages.add(new stage(canvas, nodes, map));
            nodes.get(0).makeHead();
            nodes.get(1).removeHead();
            stages.add(new stage(canvas, nodes, map));
            return stages;
        }
    }

    // Push to the back

    public ArrayList<stage> pushBack(int value) {
        if (nodes.size() == 4) {
            alert("Error", "Cannot add more than 4 nodes");
            return null;
        }
        SinglyNode newNode = new SinglyNode(canvas, value);
        if (nodes.size() == 1) {
            stages = new ArrayList<>();
            nodes.addLast(newNode);
            stages.add(new stage(canvas, nodes, null));
            // SinglyNode newNode = new SinglyNode(value, 50 + nodeNumber * 50,
            // canvas.getHeight() * 0.75);
            return stages;
        } 
        else // there will be two stages: adding the new node and updating the pointion of  
        {
            stages = new ArrayList<>();
            stages.add(new stage(canvas, nodes, map));
            nodes.addLast(newNode);

            for (int i = 0; i < nodes.size() - 2; i++) {
                // SinglyNode node1 = nodes.get(i);
                // SinglyNode node2 = nodes.get(i + 1);
                map.put(nodes.get(i).getNextPointOut(), nodes.get(i + 1).getNextPointOut());
            }
            stages.add(new stage(canvas, nodes, map));
            map.put(nodes.get(0).getNextPointOut(), nodes.get(1).getNextPointOut());
            stages.add(new stage(canvas, nodes, map));
            return stages;
        }
    }

    // Pop from the front
    public ArrayList<stage> popFront() {
        if (nodes.size() == 0) {
            alert("Error", "There are no nodes to delete");
            return null;
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
        if (nodes.size() < 0) {
            alert("Error", "There are no nodes to delete");
            return null;
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

    public ArrayList<stage> insertAt(int index, int value) {
        SinglyNode newNode = new SinglyNode(value);
        if (index == 0) {
            newNode.setNext(head);
            head = newNode;
            return;
        }
        SinglyNode temp = head;
        for (int i = 0; i < index - 1; i++) {
            if (temp.getNext() == null) {
                return null;
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

    void alert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}