package project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import project.controller.calendar.CalendarController;
import project.model.databases.NotesDatabase;
import project.model.databases.sizesAndPosition.BasicSizesAndPosition;
import project.view.notes.NoteView;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class NotesController extends ApplicationWindow implements Internationalization{

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
    @FXML
    private Button languageButton;
    @FXML
    private Button saveBtn;
    @FXML
    private Button importBtn;
    @FXML
    private Button createNoteBtn;
    @FXML
    private Label noteNameLbl;
    @FXML
    private Label noteTitle;


    private boolean isMenuShown = false;
    private NotesDatabase notesDatabase = new NotesDatabase();
    private NoteView notesView;

    private TextField newNoteName;

    public void setNewNoteName(TextField newNoteName) {
        this.newNoteName = newNoteName;
    }

    public void initialize() throws IOException, ClassNotFoundException {

        super.start(root,menuButton);
        notesView = new NoteView(this,root);
        root.setOnMouseClicked(this::removeAllThingsByClicked);
        notesDatabase.loadEvents();
        notesView.createTags(root,firstNote, notesDatabase);

    }


    private void removeAllThingsByClicked(MouseEvent mouseEvent) {
        super.hideMenu(mouseEvent);
        if(newNoteName != null)root.getChildren().remove(newNoteName);
    }

    public void OnMouseEnteredChangeColor(Label currentLabel)
    {
        notesView.changeLinkToBlue(currentLabel);
    }

    public void OnMouseLeaveChangeColor(Label currentLabel)
    {
        notesView.changeLinkToWhite(currentLabel);
    }



    public void onClick_btn_Save() throws IOException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Vyberte poznámky");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.txt" ,"*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(stage);

        if(selectedFile != null) {
            nameOfNotes.setText(selectedFile.getName());
            note.setText(notesDatabase.loadTxt(selectedFile.getPath()));
            notesDatabase.addToNotes(nameOfNotes.getText(), notesDatabase.loadTxt(selectedFile.getPath()));
            notesView.createTags(root, firstNote, notesDatabase);

            saveNote();
        }else{
            Main.LOG.log(Level.SEVERE, "File not selected.");
        }
    }


    /**
     * save old notes and create new one
     * also set notes to created one
     */
    public void createNewNote() throws IOException {
        nameOfNotes.setLayoutX(noteTitle.getLayoutX() + noteTitle.getWidth() + BasicSizesAndPosition.getGapBetweenObjects());
        notesDatabase.addToNotes(nameOfNewNote.getText(), "");
        notesView.createTags(root,firstNote, notesDatabase);

        nameOfNotes.setText(nameOfNewNote.getText());
        note.setText("");
        saveNote();

        nameOfNewNote.setText("");
    }

    public void saveNote() throws IOException {

        if(!nameOfNotes.getText().equals("")) {
            notesDatabase.addToNotes(nameOfNotes.getText(), note.getText());
        }
    }

    public void changeNote(String noteName) throws IOException {
        nameOfNotes.setLayoutX(noteTitle.getLayoutX() + noteTitle.getWidth() + BasicSizesAndPosition.getGapBetweenObjects());
        nameOfNotes.setText(noteName);
        note.setText(notesDatabase.getNoteByName(noteName));
        saveNote();
    }

    public void deleteNote(String noteToDelete) throws IOException {
        note.setText("");
        nameOfNotes.setText("");
        notesDatabase.deleteNotes(noteToDelete);
        notesView.createTags(root,firstNote, notesDatabase);
        saveNote();
    }

    public void renameNote(Label currentLabel) throws IOException {
        notesDatabase.renameNotes(currentLabel.getText(),notesDatabase.getNoteByName(currentLabel.getText()),newNoteName.getText());
        nameOfNotes.setLayoutX(noteTitle.getLayoutX() + noteTitle.getWidth() + BasicSizesAndPosition.getGapBetweenObjects());
        nameOfNotes.setText(newNoteName.getText());
        root.getChildren().remove(newNoteName);
        notesView.createTags(root,firstNote, notesDatabase);
        saveNote();

    }

    @FXML
    void translate() {
        if(languageButton.getText().equals("EN")){
            CalendarController.language = "EN";
        }
        else{
            CalendarController.language = "SK";
        }
        ResourceBundle bundle = this.changeLanguage();
        languageButton.setText(bundle.getString("language"));
        circle.setText(bundle.getString("notesTitle"));
        saveBtn.setText(bundle.getString("saveNote"));
        importBtn.setText(bundle.getString("importNotes"));
        noteNameLbl.setText(bundle.getString("noteName"));
        createNoteBtn.setText(bundle.getString("createNote"));
        firstNote.setText(bundle.getString("dajakyTag"));

    }



}
