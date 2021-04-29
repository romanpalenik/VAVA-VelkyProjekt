package project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import project.controller.calendar.CalendarController;
import project.model.Todo;
import project.model.databases.TodoDatabase;
import project.model.databases.TodoGroupDatabase;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;


public class AddTodoController implements Initializable {

    @FXML
    private Button actionButton;

    @FXML
    private Label tagWarning;

    @FXML
    private ComboBox<String> categoryCBox;

    @FXML
    private DatePicker todoDatePicker;

    @FXML
    private TextField todoNoteField;

    @FXML
    void createTodo(MouseEvent event) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Todo todo = new Todo(todoNoteField.getText(), categoryCBox.getValue(), todoDatePicker.getValue().format(formatter));
        if(todo.getTodoName().equals("")){
            if(CalendarController.language.equals("SK")){
                tagWarning.setText("TO-DO bez názvu");
            }
            else{
                tagWarning.setText("TO-DO without name");
            }
            Main.LOG.log(Level.SEVERE, "TO-DO field without name.");
            tagWarning.setVisible(true);
        } else if (TodoDatabase.findByName(todoNoteField.getText(), categoryCBox.getValue())){
            if(CalendarController.language.equals("SK")){
                tagWarning.setText("TO-DO už existuje");
            }
            else{
                tagWarning.setText("TO-DO already exists");
            }
            Main.LOG.log(Level.SEVERE, "TO-DO already exists.");
            tagWarning.setVisible(true);
        }
        else{
            TodoDatabase.todoArrList.add(todo);
            TodoDatabase.saveTodo();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList obGroupList = FXCollections.observableList(TodoGroupDatabase.todoGroupArrList);
        categoryCBox.setItems(obGroupList);
        categoryCBox.getSelectionModel().selectFirst();

        todoDatePicker.setValue(LocalDate.now());
    }
}
