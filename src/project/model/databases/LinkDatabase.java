package project.model.databases;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LinkDatabase implements Serializable{


    private Map<String, LinkGroup> linkGroups = new HashMap<>();
    private String lastCreatedNote;

    private FileInputStream fis;
    private ObjectInputStream ois;

    public String getLastCreatedNote() {
        return lastCreatedNote;
    }

    public void setLastCreatedNote(String lastCreatedNote) {
        this.lastCreatedNote = lastCreatedNote;
    }

    public void loadEvents() throws IOException, ClassNotFoundException {

        try {

            fis = new FileInputStream("links.ser");
            ois = new ObjectInputStream(fis);
            linkGroups = (Map<String, LinkGroup>) ois.readObject();
        }
        catch (IOException ignored) {}
    }

    public void safeEvents() throws IOException {
        FileOutputStream fos = new FileOutputStream("links.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(linkGroups);
    }


    public LinkDatabase() throws IOException, ClassNotFoundException {
        loadEvents();
    }

    public void addGroupLink(String linkName) throws IOException {
        LinkGroup linkGroup = new LinkGroup();
        linkGroups.put(linkName,linkGroup);
        safeEvents();
    }


    public Map<String, LinkGroup> getLinkGroups() {
        return linkGroups;
    }
}
