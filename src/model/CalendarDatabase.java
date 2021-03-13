package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarDatabase {

    private static List<String> tags = new ArrayList<String>();
    private static List<OneEvent> events = new ArrayList<>();

    public static void setTags(ArrayList<String> tags) {
        CalendarDatabase.tags = tags;
    }


    public CalendarDatabase() {

        tags.add("skola");
        tags.add("ine");

    }

    public ArrayList<String> getTags() {
        return (ArrayList<String>) tags;
    }

    /**
     * check if tag is already in array, if is not add it
     * @param newTag
     * @return
     */
    public boolean addToTags(String newTag)
    {
        for(String tag: tags)
        {
            if (tag.equals(newTag))
            {
                return false;
            }
        }
        tags.add(newTag);
        return true;
    }

    public void editTag(String tag, String name)
    {
        for(int i=0;i<tags.size(); i++)
        {
            if (tags.get(i).equals(tag))
            {
                tags.set(i, name);
            }
        }

    }

    public void addToEvents(LocalDate date, String event)
    {
        OneEvent oneEvent = new OneEvent(date, event);
        events.add(oneEvent);
    }

    public OneEvent findEventByDate(LocalDate date)
    {
        for(OneEvent event : events)
        {
            if (date.equals(event.getDate())){
                return event;
            }
        }
        return null;
    }

}
