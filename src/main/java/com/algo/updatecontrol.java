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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class updatecontrol {

    @FXML
    private ImageView mainimageview;

    @FXML
    private Button prev, next;
    private final List<String> imageUrls = new ArrayList<>();
    private int currentIndex = 0;

    Media sound = new Media(getClass().getResource("/com/algo/buttonclick.mp3").toExternalForm());
    MediaPlayer mediaplayer = new MediaPlayer(sound);

    public void initialize() {

        imageUrls.add(getClass().getResource("/com/algo/images and stylesheets/first.png").toExternalForm());
        imageUrls.add(getClass().getResource("/com/algo/images and stylesheets/second.png").toExternalForm());
        prev.setOnAction(e -> slideImage(-1));
        next.setOnAction(e -> slideImage(1));

    }

    private void slideImage(int direction) {
        int newIndex = (currentIndex + direction + imageUrls.size()) % imageUrls.size();
        Image newImage = new Image(imageUrls.get(newIndex));

        TranslateTransition slide = new TranslateTransition(Duration.seconds(0.5), mainimageview);
        slide.setFromX(-direction * -400);
        slide.setToX(0);
        slide.setOnFinished(event -> mainimageview.setImage(newImage));

        mainimageview.setImage(newImage);
        slide.play();
        currentIndex = newIndex;
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
