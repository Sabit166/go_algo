package com.algo;

import java.io.IOException;

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

public class MainMenuController {

    @FXML
    private ImageView button1image, button2image, button3image, button4image;

    @FXML
    private Text aboutustext, vtext, htext, etext, welcome, to, goalgo, learn, aaa;

    @FXML
    private AnchorPane aboutus, visualize, helpme, exit;

    Media sound = new Media(getClass().getResource("/com/algo/buttonclick.mp3").toExternalForm());
    MediaPlayer mediaplayer = new MediaPlayer(sound);

    @FXML
    public void initialize() {
        aboutustext.setFont(Font.loadFont(getClass().getResourceAsStream("supercell-magic.ttf"), 19));
        vtext.setFont(Font.loadFont(getClass().getResourceAsStream("supercell-magic.ttf"), 19));
        htext.setFont(Font.loadFont(getClass().getResourceAsStream("supercell-magic.ttf"), 19));
        etext.setFont(Font.loadFont(getClass().getResourceAsStream("supercell-magic.ttf"), 19));

        welcome.setFont(Font.loadFont(getClass().getResourceAsStream("supercell-magic.ttf"), 28));
        to.setFont(Font.loadFont(getClass().getResourceAsStream("supercell-magic.ttf"), 28));
        goalgo.setFont(Font.loadFont(getClass().getResourceAsStream("supercell-magic.ttf"), 28));

        learn.setFont(Font.loadFont(getClass().getResourceAsStream("supercell-magic.ttf"), 19));
        aaa.setFont(Font.loadFont(getClass().getResourceAsStream("supercell-magic.ttf"), 19));

        Image image1 = new Image(getClass().getResourceAsStream("/com/algo/images and stylesheets/image2.png"));
        Image image2 = new Image(getClass().getResourceAsStream("/com/algo/images and stylesheets/user.png"));
        aboutus.setOnMouseEntered(event -> {
            button4image.setImage(image1);
            aboutustext.setText("Thank you");
        });
        aboutus.setOnMouseExited(event -> {
            button4image.setImage(image2);
            aboutustext.setText("About Us");
        });

        Image image3 = new Image(getClass().getResourceAsStream("/com/algo/images and stylesheets/vimage.png"));
        Image image4 = new Image(getClass().getResourceAsStream("/com/algo/images and stylesheets/brainstorming.png"));
        visualize.setOnMouseEntered(event -> {
            button1image.setImage(image3);
            vtext.setText("Let's Visualize");
        });
        visualize.setOnMouseExited(event -> {
            button1image.setImage(image4);
            vtext.setText("Visualize");
        });

        Image image5 = new Image(getClass().getResourceAsStream("/com/algo/images and stylesheets/handshake.png"));
        Image image6 = new Image(getClass().getResourceAsStream("/com/algo/images and stylesheets/cloud-computing.png"));
        helpme.setOnMouseEntered(event -> {
            button2image.setImage(image5);
            htext.setText("Sure Gotcha!!");
        });
        helpme.setOnMouseExited(event -> {
            button2image.setImage(image6);
            htext.setText("Help Me!!");
        });

        Image image7 = new Image(getClass().getResourceAsStream("/com/algo/images and stylesheets/exit2.png"));
        Image image8 = new Image(getClass().getResourceAsStream("/com/algo/images and stylesheets/exit.png"));
        exit.setOnMouseEntered(event -> {
            button3image.setImage(image7);
            etext.setText("Come Again");
        });
        exit.setOnMouseExited(event -> {
            button3image.setImage(image8);
            etext.setText("Exit");
        });

    }

    @FXML
    private void handleStartVisualization(ActionEvent event) throws IOException {
        mediaplayer.stop();
        mediaplayer.play();
        loadPage("visualization_setup", event);
    }

    @FXML
    private void handleHelpInstructions(ActionEvent event) {
        // Handle help/instructions
        mediaplayer.stop();
        mediaplayer.play();
    }

    @FXML
    private void handleExit(ActionEvent event) {
        mediaplayer.stop();
        mediaplayer.play();
        System.exit(0);
    }

    @FXML
    private void handleAboutUs(ActionEvent event) throws IOException {
        mediaplayer.stop();
        mediaplayer.play();
        loadPage("about_us", event);
    }

    private void loadPage(String fxml, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/algo/" + fxml + ".fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(fxml.replace("_", " ").toUpperCase());
        stage.show();
    }

}
