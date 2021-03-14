package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarDatabase {

    private static List<String> tags = new ArrayList<String>();
    private static List<OneCellRecord> events = new ArrayList<>();

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

    /**
     * set new event to cell record, if has no record create one
     * @param date
     * @param event
     */
    public void addToEvents(LocalDate date, String event)
    {
        //search if cell has already record in database
        for(OneCellRecord oneCellRecord:events)
        {
            if (oneCellRecord.getDate().equals(date))
            {
                //find existing record
                oneCellRecord.addEvent(event);
                return;
            }
        }
            OneCellRecord oneCellRecord = new OneCellRecord(date, event);
            events.add(oneCellRecord);
    }

    public OneCellRecord findEventByDate(LocalDate date)
    {
        for(OneCellRecord event : events)
        {
            if (date.equals(event.getDate())){
                return event;
            }
        }
        return null;
    }

}
