package com.algo.linkedlist;

import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Pair;
import java.util.Map;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;


public class LinkedListVisualizationController{
    Scanner scanner = new Scanner(System.in);

    @FXML
    Canvas canvas;
    GraphicsContext gc;

    private SinglyLinkedList singlyLinkedList;
    private ArrayList<stage> stages;// = new ArrayList<>();


    void initialize() {
        gc = canvas.getGraphicsContext2D();
        singlyLinkedList = new SinglyLinkedList();
    }

    @FXML
    void toSingle()
    {
        singlyLinkedList = new SinglyLinkedList();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    void toDouble()
    {
        singlyLinkedList = new DoublyLinkedList();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    void HandlePushFront()
    {
        // nodeNumber++;
        // if(nodeNumber > 4) {
        //     Visualization visualization = new Visualization();
        //     visualization.alert("Error", "Cannot add more than 4 nodes");
        // }
        // horizontalPosition = 50 + nodeNumber * 50;
        //singlyLinkedList.pushFront(nodeNumber, horizontalPosition , verticalPosition);
        stages = singlyLinkedList.pushFront(50);
        display(stages);
    }

    @FXML
    void HandlePopFront()
    {
        // nodeNumber--;
        // if(nodeNumber < 0) {
        //     Visualization visualization = new Visualization();
        //     visualization.alert("Error", "Cannot remove more than 4 nodes");
        // }
        // horizontalPosition = 50 + nodeNumber * 50;
        stages = singlyLinkedList.popFront();
        display(stages);
    }


    @FXML
    void HandlePushBack()
    {
        // nodeNumber++;
        // if(nodeNumber > 4) {
        //     Visualization visualization = new Visualization();
        //     visualization.alert("Error", "Cannot add more than 4 nodes");
        // }

        // horizontalPosition = 50 + nodeNumber * 50;
        //singlyLinkedList.pushBack(nodeNumber, horizontalPosition , verticalPosition);
        stages = singlyLinkedList.pushBack(50);
        display(stages);
    }

    @FXML
    void HandlePopBack()
    {
        // nodeNumber--;
        // if(nodeNumber < 0) {
        //     Visualization visualization = new Visualization();
        //     visualization.alert("Error", "Cannot remove more than 4 nodes");
        // }
        // horizontalPosition = 50 + nodeNumber * 50;
        stages = singlyLinkedList.popBack();
        display(stages);
    }
    
    void display(ArrayList<stage> stages) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (stage s : stages) {
            
        }
    }
}

class stage
{
    LinkedList<SinglyNode> nodes;
    Map<Pair<Double, Double>, Pair<Double, Double>> map;
    public stage()
    {
        this.nodes = new LinkedList<>();
        this.map = new HashMap<>();
    }
    public stage(LinkedList<SinglyNode> nodes, Map<Pair<Double, Double>, Pair<Double, Double>> map)
    {
        this.nodes = nodes;
        this.map = map;
    }
}
