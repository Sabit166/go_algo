package com.algo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class comingsooncontroll {

    @FXML
    private AnchorPane mainpane;

    @FXML
    private Button cross;

    public void initialize() {
        cross.setOnAction(e -> {
            mainpane.getScene().getWindow().hide();
        });
    }

}
