package com.algo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BinarySearchController extends Application {

    private int target;
    private List<int[]> iterationsData = new ArrayList<>();
    private static Scene scene;
    private Timeline timeline;
    private static final int BAR_SIZE = 100; // Use square blocks
    private static final int GAP_SIZE = 75; // Gap for arrows
    private MediaPlayer mediaPlayer;
    private StackPane[] bars;
    private double LastX, LastY;
    private boolean candraw = false, menubuttonclicked = false, codevisible = false;
    private final ColorPicker colorpicker = new ColorPicker();
    private Color color = Color.BLACK;
    private int stroke;
    private final Slider slider = new Slider(1, 6, 2);
    
    Media sound = new Media(getClass().getResource("/com/algo/buttonclick.mp3").toExternalForm());
    MediaPlayer mediaplayer = new MediaPlayer(sound);

    @FXML
    private HBox barContainer;

    @FXML
    private TextField inputField, searchField;// numElementsField, elementsField;

    @FXML
    private Label foundLabel, startOperation, midOperation, endOperation, iterationOperation;

    @FXML
    private AnchorPane sidemenu, bpane, mainpane, codePane;

    @FXML
    private Button menubutton, viewCode, leftShift, rightShift;

    @FXML
    private MenuButton drawitem;

    @FXML
    private TextArea pseudoCodeArea;

    @FXML
    private Text mainText; 

    private int iterationScene;

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
                "Pseudocode for Binary Search:\n" +
                        "1. Set low = 0 and high = length of array - 1\n" +
                        "2. While low ≤ high:\n" +
                        "   a. Find mid = (low + high) / 2\n" +
                        "   b. If arr[mid] == target, return mid (found)\n" +
                        "   c. If arr[mid] < target, set low = mid + 1 (search right half)\n" +
                        "   d. Else, set high = mid - 1 (search left half)\n" +
                        "3. If not found, return -1");

        mainText.setFont(Font.loadFont(getClass().getResourceAsStream("/com/algo/fonts/supercell-magic.ttf"), 65));

        MenuItem item1 = new MenuItem("Draw");
        MenuItem item2 = new MenuItem("Erase");
        MenuItem item3 = new MenuItem("Off");

        // initilizing the slider
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

        item1.setOnAction(eh -> {
            candraw = true;
            mediaplayer.stop();
            mediaplayer.play();
            Image penImage = new Image(getClass().getResourceAsStream("/com/algo/images and stylesheets/pencil.png"));
            ImageCursor penCursor = new ImageCursor(penImage, penImage.getWidth() / 2, penImage.getHeight() / 2);
            mainpane.setCursor(penCursor);
        });
        item2.setOnAction(event -> {
            mainpane.getChildren().removeIf(node -> node instanceof Line);
            mediaplayer.stop();
            mediaplayer.play();
        });
        item3.setOnAction(eh -> {
            candraw = false;
            mainpane.setCursor(Cursor.DEFAULT);
            mediaplayer.stop();
            mediaplayer.play();
        });

        CustomMenuItem item4 = new CustomMenuItem(colorpicker);
        item4.setHideOnClick(false);

        CustomMenuItem item5 = new CustomMenuItem(slider);
        item5.setHideOnClick(false);

        drawitem.getItems().addAll(item1, item2, item3, item4, item5);

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
            iterationScene = 0;
        });

        rightShift.setDisable(true);
        leftShift.setDisable(true);
    }

    @FXML
    private void handleBinarySearch() {
        mediaplayer.stop();
        mediaplayer.play();
        initializeBars(); // Initialize the bar visualization
        //binarySearch(); // Perform the search with animation
    }

    @FXML
    private void leftIteration()
    {
        if (iterationScene > 0) {
            mediaplayer.stop();
            mediaplayer.play();
            iterationScene--;
            int[] data = iterationsData.get(iterationScene);
            highlightBars(data[0], data[2], data[1]);
            startOperation.setText("START:  " + data[0]);
            midOperation.setText("MID:  " + data[1] + "  (" + data[0] + "+" + data[2] + ")" + "/" +  "2");
            endOperation.setText("END:  " + data[2]);
            iterationOperation.setText("ITERATION:  " + iterationScene + " / " + (iterationsData.size() - 1));
            if(iterationScene == 0)
            {
                foundLabel.setText("");
                leftShift.setDisable(true);
                rightShift.setDisable(false);
            }
            else
            {
                leftShift.setDisable(false);
                rightShift.setDisable(false);
            }
            
        }
    }

    @FXML
    private void rightIteration()
    {
        if (iterationScene < iterationsData.size() - 1) {
            mediaplayer.stop();
            mediaplayer.play();
            iterationScene++;
            int[] data = iterationsData.get(iterationScene);
            highlightBars(data[0], data[2], data[1]);
            startOperation.setText("START:  " + data[0]);
            midOperation.setText("MID:  " + data[1] + "  (" + data[0] + "+" + data[2] + ")" + "/" +  "2");
            endOperation.setText("END:  " + data[2]);
            iterationOperation.setText("ITERATION:  " + iterationScene + " / " + (iterationsData.size() - 1));
            if(iterationScene == iterationsData.size() - 1 ) {
                foundLabel.setText("The target has been found at index = " + data[1]);
                rightShift.setDisable(true);
                leftShift.setDisable(false);
            }
            else
            {
                leftShift.setDisable(false);
                rightShift.setDisable(false);
            }
        }
    }

    private void initializeBars() {
        barContainer.getChildren().clear();
        foundLabel.setText(""); // Clear the found label
        String input = inputField.getText();
        try {
            String[] inputArray = input.split("\\s+");
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

            try {
                target = Integer.parseInt(searchField.getText().trim());
            } catch (NumberFormatException e) {
                showAlert("Input Error", "Ensure the search input is a valid number.");
                return;
            }

            boolean present = false;
            for (int i = 0; i < numElements; i++) {
                if (intArray[i] == target) {
                    present = true;
                    break;
                }
            }
            if(!present) {
                showAlert("Target Not Found", "The target element is not present in the array.");
                return;
            }
            

            int left = 0;
            int right = intArray.length - 1;

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

            iterationsData.clear();
            while (left <= right) {
                int mid = left + (right - left) / 2;
                iterationsData.add(new int[] { left, mid, right });
                if (intArray[mid] == target) {
                    break;
                } else if (intArray[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            iterationScene = 0;

            highlightBars(0, intArray.length - 1, (intArray.length - 1) / 2 );
            startOperation.setText("START:  " + 0);
            midOperation.setText("MID:  " + (intArray.length - 1) / 2 + "  (" + "0" + "+" + (intArray.length - 1) + ")" + "/" +  "2");
            endOperation.setText("END:  " + (intArray.length - 1));
            iterationOperation.setText("ITERATION:  " + iterationScene + " / " + (iterationsData.size() - 1));

            if(intArray[(intArray.length - 1) / 2] == target) 
            {
                foundLabel.setText("The target has been found at index = " + (intArray.length - 1) / 2);
                rightShift.setDisable(true);
            }

            else rightShift.setDisable(false);
            leftShift.setDisable(true);

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Ensure all inputs are valid numbers.");
        }
        playAudio("letsdobinarysearch"); // Play the binary search audio
    }

    private void draw(AnchorPane pane, MouseEvent event) {
        Line line = new Line(LastX, LastY, event.getSceneX(), event.getSceneY());
        line.setStroke(Color.web("#FFD700")); // Indigo color code
        line.setStrokeWidth(5);
        line.setStroke(color);
        line.setStrokeWidth(stroke);
        line.setStrokeLineJoin(StrokeLineJoin.ROUND);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        pane.getChildren().add(line);

        LastX = event.getSceneX();
        LastY = event.getSceneY();
    }


    private void binarySearch() {
        List<KeyFrame> keyFrames = new ArrayList<>();
        Duration duration = Duration.ZERO;
        Duration stepDuration = Duration.seconds(2); // Slow down the simulation

        int n = bars.length;
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = Integer.parseInt(((Label) bars[i].getChildren().get(1)).getText());
        }

        // int target;

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
        rightShift.setDisable(true);
        leftShift.setDisable(true);
        midOperation.setText("");
        startOperation.setText("");
        endOperation.setText("");
        iterationOperation.setText("");
        foundLabel.setText("");
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
        // Logic for Linked List
        mediaplayer.stop();
        mediaplayer.play();
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
        mediaplayer.stop();
        mediaplayer.play();
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/selection_sort.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Stack Visualization");
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