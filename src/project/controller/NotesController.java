package project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import project.model.databases.NotesDatabase;
import project.view.notes.NoteView;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class NotesController extends AplicationWindow{

    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane menuFMXL; //this anchor is anchor pane with menu
    @FXML
    private Label darkSideWhenMenu;
    @FXML
    private Label firstNote;
    @FXML
    private Label nameOfNotes;
    @FXML
    private TextField nameOfNewNote;
    @FXML
    private TextArea note;
    @FXML
    private Label circle;
    @FXML
    private Label menu;
    @FXML
    private Button menuButton;


    private boolean isMenuShown = false;
    private NotesDatabase notesDatabase = new NotesDatabase();
    private NoteView notesView;

    private TextField newNoteName;

    public void setNewNoteName(TextField newNoteName) {
        this.newNoteName = newNoteName;
    }

    public void initialize() throws IOException {

        super.start(root,menuButton);
        notesView = new NoteView(this,root);
        root.setOnMouseClicked(this::removeAllThingsByClicked);
        notesView.createTags(root,firstNote, notesDatabase);

    }


    private void removeAllThingsByClicked(MouseEvent mouseEvent) {
        super.hideMenu();
        if(newNoteName != null)root.getChildren().remove(newNoteName);
    }


    public void onClick_btn_Save() throws IOException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Vyberte poznamky");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.txt" ,"*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(stage);


        nameOfNotes.setText(selectedFile.getName());
        note.setText(notesDatabase.loadTxt(selectedFile.getPath()));
        notesDatabase.addToNotes(nameOfNotes.getText(), notesDatabase.loadTxt(selectedFile.getPath()));
        notesView.createTags(root,firstNote, notesDatabase);

        saveNote();
    }


    /**
     * save old notes and create new one
     * also set notes to created one
     */
    public void createNewNote()
    {
        saveNote();
        notesDatabase.addToNotes(nameOfNewNote.getText(), "");
        notesView.createTags(root,firstNote, notesDatabase);
        nameOfNotes.setText(nameOfNewNote.getText());
        note.setText("");

    }

    public void saveNote()
    {
        if(!nameOfNotes.getText().equals("")) {
            notesDatabase.addToNotes(nameOfNotes.getText(), note.getText());
        }
    }

    public void changeNote(String noteName)
    {
        nameOfNotes.setText(noteName);
        note.setText(notesDatabase.getNoteByName(noteName));
    }

    public void deleteNote(String noteToDelete)
    {
        note.setText("");
        nameOfNotes.setText("");
        notesDatabase.deleteNotes(noteToDelete);
        notesView.createTags(root,firstNote, notesDatabase);
    }

    public void renameNote(Label currentLabel)
    {
        notesDatabase.renameNotes(currentLabel.getText(),notesDatabase.getNoteByName(currentLabel.getText()),newNoteName.getText());
        nameOfNotes.setText(newNoteName.getText());
        root.getChildren().remove(newNoteName);
        notesView.createTags(root,firstNote, notesDatabase);

    }

}
