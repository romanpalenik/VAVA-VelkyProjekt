package project.model.databases;

import project.model.calendar.OneCellRecord;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarDatabase implements Serializable {

    private static List<OneCellRecord> events = new ArrayList<>();

    private static Map<String, String> tagsWithColor = new HashMap<>();
    private static Map<String, String> colorWithHexId = new HashMap<>();


    private FileInputStream fis;
    private ObjectInputStream ois;


    public void loadEvents() throws ClassNotFoundException {

        try {

            fis = new FileInputStream("events.ser");
            ois = new ObjectInputStream(fis);
            events = (List<OneCellRecord>) ois.readObject();
        }
        catch (IOException ignored) {}
    }

    public static void safeEvents() throws IOException {
        FileOutputStream fos = new FileOutputStream("events.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(events);
    }

    public static void saveTags() throws IOException {
        FileOutputStream fos = new FileOutputStream("tags.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(tagsWithColor);
    }


    public void loadTags() throws ClassNotFoundException {

        try {

            fis = new FileInputStream("tags.ser");
            ois = new ObjectInputStream(fis);
            tagsWithColor = (Map<String, String>) ois.readObject();
        }
        catch (IOException ignored) {}
    }


    public CalendarDatabase() throws IOException, ClassNotFoundException {

        colorWithHexId.put("Červená","#f5a3a3");
        colorWithHexId.put("Red","#f5a3a3");
        colorWithHexId.put("Zelená","#b0fc38");
        colorWithHexId.put("Green","#b0fc38");
        colorWithHexId.put("Modrá","#a3d6f5");
        colorWithHexId.put("Blue","#a3d6f5");
        colorWithHexId.put("Fialová","#b2a3f5");
        colorWithHexId.put("Purple","#b2a3f5");
        colorWithHexId.put("Žltá","#f5f3a3");
        colorWithHexId.put("Yellow","#f5f3a3");

        loadEvents();
        loadTags();

    }


    /**
     * set new event to cell record, if has no record create one
     * @param date
     * @param event
     */
    public void addToEvents(LocalDate date, String event, String tag) throws IOException {
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

        safeEvents();
    }

    public void deleteFromEvents(LocalDate date, String event, String tag) throws IOException {
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
    public boolean addToTags(String newTag, String color) throws IOException {
        tagsWithColor.put(newTag,colorWithHexId.get(color));
        saveTags();
        return true;
    }

    /**
     * if tag is edited by right clicked, then program has only hex code of color not slovak name
     * @param newTag
     * @return
     */
    public boolean addToTagsWithHex(String newTag, String color)
    {
        tagsWithColor.put(newTag,color);
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

    public void renameTag(String oldNoteName, String colorToTag,String newNoteName )
    {
        deleteTag(oldNoteName);
        addToTagsWithHex(newNoteName,colorToTag);
    }
}
