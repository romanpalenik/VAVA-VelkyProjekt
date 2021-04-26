package project.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application{


    public static Stage primaryStage;


    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Škola");
        showMainView();
    }

    /**
     * Display main stage with login scene
     * @throws IOException
     */
    public static void showMainView() throws IOException {
        Locale locale = new Locale("sk");
        ResourceBundle bundle = ResourceBundle.getBundle("/project/Bundle", locale);
        Parent root = FXMLLoader.load(Main.class.getResource("/project/view/calendar/calendar.fxml"), bundle);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
