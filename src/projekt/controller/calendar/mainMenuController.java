package projekt.controller.calendar;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import projekt.controller.Main;

import java.io.IOException;

public class mainMenuController {

    /**
     * open new window when you can create new invoice
     * @throws IOException
     */
    public void changeSceneToLinks() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/projekt/view/useFullLinks.fxml"));
        Scene scene = new Scene(root);
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }

    /**
     * Display main stage with login scene
     * @throws IOException
     */
    public void showCalendar() throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/projekt/view/calendar/calendar.fxml"));
        Scene scene = new Scene(root);
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }

}
