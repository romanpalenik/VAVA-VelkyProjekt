package project.model.databases;

import project.model.Todo;

import java.util.ArrayList;

public class TodoDatabase {
    public static ArrayList<Todo> todoArrList = new ArrayList<Todo>();
    static {
        todoArrList.add(new Todo("Projekt boa","DBS", "09/10/2020", "Nie"));
        todoArrList.add(new Todo("Tak len dopixi","DBS", "05/05/2020", "Nie"));
    }


    public static ArrayList<Todo> findTodoByCategory(String categoryName){
        ArrayList<Todo> todoArrListByCategory = new ArrayList<Todo>();
        for(int i = 0; i < todoArrList.size(); i++) {
            if(categoryName.equals(todoArrList.get(i).getGroupTodo())){
                todoArrListByCategory.add(todoArrList.get(i));
            }
        }
        return todoArrListByCategory;
    }

    public static void updateCompleted(Todo item){
        for(int i = 0; i < todoArrList.size(); i++) {
            if(item.getTodoName().equals(todoArrList.get(i).getTodoName())){
                todoArrList.get(i).setCompleted("Áno");
            }
        }
    }

}
