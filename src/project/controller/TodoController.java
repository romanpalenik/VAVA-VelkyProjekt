package project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import project.controller.calendar.CalendarController;
import project.model.Todo;
import project.model.databases.TodoDatabase;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TodoController extends AplicationWindow implements Internationalization, Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Label titleLbl;
    @FXML
    private Label firstTagForLinkGroups;
    @FXML
    private Button menuButton;
    @FXML
    private Button languageButton;
    @FXML
    private Button addButton;
    @FXML
    private Button addGroupButton;
    @FXML
    private TableView<Todo> todoTable;
    @FXML
    private TableColumn<Todo, String> todoClmn;
    @FXML
    private TableColumn<Todo, String> dateClmn;
    @FXML
    private TableColumn<Todo, String> completedClmn;
    @FXML
    private Label choosedGroupLbl;
    @FXML
    private TextField addGroupField;
    @FXML
    private Label groupTitleLbl;
//    -----------
    @FXML
    private Button actionButton;
    @FXML
    private Label tagWarning;
    @FXML
    private ComboBox<?> categoryCBox;
    @FXML
    private DatePicker todoDatePicker;
    @FXML
    private TextField todoNoteField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            super.start(root, menuButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
        todoClmn.setCellValueFactory(new PropertyValueFactory("todoName"));
        dateClmn.setCellValueFactory(new PropertyValueFactory("date"));
        completedClmn.setCellValueFactory(new PropertyValueFactory("completed"));
        ArrayList<Todo> todoArrListByCategory = TodoDatabase.findTodoByCategory("DBS");
        if(todoArrListByCategory.size() > 0) {
            ObservableList<Todo> data = FXCollections.observableArrayList(todoArrListByCategory);
            todoTable.getItems().setAll(data);
        }
        choosedGroupLbl.setText("DBS");
    }

    @FXML
    void markCompleted(MouseEvent event) {
        Todo todo = todoTable.getSelectionModel().getSelectedItem();
        if (todo != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Máš hotovo?");
            ButtonType jopBtn = new ButtonType("Jo");
            alert.getButtonTypes().setAll(jopBtn, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == jopBtn){
                TodoDatabase.updateCompleted(todo);
                ArrayList<Todo> todoArrListByCategory = TodoDatabase.findTodoByCategory("DBS");
                ObservableList<Todo> data = FXCollections.observableArrayList(todoArrListByCategory);
                todoTable.getItems().setAll(data);
            }
            todoTable.getSelectionModel().clearSelection();
        }

    }


    @FXML
    void translate(ActionEvent event) {
        if(languageButton.getText().equals("EN")){
            CalendarController.language = "EN";
        }
        else{
            CalendarController.language = "SK";
        }
        ResourceBundle bundle = this.changeLanguage();
        addButton.setText(bundle.getString("addTodo"));
        addGroupButton.setText(bundle.getString("addGroup"));
        addGroupField.setText(bundle.getString("addGroupField"));
        firstTagForLinkGroups.setText(bundle.getString("groups"));
        languageButton.setText(bundle.getString("language"));
        dateClmn.setText(bundle.getString("todoDate"));
        completedClmn.setText(bundle.getString("todoCompleted"));
        groupTitleLbl.setText(bundle.getString("groupTitle"));

    }

    @FXML
    void createTodo(ActionEvent event) {

    }

    @FXML
    void createGroupTodo(ActionEvent event) {

    }


}
