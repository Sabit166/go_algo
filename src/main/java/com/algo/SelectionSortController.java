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
import javafx.scene.control.Slider;
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
import javafx.stage.Stage;
import javafx.util.Duration;

public class SelectionSortController extends Application {

    private boolean candraw = false;
    private double LastX, LastY;

    private boolean menubuttonclicked = false;
    private boolean codevisible = false;

    private static Scene scene;
    private static final int BAR_WIDTH = 100; // Increased width
    private static final int MAX_HEIGHT = 400; // Increased height
    private StackPane[] bars;

    Media sound = new Media(getClass().getResource("/com/algo/buttonclick.mp3").toExternalForm());
    MediaPlayer mediaplayer = new MediaPlayer(sound);

    private final Slider slider = new Slider();
    private final ColorPicker colorpicker = new ColorPicker();

    private int stroke;
    private Color color;

    @FXML
    private HBox barContainer;

    @FXML
    private TextField inputField, searchField, numElementsField, elementsField;

    @FXML
    private Label foundLabel, startOperation, midOperation, endOperation, iterationOperation,currentLabel, smallestLabel,swaplabel;

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
            mediaplayer.stop();
            mediaplayer.play();
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
                "Pseudocode for Selection Sort:\n"
                + "1. Iterate over the array from the first element to the second last element.\n"
                + "2. For each element, assume it is the minimum.\n"
                + "3. Compare it with every other element in the array.\n"
                + "4. If any element is smaller, update the minimum.\n"
                + "5. Swap the minimum element with the current element.\n"
                + "6. Repeat until the array is sorted.");

        stroke = (int) slider.getValue();
        slider.valueProperty().addListener((obs, oldval, newVal) -> {
            stroke = (int) newVal.intValue();
        });

        colorpicker.setValue(Color.BLACK);
        colorpicker.setOnAction(eh -> color = colorpicker.getValue());

        item1.setOnAction(eh -> {
            candraw = true;
            mediaplayer.stop();
            mediaplayer.play();
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

        item1.getStyleClass().add("menu-item");
        item2.getStyleClass().add("menu-item");
        item3.getStyleClass().add("menu-item");

        CustomMenuItem item4 = new CustomMenuItem(colorpicker);
        item4.setHideOnClick(false);

        CustomMenuItem item5 = new CustomMenuItem(slider);
        item5.setHideOnClick(false);

        drawitem.getItems().addAll(item4, item5);

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
    private void handleSelectionSort() {
        mediaplayer.stop();
        mediaplayer.play();
        initializeBars(); // Initialize the bar visualization
        selectionSort();  // Perform the sorting with animation
    }

    private void initializeBars() {
        barContainer.getChildren().clear();
        String numElementsInput = numElementsField.getText().trim();
        String elementsInput = elementsField.getText().trim();

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
                rectangle.setStroke(Color.BLACK); // Set the border color
                rectangle.setStrokeWidth(5); // Set the border width
                Label label = new Label(elementsArray[i].trim());
                label.setTextFill(Color.BLACK);
                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(rectangle, label);
                bars[i] = stackPane;
                barContainer.getChildren().add(bars[i]);
            }
            barContainer.setSpacing(10); // Add spacing between bars
            barContainer.setAlignment(Pos.CENTER); // Center align the bars
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Ensure the size and all elements are valid numbers.");
        }
    }

    private void selectionSort() {
        List<KeyFrame> keyFrames = new ArrayList<>();
        Duration duration = Duration.ZERO;
        Duration stepDuration = Duration.seconds(1);

        int n = bars.length;
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = Integer.parseInt(((Label) bars[i].getChildren().get(1)).getText());
        }

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            int ty = i;
            for (int j = i + 1; j < n; j++) {
                int finalJ = j;
                int finalMinIndex = minIndex;

                // Highlight the bars being compared
                duration = duration.add(stepDuration);
                keyFrames.add(new KeyFrame(duration, e -> highlightBars(finalJ, ty, -1)));

                if (values[j] < values[minIndex]) {
                    minIndex = j;
                }
            }
            final int tmp = minIndex;
            duration = duration.add(stepDuration);
            keyFrames.add(new KeyFrame(duration, e -> highlightBars(-1, ty, tmp)));

            // Uplift bars before swapping
            int finalI = i;
            int finalMinIndex1 = minIndex;
            System.out.println(values[finalI]+" "+values[finalMinIndex1]);
            duration = duration.add(stepDuration);
            //keyFrames.add(new KeyFrame(duration, e -> this.currentLabel.setText("Current:  " + values[finalI])));
            //duration = duration.add(stepDuration);
            //currentLabel.setText("Current: " + finalI);
            //smallestLabel.setText("Smallest: " + finalMinIndex1);
            //keyFrames.add(new KeyFrame(duration, e -> this.smallestLabel.setText("Smallest:  " + values[finalMinIndex1])));
            if (finalI != finalMinIndex1) {
                keyFrames.add(new KeyFrame(duration, e -> upliftBars(finalI, finalMinIndex1)));
                //swaplabel.setText("Swapping " + values[finalI] + " with " + values[finalMinIndex1]);
            }
            // else
            // {
            //      swaplabel.setText("No swapping required");
            // }
            // Swap values
            int temp = values[minIndex];
            values[minIndex] = values[i];
            values[i] = temp;

            // Swap bars visually
            duration = duration.add(stepDuration);
            keyFrames.add(new KeyFrame(duration, e -> swapBars(finalI, finalMinIndex1)));

            // Lower bars after swapping
            duration = duration.add(stepDuration);
            if (finalI != finalMinIndex1) {
                keyFrames.add(new KeyFrame(duration, e -> lowerBars(finalI, finalMinIndex1)));
            }
        //    currentLabel.setText("Current: ");
        //    smallestLabel.setText("Smallest: ");
        //    swaplabel.setText("Swapping ");        
        }

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

    private void highlightBars(int index2, int index3, int index1) {
        resetBarColors();
        if (index1 != -1) {
            ((Rectangle) bars[index1].getChildren().get(0)).setFill(Color.GREEN); // Highlight the selected bar

        }
        if (index2 != -1) {
            ((Rectangle) bars[index2].getChildren().get(0)).setFill(Color.RED);   // Highlight the comparison

        }
        ((Rectangle) bars[index3].getChildren().get(0)).setFill(Color.BLUE);   // Highlight the initial bar
    }

    private void resetBarColors() {
        for (StackPane bar : bars) {
            ((Rectangle) bar.getChildren().get(0)).setFill(Color.DARKVIOLET);
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
    private void handleBubbleSort(ActionEvent event) throws IOException {
        // Load the Bubble Sort Visualization screen
        mediaplayer.stop();
        mediaplayer.play();
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/bubble_sort.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Bubble Sort Visualization");
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
        scene = new Scene(loadFXML("selection_sort"));
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle("Selection Sort");
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