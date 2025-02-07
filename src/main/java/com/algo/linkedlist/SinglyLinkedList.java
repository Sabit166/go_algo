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
            // System.out.println("Nodes after adding first node: " + nodes);
            stages.add(new stage(canvas, nodes, null));
            return stages;
        }
        stages = new ArrayList<>();
        nodes.addFirst(newNode);
        // System.out.println("Nodes after adding new node: " + nodes);
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
        nodes.get(1).setNormalGradient();
        stages.add(new stage(canvas, nodes, map));
        return stages;

    }

    // Push to the back

    public ArrayList<stage> pushBack(int value) {
        if (nodes.size() == 4) {
            alert("Error", "Cannot add more than 4 nodes");
            return null;
        }
        SinglyNode newNode = new SinglyNode(canvas, value);
        if (nodes.size() == 1) {
            return pushFront(value);
        }

        stages = new ArrayList<>();
        stages.add(new stage(canvas, nodes, map));
        nodes.get(0).makeTemp();

        for (int i = 1; i < nodes.size(); i++) {
            if(i == 1) nodes.get(i - 1).makeHead();
            else nodes.get(i - 1).setNormalGradient();
            nodes.get(i).makeTemp();
            stages.add(new stage(canvas, nodes, map));
            newNode.shiftRight();
        }

        nodes.addLast(newNode);
        stages.add(new stage(canvas, nodes, map));

        for (int i = 0; i < nodes.size() - 2; i++) {
            map.put(nodes.get(i).getNextPointOut(), nodes.get(i + 1).getNextPointOut());
        }
        stages.add(new stage(canvas, nodes, map));
        map.put(nodes.get(nodes.size() - 2).getNextPointOut(), nodes.get(nodes.size() - 1).getNextPointOut());
        stages.add(new stage(canvas, nodes, map));
        return stages;

    }

    // Pop from the front
    public ArrayList<stage> popFront() {
        if (nodes.size() == 0) {
            alert("Error", "There are no nodes to delete");
            return null;
        }
        if (nodes.size() == 1) {
            stages = new ArrayList<>();
            nodes.removeFirst();
            stages.add(new stage(canvas, nodes, null));
            return stages;
        }
        stages = new ArrayList<>();
        stages.add(new stage(canvas, nodes, map));
        nodes.get(0).setNormalGradient();
        nodes.get(1).makeHead();
        stages.add(new stage(canvas, nodes, map));
        nodes.removeFirst();
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).shiftLeft();
        }
        setPointers();
        stages.add(new stage(canvas, nodes, map));
        return stages;
    }

    // Pop from the back
    public ArrayList<stage> popBack() {
        if (nodes.size() == 0) {
            alert("Error", "There are no nodes to delete");
            return null;
        }
        if (nodes.size() == 1) {
            return popFront();
        }
        stages = new ArrayList<>();
        stages.add(new stage(canvas, nodes, map));
        nodes.get(0).makeTemp();

        for (int i = 1; i < nodes.size(); i++) {
            if(i==1) nodes.get(i).makeHead();
            else nodes.get(i - 1).setNormalGradient();
            nodes.get(i).makeTemp();
            stages.add(new stage(canvas, nodes, map));
        }
        nodes.removeLast();
        setPointers();
        stages.add(new stage(canvas, nodes, map));
        return stages;
    }

    public ArrayList<stage> insertAt(int index, int value) {
        if (nodes.size() == 4) {
            alert("Error", "Cannot add more than 4 nodes");
            return null;
        }
        if (nodes.size() == 0) {
            return pushFront(value);
        }
        SinglyNode newNode = new SinglyNode(canvas, value);
        nodes.get(0).makeHead();
        int i;
        for(i=1;i<nodes.size();i++){
            nodes.get(i).setNormalGradient();
        }
        return stages;
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

    void setPointers() {
        map.clear();
        for (int i = 0; i < nodes.size() - 1; i++) {
            map.put(nodes.get(i).getNextPointOut(), nodes.get(i + 1).getNextPointIn());
        }
    }

    void alert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}