package project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.controller.calendar.CalendarController;
import project.model.Todo;
import project.model.databases.TodoDatabase;
import project.model.databases.TodoGroupDatabase;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TodoController extends ApplicationWindow implements Internationalization, Initializable {

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
    private Label choosedGroupLbl;
    @FXML
    private TextField addGroupField;
    @FXML
    private Label groupTitleLbl;
    @FXML
    private ComboBox<String> groupCBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            super.start(root, menuButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
        todoClmn.setCellValueFactory(new PropertyValueFactory("todoName"));
        dateClmn.setCellValueFactory(new PropertyValueFactory("date"));

        initGroupCBox();
        addGroupField.setPromptText(resourceBundle.getString("addGroupField"));
        initTable(String.valueOf(groupCBox.getValue()));

        root.setOnMouseClicked(this::removeAllThingsByClicked);
    }

    private void removeAllThingsByClicked(MouseEvent mouseEvent) {
        super.hideMenu(mouseEvent);
    }

    public void initTable(String choosedValue){
        ArrayList<Todo> todoArrListByCategory = TodoDatabase.findTodoByCategory(choosedValue);
        ObservableList<Todo> data = FXCollections.observableArrayList(todoArrListByCategory);
        todoTable.getItems().setAll(data);
        choosedGroupLbl.setText(choosedValue);
    }

    private void initGroupCBox(){
        ObservableList obGroupList = FXCollections.observableList(TodoGroupDatabase.todoGroupArrList);
        groupCBox.setItems(obGroupList);
        groupCBox.getSelectionModel().selectFirst();
        addGroupField.setText("");
    }

    @FXML
    void showSelectedGroup(ActionEvent event) {
        initTable(String.valueOf(groupCBox.getValue()));
    }

    @FXML
    void markCompleted(MouseEvent event) throws IOException {
        ButtonType jopBtn;
        Todo todo = todoTable.getSelectionModel().getSelectedItem();
        if (todo != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            if(CalendarController.language.equals("SK")){
                alert.setContentText("Máš hotovo?");
                jopBtn = new ButtonType("Jo");
            }
            else{
                alert.setContentText("Task completed?");
                jopBtn = new ButtonType("Yes");
            }
            alert.getButtonTypes().setAll(jopBtn, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == jopBtn){
                TodoDatabase.updateArrListIfCompleted(todo);
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
        addGroupField.setPromptText(bundle.getString("addGroupField"));
        firstTagForLinkGroups.setText(bundle.getString("groups"));
        languageButton.setText(bundle.getString("language"));
        dateClmn.setText(bundle.getString("todoDate"));
        groupTitleLbl.setText(bundle.getString("groupTitle"));

    }

    @FXML
    void createTodo(MouseEvent event) throws IOException {
        ResourceBundle bundle = this.changeLanguage();
        Parent root = FXMLLoader.load(TodoController.class.getResource("/project/view/todo/addTodoView.fxml"), bundle);
        Stage stage = new Stage();
        stage.setTitle("Vytvor TO-DO");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void createGroupTodo(MouseEvent event) throws IOException {
        if(!addGroupField.getText().equals("") && testGroup(addGroupField.getText())) {
            TodoGroupDatabase.todoGroupArrList.add(addGroupField.getText());
            initGroupCBox();
            TodoGroupDatabase.saveTodoGroups();
        }else{
            Main.LOG.log(Level.SEVERE, "Text field for group name is empty.");
        }
    }


    public boolean testGroup(String toDoGroup){
        Pattern pattern = Pattern.compile("^[A-Z]*$");
        Matcher matcher = pattern.matcher(toDoGroup);
        return matcher.find();
    }

}
