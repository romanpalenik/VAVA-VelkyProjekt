package project.controller.calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.model.databases.CalendarDatabase;
import project.view.calendar.CalendarViewCreatingThings;

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
    @FXML
    private Label tagsDescription;
    @FXML
    private Label colorDescription;
    @FXML
    private Button actionButton;
    @FXML
    private Label tagNameDescription;


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
        tagsDescription.setVisible(false);

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
        calendarController.updateCalendar(calendarController.getCurrentMonth());
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
            actionButton.setText("Upraviť tag");
            tagsDescription.setText("Tag na úpravu");

            colorDescription.setVisible(true);
            tagsDescription.setVisible(true);
            choiceTag.setVisible(true);

            ObservableList<String> tags = FXCollections.observableArrayList(calendarDatabase.getTagsWithColor().keySet());
            choiceTag.setItems(tags);
        }
        else if (choice.equals("Pridať nový"))
        {
            actionButton.setText("Pridať tag");

            colorDescription.setVisible(true);
            tagsDescription.setVisible(false);
            tagName.setVisible(true);
            choiceColor.setValue(true);
            choiceTag.setVisible(false);
        }
        else if (choice.equals("Vymazať"))
        {
            actionButton.setText("Vymazať");
            tagsDescription.setText("Tag na vymazanie");

            tagNameDescription.setVisible(true);
            tagsDescription.setVisible(false);
            colorDescription.setVisible(false);
            choiceTag.setVisible(true);
            tagName.setVisible(false);
            choiceColor.setVisible(false);

            ObservableList<String> tags = FXCollections.observableArrayList(calendarDatabase.getTagsWithColor().keySet());
            choiceTag.setItems(tags);
        }
    }

    public void renameNote(Label currentTag) {

        calendarDatabase.renameTag(currentTag.getText(), calendarDatabase.findColorToTag(currentTag.getText()), newNoteName.getText());
        root.getChildren().remove(newNoteName);
        calendarView.createTags(root,calendarDatabase);
    }

    public void deleteNote(Label currentLabel) {
        calendarDatabase.deleteTag(currentLabel.getText());
        root.getChildren().remove(newNoteName);
        calendarView.createTags(root,calendarDatabase);
        calendarController.updateCalendar(calendarController.getCurrentMonth());

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

    public void changeColor(Label currentTag, String color)
    {
        calendarDatabase.addToTags(currentTag.getText(), color);
        calendarView.createTags(root,calendarDatabase);
        calendarController.updateCalendar(calendarController.getCurrentMonth());

    }

}
