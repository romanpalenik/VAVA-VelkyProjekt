package project.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.model.databases.TodoDatabase;
import project.model.databases.TodoGroupDatabase;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main extends Application{
    public static Stage primaryStage;
    public static FileHandler loggerFile;
    public static final Logger LOG = Logger.getLogger(Main.class.getName());


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        loggerFile = new FileHandler("logging.txt", true);
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        loggerFile.setFormatter(simpleFormatter);
        LOG.addHandler(Main.loggerFile);

        TodoDatabase.loadTodo();
        TodoGroupDatabase.loadTodoGroups();
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
