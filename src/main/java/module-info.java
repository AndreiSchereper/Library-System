module com.example.endassignment {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.endassignment to javafx.fxml;
    exports com.example.endassignment;
}