package project.model;

import java.io.Serializable;

public class Todo implements Serializable {
    private String todoName;
    private String groupTodo;
    private String date;

    public Todo(String todoName, String groupTodo, String date) {
        this.todoName = todoName;
        this.groupTodo = groupTodo;
        this.date = date;
    }

    public String getTodoName() {
        return todoName;
    }

    public String getGroupTodo() {
        return groupTodo;
    }

    public String getDate() {
        return date;
    }

}
