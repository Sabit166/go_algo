package com.algo.linkedlist;

import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


public class LinkedListVisualizationController{
    Scanner scanner = new Scanner(System.in);

    @FXML
    Canvas canvas;
    GraphicsContext gc;

    private SinglyLinkedList singlyLinkedList;

    double verticalPosition = canvas.getHeight() / 2;
    double horizontalPosition;
    int nodeNumber = 0;

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
        nodeNumber++;
        if(nodeNumber > 4) {
            Visualization visualization = new Visualization();
            visualization.alert("Error", "Cannot add more than 4 nodes");
        }
        horizontalPosition = 50 + nodeNumber * 50;
        singlyLinkedList.pushFront(nodeNumber, horizontalPosition , verticalPosition);
    }

    @FXML
    void HandlePopFront()
    {
        nodeNumber--;
        if(nodeNumber < 0) {
            Visualization visualization = new Visualization();
            visualization.alert("Error", "Cannot remove more than 4 nodes");
        }
        horizontalPosition = 50 + nodeNumber * 50;
        singlyLinkedList.popFront();
    }


    @FXML
    void HandlePushBack()
    {
        nodeNumber++;
        if(nodeNumber > 4) {
            Visualization visualization = new Visualization();
            visualization.alert("Error", "Cannot add more than 4 nodes");
        }

        horizontalPosition = 50 + nodeNumber * 50;
        singlyLinkedList.pushBack(nodeNumber, horizontalPosition , verticalPosition);
    }

    @FXML
    void HandlePopBack()
    {
        nodeNumber--;
        if(nodeNumber < 0) {
            Visualization visualization = new Visualization();
            visualization.alert("Error", "Cannot remove more than 4 nodes");
        }
    }
    
}
