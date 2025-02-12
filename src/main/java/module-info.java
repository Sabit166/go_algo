module com.algo {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.algo to javafx.fxml;
    opens com.algo.linkedlist to javafx.fxml;
    opens com.algo.segmenttree to javafx.fxml;
    
    exports com.algo;
    exports com.algo.linkedlist;
    exports com.algo.segmenttree;
}
