package projekt.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class UseFullLinksController {

    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane menuFMXL; //this anchor is anchor pane with menu
    @FXML
    private Label darkSideWhenMenu;

    private boolean isMenuShown = false;

    public void initialize() throws IOException {

        darkFilterWhileMenu();
        darkSideWhenMenu.setVisible(false);

        root.setOnMouseClicked(this::removeAllThingsByClicked);


    }

    private void removeAllThingsByClicked(MouseEvent mouseEvent) {
    }

    /**
     * show menu, if is menu already shown hide it
     * @throws IOException
     */
    public void showMenu() throws IOException {

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

    private void hideMenu(MouseEvent mouseEvent) {

        root.getChildren().remove(darkSideWhenMenu);
        isMenuShown = false;
        try {
            root.getChildren().remove(menuFMXL);
            darkSideWhenMenu.setVisible(false);
        }
        catch (NullPointerException e)
        {

        }
    }


    /**
     * load anchor pane with menu buttons
     * @return menu
     */
    public AnchorPane loadFMXLMenu() throws IOException {
        
        menuFMXL = FXMLLoader.load(Main.class.getResource("/projekt/view/mainMenu.fxml"));
        menuFMXL.setLayoutY(75);
        return menuFMXL;
    }

    public Label darkFilterWhileMenu() throws IOException {
        darkSideWhenMenu = FXMLLoader.load(Main.class.getResource("/projekt/view/darkFilterWhileMenu.fxml"));
        darkSideWhenMenu.setLayoutX(174);
        return darkSideWhenMenu;
    }


}
