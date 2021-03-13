package model;

import java.util.ArrayList;

public class CalendarDatabase {

    ArrayList<String> tags = new ArrayList<String>();

    public CalendarDatabase() {

        tags.add("skola");
        tags.add("ine");

    }

    public ArrayList<String> getTags() {
        return tags;
    }
}
