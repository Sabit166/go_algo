package com.algo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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

public class GraphVisualizerController {

    private static final int NODE_RADIUS = 50;
    private static final int GAP_SIZE = 50;
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
    private HBox queueContainer;

    @FXML
    private void handleBFS() {
        if (initializeGraph()) { // Initialize the graph visualization
            bfs();             // Perform BFS with animation
        }
    }

    private boolean initializeGraph() {
        graphContainer.getChildren().clear();
        queueContainer.getChildren().clear();
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
            initializeNodes(numNodes, graphContainer.getWidth(), graphContainer.getHeight());
            String[] edgesArray = edgesText.split(",");
            if (edgesArray.length != numEdges) {
                showAlert("Input Error", "The number of edges does not match the provided list.");
                return false;
            }
            for (String edge : edgesArray) {
                String[] nodes = edge.split("-");
                int node1 = Integer.parseInt(nodes[0].trim());
                int node2 = Integer.parseInt(nodes[1].trim());
                Line line = createEdge(node1, node2);
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

    private void initializeNodes(int numNodes, double width, double height) {
        double centerX = width / 2 +100;
        double centerY = height / 2 +50;
        double angleStep = 360.0 / numNodes;

        for (int i = 0; i < numNodes; i++) {
            double angle = Math.toRadians(i * angleStep);
            double x = centerX + 200 * Math.cos(angle);
            double y = centerY + 100 * Math.sin(angle);

            Circle circle = new Circle(NODE_RADIUS, Color.SKYBLUE);
            Label label = new Label(String.valueOf(i));
            label.setTextFill(Color.BLACK);
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(circle, label);
            stackPane.setLayoutX(x - NODE_RADIUS);
            stackPane.setLayoutY(y - NODE_RADIUS);
            nodes.put(i, stackPane);
            graphContainer.getChildren().add(stackPane);
            adjacencyList.put(i, new ArrayList<>());
        }
    }

    private Line createEdge(int node1, int node2) {
        StackPane node1Pane = nodes.get(node1);
        StackPane node2Pane = nodes.get(node2);

        double startX = node1Pane.getLayoutX() + NODE_RADIUS;
        double startY = node1Pane.getLayoutY() + NODE_RADIUS;
        double endX = node2Pane.getLayoutX() + NODE_RADIUS;
        double endY = node2Pane.getLayoutY() + NODE_RADIUS;

        double dx = endX - startX;
        double dy = endY - startY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double offsetX = dx * NODE_RADIUS / distance;
        double offsetY = dy * NODE_RADIUS / distance;

        Line line = new Line();
        line.setStartX(startX + offsetX);
        line.setStartY(startY + offsetY);
        line.setEndX(endX - offsetX);
        line.setEndY(endY - offsetY);
        line.setStroke(Color.WHITE); // Set the line color to white

        return line;
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
            keyFrames.add(new KeyFrame(duration, e -> {
                highlightNode(finalCurrentNode, Color.GREEN);
                updateQueueVisualization(queue, visited);
            }));

            for (int neighbor : adjacencyList.get(currentNode)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    int finalNeighbor = neighbor;
                    duration = duration.add(stepDuration);
                    keyFrames.add(new KeyFrame(duration, e -> {
                        highlightNode(finalNeighbor, Color.DARKSLATEGRAY);
                        updateQueueVisualization(queue, visited);
                    }));
                }
            }
        }

        // Play the timeline animation
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(keyFrames);
        timeline.setOnFinished(e -> resultLabel.setText("BFS Completed"));
        timeline.play();
    }

    private void highlightNode(int node, Color color) {
        ((Circle) nodes.get(node).getChildren().get(0)).setFill(color);
    }

    private void updateQueueVisualization(Queue<Integer> queue, Set<Integer> visited) {
        queueContainer.getChildren().clear();
        boolean isFirst = true;
        for (int node : queue) {
            Label label = new Label(String.valueOf(node));
            if (isFirst) {
                label.setStyle("-fx-background-color: darkslategray; -fx-padding: 5px;");
                isFirst = false;
            } else {
                label.setStyle("-fx-background-color: lightgray; -fx-padding: 5px;");
            }
            queueContainer.getChildren().add(label);
        }
        for (int node : visited) {
            Label label = new Label(String.valueOf(node));
            label.setStyle("-fx-background-color: green; -fx-padding: 5px;");
            queueContainer.getChildren().add(label);
        }
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