package project.controller.calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.model.databases.CalendarDatabase;
import project.model.calendar.OneCellRecord;

public class EventDetail {

    @FXML
    private TextField eventName;
    @FXML
    private TextField locationTextField;
    @FXML
    private ComboBox deleteOrChangeTag;
    @FXML
    private ComboBox tagsChoice;
    @FXML
    private Label tagChoiceLabel;
    @FXML
    private TextArea notes;

    private Stage stage;
    private OneCellRecord oneCellRecord;
    private OneCellRecord.Event event;
    private CalendarController calendarController;
    private CalendarDatabase calendarDatabase;


    private ObservableList<String> tags;
    private ObservableList<String> choiceDeleteOrEdit = FXCollections.observableArrayList("Vymazat","Zmenit","Ziadna zmena");

    public void initData(Stage stage, OneCellRecord oneCellRecord, OneCellRecord.Event eventName, CalendarController calendarController, CalendarDatabase calendarDatabase) {
        this.stage = stage;
        this.oneCellRecord = oneCellRecord;
        this.event = eventName;
        this.calendarController = calendarController;
        this.calendarDatabase = calendarDatabase;
        start();
    }

    public void start()
    {
        tagChoiceLabel.setVisible(false);
        tagsChoice.setVisible(false);

        notes.setText(event.getNotes());
        eventName.setText(event.getEventName());
        locationTextField.setText(event.getLocation());

        tags = FXCollections.observableArrayList(calendarDatabase.getTagsWithColor().keySet());
        tagsChoice.setItems(tags);
        deleteOrChangeTag.setItems(choiceDeleteOrEdit);

        EventHandler<ActionEvent> event2 = actionEvent -> {
            try {
                showTagsOrHide();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        deleteOrChangeTag.setOnAction(event2);
    }

    /**
     * deleting event from cell and update calendar
     */
    public void deleteEvent()
    {
        oneCellRecord.deleteFromEvents(event.getEventName());
        calendarController.updateCalendar(calendarController.getCurrentMonth());
        stage.close();
    }

    /**
     * if is one of the information not blank than change that information in event
     */
    public void saveEvent()
    {
        if(!eventName.getText().equals(""))
        {
            event.setEventName(eventName.getText());
        }
        if(!locationTextField.getText().equals(""))
        {
            event.setLocation(locationTextField.getText());
        }
        try {
            if (deleteOrChangeTag.getValue().equals("Zmenit")) {
                event.setTag((String) tagsChoice.getValue());
            }
            if(deleteOrChangeTag.getValue().equals("Vymazat"))
            {
                event.setTag("");
            }
        }
        catch (NullPointerException e)
        {

        }
        event.setNotes(notes.getText());
        calendarController.updateCalendar(calendarController.getCurrentMonth());
    }

    /**
     * method show combo box with aliavable tags and label with description
     * or hide them
     */
    public void showTagsOrHide()
    {
        if(deleteOrChangeTag.getValue().equals("Zmenit")) {
            tagsChoice.setValue(event.getTag());
            tagChoiceLabel.setVisible(true);
            tagsChoice.setVisible(true);
        }
        else
        {
            tagChoiceLabel.setVisible(false);
            tagsChoice.setVisible(false);
        }

    }

}