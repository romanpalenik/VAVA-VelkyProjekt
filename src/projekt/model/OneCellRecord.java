package projekt.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class OneCellRecord {

    private LocalDate date;
    private String event;
    private ArrayList<String> everyEvent = new ArrayList<>();


    public ArrayList<String> getEveryEvent() {
        return everyEvent;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public OneCellRecord(LocalDate date, String event) {
        this.date = date;
        addEvent(event);
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void addEvent(String event)
    {
        this.everyEvent.add(event);
    }
}
