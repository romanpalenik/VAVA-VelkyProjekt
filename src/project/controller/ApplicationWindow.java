package project.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.controller.calendar.AnchorPaneNode;
import project.controller.calendar.EventDetail;

import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ApplicationWindow implements Internationalization{


    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane menuFMXL; //this anchor is anchor pane with menu
    @FXML
    private Button menuButton;

    @FXML
    private Button menuButtonInMenu;

    private Label darkSideWhenMenu;

    public void setMenuButtonInMenu(Button menuButtonInMenu) {
        this.menuButtonInMenu = menuButtonInMenu;
    }

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
        darkSideWhenMenu.setVisible(false);

        //Section to set connect all object connected to calendar and tags
        AnchorPaneNode.setRoot(root);

    }



    /**
     * show menu, if is menu already shown hide it
     * @throws IOException if menu is not loaded
     */
    public void showMenu() throws IOException {

        //TODO ked sa klikne na tlacidlo, tak nejde

        if (isMenuShown)
        {
            hideMenu(null);
            return;
        }

        root.getChildren().add(darkSideWhenMenu);
        root.getChildren().add(loadFMXLMenu());

        isMenuShown = true;
        darkSideWhenMenu.setVisible(true);

    }

    protected void hideMenu(MouseEvent event) {
       if(event.getSceneX() > 225 || event.getSource().equals(menuButtonInMenu)) {
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/view/menu/mainMenu.fxml"), bundle);
        menuFMXL = loader.load();
        MainMenuController controller = loader.getController();
        controller.initData(menuButton, this);
        return menuFMXL;
    }

    public void darkFilterWhileMenu() throws IOException {
        darkSideWhenMenu = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/menu/darkFilterWhileMenu.fxml")));
    }

}
