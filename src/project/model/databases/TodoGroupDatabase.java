package project.model.databases;

import java.io.*;
import java.util.ArrayList;

public class TodoGroupDatabase {
    public static ArrayList<String> todoGroupArrList = new ArrayList<String>();
//    static {
//        todoGroupArrList.add("DBS");
//        todoGroupArrList.add("VAVA");
//    }

    private static FileInputStream fis;
    private static ObjectInputStream ois;

    public static void loadTodoGroups() throws IOException, ClassNotFoundException {
        try {
            fis = new FileInputStream("todoGroups.ser");
            ois = new ObjectInputStream(fis);
            todoGroupArrList = (ArrayList<String>) ois.readObject();
        }
        catch (IOException ignored) {}
    }

    public static void saveTodoGroups() throws IOException {
        FileOutputStream fos = new FileOutputStream("todoGroups.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(todoGroupArrList);
    }
}
