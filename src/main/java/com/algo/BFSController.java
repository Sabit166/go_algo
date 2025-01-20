package com.algo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.*;

public class BFSController {

    private static final int NODE_RADIUS = 20;
    private static final int CIRCLE_RADIUS = 150; // Radius for circular layout
    private Map<Integer, StackPane> nodes;
    private List<Line> edges;
    private Map<Integer, List<Integer>> adjacencyList;

    @FXML
    private Pane graphContainer;

    @FXML
    private TextField numNodesField;

    @FXML
    private TextField numEdgesField;

    @FXML
    private TextField edgesField;

    @FXML
    private TextField startNodeField;

    @FXML
    private Label resultLabel;

    @FXML
    private void handleBFS() {
        if (initializeGraph()) { // Initialize the graph visualization
            bfs();             // Perform BFS with animation
        }
    }

    private boolean initializeGraph() {
        graphContainer.getChildren().clear();
        resultLabel.setText(""); // Clear the result label
        String numNodesText = numNodesField.getText();
        String numEdgesText = numEdgesField.getText();
        String edgesText = edgesField.getText();
        if (numNodesText.isEmpty() || numEdgesText.isEmpty() || edgesText.isEmpty()) {
            showAlert("Input Error", "Please provide the number of nodes, number of edges, and edges.");
            return false;
        }
        try {
            int numNodes = Integer.parseInt(numNodesText.trim());
            int numEdges = Integer.parseInt(numEdgesText.trim());
            nodes = new HashMap<>();
            edges = new ArrayList<>();
            adjacencyList = new HashMap<>();

            // Create nodes in a circular layout
            for (int i = 0; i < numNodes; i++) {
                Circle circle = new Circle(NODE_RADIUS, Color.SKYBLUE);
                Label label = new Label(String.valueOf(i));
                label.setTextFill(Color.BLACK);
                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(circle, label);

                // Circular layout
                double angle = 2 * Math.PI * i / numNodes;
                double centerX = graphContainer.getWidth() / 2;
                double centerY = graphContainer.getHeight() / 2;
                double x = centerX + CIRCLE_RADIUS * Math.cos(angle);
                double y = centerY + CIRCLE_RADIUS * Math.sin(angle);
                stackPane.setLayoutX(x);
                stackPane.setLayoutY(y);

                nodes.put(i, stackPane);
                graphContainer.getChildren().add(stackPane);
                adjacencyList.put(i, new ArrayList<>());
            }

            // Parse edges
            String[] edgesArray = edgesText.split(",");
            if (edgesArray.length != numEdges) {
                showAlert("Input Error", "The number of edges does not match the provided list.");
                return false;
            }
            for (String edge : edgesArray) {
                String[] nodes = edge.split("-");
                int node1 = Integer.parseInt(nodes[0].trim());
                int node2 = Integer.parseInt(nodes[1].trim());
                Line line = new Line();
                line.startXProperty().bind(this.nodes.get(node1).layoutXProperty().add(NODE_RADIUS));
                line.startYProperty().bind(this.nodes.get(node1).layoutYProperty().add(NODE_RADIUS));
                line.endXProperty().bind(this.nodes.get(node2).layoutXProperty().add(NODE_RADIUS));
                line.endYProperty().bind(this.nodes.get(node2).layoutYProperty().add(NODE_RADIUS));
                edges.add(line);
                graphContainer.getChildren().add(line);

                adjacencyList.get(node1).add(node2);
                adjacencyList.get(node2).add(node1);
            }

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Ensure all inputs are valid numbers.");
            return false;
        }

        return true;
    }

    private void bfs() {
        String startNodeText = startNodeField.getText().trim();
        int startNode;
        try {
            startNode = Integer.parseInt(startNodeText);
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Ensure the start node is a valid number.");
            return;
        }

        List<KeyFrame> keyFrames = new ArrayList<>();
        Duration duration = Duration.ZERO;
        Duration stepDuration = Duration.seconds(2); // Slow down the simulation

        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.add(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            int finalCurrentNode = currentNode;
            duration = duration.add(stepDuration);
            keyFrames.add(new KeyFrame(duration, e -> highlightNode(finalCurrentNode, Color.GREEN)));

            for (int neighbor : adjacencyList.get(currentNode)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);

                    // Highlight edge
                    Line edge = findEdge(currentNode, neighbor);
                    int finalNeighbor = neighbor;
                    duration = duration.add(stepDuration);
                    keyFrames.add(new KeyFrame(duration, e -> edge.setStroke(Color.ORANGE)));

                    duration = duration.add(stepDuration);
                    keyFrames.add(new KeyFrame(duration, e -> highlightNode(finalNeighbor, Color.YELLOW)));
                }
            }
        }

        // Play the timeline animation
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(keyFrames);
        timeline.setOnFinished(e -> resultLabel.setText("BFS Completed"));
        timeline.play();
    }

    private Line findEdge(int node1, int node2) {
        for (Line edge : edges) {
            if ((edge.getStartX() == nodes.get(node1).getLayoutX() + NODE_RADIUS &&
                 edge.getStartY() == nodes.get(node1).getLayoutY() + NODE_RADIUS &&
                 edge.getEndX() == nodes.get(node2).getLayoutX() + NODE_RADIUS &&
                 edge.getEndY() == nodes.get(node2).getLayoutY() + NODE_RADIUS) ||
                (edge.getStartX() == nodes.get(node2).getLayoutX() + NODE_RADIUS &&
                 edge.getStartY() == nodes.get(node2).getLayoutY() + NODE_RADIUS &&
                 edge.getEndX() == nodes.get(node1).getLayoutX() + NODE_RADIUS &&
                 edge.getEndY() == nodes.get(node1).getLayoutY() + NODE_RADIUS)) {
                return edge;
            }
        }
        return null;
    }

    private void highlightNode(int node, Color color) {
        ((Circle) nodes.get(node).getChildren().get(0)).setFill(color);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/visualization_setup.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Visualization Setup");
        stage.show();
    }
}
