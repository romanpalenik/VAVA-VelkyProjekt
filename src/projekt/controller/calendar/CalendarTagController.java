package projekt.controller.calendar;

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
import projekt.model.calendar.CalendarDatabase;
import projekt.view.calendar.CalendarViewCreatingThings;

public class CalendarTagController {

    @FXML
    private TextField tagName;
    @FXML
    private Label tagWarning;
    @FXML
    private ComboBox choiceMode;
    @FXML
    private ComboBox choiceColor;
    @FXML
    private ComboBox choiceTag;


    ObservableList<String> choice = FXCollections.observableArrayList("Pridať nový","Upraviť","Vymazať");
    ObservableList<String> colors = FXCollections.observableArrayList("Červená","Zelená","Modrá","Fialová","Žltá");

    private CalendarDatabase calendarDatabase;
    private CalendarViewCreatingThings calendarView;
    private CalendarController calendarController;
    private Stage stage;


    private AnchorPane root;
    private Label firstTag;
    private TextField newNoteName;

    public void setNewNoteName(TextField newNoteName) {
        this.newNoteName = newNoteName;
    }

    public void initialize()
    {
        choiceColor.setItems(colors);
        choiceMode.setItems(choice);
        choiceMode.setValue("Pridať nový");
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

        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent> (){

            public void handle(ActionEvent actionEvent) {
                try {
                    tagName.setText((String) choiceTag.getValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        choiceTag.setOnAction(event2);



    }
    public void initData(CalendarDatabase calendarDatabase, CalendarViewCreatingThings calendarView, Stage stage, AnchorPane root,
                         Label firstTag, CalendarController calendarController) {
        this.calendarDatabase = calendarDatabase;
        this.calendarView = calendarView;
        this.stage = stage;
        this.root = root;
        this.firstTag = firstTag;
        this.calendarController = calendarController;
    }

    public void choiceEditOrCreate()
    {
        String choice = (String) choiceMode.getValue();
        if(choice.equals("Upraviť"))
        {
            editTag();
        }
        else if (choice.equals("Pridať nový"))
        {
            addNewTag();
        }
        else if (choice.equals("Vymazať"))
        {
            deleteTag();
        }

    }


    public void addNewTag()
    {
        tagWarning.setVisible(false);

        if(calendarDatabase.addToTags(tagName.getText(), (String) choiceColor.getValue()))
        {
            calendarView.createTags(root,calendarDatabase);
            stage.close();
        }
        else {
            tagWarning.setText("Tag uz existuje");
            tagWarning.setVisible(true);
            }

    }

    public void editTag()
    {
        calendarDatabase.addToTags(tagName.getText(), (String) choiceColor.getValue());
        calendarView.createTags(root,calendarDatabase);
        stage.close();
    }

    private void deleteTag()
    {
        calendarDatabase.deleteTag((String) choiceTag.getValue());
        calendarView.createTags(root,calendarDatabase);
        calendarController.updateCalendar(calendarController.getCurrentMonth());
        stage.close();
    }


    public void switchToEditOrCreateMode()
    {


        String choice = (String) choiceMode.getValue();
        if(choice.equals("Upraviť"))
        {
            choiceTag.setVisible(true);
            ObservableList<String> tags = FXCollections.observableArrayList(calendarDatabase.getTagsWithColor().keySet());
            choiceTag.setItems(tags);
        }
        else if (choice.equals("Pridať nový"))
        {
            tagName.setVisible(true);
            choiceColor.setValue(true);
            choiceTag.setVisible(false);
        }
        else if (choice.equals("Vymazať"))
        {

            choiceTag.setVisible(true);
            tagName.setVisible(false);
            choiceColor.setVisible(false);

            ObservableList<String> tags = FXCollections.observableArrayList(calendarDatabase.getTagsWithColor().keySet());
            choiceTag.setItems(tags);
        }
    }

    public void renameNote(Label currentLabel) {

        calendarDatabase.renameTag(currentLabel.getText(), calendarDatabase.findColorToTag(currentLabel.getText()), newNoteName.getText());
        root.getChildren().remove(newNoteName);
        calendarView.createTags(root,calendarDatabase);
    }

    public void setCalendarDatabase(CalendarDatabase calendarDatabase) {
        this.calendarDatabase = calendarDatabase;
    }

    public void setCalendarView(CalendarViewCreatingThings calendarView) {
        this.calendarView = calendarView;
    }

    public void setCalendarController(CalendarController calendarController) {
        this.calendarController = calendarController;
    }

}
