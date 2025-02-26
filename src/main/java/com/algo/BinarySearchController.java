package com.algo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.event.ActionEvent;

import javafx.application.Application;

import java.io.File;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class BinarySearchController extends Application {

    private static Scene scene;
    private Timeline timeline;
    private static final int BAR_SIZE = 100; // Use square blocks
    private static final int GAP_SIZE = 75; // Gap for arrows
    private MediaPlayer mediaPlayer;
    private StackPane[] bars;
    private double LastX, LastY;
    private boolean candraw = false;

    @FXML
    private HBox barContainer;

    @FXML
    private TextField inputField, searchField;

    @FXML
    private Label foundLabel, startOperation, midOperation, endOperation, iterationOperation;

    @FXML
    private AnchorPane sidemenu, bpane, mainpane;

    @FXML
    private Button menubutton;

    @FXML
    private MenuButton drawitem;

    @FXML
    public void initialize() {
        sidemenu.setVisible(false);
        // gc = canvas.getGraphicsContext2D();
        // Initialize the segment_tree array with Segment_Tree_Nodes instances

        timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            bpane.setOpacity(0.3);
        }));
        timeline.setCycleCount(1); // Run only once

        timeline.play();

        bpane.setOnMouseEntered(event -> {
            bpane.setOpacity(1.0); // Reset opacity to full
            timeline.stop(); // Stop the existing timeline
        });

        bpane.setOnMouseExited(event -> {
            timeline.stop();
            timeline.playFromStart(); // Restart the countdown
        });

        menubutton.setOnAction(event -> {
            sidemenu.setVisible(true);
            bpane.setDisable(true);
            bpane.setVisible(false);
        });

        mainpane.setOnMouseClicked(event -> {
            sidemenu.setVisible(false);
            bpane.setDisable(false);
            bpane.setVisible(true);
        });

        MenuItem item1 = new MenuItem("Draw");
        MenuItem item2 = new MenuItem("Erase");
        MenuItem item3 = new MenuItem("Off");

        item1.setOnAction(eh -> {
            candraw = true;
        });
        item2.setOnAction(event -> mainpane.getChildren().removeIf(node -> node instanceof Line));
        item3.setOnAction(eh -> {
            candraw = false;
        });

        drawitem.getItems().addAll(item1, item2, item3);

        mainpane.setOnMousePressed(event -> {
            if (candraw) {
                LastX = event.getSceneX();
                LastY = event.getSceneY();
            }
        });

        mainpane.setOnMouseDragged(event -> {
            if (candraw) {
                draw(mainpane, event);
            }
        });

    }

    @FXML
    private void handleBinarySearch() {
        initializeBars(); // Initialize the bar visualization
        binarySearch(); // Perform the search with animation
    }

    private void initializeBars() {
        barContainer.getChildren().clear();
        foundLabel.setText(""); // Clear the found label
        String input = inputField.getText();
        try {
            String[] inputArray = input.split(",");
            int numElements = inputArray.length;
            if (inputArray.length != numElements) {
                showAlert("Input Error", "The number of elements does not match the provided list.");
                return;
            }
            int[] intArray = new int[numElements];
            for (int i = 0; i < numElements; i++) {
                intArray[i] = Integer.parseInt(inputArray[i].trim());
            }

            // Check if the array is sorted
            for (int i = 1; i < intArray.length; i++) {
                if (intArray[i] < intArray[i - 1]) {
                    showAlert("Input Error", "The input array must be sorted in ascending order.");
                    return;
                }
            }
            bars = new StackPane[numElements];
            for (int i = 0; i < numElements; i++) {
                Rectangle rectangle = new Rectangle(BAR_SIZE, BAR_SIZE);
                rectangle.getStyleClass().add("rectangle"); // Apply the CSS class to the rectangle
                Label label = new Label(inputArray[i].trim());
                label.getStyleClass().add("label"); // Apply the CSS class to the label
                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(rectangle, label);
                bars[i] = stackPane;
                barContainer.getChildren().add(bars[i]);
            }
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Ensure all inputs are valid numbers.");
        }
    }

    private void draw(AnchorPane pane, MouseEvent event) {
        Line line = new Line(LastX, LastY, event.getSceneX(), event.getSceneY());
        line.setStroke(Color.INDIGO);
        line.setStrokeWidth(5);
        pane.getChildren().add(line);

        LastX = event.getSceneX();
        LastY = event.getSceneY();
    }

    private void binarySearch() {
        List<KeyFrame> keyFrames = new ArrayList<>();
        Duration duration = Duration.ZERO;
        Duration stepDuration = Duration.seconds(2); // Slow down the simulation
        playAudio("letsdobinarysearch"); // Play the binary search audio

        int n = bars.length;
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = Integer.parseInt(((Label) bars[i].getChildren().get(1)).getText());
        }

        int target;
        try {
            target = Integer.parseInt(searchField.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Ensure the search input is a valid number.");
            return;
        }

        int iterations = 0;
        int left = 0;
        int right = n - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // Highlight the current start, end, and middle elements
            int finalLeft = left;
            int finalRight = right;
            int finalMid = mid;
            int finalIterations = iterations;
            duration = duration.add(stepDuration);
            keyFrames.add(new KeyFrame(duration, e -> this.startOperation.setText("START:  " + finalLeft)));
            keyFrames.add(new KeyFrame(duration, e -> this.midOperation.setText("MID:  " + finalMid)));
            keyFrames.add(new KeyFrame(duration, e -> this.endOperation.setText("END:  " + finalRight)));
            keyFrames.add(
                    new KeyFrame(duration, e -> this.iterationOperation.setText("ITERATION:  " + finalIterations)));
            keyFrames.add(new KeyFrame(duration, e -> highlightBars(finalLeft, finalRight, finalMid)));

            if (values[mid] == target) {
                // Target found
                duration = duration.add(stepDuration);
                keyFrames.add(new KeyFrame(duration, e -> playAudio("timecomplexity")));
                keyFrames.add(new KeyFrame(duration, e -> highlightFound(finalMid)));
                break;
            } else if (values[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
            iterations++;
        }

        // Play the timeline animation
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(keyFrames);
        timeline.setOnFinished(e -> resetBarColors());
        timeline.play();
    }

    private void highlightBars(int left, int right, int mid) {
        resetBarColors();
        boolean eq = false;
        if (left == right)
            eq = true;
        addArrow(left, Color.BLUE, "up", eq); // Start element: blue arrow above
        addArrow(right, Color.RED, "up1", eq); // End element: red arrow below
        addArrow(mid, Color.GREEN, "down", eq); // Middle element: green arrow on the block
    }

    private void addArrow(int index, Color color, String position, boolean isOverlap) {
        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(new Double[] {
                0.0, 0.0,
                30.0, 60.0,
                -30.0, 60.0 });
        arrow.setFill(color);

        if (position.equals("up")) {
            arrow.setRotate(180);
            bars[index].getChildren().add(arrow);
            arrow.setTranslateY(-GAP_SIZE); // Gap above the bar
            if (isOverlap) {
                arrow.setTranslateX(-20); // Shift left to avoid overlap
            }
        } else if (position.equals("up1")) {
            arrow.setRotate(180);
            bars[index].getChildren().add(arrow);
            arrow.setTranslateY(-GAP_SIZE); // Gap above the bar
            if (isOverlap) {
                arrow.setTranslateX(0); // Shift left to avoid overlap
            }
        } else if (position.equals("down")) {
            bars[index].getChildren().add(arrow);
            arrow.setTranslateY(GAP_SIZE); // Gap below the bar
            if (isOverlap) {
                arrow.setTranslateX(10); // Shift right to avoid overlap
            }
        } else if (position.equals("mid")) {
            bars[index].getChildren().add(arrow);
            arrow.setTranslateY(0); // Centered directly on the bar
        }
    }

    private void highlightFound(int index) {
        resetBarColors();
        Label foundLabel = new Label("Found");
        foundLabel.setTextFill(Color.GREEN);
        bars[index].getChildren().add(foundLabel); // Highlight the found element with "Found"
        this.foundLabel.setText("It has been found at index = " + index); // Update the found label
    }

    private void resetBarColors() {
        for (StackPane bar : bars) {
            ((Rectangle) bar.getChildren().get(0)).setFill(Color.web("#1E90FF"));
            bar.getChildren().removeIf(node -> node instanceof Polygon
                    || (node instanceof Label && "Found".equals(((Label) node).getText())));
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void playAudio(String fileName) {
        try {
            String path = new File("src/main/resources/com/algo/" + fileName + ".mp3").toURI().toString();
            Media media = new Media(path);
            mediaPlayer = new MediaPlayer(media); // Store it in the class variable

            // Ensure the audio plays fully
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.dispose());

            mediaPlayer.play();
        } catch (Exception e) {
            showAlert("Audio Error", "Failed to play audio: " + e.getMessage());
        }
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

    @FXML
    private void handleBubbleSort(ActionEvent event) throws IOException {
        // Logic for Linked List
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/bubble_sort.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setFullScreen(true);
    }

    @FXML
    private void handleSelectionSort(ActionEvent event) throws IOException {
        // Load the Stack Visualization screen
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/selection_sort.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Stack Visualization");
        stage.show();
    }

    @FXML
    private void handleBFS(ActionEvent event) throws IOException {
        // Load the Queue Visualization screen
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/bfs.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Queue Visualization");
        stage.show();
    }


    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("binary_search"));
        scene.getStylesheets()
                .add(getClass().getResource("/com/algo/images and stylesheets/stylebinarysearch.css").toExternalForm());
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle("BINARY SEARCH");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/algo/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}