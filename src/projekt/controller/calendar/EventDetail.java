package projekt.controller.calendar;

import javafx.stage.Stage;
import projekt.model.OneCellRecord;


public class EventDetail {

    private Stage stage;
    private OneCellRecord oneCellRecord;
    private String event;
    private CalendarController calendarController;

    public void initData(Stage stage, OneCellRecord oneCellRecord, String eventName, CalendarController calendarController) {
        this.stage = stage;
        this.oneCellRecord = oneCellRecord;
        this.event = eventName;
        this.calendarController = calendarController;
    }

    public void deleteEvent()
    {
        oneCellRecord.deleteFromEvents(event);
        calendarController.updateCalendar(calendarController.getCurrentMonth());
        stage.close();
    }
}
