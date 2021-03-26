package projekt.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class OneCellRecord {

    private LocalDate date;
    private ArrayList<Event> everyEvent = new ArrayList<>();

    
    public ArrayList<Event> getEveryEvent() {
        return everyEvent;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public OneCellRecord(LocalDate date, String event, String tag) {
        this.date = date;
        addEvent(event, tag);
    }

    public void deleteFromEvents(String event)
    {
        for(int i = 0;i < everyEvent.size();i++)
        {
            if(everyEvent.get(i).getEventName().equals(event))
            {
                everyEvent.remove(i);
            }
        }
    }
    
    public void addEvent(String event, String tag)
    {
        Event eventObject = new Event(event,tag);
        this.everyEvent.add(eventObject);
    }

    /**
     * class to hold information of every event
     */
    public class Event
    {
        private String eventName;
        private String tag;
        private String notes;
        private String location;

        public Event(String eventName, String tag) {
            this.eventName = eventName;
            this.tag = tag;
            this.location = "";
            this.notes = "";
        }

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public String getTag() {
            return tag;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

    }
}
