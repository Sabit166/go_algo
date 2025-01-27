package com.algo.linkedlist;

import javafx.scene.paint.Color;

import javafx.scene.canvas.GraphicsContext;

public class Visualization {
    void buildCircle(GraphicsContext gc, Color color, SinglyNode list) {
        double x = list.getX();
        double y = list.getY();

        gc.strokeOval(x, y, 30, 30);
    }


    void alert(String title, String message) {
        System.out.println(title + ": " + message);
    }

    void removeCircle()
    {
        
    }

}
