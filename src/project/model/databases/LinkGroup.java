package project.model.databases;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LinkGroup implements Serializable {

    private Map<String, String> linksWithNames = new HashMap<>();

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
}
