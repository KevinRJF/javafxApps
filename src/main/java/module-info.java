module com.example.javafxapps {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javafxapps to javafx.fxml;
    exports com.example.javafxapps;
}