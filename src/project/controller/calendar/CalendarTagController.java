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

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

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


    ObservableList<String> choiceSK = FXCollections.observableArrayList("Pridať nový","Upraviť","Vymazať");
    ObservableList<String> colorsSK = FXCollections.observableArrayList("Červená","Zelená","Modrá","Fialová","Žltá");
    ObservableList<String> choiceEN = FXCollections.observableArrayList("Add new","Edit","Delete");
    ObservableList<String> colorsEN = FXCollections.observableArrayList("Red","Green","Blue","Purple","Yellow");

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
        if(CalendarController.language.equals("SK")){
            choiceColor.setItems(colorsSK);
            choiceMode.setItems(choiceSK);
            choiceMode.setValue("Pridať nový");
        }
        else{
            choiceColor.setItems(colorsEN);
            choiceMode.setItems(choiceEN);
            choiceMode.setValue("Add new");
        }
        tagWarning.setVisible(false);
        choiceTag.setVisible(false);
        tagsDescription.setVisible(false);

        EventHandler<ActionEvent> event = actionEvent -> {
            try {
                switchToEditOrCreateMode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        choiceMode.setOnAction(event);

        EventHandler<ActionEvent> event2 = actionEvent -> {
            try {
                tagName.setText((String) choiceTag.getValue());
            } catch (Exception e) {
                e.printStackTrace();
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

    public void choiceEditOrCreate() throws IOException {
        String choice = (String) choiceMode.getValue();
        switch (choice) {
            case "Upraviť":
            case "Edit":
                editTag();
                break;
            case "Pridať nový":
            case "Add new":
                addNewTag();
                break;
            case "Vymazať":
            case "Delete":
                deleteTag();
                break;
        }

    }


    public void addNewTag() throws IOException {
        tagWarning.setVisible(false);

        if(calendarDatabase.addToTags(tagName.getText(), (String) choiceColor.getValue())) {
            calendarView.createTags(root,calendarDatabase);
            stage.close();
        }
        else {
            tagWarning.setVisible(true);
        }
    }

    public void editTag() throws IOException {
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


    public void switchToEditOrCreateMode() {
        String choice = (String) choiceMode.getValue();
        switch (choice) {
            case "Upraviť":
            case "Edit":
                if(CalendarController.language.equals("SK")){
                    actionButton.setText("Upraviť tag");
                    tagsDescription.setText("Tag na úpravu");
                }
                else{
                    actionButton.setText("Edit tag");
                    tagsDescription.setText("Edit Tag");
                }
                colorDescription.setVisible(true);
                tagsDescription.setVisible(true);
                choiceTag.setVisible(true);
                choiceColor.setVisible(true);
                tagName.setVisible(true);
                tagNameDescription.setVisible(true);

                ObservableList<String> tags = FXCollections.observableArrayList(calendarDatabase.getTagsWithColor().keySet());
                choiceTag.setItems(tags);
                break;
            case "Pridať nový":
            case "Add new":
                if(CalendarController.language.equals("SK")){
                    actionButton.setText("Pridať tag");
                }
                else{
                    actionButton.setText("Add tag");
                }
                colorDescription.setVisible(true);
                tagsDescription.setVisible(false);
                tagName.setVisible(true);
                tagNameDescription.setVisible(true);
                choiceColor.setVisible(true);
                choiceTag.setVisible(false);
                break;
            case "Vymazať":
            case "Delete":
                if(CalendarController.language.equals("SK")){
                    actionButton.setText("Vymazať");
                    tagsDescription.setText("Tag na vymazanie");
                }
                else{
                    actionButton.setText("Delete");
                    tagsDescription.setText("Delete tag");
                }
                tagNameDescription.setVisible(false);
                tagsDescription.setVisible(true);
                colorDescription.setVisible(false);
                choiceTag.setVisible(true);
                tagName.setVisible(false);
                choiceColor.setVisible(false);

                ObservableList<String> tags2 = FXCollections.observableArrayList(calendarDatabase.getTagsWithColor().keySet());
                choiceTag.setItems(tags2);
                break;
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

    public void changeColor(Label currentTag, String color) throws IOException {
        calendarDatabase.addToTags(currentTag.getText(), color);
        calendarView.createTags(root,calendarDatabase);
        calendarController.updateCalendar(calendarController.getCurrentMonth());
    }

}
