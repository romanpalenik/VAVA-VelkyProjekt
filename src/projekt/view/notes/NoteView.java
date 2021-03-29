package projekt.view.notes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import projekt.controller.NotesController;
import projekt.model.notes.NotesDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NoteView {

    private Map<String, String> noteWithName = new HashMap<>();
    private ArrayList<Label> labelTags = new ArrayList<>();
    private NotesController notesController;

    private AnchorPane root;

//    selected node to know which one delete, is it sending to controler to delete
    private Label currentLabel;
//
    private TextField newNoteName;
    //menu
    ContextMenu contextMenu = new ContextMenu();
    MenuItem item2 = new MenuItem("Vymazať");
    MenuItem item1 = new MenuItem("Premenovať");

    public NoteView(NotesController notesController, AnchorPane root) {
        this.notesController = notesController;
        this.root = root;
        contextMenu.getItems().addAll(item1, item2);
    }

    public void createTags(AnchorPane root, Label firstNote , NotesDatabase notesDatabase) {

        for(Label label:labelTags)
        {
            root.getChildren().remove(label);
        }

        double yPosition = firstNote.getLayoutY() +10;
        double xPosition = firstNote.getLayoutX() -2;

        noteWithName = notesDatabase.getNotesWithName();

        for ( Map.Entry<String, String> entry : noteWithName.entrySet() ) {

            Label label = new Label(entry.getKey());

            label.setTranslateX(xPosition);
            label.setTranslateY(yPosition);

            label.setTextFill(Color.color(1, 1, 1));

            label.setPadding(new Insets(3 ,5 ,3,5));

            label.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY)
                {
                    notesController.changeNote(label.getText());
                } else if (event.getButton() == MouseButton.SECONDARY)
                {
                    setContextMenu(label);
                    contextMenu.show(label, event.getScreenX(), event.getScreenY());
                }

            });


            labelTags.add(label);

            root.getChildren().add(label);

            yPosition += 30;
        }
    }

    public void setContextMenu(Label currentLabel)
    {

        item1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //create textfield to rename event
                newNoteName = new TextField();
                newNoteName.setLayoutX(currentLabel.getLayoutX());
                newNoteName.setLayoutY(currentLabel.getLayoutY());
                root.getChildren().add(newNoteName);
                notesController.renameNote(newNoteName, currentLabel);
            }
        });
        item2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                notesController.deleteNote(currentLabel.getText());
            }
        });

        // Add MenuItem to ContextMenu

    }

}
