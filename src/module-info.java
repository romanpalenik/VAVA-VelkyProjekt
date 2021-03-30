module ProjektVelky {

    requires javafx.graphics;
    requires javafx.fxml;

    requires javafx.controls;
    opens project.controller;
    opens project.controller.calendar;
}