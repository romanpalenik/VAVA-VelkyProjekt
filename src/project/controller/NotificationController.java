package project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import project.controller.calendar.CalendarController;


import java.io.IOException;
import java.util.ResourceBundle;

public class NotificationController extends AplicationWindow {

    @FXML
    private AnchorPane root;
    @FXML
    private Button menuButton;
    @FXML
    private Button languageButton;
    @FXML
    private Label firstTag;



    public void initialize() throws IOException {

        super.start(root, menuButton);
        root.setOnMouseClicked(this::removeAllThingsByClicked);
    }


    private void removeAllThingsByClicked(MouseEvent mouseEvent) {
        super.hideMenu(mouseEvent);

    }

    public void translate(ActionEvent actionEvent) {
    }


    @FXML
    void translate() {
        if(languageButton.getText().equals("EN")){
            CalendarController.language = "EN";
        }
        else{
            CalendarController.language = "SK";
        }
        ResourceBundle bundle = this.changeLanguage();
        languageButton.setText(bundle.getString("language"));
        firstTag.setText(bundle.getString("notificationTitle"));

    }


}
