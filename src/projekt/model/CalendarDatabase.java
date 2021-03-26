package projekt.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarDatabase {

    private static List<String> tags = new ArrayList<String>();
    private static List<OneCellRecord> events = new ArrayList<>();
    private static List<String> colors = new ArrayList<>();

    private static Map<String, String> tagsWithColor = new HashMap<>();
    private Map<String, String> colorWithHexId = new HashMap<>();

    /**
     * used when is created new tag
     * @param tag
     * @param color
     */
    public void addNewTagWithColor(String tag, String color)
    {
        tagsWithColor.put(tag, color);
    }

    public CalendarDatabase() {

        colorWithHexId.put("Modrá","#a3d6f5");
        colorWithHexId.put("Fialová","#b2a3f5");
        colorWithHexId.put("Zelená","#b0fc38");
        colorWithHexId.put("Žltá","#f5f3a3");
        colorWithHexId.put("Červená","#f5a3a3");

    }

    public static void setTags(ArrayList<String> tags) {
        CalendarDatabase.tags = tags;
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
    public void addToEvents(LocalDate date, String event, String tag)
    {
        //search if cell has already record in database
        for(OneCellRecord oneCellRecord:events)
        {
            if (oneCellRecord.getDate().equals(date))
            {
                //find existing record
                oneCellRecord.addEvent(event, tag);
                return;
            }
        }
            OneCellRecord oneCellRecord = new OneCellRecord(date, event, tag);
            events.add(oneCellRecord);
    }

    public void deleteFromEvents(LocalDate date, String event, String tag)
    {
        //search if cell has already record in database
        for(OneCellRecord oneCellRecord:events)
        {
            if (oneCellRecord.getDate().equals(date))
            {
                //find existing record
                oneCellRecord.addEvent(event, tag);
                return;
            }
        }
        OneCellRecord oneCellRecord = new OneCellRecord(date, event, tag);
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

    public String findColorToTag(String tag)
    {
        return tagsWithColor.get(tag);
    }

    /**
     * check if tag is already in array, if is not add it
     * @param newTag
     * @return
     */
    public boolean addToTags(String newTag, String color)
    {
        tagsWithColor.put(newTag,colorWithHexId.get(color));
        return true;
    }

    public Map<String, String> getTagsWithColor()
    {
        return tagsWithColor;
    }

    public void deleteTag(String tagName)
    {
        tagsWithColor.remove(tagName);
    }
}
