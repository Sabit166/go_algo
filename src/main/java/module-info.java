module com.algo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.algo to javafx.fxml;
    exports com.algo;
}

// module com.algo {
//     requires javafx.controls;
//     requires javafx.fxml;
    
//     // Add other required modules here
// }
