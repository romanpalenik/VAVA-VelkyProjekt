package project.model.databases;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class NotesDatabase {

    private Map<String, String> notesWithName = new HashMap<>();

    private FileInputStream fis;
    private ObjectInputStream ois;

    public void loadEvents() throws IOException, ClassNotFoundException {

        try {

            fis = new FileInputStream("notes.ser");
            ois = new ObjectInputStream(fis);
            notesWithName = (Map<String, String>) ois.readObject();
        } catch (IOException ignored) {
        }
    }

    public void safeEvents() throws IOException {
        FileOutputStream fos = new FileOutputStream("notes.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(notesWithName);
    }

    public String loadTxt(String filePath) throws IOException {
        Path fileName = Path.of(filePath);
        String actual = Files.readString(fileName);
        return actual;
    }

    public Map<String, String> getNotesWithName() {
        return notesWithName;
    }

    public String getNoteByName(String name)
    {
        return notesWithName.get(name);
    }

    /**
     * check if tag is already in array, if is not add it
     * @return
     */
    public boolean addToNotes(String name, String notes) throws IOException {
        notesWithName.put(name,notes);
        safeEvents();
        return true;
    }

    public void deleteNotes(String note) throws IOException {
        notesWithName.remove(note);
        safeEvents();
    }

    public void renameNotes(String oldNoteName, String note,String newNoteName ) throws IOException {
        deleteNotes(oldNoteName);
        addToNotes(newNoteName,note);
    }
}
