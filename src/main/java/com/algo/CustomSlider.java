package com.algo;

import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.control.skin.SliderSkin;

public class CustomSlider extends Slider {

    public CustomSlider() {
        super(1, 6, 2);
        this.setShowTickLabels(true);
        this.setShowTickMarks(true);
        this.setMajorTickUnit(1);
        this.setBlockIncrement(1);
        this.setSnapToTicks(true);

        this.getStyleClass().add("my-slider");
        this.getStylesheets()
                .add(getClass().getResource("/com/algo/images and stylesheets/slider.css").toExternalForm());
        this.setSkin(new SliderSkin(this) {
            @Override
            protected void layoutChildren(double x, double y, double w, double h) {
                super.layoutChildren(x, y, w, h);
                for (Node node : getChildren()) {
                    if (node.getStyleClass().contains("thumb")) {
                        node.setStyle("-fx-background-color: red; -fx-shape: 'M 0 0 L 10 5 L 0 10 L 2 5 Z';");
                        break;
                    }
                }
            }
        });
    }
}
