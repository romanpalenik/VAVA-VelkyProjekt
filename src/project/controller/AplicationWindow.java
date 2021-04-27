package project.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import project.controller.calendar.AnchorPaneNode;
import project.controller.calendar.CalendarController;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class AplicationWindow implements Internationalization{

    @FXML
    private Button calendarBtn;
    @FXML
    private Button todoBtn;
    @FXML
    private Button linksBtn;
    @FXML
    private Button notificationsBtn;
    @FXML
    private Button notesBtn;

    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane menuFMXL; //this anchor is anchor pane with menu
    @FXML
    private Button menuButton;

    private Label darkSideWhenMenu;



    private boolean isMenuShown = false;


    public void start(AnchorPane root, Button menuButton) throws IOException {

        this.root = root;
        this.menuButton = menuButton;
        this.menuButton.setOnAction((EventHandler) event -> {
            try {
                showMenu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        darkFilterWhileMenu();
        darkSideWhenMenu = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/menu/darkFilterWhileMenu.fxml")));
//        darkSideWhenMenu.setLayoutX(174);
        darkSideWhenMenu.setVisible(false);

        //Section to set connect all object connected to calendar and tags
        AnchorPaneNode.setRoot(root);

    }



    /**
     * show menu, if is menu already shown hide it
     * @throws IOException if menu is not loaded
     */
    public void showMenu() throws IOException {

//        if (isMenuShown)
//        {
//            hideMenu();
//            return;
//        }

        root.getChildren().add(darkSideWhenMenu);
        root.getChildren().add(loadFMXLMenu());

        isMenuShown = true;
        darkSideWhenMenu.setVisible(true);

    }

    protected void hideMenu(MouseEvent event) {
        if(event.getSceneX() > 172) {
            root.getChildren().remove(darkSideWhenMenu);
            isMenuShown = false;
            try {
                root.getChildren().remove(menuFMXL);
                darkSideWhenMenu.setVisible(false);
            } catch (NullPointerException e) {

            }
        }
    }


    /**
     * load anchor pane with menu buttons
     * @return menu
     */
    public AnchorPane loadFMXLMenu() throws IOException {
        ResourceBundle bundle = this.changeLanguage();
        menuFMXL = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/menu/mainMenu.fxml")), bundle);
//        menuFMXL.setLayoutY(75);
        return menuFMXL;
    }

    public void darkFilterWhileMenu() throws IOException {
        darkSideWhenMenu = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/menu/darkFilterWhileMenu.fxml")));
//        darkSideWhenMenu.setLayoutX(174);
    }

}
