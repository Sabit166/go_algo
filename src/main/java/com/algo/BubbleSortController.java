package com.algo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BubbleSortController extends Application {

    private boolean candraw = false;
    private double LastX, LastY;

    private boolean menubuttonclicked = false;
    private boolean codevisible = false;

    private static Scene scene;
    private static final int BAR_WIDTH = 100; // Increased width
    private static final int MAX_HEIGHT = 400; // Increased height
    private StackPane[] bars;
    private Color color;
    private final CustomSlider slider = new CustomSlider();
    private int stroke;
    private ColorPicker colorpicker = new ColorPicker();

    Media sound = new Media(getClass().getResource("/com/algo/buttonclick.mp3").toExternalForm());
    MediaPlayer mediaplayer = new MediaPlayer(sound);

    @FXML
    private HBox barContainer;

    @FXML
    private TextField inputField, searchField, numElementsField, elementsField;

    @FXML
    private Label mainLabel;

    @FXML
    private Label iterator1; // Ensure this is a Label, not an int

    @FXML
    private Label iterator2;

    @FXML
    private Label swap;

    @FXML
    private AnchorPane sidemenu, bpane, mainpane, codePane;

    @FXML
    private Button menubutton, viewCode;

    @FXML
    private MenuButton drawitem;

    @FXML
    private MenuItem item1, item2, item3;

    @FXML
    TextArea pseudoCodeArea;

    private Timeline timeline;

    @FXML
    public void initialize() {
        sidemenu.setVisible(false);

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

        viewCode.setOnAction(event -> {
            codevisible = !codevisible;
            mediaplayer.stop();
            mediaplayer.play();
            if (codevisible) {
                codePane.setVisible(true);
            } else {
                codePane.setVisible(false);
            }
        });

        pseudoCodeArea.setText(
                "Pseudocode for Bubble Sort:\n"
                + "1. Iterate over the array from the first element to the second last element.\n"
                + "2. For each element, compare it with the next element.\n"
                + "3. If the current element is greater than the next element, swap them.\n"
                        + "4. Repeat until the array is sorted.");

        stroke = (int) slider.getValue();
        slider.valueProperty().addListener((obs, oldval, newVal) -> {
            stroke = (int) newVal.intValue();
        });

        colorpicker.setValue(Color.BLACK);
        colorpicker.setOnAction(eh -> color = colorpicker.getValue());
        item1.setOnAction(eh -> {
            mediaplayer.stop();
            mediaplayer.play();
            candraw = true;
            Image penImage = new Image(getClass().getResourceAsStream("/com/algo/images and stylesheets/pencil.png"));
            ImageCursor penCursor = new ImageCursor(penImage, penImage.getWidth() / 2, penImage.getHeight() / 2);
            mainpane.setCursor(penCursor);
        });
        item2.setOnAction(event -> mainpane.getChildren().removeIf(node -> node instanceof Line));
        item3.setOnAction(eh -> {
            candraw = false;
            mediaplayer.stop();
            mediaplayer.play();
            mainpane.setCursor(Cursor.DEFAULT);
        });

        mainLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/com/algo/supercell-magic.ttf"), 60));
        item1.getStyleClass().add("menu-item");
        item2.getStyleClass().add("menu-item");
        item3.getStyleClass().add("menu-item");

        mainpane.setOnMousePressed(event -> {
            if (candraw) {
                LastX = event.getSceneX();
                LastY = event.getSceneY();
            }
        });

        CustomMenuItem item4 = new CustomMenuItem(colorpicker);
        item4.setHideOnClick(false);

        CustomMenuItem item5 = new CustomMenuItem(slider);
        item5.setHideOnClick(false);

        drawitem.getItems().addAll(item4, item5);

        mainpane.setOnMouseDragged(event -> {
            if (candraw) {
                draw(mainpane, event);
            }
        });
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

    @FXML
    private void handleBubbleSort() {
        mediaplayer.stop();
        mediaplayer.play();
        initializeBars(); // Initialize the bar visualization
        bubbleSort();     // Perform the sorting with animation
    }

    private void initializeBars() {
        barContainer.getChildren().clear();
        String numElementsInput = numElementsField.getText().trim();
        String elementsInput = elementsField.getText().trim();
        System.out.println(numElementsInput);
        System.out.println(elementsInput);
        if (numElementsInput.isEmpty() || elementsInput.isEmpty()) {
            showAlert("Input Error", "Please provide the number of elements and the elements separated by spaces.");
            return;
        }

        try {
            int size = Integer.parseInt(numElementsInput);
            String[] elementsArray = elementsInput.split("\\s+");

            if (elementsArray.length != size) {
                showAlert("Input Error", "The number of elements does not match the specified size.");
                return;
            }

            bars = new StackPane[size];
            for (int i = 0; i < size; i++) {
                double height = 100; // Set a constant height for all bars
                Rectangle rectangle = new Rectangle(BAR_WIDTH, height, Color.DARKVIOLET);
                //rectangle.setStroke(Color.BLACK); // Set the border color
                //rectangle.setStrokeWidth(5); // Set the border width
                rectangle.getStyleClass().add("rectangle");
                Label label = new Label(elementsArray[i].trim());
                label.getStyleClass().add("label-rectangles");
                //label.setTextFill(Color.BLACK);
                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(rectangle, label);
                bars[i] = stackPane;
                barContainer.getChildren().add(bars[i]);
            }
            barContainer.setSpacing(10); // Add spacing between bars
            barContainer.setAlignment(Pos.CENTER); // Center align the bars
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Ensure all inputs are valid numbers.");
        }
    }

    private void bubbleSort() {
        List<KeyFrame> keyFrames = new ArrayList<>();
        Duration duration = Duration.ZERO;
        Duration stepDuration = Duration.seconds(1);

        int n = bars.length;
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = Integer.parseInt(((Label) bars[i].getChildren().get(1)).getText());
        }

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                final int finalJ = j; // Create a final copy of j
                final int finalJ1 = j + 1; // Create a final copy of j + 1

                // Highlight the bars being compared
                duration = duration.add(stepDuration);
                keyFrames.add(new KeyFrame(duration, e -> highlightBars(finalJ, finalJ1)));
                keyFrames.add(new KeyFrame(duration, e -> this.iterator1.setText("Iterator 1 : " + finalJ)));
                keyFrames.add(new KeyFrame(duration, e -> this.iterator2.setText("Iterator 2 : " + finalJ1)));

                if (values[j] > values[j + 1]) {
                    // Uplift bars before swapping
                    duration = duration.add(stepDuration);
                    keyFrames.add(new KeyFrame(duration, e -> upliftBars(finalJ, finalJ1)));

                    // Swap values
                    int temp = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = temp;
                    keyFrames.add(new KeyFrame(duration, e -> this.swap.setText("Swapped indices " + finalJ + " and " + finalJ1)));

                    // Swap bars visually
                    duration = duration.add(stepDuration);
                    keyFrames.add(new KeyFrame(duration, e -> swapBars(finalJ, finalJ1)));

                    // Lower bars after swapping
                    duration = duration.add(stepDuration);
                    keyFrames.add(new KeyFrame(duration, e -> lowerBars(finalJ, finalJ1)));
                }
            }
        }
        keyFrames.add(new KeyFrame(duration, e -> this.swap.setText("Sorted!")));

        // Play the timeline animation
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(keyFrames);
        timeline.setOnFinished(e -> resetBarColors());
        timeline.play();
    }

    private void swapBars(int index1, int index2) {
        String tempLabel = ((Label) bars[index1].getChildren().get(1)).getText();
        ((Label) bars[index1].getChildren().get(1)).setText(((Label) bars[index2].getChildren().get(1)).getText());
        ((Label) bars[index2].getChildren().get(1)).setText(tempLabel);
    }

    private void highlightBars(int index1, int index2) {
        resetBarColors();
        ((Rectangle) bars[index1].getChildren().get(0)).setFill(Color.BLUE); // Highlight the selected bar
        ((Rectangle) bars[index2].getChildren().get(0)).setFill(Color.RED);   // Highlight the comparison
    }

    private void resetBarColors() {
        for (StackPane bar : bars) {
            ((Rectangle) bar.getChildren().get(0)).setFill(Color.web("#1e90ff"));
        }
    }

    private void upliftBars(int index1, int index2) {
        ((Rectangle) bars[index1].getChildren().get(0)).setTranslateY(-20); // Uplift by 20 pixels
        ((Rectangle) bars[index2].getChildren().get(0)).setTranslateY(-20); // Uplift by 20 pixels
    }

    private void lowerBars(int index1, int index2) {
        ((Rectangle) bars[index1].getChildren().get(0)).setTranslateY(0); // Lower back to original position
        ((Rectangle) bars[index2].getChildren().get(0)).setTranslateY(0); // Lower back to original position
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
        mediaplayer.stop();
        mediaplayer.play();
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/visualization_setup.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Visualization Setup");
        stage.show();
        stage.setFullScreen(false);
        stage.centerOnScreen();
    }

    @FXML
    private void handleSelectionSort(ActionEvent event) throws IOException {
        // Load the Selection Sort Visualization screen
        mediaplayer.stop();
        mediaplayer.play();
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/selection_sort.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Selection Sort Visualization");
        stage.show();
        stage.setFullScreen(true);
    }

    @FXML
    private void handleBinarySearch(ActionEvent event) throws IOException {
        // Load the Binary Search Visualization screen
        mediaplayer.stop();
        mediaplayer.play();
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/binary_search.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Binary Search Visualization");
        stage.show();
        stage.setFullScreen(true);
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
        //Parent root = FXMLLoader.load(getClass().getResource("/com/algo/bubble_sort.fxml"));
        scene = new Scene(loadFXML("bubble_sort"));
        stage.setScene(scene);
        //Scene scene = new Scene(root);
        //stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle("Bubble Sort");
        stage.show();
    }

    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
