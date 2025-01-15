module com.algo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.algo to javafx.fxml;
    exports com.algo;
}
