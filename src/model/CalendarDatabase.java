package model;

import java.util.ArrayList;

public class CalendarDatabase {

    private static ArrayList<String> tags = new ArrayList<String>();

    public CalendarDatabase() {

        tags.add("skola");
        tags.add("ine");

    }

    public ArrayList<String> getTags() {
        return tags;
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
}
