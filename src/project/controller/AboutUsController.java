package project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import project.controller.calendar.CalendarController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutUsController extends ApplicationWindow implements Internationalization, Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Label firstTag;
    @FXML
    private Button languageButton;
    @FXML
    private Label aboutUsLbl;
    @FXML
    private Button redirectBtn;
    @FXML
    private Button menuButton;

    @FXML
    void redirect(MouseEvent event) {
        String url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";

        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();

        try{

            if (os.contains("win")) {
                // this doesn't support showing urls in the form of "page.html#nameLink"
                rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);

            } else if (os.indexOf( "mac" ) >= 0) {
                rt.exec( "open " + url);

            } else if (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0) {

                // Do a best guess on unix until we get a platform independent way
                // Build a list of browsers to try, in this order.
                String[] browsers = {"chrome","vivaldi","epiphany", "firefox", "mozilla", "konqueror",
                        "netscape","opera","links","lynx","brave"};

                // Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
                StringBuffer cmd = new StringBuffer();
                for (int i=0; i<browsers.length; i++)
                    cmd.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + url + "\" ");

                rt.exec(new String[] { "sh", "-c", cmd.toString() });

            } else {
                return;
            }
        }catch (Exception e){
            return;
        }
    }

    @FXML
    void translate(ActionEvent event) {
        if(languageButton.getText().equals("EN")){
            CalendarController.language = "EN";
        }
        else{
            CalendarController.language = "SK";
        }
        ResourceBundle bundle = this.changeLanguage();
        firstTag.setText(bundle.getString("aboutUsTitle"));
        aboutUsLbl.setText(bundle.getString("aboutUsInfo"));
        redirectBtn.setText(bundle.getString("aboutUsBtn"));
        languageButton.setText(bundle.getString("language"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            super.start(root, menuButton);
        } catch (IOException e) {
            e.printStackTrace();
        }

        root.setOnMouseClicked(this::removeAllThingsByClicked);
    }

    private void removeAllThingsByClicked(MouseEvent mouseEvent) {
        super.hideMenu(mouseEvent);
    }
}
