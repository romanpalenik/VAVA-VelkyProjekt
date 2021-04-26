module ProjektVelky {

    requires javafx.graphics;
    requires javafx.fxml;

    requires javafx.controls;
    requires javafx.base;
    requires java.desktop;
    opens project.controller;
    opens project.controller.calendar;
}