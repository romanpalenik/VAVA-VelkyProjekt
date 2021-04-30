package project.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import project.model.databases.sizesAndPosition.BasicSizesAndPosition;

import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainMenuController implements Internationalization{

    @FXML
    private AnchorPane root;

    @FXML
    private Button menuButton2;
    private ApplicationWindow appWindow;

    public void initialize()
    {
        BasicSizesAndPosition.setWidthOfMenu(root.getPrefWidth());
    }

    /**
     * open new window when you can create new invoice
     * @throws IOException if fxml is not loaded
     */
    public void changeSceneToLinks() throws IOException {
        ResourceBundle bundle = this.changeLanguage();
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
        ResourceBundle bundle = this.changeLanguage();
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
        ResourceBundle bundle = this.changeLanguage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/notes/notes.fxml")), bundle);
        Scene scene = new Scene(root);
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }

    public void changeSceneToTodo() throws IOException {
        ResourceBundle bundle = this.changeLanguage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/todo/todo.fxml")), bundle);
        Scene scene = new Scene(root);
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }


    public void initData(Button menuButton, ApplicationWindow appWindow) {
        this.appWindow = appWindow;

        appWindow.setMenuButtonInMenu(menuButton2);
        this.menuButton2.setOnMouseClicked((EventHandler) event -> {
            appWindow.hideMenu((MouseEvent) event);
        });
    }
}
