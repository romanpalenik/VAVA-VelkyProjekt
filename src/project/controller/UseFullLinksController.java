package project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import project.controller.calendar.CalendarController;
import project.model.databases.LinkDatabase;
import project.view.useLinks.UseFullLinksView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.ResourceBundle;

public class UseFullLinksController extends AplicationWindow {

    @FXML
    private AnchorPane root;
    @FXML
    private Label firstTag;
    @FXML
    private Button menuButton;
    @FXML
    private Label firstTagForLinkGroups;
    @FXML
    private TextField newLink;
    @FXML
    private Button addButton;
    @FXML
    private Button languageButton;

    private UseFullLinksView linkView;
    private LinkDatabase linkDatabase = new LinkDatabase();
    private String currentLinkGroup;

    private TextField newNoteName;


    public void setNewNoteName(TextField newNoteName) {
        this.newNoteName = newNoteName;
    }


    public void initialize() throws IOException {

        super.start(root, menuButton);

        linkView = new UseFullLinksView(this, linkDatabase, firstTag, root, firstTagForLinkGroups,addButton);
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

            if (os.indexOf( "win" ) >= 0) {

                // this doesn't support showing urls in the form of "page.html#nameLink"
                rt.exec( "rundll32 url.dll,FileProtocolHandler " + arrOfStr[1]);

            } else if (os.indexOf( "mac" ) >= 0) {

                rt.exec( "open " + arrOfStr[1]);

            } else if (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0) {

                // Do a best guess on unix until we get a platform independent way
                // Build a list of browsers to try, in this order.
                String[] browsers = {"vivaldi","epiphany", "firefox", "mozilla", "konqueror",
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


    public void renameLink(String linkName) {
        linkDatabase.renameTag(linkName, newNoteName.getText(),linkDatabase.findLinkToName(linkName));
        root.getChildren().remove(newNoteName);
        linkView.showGroupLinks();
    }

    public void changeLink(String newLink, String linkName) {
        linkDatabase.addToLinks(linkName,newNoteName.getText());
        root.getChildren().remove(newNoteName);
        linkView.showGroupLinks();
    }

    public void OnMouseEnteredChangeColor(Label currentLabel)
    {
        linkView.changeLinkToBlue(currentLabel);
    }

    public void OnMouseLeaveChangeColor(Label currentLabel)
    {
        linkView.changeLinkToWhite(currentLabel);
    }

    public void changeLinkGroup(Label currentLinkGroup)
    {
        this.currentLinkGroup = currentLinkGroup.getText();
        linkView.setCurrentLinkGroup(currentLinkGroup.getText());
        linkView.showLinkInSelectedGroup();
    }
//
//    public void deleteNote(Label currentLabel) {
//        calendarDatabase.deleteTag(currentLabel.getText());
//        root.getChildren().remove(newNoteName);
//        calendarView.createTags(root,calendarDatabase);
//        calendarController.updateCalendar(calendarController.getCurrentMonth());
//
//    }

    public void addToLinks() {
        linkDatabase.getLinkGroups().get(currentLinkGroup).addToLinks("jako",newLink.getText());
    }

    @FXML
    void changeLanguage() {
        Locale locale;
        ResourceBundle bundle;
        if(languageButton.getText().equals("EN")){
            locale = new Locale("en");
            bundle = ResourceBundle.getBundle("/project/Bundle", locale);
            CalendarController.language = "EN";
        }
        else{
            locale = new Locale("sk");
            bundle = ResourceBundle.getBundle("/project/Bundle", locale);
            CalendarController.language = "SK";
        }
        firstTag.setText(bundle.getString("usefulLinksTitle"));
        addButton.setText(bundle.getString("addLink"));
        languageButton.setText(bundle.getString("language"));
        firstTagForLinkGroups.setText(bundle.getString("links"));
    }


}


