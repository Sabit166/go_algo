package com.algo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class SegmentTreeVisualizationController extends Application {
    @FXML
    private TextField BuildInput;
    @FXML
    private TextField QuerryInput;
    @FXML
    private TextField UpdateInput;
    @FXML

    private static Scene scene;
    int N = 100000;
    int[] numbers = new int[N];
    int[] segment_tree = new int[4*N];

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("segment_tree_visualizer"), 640, 480);
        stage.setScene(scene);
        stage.setTitle("SEGMENT TREE VISUALIZER");
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/algo/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }


    void HandleBuild() {
        String input = BuildInput.getText();
        if (input.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter some numbers.");
            alert.showAndWait();
            return;
        }
        if (!input.matches("[0-9 ]+")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter only digits separated by spaces.");
            alert.showAndWait();
            return;
        }

        String[] inputArray = input.split("\\s+");
        //int[] numbers = new int[inputArray.length];
        for (int i = 0; i < inputArray.length; i++) {
            numbers[i] = Integer.parseInt(inputArray[i]);
        }

        for(int i=0;i<numbers.length;i++){
            System.out.println(numbers[i]);
        }

        segment_tree = new int[4*numbers.length];
        build_segment_tree_array(1, 0, numbers.length-1);
    }

    void build_segment_tree_array(int node, int start, int end){
        if(start == end){
            segment_tree[node] = numbers[start];
        }
        else{
            int mid = (start + end) / 2;
            build_segment_tree_array(2*node, start, mid);
            build_segment_tree_array(2*node+1, mid+1, end);
            segment_tree[node] = segment_tree[2*node] + segment_tree[2*node+1];
        }
    }

    void visualize_segment_tree()
    {

    }

    public static void main(String[] args) {
        launch(args);
    }
}