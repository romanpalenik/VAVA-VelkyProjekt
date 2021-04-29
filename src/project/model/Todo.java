package project.model;

public class Todo {
    private String todoName;
    private String groupTodo;
    private String date;
    private String completed;

    public Todo(String todoName, String groupTodo, String date, String completed) {
        this.todoName = todoName;
        this.groupTodo = groupTodo;
        this.date = date;
        this.completed = completed;
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

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }
}
