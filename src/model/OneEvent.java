package model;

import java.time.LocalDate;

public class OneEvent {

    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public OneEvent(LocalDate date, String event) {
        this.date = date;
        this.event = event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    private String event;
}
