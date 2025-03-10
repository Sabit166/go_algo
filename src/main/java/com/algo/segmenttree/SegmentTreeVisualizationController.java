package com.algo.segmenttree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SegmentTreeVisualizationController extends Application {

    @FXML
    protected Canvas canvas;
    protected GraphicsContext gc;

    @FXML
    private TextField BuildInput, QuerryInput, UpdateInput;

    @FXML
    private AnchorPane sidemenu, bpane, mainpane, codePane;

    private Timeline timeline;
    @FXML
    private Button menubutton, codeHide;

    @FXML
    private MenuButton drawitem, writeCode;

    @FXML
    TextArea pseudoCodeArea;

    private double LastX, LastY;
    private boolean candraw = false;

    private final ColorPicker colorpicker = new ColorPicker();
    private Color color = Color.BLACK;
    private int stroke;

    private final Slider slider = new Slider(1, 6, 2);

    Media sound = new Media(getClass().getResource("/com/algo/buttonclick.mp3").toExternalForm());
    MediaPlayer mediaplayer = new MediaPlayer(sound);

    SegmentTreeVisualizationHelper helper;
    SegmentTreeWriteandDraw draw;
    private int maxSize = 16;
    protected int[] numbers;
    protected SegmentTreeNodes[] segment_tree = new SegmentTreeNodes[4 * maxSize]; // Initialize the segment tree

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadFXML("segment_tree_visualizer");
        Scene scene = new Scene(root);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.setTitle("SEGMENT TREE VISUALIZER");
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                SegmentTreeVisualizationController.class.getResource("/com/algo/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @FXML
    public void initialize() {
        sidemenu.setVisible(false);
        gc = canvas.getGraphicsContext2D();
        // Initialize the segment_tree array with Segment_Tree_Nodes instances
        for (int i = 0; i < segment_tree.length; i++) {
            segment_tree[i] = new SegmentTreeNodes();
        }

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

        //initilizing the slider 
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(1);
        slider.setSnapToTicks(true);
        stroke = (int) slider.getValue();
        slider.valueProperty().addListener((obs, oldval, newVal) -> {
            stroke = (int) newVal.intValue();
        });

        MenuItem item1 = new MenuItem("Draw");
        MenuItem item2 = new MenuItem("Erase");
        MenuItem item3 = new MenuItem("Off");
        MenuItem build = new MenuItem("Build");
        MenuItem query = new MenuItem("Query");
        MenuItem update = new MenuItem("Update");
        colorpicker.setValue(Color.BLACK);
        colorpicker.setOnAction(eh -> color = colorpicker.getValue());

        CustomMenuItem item4 = new CustomMenuItem(colorpicker);
        item4.setHideOnClick(false);

        CustomMenuItem item5 = new CustomMenuItem(slider);
        item5.setHideOnClick(false);

        item1.setOnAction(eh -> {
            candraw = true;
            Image penImage = new Image(getClass().getResourceAsStream("/com/algo/images and stylesheets/pencil.png"));
            ImageCursor penCursor = new ImageCursor(penImage, penImage.getWidth() / 2, penImage.getHeight() / 2);
            mainpane.setCursor(penCursor);
        });
        item2.setOnAction(event -> mainpane.getChildren().removeIf(node -> node instanceof Line));
        item3.setOnAction(eh -> {
            candraw = false;
            mainpane.setCursor(Cursor.DEFAULT);
        });

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

        // pseudoCodeArea.setText(
        //     "Pseudocode for Binary Search:\n" +
        //         "1. Set low = 0 and high = length of array - 1\n" +
        //         "2. While low ≤ high:\n" +
        //         "   a. Find mid = (low + high) / 2\n" +
        //         "   b. If arr[mid] == target, return mid (found)\n" +
        //         "   c. If arr[mid] < target, set low = mid + 1 (search right half)\n" +
        //         "   d. Else, set high = mid - 1 (search left half)\n" +
        //         "3. If not found, return -1");
        build.setOnAction(eh -> {
            pseudoCodeArea.setVisible(true);
            codeHide.setVisible(true);
            try {
                String content = new String(
                        Files.readAllBytes(Paths.get("src/main/java/com/algo/segmenttree/BuildOperation.txt")));
                pseudoCodeArea.setText(content);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        query.setOnAction(eh -> {
            pseudoCodeArea.setVisible(true);
            codeHide.setVisible(true);
            try {
                String content = new String(
                        Files.readAllBytes(Paths.get("src/main/java/com/algo/segmenttree/QueryOperation.txt")));
                pseudoCodeArea.setText(content);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        update.setOnAction(eh -> {
            pseudoCodeArea.setVisible(true);
            codeHide.setVisible(true);
            try {
                String content = new String(
                        Files.readAllBytes(Paths.get("src/main/java/com/algo/segmenttree/UpdateOperation.txt")));
                pseudoCodeArea.setText(content);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        codeHide.setOnAction(eh -> {
            pseudoCodeArea.setVisible(false);
            codeHide.setVisible(false);
        });

        drawitem.getItems().addAll(item1, item2, item3, item4, item5);
        writeCode.getItems().addAll(build, query, update);

    }

    private void draw(AnchorPane pane, MouseEvent event) {
        Line line = new Line(LastX, LastY, event.getSceneX(), event.getSceneY());
        line.setStroke(color);
        line.setStrokeWidth(stroke);
        line.setStrokeLineJoin(StrokeLineJoin.ROUND);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        pane.getChildren().add(line);

        LastX = event.getSceneX();
        LastY = event.getSceneY();
    }

    void reset() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < segment_tree.length; i++) {
            segment_tree[i] = new SegmentTreeNodes();
        }
    }

    @FXML
    private void HandleBuild() {
        mediaplayer.stop();
        mediaplayer.play();
        reset();

        String input = BuildInput.getText();
        if (input.isEmpty()) {
            helper.alert("Please enter some digits.");
            return;
        }
        if (!input.matches("[0-9 ]+")) {
            helper.alert("Please enter only digits separated by spaces.");
            return;
        }

        String[] inputArray = input.split("\\s+");

        if (inputArray.length > maxSize) {
            helper.alert("Please enter at most " + maxSize + " digits.");
            return;
        }
        numbers = new int[inputArray.length];

        numbers = new int[inputArray.length];
        for (int i = 0; i < inputArray.length; i++) {
            numbers[i] = Integer.parseInt(inputArray[i]);
        }

        draw = new SegmentTreeWriteandDraw(canvas);
        helper = new SegmentTreeVisualizationHelper(canvas, numbers, draw);

        double canvas_width = canvas.getWidth();
        helper.build_segment_tree(1, 0, numbers.length - 1, 0, canvas_width);
        helper.build_lines(1, 0, numbers.length - 1);
        helper.build_circle(1, 0, numbers.length - 1);
        helper.prompt("Segment Tree is building.", "Time Complexity: O(n)");
    }

    @FXML
    private void HandleQuerry() {
        mediaplayer.stop();
        mediaplayer.play();
        String input = QuerryInput.getText();
        if (!input.matches("[0-9 ]+")) { // Match two numbers separated by one or more spaces, allowing leading/trailing
            helper.alert("Please enter only digits.");
            return;
        }

        String[] inputArray = input.split("\\s+");
        for (int i = 0; i < inputArray.length; i++) {
            System.out.println(inputArray[i]);
        }
        if (inputArray.length != 2) {
            helper.alert("Please enter only two digits.");
            return;
        }
        int l = Integer.parseInt(inputArray[0]);
        int r = Integer.parseInt(inputArray[1]);

        if (l < 0 || l >= numbers.length || r < 0 || r >= numbers.length) {
            helper.alert("Please enter valid indices.");
            return;
        }

        helper.instant_build_circle(1, 0, numbers.length - 1);
        int result = helper.query_segment_tree(1, 0, numbers.length - 1, l, r);
        helper.prompt("The sum of the range [" + l + ", " + r + "] is: " + result, "Time Complexity: O(log n)");
    }

    @FXML
    private void HandleUpdate() {
        mediaplayer.stop();
        mediaplayer.play();
        String input = UpdateInput.getText(); // Trim leading/trailing spaces
        if (!input.matches("[0-9 ]+")) { // Match two numbers separated by one or more spaces
            helper.alert("Please enter an index and a value separated by a space.");
            return;
        }

        String[] inputArray = input.split("\\s+"); // Split on one or more spaces
        if (inputArray.length != 2) {
            helper.alert("Please enter only two digits.");
            return;
        }
        int index = Integer.parseInt(inputArray[0]);
        int value = Integer.parseInt(inputArray[1]);

        if (index < 0 || index >= numbers.length) {
            helper.alert("Please enter a valid index.");
            return;
        }

        helper.instant_build_circle(1, 0, numbers.length - 1);
        helper.update_segment_tree(1, 0, numbers.length - 1, index, value);
        helper.prompt("The value at index [" + index + "] has been updated to: " + value, "Time Complexity: O(log n)");
    }

    @FXML
    private void handleLinkedList(ActionEvent event) throws IOException {
        // Logic for Linked List
        mediaplayer.play();
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/comingsoon.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setFullScreen(true);
    }

    @FXML
    private void handleStack(ActionEvent event) throws IOException {
        // Load the Stack Visualization screen
        mediaplayer.play();
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/stack_visualization.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle("Stack Visualization");
        stage.show();
    }

    @FXML
    private void handleQueue(ActionEvent event) throws IOException {
        // Load the Queue Visualization screen
        mediaplayer.play();
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/queue_visualization.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle("Queue Visualization");
        stage.show();
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        // Load the visualization setup screen
        mediaplayer.play();
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/data_structures.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.setTitle("Data Structures");
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
