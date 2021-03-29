module ProjektVelky {

    requires javafx.graphics;
    requires javafx.fxml;

    requires javafx.controls;
    opens projekt.controller;
    opens projekt.controller.calendar;
}