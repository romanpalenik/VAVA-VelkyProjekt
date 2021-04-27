package project.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import project.controller.calendar.CalendarController;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainMenuController implements Internationalization{

    /**
     * open new window when you can create new invoice
     * @throws IOException if fxml is not loaded
     */
    public void changeSceneToLinks() throws IOException {
        this.translate();
        ResourceBundle bundle = changeLanguage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/view/useLinks/useFullLinks.fxml")), bundle);
        Scene scene = new Scene(root);
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }

    /**
     * Display main stage with login scene
     * @throws IOException if fxml is not loaded
     */
    public void showCalendar() throws IOException {
        ResourceBundle bundle = changeLanguage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/calendar/calendar.fxml")), bundle);
        Scene scene = new Scene(root);
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }

    /**
     * open new window when you can create new invoice
     * @throws IOException if fxml is not loaded
     */
    public void changeSceneToNotes() throws IOException {
        ResourceBundle bundle = changeLanguage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/notes/notes.fxml")), bundle);
        Scene scene = new Scene(root);
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }

    private ResourceBundle changeLanguage() {
        Locale locale;
        ResourceBundle bundle;
        if(CalendarController.language.equals("EN")){
            locale = new Locale("en");
            bundle = ResourceBundle.getBundle("/project/Bundle", locale);
        }
        else{
            locale = new Locale("sk");
            bundle = ResourceBundle.getBundle("/project/Bundle", locale);
        }
        return bundle;
    }

}
