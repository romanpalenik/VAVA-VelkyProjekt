package project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import project.controller.calendar.CalendarController;
import project.model.databases.LinkDatabase;
import project.model.databases.sizesAndPosition.BasicSizesAndPosition;
import project.view.useLinks.UseFullLinksView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ResourceBundle;

public class UseFullLinksController extends ApplicationWindow implements Internationalization{

    @FXML
    private AnchorPane root;
    @FXML
    private Label firstTag;
    @FXML
    private Button menuButton;
    @FXML
    private Label firstTagForLinkGroups;
    @FXML
    private Button addButton;
    @FXML
    private Button addGroupButton;
    @FXML
    private Button languageButton;
    @FXML
    private TextField newLinkGroup;

    private UseFullLinksView linkView;
    private LinkDatabase linkDatabase = new LinkDatabase();
    private String currentLinkGroup;


    private TextField newLink;
    private TextField newLinkName;
    private TextField newNoteName;

    public UseFullLinksController() throws IOException, ClassNotFoundException {
    }


    public void setNewNoteName(TextField newNoteName) {
        this.newNoteName = newNoteName;
    }

    public void setNewLink(TextField newLink) {
        this.newLink = newLink;
    }

    public void setNewLinkName(TextField newLinkName) {
        this.newLinkName = newLinkName;
    }

    public void setNewLinkGroup(TextField newLinkGroup) {
        this.newLinkGroup = newLinkGroup;
    }

    public void initialize() throws IOException {

        firstTag.setLayoutX(BasicSizesAndPosition.getWidthOfMenu() + BasicSizesAndPosition.getGapBetweenObjects());
        super.start(root, menuButton);

        linkView = new UseFullLinksView(this, linkDatabase, root, firstTagForLinkGroups,addButton, addGroupButton, firstTag,languageButton);
        linkView.showGroupLinks();

        root.setOnMouseClicked(this::removeAllThingsByClicked);

    }


    private void removeAllThingsByClicked(MouseEvent mouseEvent) {
        super.hideMenu(mouseEvent);
        root.getChildren().remove(newNoteName);

    }


    public void openLink(Label label) throws URISyntaxException, IOException {

        String url = label.getText();
        String[] arrOfStr = url.split(": ");

        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();

        try{

            if (os.contains("win")) {

                // this doesn't support showing urls in the form of "page.html#nameLink"
                rt.exec( "rundll32 url.dll,FileProtocolHandler " + arrOfStr[1]);

            } else if (os.indexOf( "mac" ) >= 0) {

                rt.exec( "open " + arrOfStr[1]);

            } else if (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0) {

                // Do a best guess on unix until we get a platform independent way
                // Build a list of browsers to try, in this order.
                String[] browsers = {"chrome","vivaldi","epiphany", "firefox", "mozilla", "konqueror",
                        "netscape","opera","links","lynx","brave"};

                // Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
                StringBuffer cmd = new StringBuffer();
                for (int i=0; i<browsers.length; i++)
                    cmd.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + arrOfStr[1] + "\" ");

                rt.exec(new String[] { "sh", "-c", cmd.toString() });

            } else {
                return;
            }
        }catch (Exception e){
            return;
        }
        return;
    }


    public void renameLink(String linkName) throws IOException {
        linkDatabase.getLinkGroups().get(currentLinkGroup).renameTag(linkName, newNoteName.getText(),linkDatabase.getLinkGroups().get(currentLinkGroup).findLinkToName(linkName));
        root.getChildren().remove(newNoteName);
        linkView.showGroupLinks();
        linkDatabase.safeEvents();
    }

    public void changeLink(String linkName) throws IOException {
        root.getChildren().remove(newNoteName);
        linkDatabase.getLinkGroups().get(currentLinkGroup).addToLinks(linkName,newNoteName.getText());
        linkView.showGroupLinks();
        linkDatabase.safeEvents();
    }

    public void changeLinkGroup(Label currentLinkGroup) throws IOException {
        firstTag.setText(currentLinkGroup.getText());
        this.currentLinkGroup = currentLinkGroup.getText();
        linkView.setCurrentLinkGroup(currentLinkGroup.getText());
        linkView.showLinkInSelectedGroup();
        linkDatabase.safeEvents();
    }

    public void OnMouseEnteredChangeColor(Label currentLabel)
    {
        linkView.changeLinkToBlue(currentLabel);
    }

    public void OnMouseLeaveChangeColor(Label currentLabel)
    {
        linkView.changeLinkToWhite(currentLabel);
    }

    public void deleteNote(String currentLabel) throws IOException {
        linkDatabase.getLinkGroups().get(currentLinkGroup).deleteTag(currentLabel);
        linkView.showGroupLinks();
        linkDatabase.safeEvents();

    }

    public void addToLinks() throws IOException {
        linkDatabase.getLinkGroups().get(currentLinkGroup).addToLinks(newLinkName.getText(),newLink.getText());
        linkView.showLinkInSelectedGroup();
        linkView.showAddLink();
        linkDatabase.safeEvents();
    }

    public void addLinkGroup() throws IOException {
        linkDatabase.setLastCreatedNote(newLinkGroup.getText());
        linkDatabase.addGroupLink(newLinkGroup.getText());
        linkView.showGroupLinks();
        linkView.showAddGroup();
        linkDatabase.safeEvents();

    }

    @FXML
    void translate() {
        if(languageButton.getText().equals("EN")){
            CalendarController.language = "EN";
        }
        else{
            CalendarController.language = "SK";
        }
        ResourceBundle bundle = this.changeLanguage();
        firstTag.setText(bundle.getString("usefulLinksTitle"));
        addButton.setText(bundle.getString("addLink"));
        languageButton.setText(bundle.getString("language"));
        firstTagForLinkGroups.setText(bundle.getString("links"));
        addGroupButton.setText(bundle.getString("addGroupLink"));
    }


    public void deleteGroup(Label currentLabel) throws IOException {
        linkView.showGroupLinks();
        linkDatabase.deleteGroup(currentLabel.getText());
        linkDatabase.safeEvents();
    }
}


