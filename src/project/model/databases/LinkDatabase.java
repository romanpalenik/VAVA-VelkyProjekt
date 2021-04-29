package project.model.databases;

import project.model.LinkGroup;

import java.util.HashMap;
import java.util.Map;

public class LinkDatabase {

    private Map<String, String> linksWithNames = new HashMap<>();

    public LinkDatabase() {
        LinkGroup linkGroup = new LinkGroup();
        linkGroup.addToLinks("Easter egg", "https://www.youtube.com/watch?v=dQw4w9WgXcQ");
        linkGroups.put("Vseobecne",linkGroup);
    }

    public void addGroupLink(String linkName)
    {
        LinkGroup linkGroup = new LinkGroup();
        linkGroups.put(linkName,linkGroup);
    }


    private Map<String, LinkGroup> linkGroups = new HashMap<>();

    public Map<String, LinkGroup> getLinkGroups() {
        return linkGroups;
    }
}
