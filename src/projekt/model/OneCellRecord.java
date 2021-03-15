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

    public class Event
    {
        public Event(String eventName, String tag) {
            this.eventName = eventName;
            this.tag = tag;
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

        public void setTag(String tag) {
            this.tag = tag;
        }

        private String eventName;
        private String tag;

      
    }
}
