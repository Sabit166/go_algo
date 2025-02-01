package com.algo.linkedlist;

import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;


public class LinkedListVisualizationController{
    Scanner scanner = new Scanner(System.in);

    @FXML
    Canvas canvas;
    GraphicsContext gc;

    private SinglyLinkedList singlyLinkedList;
    private ArrayList<stage> stages;


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
        stages = singlyLinkedList.pushFront(50);
        display(stages);
    }

    @FXML
    void HandlePopFront()
    {
        stages = singlyLinkedList.popFront();
        display(stages);
    }


    @FXML
    void HandlePushBack()
    {
        stages = singlyLinkedList.pushBack(50);
        display(stages);
    }

    @FXML
    void HandlePopBack()
    {
        stages = singlyLinkedList.popBack();
        display(stages);
    }
    
    void display(ArrayList<stage> stages) 
    { 
        for (stage s : stages) {
            s.draw(s);
        }
    }
}

