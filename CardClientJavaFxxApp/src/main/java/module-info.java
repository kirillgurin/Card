module com.kirillgurin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens com.kirillgurin to javafx.fxml;
    exports com.kirillgurin;
    exports com.kirillgurin.model;
}
