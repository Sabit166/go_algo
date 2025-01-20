module com.algo {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.algo to javafx.fxml;
    exports com.algo;

}


