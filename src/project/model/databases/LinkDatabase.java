package project.model.databases;

import project.model.LinkGroup;

import java.util.HashMap;
import java.util.Map;

public class LinkDatabase {

    private Map<String, String> linksWithNames = new HashMap<>();

    public LinkDatabase() {
        addToLinks("youtube","https://www.youtube.com/watch?v=Keooxe5x6Ts" );
        LinkGroup linkGroup = new LinkGroup();
        linkGroup.addToLinks("Ed", "https://www.youtube.com/watch?v=2fngvQS_PmQ");
        linkGroups.put("Vseobecne",linkGroup);
    }

    public void addToLinks(String linkName, String link)
    {
        linksWithNames.put(linkName, link);
    }

    public Map<String, String> getLinksWithNames()
    {
        return linksWithNames;
    }

    public String findLinkToName(String linkName)
    {
        return linksWithNames.get(linkName);
    }

    public void deleteTag(String tagName)
    {
        linksWithNames.remove(tagName);
    }

    public void renameTag(String oldNoteName,String newNoteName, String link )
    {
        deleteTag(oldNoteName);
        addToLinks(newNoteName,link);
    }

    private Map<String, LinkGroup> linkGroups = new HashMap<>();

    public Map<String, LinkGroup> getLinkGroups() {
        return linkGroups;
    }
}
