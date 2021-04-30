package project.view.notes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import project.controller.NotesController;
import project.model.databases.NotesDatabase;
import project.model.databases.sizesAndPosition.NotesSizesAndPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NoteView {

    private Map<String, String> noteWithName = new HashMap<>();
    private ArrayList<Label> labelTags = new ArrayList<>();
    private NotesController notesController;

    private NotesSizesAndPosition notesSizesAndPosition = new NotesSizesAndPosition();

    private AnchorPane root;
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

        double yPosition = firstNote.getLayoutY() + notesSizesAndPosition.getDistanceFromFistNote();
        double xPosition = firstNote.getLayoutX() -2;

        noteWithName = notesDatabase.getNotesWithName();

        for ( Map.Entry<String, String> entry : noteWithName.entrySet() ) {

            Label noteBlog = new Label(entry.getKey());

            noteBlog.setTranslateX(xPosition);
            noteBlog.setTranslateY(yPosition);

            noteBlog.setTextFill(Color.color(1, 1, 1));

            noteBlog.setPadding(new Insets(3 ,5 ,3,5));

            noteBlog.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY)
                {
                    try {
                        notesController.changeNote(noteBlog.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (event.getButton() == MouseButton.SECONDARY)
                {
                    setContextMenu(noteBlog);
                    contextMenu.show(noteBlog, event.getScreenX(), event.getScreenY());
                }
            });

            noteBlog.setOnMouseEntered(event -> notesController.OnMouseEnteredChangeColor(noteBlog));
            noteBlog.setOnMouseExited(event -> notesController.OnMouseLeaveChangeColor(noteBlog));

            labelTags.add(noteBlog);

            root.getChildren().add(noteBlog);

            yPosition += notesSizesAndPosition.getDistanceBetweenNameOfBlogs();
        }
    }

    public void setContextMenu(Label currentLabel)
    {

        item1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //create textfield to rename event
                newNoteName = new TextField();
                newNoteName.setPrefSize(152,10);
                newNoteName.setText(currentLabel.getText());
                newNoteName.setLayoutX(currentLabel.getTranslateX()-5);
                newNoteName.setLayoutY(currentLabel.getTranslateY());
                root.getChildren().add(newNoteName);
                notesController.setNewNoteName(newNoteName);

                newNoteName.setOnKeyPressed(new EventHandler<KeyEvent>() {

                    @Override
                    public void handle(KeyEvent event) {
                        if(event.getCode().equals(KeyCode.ENTER)) {
                            try {
                                notesController.renameNote(currentLabel);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }

        });
        item2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    notesController.deleteNote(currentLabel.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Add MenuItem to ContextMenu
    }

    public void changeLinkToBlue(Label currentLink)
    {
        currentLink.setTextFill(Color.rgb(55, 123, 225, 1));
    }

    public void changeLinkToWhite(Label currentLink)
    {
        currentLink.setTextFill(Color.color(1,1,1));
    }

}
