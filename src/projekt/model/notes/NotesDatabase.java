package projekt.model.notes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class NotesDatabase {

    private Map<String, String> notesWithName = new HashMap<>();

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
    public boolean addToNotes(String name, String notes)
    {
        notesWithName.put(name,notes);
        return true;
    }

    public void deleteNotes(String note)
    {
        notesWithName.remove(note);
    }

    public void renameNotes(String oldNoteName, String note,String newNoteName )
    {
        deleteNotes(oldNoteName);
        addToNotes(newNoteName,note);
    }
}
