module com.example.online_shop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.example.online_shop to javafx.fxml;
    exports com.example.online_shop;
}