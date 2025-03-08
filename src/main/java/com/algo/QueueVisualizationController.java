package com.algo;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.stage.Stage;
import javafx.util.Duration;

public class QueueVisualizationController extends Application {

    @FXML
    private ComboBox<String> dataTypeComboBox;

    @FXML
    private TextField inputField;

    @FXML
    private HBox queueContainer;

    @FXML
    private AnchorPane sidemenu, bpane, mainpane;

    @FXML
    private Button menubutton, viewCode;

    @FXML
    private MenuButton drawitem;

    @FXML
    private MenuItem item1, item2, item3;

    Timeline timeline;
    private static Scene scene;
    private double LastX, LastY;
    private boolean menubuttonclicked = false;
    private boolean candraw = false;

    private final ColorPicker colorpicker = new ColorPicker();
    private Color color = Color.BLACK;
    private int stroke;

    private final Slider slider = new Slider(1, 6, 2);

    Media sound = new Media(getClass().getResource("/com/algo/buttonclick.mp3").toExternalForm());
    MediaPlayer mediaplayer = new MediaPlayer(sound);

    private Queue<Object> queue = new LinkedList<>();

    @FXML
    private void initialize() {
        dataTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            queue.clear();
            updateQueueVisualization();
        });

        inputField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    handleEnqueue();
                    break;
                default:
                    break;
            }
        });

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
            menubuttonclicked = !menubuttonclicked;
            if (menubuttonclicked) {
                sidemenu.setVisible(true);
                bpane.setDisable(true);
            } else {
                sidemenu.setVisible(false);
                bpane.setDisable(false);
            }
        });

        mainpane.setOnMouseClicked(event -> {
            sidemenu.setVisible(false);
            bpane.setDisable(false);
            bpane.setVisible(true);
        });

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

        // Add CSS classes to the menu items
        item1.getStyleClass().add("menu-item");
        item2.getStyleClass().add("menu-item");
        item3.getStyleClass().add("menu-item");

        // drawitem.getItems().addAll(item1, item2, item3);
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

        colorpicker.setValue(Color.BLACK);
        colorpicker.setOnAction(eh -> color = colorpicker.getValue());

        CustomMenuItem item4 = new CustomMenuItem(colorpicker);
        item4.setHideOnClick(false);

        CustomMenuItem item5 = new CustomMenuItem(slider);
        item5.setHideOnClick(false);

        drawitem.getItems().addAll(item4, item5);

    }

    @FXML
    private void handleEnqueue() {
        mediaplayer.stop();
        mediaplayer.play();
        if (queue.size() >= 15) {
            showAlert("Error", "Queue is full. Maximum 15 elements allowed.");
            return;
        }

        String dataType = dataTypeComboBox.getValue();
        String input = inputField.getText();

        if (input.isEmpty()) {
            showAlert("Error", "Please enter a value.");
            return;
        }

        if (dataType == null) {
            dataType = "Integer";
        }

        Object value;
        try {
            switch (dataType) {
                case "Integer":
                    value = Integer.parseInt(input);
                    break;
                case "Character":
                    if (input.length() != 1) {
                        throw new IllegalArgumentException();
                    }
                    value = input.charAt(0);
                    break;
                case "String":
                    value = input;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            showAlert("Error", "Invalid input for the selected data type.");
            return;
        }

        queue.add(value);
        updateQueueVisualization();
        inputField.clear();
    }

    @FXML
    private void handleDequeue() {
        mediaplayer.stop();
        mediaplayer.play();
        if (queue.isEmpty()) {
            showAlert("Error", "Queue is empty.");
            return;
        }

        queue.poll();
        updateQueueVisualization();
    }

    @FXML
    private void handleFront() {
        mediaplayer.stop();
        mediaplayer.play();
        if (queue.isEmpty()) {
            showAlert("Error", "Queue is empty.");
            return;
        }

        Object frontElement = queue.peek();
        showAlert("Front Element", "Front element is: " + frontElement);
    }

    private void updateQueueVisualization() {
        queueContainer.getChildren().clear();
        for (Object element : queue) {
            Label label = new Label(element.toString());
            label.setStyle("-fx-border-color: black; -fx-padding: 5;");
            queueContainer.getChildren().add(label);
        }
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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        mediaplayer.play();
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/data_structures.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.centerOnScreen();
        stage.setTitle("Data Structures");
        stage.show();
    }

    @FXML
    private void handleSegmentTree(ActionEvent event) throws IOException {

        // Logic for Linked List
        mediaplayer.stop();
        mediaplayer.play();
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/segment_tree_visualizer.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
        stage.setFullScreen(true);
    }

    @FXML
    private void handleStack(ActionEvent event) throws IOException {
        // Load the Stack Visualization screen
        mediaplayer.stop();
        mediaplayer.play(); 
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/stack_visualization.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle("Stack Visualization");
        stage.show();
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("queue_visualization"));
        scene.getStylesheets()
                .add(getClass().getResource("/com/algo/images and stylesheets/stylestackqueue.css").toExternalForm());
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle("Queue Visualization");
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
