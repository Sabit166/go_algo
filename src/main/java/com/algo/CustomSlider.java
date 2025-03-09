package com.algo;

import javafx.scene.control.Slider;

public class CustomSlider extends Slider {


    public CustomSlider() {
        super(1, 6, 2);
        this.setShowTickLabels(true);
        this.setShowTickMarks(true);
        this.setMajorTickUnit(1);
        this.setBlockIncrement(1);
        this.setSnapToTicks(true);

        this.getStyleClass().add("my-slider");
        this.getStylesheets() .add(getClass().getResource("/com/algo/images and stylesheets/slider.css").toExternalForm());
    }

}
