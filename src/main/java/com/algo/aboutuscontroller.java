package com.algo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class aboutuscontroller {

    @FXML
    private Button prev, next;

    @FXML
    private AnchorPane mainpane;

    @FXML
    private ImageView aboutus;
    @FXML
    private HBox dotbox;

    private final List<String> imageUrls = new ArrayList<>();
    private final List<Circle> dots = new ArrayList<>();
    private int currentIndex = 0;

    Media sound = new Media(getClass().getResource("/com/algo/buttonclick.mp3").toExternalForm());
    MediaPlayer mediaplayer = new MediaPlayer(sound);

    public void initialize() throws Exception {

        Image bgimage = new Image(getClass().getResourceAsStream("/com/algo/images and stylesheets/aboutus.jpg"));

        BackgroundImage bg = new BackgroundImage(bgimage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new javafx.scene.layout.BackgroundSize(100, 100, true, true, true, true));
        mainpane.setBackground(new javafx.scene.layout.Background(bg));


        imageUrls.add(getClass().getResource("/com/algo/images and stylesheets/tamim.png").toExternalForm());
        imageUrls.add(getClass().getResource("/com/algo/images and stylesheets/alif.png").toExternalForm());
        imageUrls.add(getClass().getResource("/com/algo/images and stylesheets/sabit.png").toExternalForm());

        aboutus.setImage(new Image(imageUrls.get(0)));

        prev.setOnAction(e -> slideImage(-1));
        next.setOnAction(e -> slideImage(1));

        dotbox.setStyle("-fx-alignment: center;");
        for (int i = 0; i < imageUrls.size(); i++) {
            Circle circle = new Circle(4);
            circle.setFill(i == 0 ? Color.BEIGE : Color.TRANSPARENT);
            circle.setRadius(i == 0 ? 5 : 3);
            circle.setStroke(Color.BEIGE);
            dots.add(circle);
            dotbox.getChildren().add(circle);
        }

    }

    private void slideImage(int direction) {
        int newIndex = (currentIndex + direction + imageUrls.size()) % imageUrls.size();
        Image newImage = new Image(imageUrls.get(newIndex));

        TranslateTransition slide = new TranslateTransition(Duration.seconds(0.5), aboutus);
        slide.setFromX(-direction * -400);
        slide.setToX(0);
        slide.setOnFinished(event -> aboutus.setImage(newImage));

        aboutus.setImage(newImage);
        slide.play();
        currentIndex = newIndex;
        updateDots();
    }

    private void updateDots() {
        for (int i = 0; i < dots.size(); i++) {
            dots.get(i).setFill(i == currentIndex ? Color.BEIGE : Color.TRANSPARENT);
            dots.get(i).setRadius(i == currentIndex ? 5 : 3);
        }
    }

    public void back_button(ActionEvent event) throws IOException {
        mediaplayer.stop();
        mediaplayer.play();
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/main_menu.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
