package projekt.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{


    public static Stage primaryStage;


    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Skola");
        showMainView();
    }

    /**
     * Display main stage with login scene
     * @throws IOException
     */
    public static void showMainView() throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/projekt/view/calendar/calendar.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
