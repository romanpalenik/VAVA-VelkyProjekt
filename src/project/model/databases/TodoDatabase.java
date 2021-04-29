package project.model.databases;

import project.model.Todo;

import java.io.*;
import java.util.ArrayList;

public class TodoDatabase {
    public static ArrayList<Todo> todoArrList = new ArrayList<Todo>();
//    static {
//        todoArrList.add(new Todo("Projekt boa","DBS", "09/10/2020"));
//        todoArrList.add(new Todo("Tak len dopixi","DBS", "05/05/2020"));
//    }


    public static ArrayList<Todo> findTodoByCategory(String categoryName){
        ArrayList<Todo> todoArrListByCategory = new ArrayList<Todo>();
        for(int i = 0; i < todoArrList.size(); i++) {
            if(categoryName.equals(todoArrList.get(i).getGroupTodo())){
                todoArrListByCategory.add(todoArrList.get(i));
            }
        }
        return todoArrListByCategory;
    }

    public static void updateArrListIfCompleted(Todo item){
        for(int i = 0; i < todoArrList.size(); i++) {
            if(item.getTodoName().equals(todoArrList.get(i).getTodoName())){
                todoArrList.remove(todoArrList.get(i));
                break;
            }
        }
    }

    public static boolean findByName(String item, String itemGroup){
        for(int i = 0; i < todoArrList.size(); i++) {
            if(item.equals(todoArrList.get(i).getTodoName()) && itemGroup.equals(todoArrList.get(i).getGroupTodo())){
                return true;
            }
        }
        return false;
    }

    private static FileInputStream fis;
    private static ObjectInputStream ois;

    public static void loadTodo() throws IOException, ClassNotFoundException {
        try {
            fis = new FileInputStream("todo.ser");
            ois = new ObjectInputStream(fis);
            todoArrList = (ArrayList<Todo>) ois.readObject();
        }
        catch (IOException ignored) {}
    }

    public static void saveTodo() throws IOException {
        FileOutputStream fos = new FileOutputStream("todo.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(todoArrList);
    }

}
