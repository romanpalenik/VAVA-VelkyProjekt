package projekt.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import projekt.model.notes.NotesDatabase;
import projekt.view.notes.NoteView;

import java.io.File;
import java.io.IOException;

public class NotesController {

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


    private boolean isMenuShown = false;
    private NotesDatabase notesDatabase = new NotesDatabase();
    private NoteView notesView;

    public void initialize() throws IOException {

        notesView = new NoteView(this,root);
        darkFilterWhileMenu();
        darkSideWhenMenu.setVisible(false);

        root.setOnMouseClicked(this::removeAllThingsByClicked);
        notesView.createTags(root,firstNote, notesDatabase);


    }

    private void removeAllThingsByClicked(MouseEvent mouseEvent) {
        root.getChildren().remove(menuFMXL);
        darkSideWhenMenu.setVisible(false);
    }

    /**
     * show menu, if is menu already shown hide it
     * @throws IOException
     */
    public void showMenu() throws IOException {

        if (isMenuShown)
        {
            hideMenu(null);
            return;
        }

        root.getChildren().add(darkSideWhenMenu);
        root.getChildren().add(loadFMXLMenu());

        isMenuShown = true;
        darkSideWhenMenu.setVisible(true);

    }

    private void hideMenu(MouseEvent mouseEvent) {

        root.getChildren().remove(darkSideWhenMenu);
        isMenuShown = false;
        try {
            root.getChildren().remove(menuFMXL);
            darkSideWhenMenu.setVisible(false);
        }
        catch (NullPointerException e)
        {

        }
    }


    /**
     * load anchor pane with menu buttons
     * @return menu
     */
    public AnchorPane loadFMXLMenu() throws IOException {

        menuFMXL = FXMLLoader.load(Main.class.getResource("/projekt/view/mainMenu.fxml"));
        menuFMXL.setLayoutY(75);
        return menuFMXL;
    }

    public Label darkFilterWhileMenu() throws IOException {
        darkSideWhenMenu = FXMLLoader.load(Main.class.getResource("/projekt/view/darkFilterWhileMenu.fxml"));
        darkSideWhenMenu.setLayoutX(174);
        return darkSideWhenMenu;
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
        notesDatabase.addToNotes(nameOfNotes.getText(),note.getText());
    }

    public void changeNote(String noteName)
    {
        nameOfNotes.setText(noteName);
        note.setText(notesDatabase.getNoteByName(noteName));
    }

    public void deleteNote(String note)
    {
        notesDatabase.deleteNotes(note);
        notesView.createTags(root,firstNote, notesDatabase);
    }

    public void renameNote(TextField newNoteName, Label currentLabel)
    {
        notesDatabase.renameNotes(currentLabel.getText(),notesDatabase.getNoteByName(currentLabel.getText()),newNoteName.getText());
    }

}
