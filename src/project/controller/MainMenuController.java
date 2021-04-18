package project.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class MainMenuController {

    /**
     * open new window when you can create new invoice
     * @throws IOException
     */
    public void changeSceneToLinks() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/project/view/useFullLinks.fxml"));
        Scene scene = new Scene(root);
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }

    /**
     * Display main stage with login scene
     * @throws IOException
     */
    public void showCalendar() throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/project/view/calendar/calendar.fxml"));
        Scene scene = new Scene(root);
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }

    /**
     * open new window when you can create new invoice
     * @throws IOException
     */
    public void changeSceneToNotes() throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/project/view/notes/notes.fxml"));
        Scene scene = new Scene(root);
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }

}