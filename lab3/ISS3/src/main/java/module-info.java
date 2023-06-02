module com.example.iss3a {
    requires javafx.controls;
    requires javafx.fxml;
    requires Persistence;

    opens Controller to javafx.fxml;
    exports Controller;

    opens com.example.iss3 to javafx.fxml;
    exports com.example.iss3;
}