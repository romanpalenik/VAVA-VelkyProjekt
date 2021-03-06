module ProjektVelky {

    requires javafx.graphics;
    requires javafx.fxml;

    requires javafx.controls;
    requires javafx.base;
    requires java.desktop;
    requires java.management;
    requires java.logging;

    opens project.controller;
    opens project.controller.calendar;

    opens project.model to javafx.base;
}