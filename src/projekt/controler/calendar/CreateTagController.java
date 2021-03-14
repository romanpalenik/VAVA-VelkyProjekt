package projekt.controler.calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import projekt.model.CalendarDatabase;
import projekt.view.calendar.CalendarViewCreatingThings;

public class CreateTagController {

    @FXML
    private TextField tagName;
    @FXML
    private Label tagWarning;
    @FXML
    private ComboBox choiceMode;
    @FXML
    private ComboBox choiceColor;
    @FXML
    private Label colorDescription;
    @FXML
    private ComboBox choiceTag;


    ObservableList<String> choice = FXCollections.observableArrayList("Upravit","Pridat novy");


    private CalendarDatabase calendarDatabase;
    private CalendarViewCreatingThings calendarView;
    private Stage stage;
    private AnchorPane root;
    private Label firstTag;

    public void initialize()
    {

        choiceMode.setItems(choice);
        choiceMode.setValue("Pridat novy");
        tagWarning.setVisible(false);
        choiceTag.setVisible(false);

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent> (){

            public void handle(ActionEvent actionEvent) {
                try {
                    switchToEditOrCreateMode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        choiceMode.setOnAction(event);

    }
    public void initData(CalendarDatabase calendarDatabase, CalendarViewCreatingThings calendarView, Stage stage, AnchorPane root,
                         Label firstTag) {
        this.calendarDatabase = calendarDatabase;
        this.calendarView = calendarView;
        this.stage = stage;
        this.root = root;
        this.firstTag = firstTag;
    }

    public void choiceEditOrCreate()
    {
        String choice = (String) choiceMode.getValue();
        if(choice.equals("Upravit"))
        {
            editTag();
        }
        else if (choice.equals("Pridat novy"))
        {
            addNewTag();
        }

    }

    public void addNewTag()
    {
        tagWarning.setVisible(false);

        if(calendarDatabase.addToTags(tagName.getText()))
        {
            calendarView.createTags(root,firstTag, calendarDatabase);
            stage.close();
        }
        else {
            tagWarning.setText("Tag uz existuje");
            tagWarning.setVisible(true);
            }

    }

    public void editTag()
    {
        calendarDatabase.editTag((String) choiceTag.getValue(),tagName.getText());
        calendarView.createTags(root,firstTag, calendarDatabase);
        stage.close();
    }

    public void switchToEditOrCreateMode()
    {
        String choice = (String) choiceMode.getValue();
        if(choice.equals("Upravit"))
        {
            choiceTag.setVisible(true);
            ObservableList<String> tags = FXCollections.observableArrayList(calendarDatabase.getTags());
            choiceTag.setItems(tags);
        }
        else if (choice.equals("Pridat novy"))
        {
            choiceTag.setVisible(false);
        }
    }

}
