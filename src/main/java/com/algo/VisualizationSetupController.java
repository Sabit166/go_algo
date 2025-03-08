package com.algo;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VisualizationSetupController {

    @FXML
    private Text title;

    @FXML
    private AnchorPane leftanchor, rightanchor, dstruct, algo;

    @FXML
    private ImageView anya;

    Media sound = new Media(getClass().getResource("/com/algo/buttonclick.mp3").toExternalForm());
    MediaPlayer mediaplayer = new MediaPlayer(sound);

    @FXML
    public void initialize() {

        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(600));
        fade.setInterpolator(Interpolator.EASE_BOTH);
        fade.setFromValue(0);
        fade.setToValue(1);

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.millis(600));
        slide.setInterpolator(Interpolator.EASE_BOTH);
        slide.setFromX(-150);
        slide.setToX(0);

        TranslateTransition slide2 = new TranslateTransition();
        slide2.setDuration(Duration.millis(600));
        slide2.setInterpolator(Interpolator.EASE_BOTH);
        slide2.setFromX(180);
        slide2.setToX(10);

        title.setFont(Font.loadFont(getClass().getResourceAsStream("supercell-magic.ttf"), 36));

        Image image1 = new Image(getClass().getResourceAsStream("/com/algo/images and stylesheets/Bond Anya Sticker.gif"));
        Image image2 = new Image(getClass().getResourceAsStream("/com/algo/images and stylesheets/anya2.png"));

        leftanchor.setVisible(false);
        rightanchor.setVisible(false);

        dstruct.setOnMouseEntered(event -> {
            leftanchor.setVisible(false);
            rightanchor.setVisible(true);
            algo.setVisible(false);
            anya.setVisible(false);

            slide2.setNode(rightanchor);
            slide2.play();
        });

        dstruct.setOnMouseExited(event -> {
            leftanchor.setVisible(false);
            rightanchor.setVisible(false);
            algo.setVisible(true);
            anya.setVisible(true);
        });

        algo.setOnMouseEntered(event -> {
            leftanchor.setVisible(true);
            rightanchor.setVisible(false);
            dstruct.setVisible(false);
            anya.setVisible(false);

            slide.setNode(leftanchor);
            slide.play();
        });

        algo.setOnMouseExited(event -> {
            leftanchor.setVisible(false);
            rightanchor.setVisible(false);
            dstruct.setVisible(true);
            anya.setVisible(true);
        });

        anya.setOnMouseEntered(event -> {
            anya.setImage(image2);
        });

        anya.setOnMouseExited(event -> {
            anya.setImage(image1);
        });
    }

    @FXML
    private void handleDataStructures(ActionEvent event) throws IOException {
        mediaplayer.stop();
        mediaplayer.play();
        loadPage("data_structures", event);
    }

    @FXML
    private void handleAlgorithms(ActionEvent event) throws IOException {
        mediaplayer.stop();
        mediaplayer.play();
        loadPage("algorithms", event);
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        mediaplayer.stop();
        mediaplayer.play();
        loadPage("main_menu", event);
    }

    private void loadPage(String fxml, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/" + fxml + ".fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        if (fxml != "main_menu" && fxml != "data_structures" && fxml != "algorithms") {
            stage.setFullScreen(true);
        }
        else {
            stage.setFullScreen(false);
            stage.centerOnScreen();
        }
        stage.setTitle(fxml.replace("_", " ").toUpperCase());
        stage.show();
    }
}
