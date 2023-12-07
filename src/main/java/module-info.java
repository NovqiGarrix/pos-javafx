module com.novqigarrix.pos {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.zaxxer.hikari;
    requires java.sql;
    requires reactfx;

    opens com.novqigarrix.pos to javafx.fxml;
    opens com.novqigarrix.pos.controllers to javafx.fxml;
    opens com.novqigarrix.pos.services to javafx.fxml;
    opens com.novqigarrix.pos.exceptions to javafx.fxml;
    opens com.novqigarrix.pos.models to javafx.fxml;

    exports com.novqigarrix.pos;
    exports com.novqigarrix.pos.services;
    exports com.novqigarrix.pos.exceptions;
    exports com.novqigarrix.pos.models;
    exports com.novqigarrix.pos.controllers;
}