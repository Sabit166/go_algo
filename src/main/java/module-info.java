module com.algo {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.algo to javafx.fxml;
    exports com.algo;

    // Specify the main class to run when debugging
    uses com.algo.App;
}

// module com.algo {
//     requires javafx.controls;
//     requires javafx.fxml;
    
//     // Add other required modules here
// }
