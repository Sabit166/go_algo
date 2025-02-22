module com.algo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;


    opens com.algo to javafx.fxml;
    exports com.algo;
}