package project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import project.controller.calendar.CalendarController;

import java.awt.*;
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


        String title = "Congratulations sir";
        String message = "You've successfully created your first Tray Notification";

        try{
            //Obtain only one instance of the SystemTray object
            SystemTray tray = SystemTray.getSystemTray();

            // If you want to create an icon in the system tray to preview
            Image image = Toolkit.getDefaultToolkit().createImage("some-icon.png");
            //Alternative (if the icon is on the classpath):
            //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

            TrayIcon trayIcon = new TrayIcon(image, "Java AWT Tray Demo");
            //Let the system resize the image if needed
            trayIcon.setImageAutoSize(true);
            //Set tooltip text for the tray icon
            trayIcon.setToolTip("System tray icon demo");
            tray.add(trayIcon);

            // Display info notification:
            trayIcon.displayMessage("Hello, World", "Java Notification Demo", TrayIcon.MessageType.INFO);
            // Error:
            // trayIcon.displayMessage("Hello, World", "Java Notification Demo", MessageType.ERROR);
            // Warning:
            // trayIcon.displayMessage("Hello, World", "Java Notification Demo", MessageType.WARNING);
        }catch(Exception ex){
            System.err.print(ex);
        }
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
