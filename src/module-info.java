module ProjektVelky {

    requires javafx.graphics;
    requires javafx.fxml;

    requires javafx.controls;
    requires javafx.base;
    requires java.desktop;
    requires java.management;
    requires org.controlsfx.controls;
    opens project.controller;
    opens project.controller.calendar;
}