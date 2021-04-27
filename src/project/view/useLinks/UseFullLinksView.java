package project.view.useLinks;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import project.controller.UseFullLinksController;
import project.model.LinkGroup;
import project.model.databases.LinkDatabase;
import project.model.databases.sizesAndPosition.CalendarSizesAndPositonOfObjects;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UseFullLinksView {

    private AnchorPane root;
    private Button addLinkButton;
    private Button addGroupButton;
    private TextField newLinkName;
    private TextField newLink;
    private TextField newGroup;
    private Separator separator1;
    private Button addToDatabaseButton;

    private CalendarSizesAndPositonOfObjects calendarSizes = new CalendarSizesAndPositonOfObjects();
    private UseFullLinksController useFullLinksController;
    private LinkDatabase linkDatabase;

    private Label firstTag;
    private Label firstTagForLinkGroups;
    private Map<String, LinkGroup> groupNames = new HashMap<>();
    private Map<String, String> linkWithNames = new HashMap<>();
    private ArrayList<Label> linkGroup = new ArrayList<>();
    private ArrayList<Label> labelTags = new ArrayList<>();

    private String currentLinkGroup;
    private boolean isNewLinkCreatorShow = false;
    private boolean isNewGroupCreatorShow = false;

    //menu to tags
    ContextMenu contextMenu = new ContextMenu();
    MenuItem item1 = new MenuItem("Premenovat link");
    MenuItem item2 = new MenuItem("Zmenit link");
    MenuItem item3 = new MenuItem("Vymazat link");

    Menu parentMenu = new Menu("Zmena farby");


    public UseFullLinksView(UseFullLinksController useFullLinksController, LinkDatabase linkDatabase, Label firstTag, AnchorPane root, Label firstTagForLinkGroups, Button addLinkButton, Button addGroupButton) {
        this.root = root;
        this.firstTag = firstTag;
        this.useFullLinksController = useFullLinksController;
        this.linkDatabase = linkDatabase;
        this.firstTagForLinkGroups = firstTagForLinkGroups;
        this.addLinkButton = addLinkButton;
        this.addGroupButton = addGroupButton;

        addLinkButton.setOnAction((EventHandler) event -> {
                showAddLink();
        });
        addGroupButton.setOnAction((EventHandler) event -> {
            showAddGroup();
        });
        contextMenu.getItems().addAll(item1, item2, item3, parentMenu);

    }

    public void setCurrentLinkGroup(String currentLinkGroup) {
        this.currentLinkGroup = currentLinkGroup;
    }

    /**
     * delete all link, and create new from database
     */
    public void showGroupLinks() {
        
        for(Label label:linkGroup)
        {
            root.getChildren().remove(label);
            root.getChildren().remove(newLinkName);
            root.getChildren().remove(separator1);
            root.getChildren().remove(newLink);
            root.getChildren().remove(addToDatabaseButton);
            isNewLinkCreatorShow = false;
        }

        double yPosition = firstTagForLinkGroups.getLayoutY() + 50;
        double xPosition = firstTagForLinkGroups.getLayoutX();
        groupNames = linkDatabase.getLinkGroups();

        for ( Map.Entry<String, LinkGroup> entry : groupNames.entrySet() ) {

            Label tag = new Label(entry.getKey());

            tag.setTranslateX(xPosition);
            tag.setTranslateY(yPosition);

            tag.setFont(new Font("Arial", 14));
            tag.setTextFill(Color.color(1,1,1));
            tag.setPadding(new Insets(3 ,5 ,3,5));

            tag.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY)
                {
                    setContextMenu(tag);
                    contextMenu.show(tag, event.getScreenX(), event.getScreenY());
                }

                if (event.getButton() == MouseButton.PRIMARY)
                {
                    useFullLinksController.changeLinkGroup(tag);
                }

            });

            tag.setOnMouseEntered(event ->
            {
                useFullLinksController.OnMouseEnteredChangeColor(tag);
            });

            tag.setOnMouseExited(event ->
            {
                useFullLinksController.OnMouseLeaveChangeColor(tag);
            });

            linkGroup.add(tag);
            root.getChildren().add(tag);

            yPosition += 30;
        }
        showLinkInSelectedGroup();
    }

    /**
     * delete all link, and create new from database
     */
    public void showLinkInSelectedGroup() {

        for(Label label:labelTags)
        {
            root.getChildren().remove(label);
            root.getChildren().remove(newLinkName);
            root.getChildren().remove(separator1);
            root.getChildren().remove(newLink);
            isNewLinkCreatorShow = false;
        }

        double yPosition = addLinkButton.getLayoutY();
        double xPosition = addLinkButton.getLayoutX() + 150;
        try{linkWithNames = linkDatabase.getLinkGroups().get(currentLinkGroup).getLinksWithNames();}
        catch (Exception ignored){}

        for ( Map.Entry<String, String> entry : linkWithNames.entrySet() ) {

            Label tag = new Label(entry.getKey() + ": "+entry.getValue());

            tag.setTranslateX(xPosition);
            tag.setTranslateY(yPosition);

            tag.setFont(new Font("Arial", 14));
            tag.setTextFill(Color.color(1,1,1));
            tag.setPadding(new Insets(3 ,5 ,3,5));

            tag.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY)
                {
                    setContextMenu(tag);
                    contextMenu.show(tag, event.getScreenX(), event.getScreenY());
                }

                if (event.getButton() == MouseButton.PRIMARY)
                {
                    try {
                        useFullLinksController.openLink(tag);
                    } catch (URISyntaxException | IOException e) {
                        e.printStackTrace();
                    }
                }

            });

            tag.setOnMouseEntered(event ->
                    useFullLinksController.OnMouseEnteredChangeColor(tag));

            tag.setOnMouseExited(event ->
                    useFullLinksController.OnMouseLeaveChangeColor(tag));

            labelTags.add(tag);
            root.getChildren().add(tag);

            yPosition += 30;
        }
    }



    public void setContextMenu(Label currentLabel)
    {

        item1.setOnAction(event -> {
            //create textfield to rename event
            String[] arrOfStr = currentLabel.getText().split(": ");
            newLinkName = new TextField(arrOfStr[0]);
            useFullLinksController.setNewNoteName(newLinkName);

            newLinkName.setLayoutX(currentLabel.getTranslateX() - 5);
            newLinkName.setPrefSize(lengthOfWord(arrOfStr[0]), currentLabel.getHeight());

            newLinkName.setLayoutY(currentLabel.getTranslateY());
            root.getChildren().add(newLinkName);
            useFullLinksController.setNewNoteName(newLinkName);
            useFullLinksController.setNewNoteName(newLinkName);

            newLinkName.setOnKeyPressed(event1 -> {
                if (event1.getCode().equals(KeyCode.ENTER)) {
                    useFullLinksController.renameLink(arrOfStr[0]);
                }
            });

            newLinkName.textProperty().addListener((observable, oldValue, newValue) -> {
                newLinkName.setPrefSize(lengthOfWord(newValue), currentLabel.getHeight());
            });

        });

        item2.setOnAction(event -> {
            //create textfield to rename event
            newLink = new TextField();

            String[] arrOfStr = currentLabel.getText().split(": ");
            newLink.setText(arrOfStr[1]);

            newLink.setPrefSize(lengthOfWord(arrOfStr[1]), currentLabel.getHeight());
            newLink.setLayoutX(currentLabel.getTranslateX() - 5 + lengthOfWord(arrOfStr[0]));
            newLink.setLayoutY(currentLabel.getTranslateY());
            root.getChildren().add(newLink);
            useFullLinksController.setNewNoteName(newLink);


            newLink.setOnKeyPressed(event1 -> {
                if (event1.getCode().equals(KeyCode.ENTER)) {
                    useFullLinksController.changeLink(arrOfStr[0]);

                }
            });

            newLink.textProperty().addListener((observable, oldValue, newValue) ->
                    newLink.setPrefSize(lengthOfWord(newLink.getText()), currentLabel.getHeight()));
        });
    }

    public double lengthOfWord(String word)
    {
        double length = 12;
        double smallLetterLength = 8.85;
        double bigLetterLength = 10;
        double number = 10.3;
        double space = 4.2;
        for (char ch: word.toCharArray()) {
            if(ch>=48 && ch<=57 )
            {
                length += number;
            }

            else if (ch>=65 && ch<=90 )
            {
                length += bigLetterLength;
            }

            else if (ch>=97 && ch<=122 )
            {
                length += smallLetterLength;
            }
            else if(ch==32)
            {
                length += space;
            }
            else{length+=smallLetterLength;}
        }
        return length;
    }


    public void changeLinkToBlue(Label currentLink)
    {
        currentLink.setTextFill(Color.rgb(55, 123, 225, 1));
    }

    public void changeLinkToWhite(Label currentLink)
    {
        currentLink.setTextFill(Color.color(1,1,1));
    }

    public void showAddLink()
    {
        if(isNewLinkCreatorShow)
        {
            for(Label label:labelTags)
            {
                label.setLayoutY(label.getLayoutY() - 70);
            }
            root.getChildren().remove(newLinkName);
            root.getChildren().remove(separator1);
            root.getChildren().remove(newLink);
            root.getChildren().remove(addToDatabaseButton);
            isNewLinkCreatorShow = false;
        }
        else {

            for (Label label : labelTags)
            {
                label.setLayoutY(label.getLayoutY() + 70);
            }
            newLinkName = new TextField();
            newLinkName.setPromptText("nazov linku");
            newLinkName.setLayoutX(addLinkButton.getLayoutX() + 110);
            newLinkName.setLayoutY(addLinkButton.getLayoutY());
            useFullLinksController.setNewLinkName(newLinkName);

            root.getChildren().add(newLinkName);

            newLink = new TextField();
            newLink.setPromptText("link");
            newLink.setLayoutX(addLinkButton.getLayoutX() + 180 + 110);
            newLink.setLayoutY(addLinkButton.getLayoutY());
            root.getChildren().add(newLink);
            useFullLinksController.setNewLink(newLink);

            addToDatabaseButton = new Button();
            addToDatabaseButton.setText("Pridat do databazy");
            addToDatabaseButton.setLayoutX(addLinkButton.getLayoutX() + 365 + 110);
            addToDatabaseButton.setLayoutY(addLinkButton.getLayoutY());
            root.getChildren().add(addToDatabaseButton);
            addToDatabaseButton.setOnAction(event -> { useFullLinksController.addToLinks(); });

            separator1 = new Separator();
            separator1.setLayoutX(addLinkButton.getLayoutX()- 11 + 110);
            separator1.setLayoutY(addLinkButton.getLayoutY() + 50);
            separator1.setPrefWidth(1000);
            root.getChildren().add(separator1);

            isNewLinkCreatorShow = true;
        }

    }

    public void showAddGroup()
    {
        if(isNewGroupCreatorShow)
        {
            for(Label label:labelTags)
            {
                label.setLayoutY(label.getLayoutY() - 70);
            }
            root.getChildren().remove(newGroup);
            root.getChildren().remove(separator1);
            root.getChildren().remove(addToDatabaseButton);
            isNewGroupCreatorShow = false;
        }
        else {

            for (Label label : labelTags)
            {
                label.setLayoutY(label.getLayoutY() + 70);
            }

            newGroup = new TextField();
            newGroup.setPromptText("nazov linku");
            newGroup.setLayoutX(addLinkButton.getLayoutX() + 150);
            newGroup.setLayoutY(addLinkButton.getLayoutY());
            root.getChildren().add(newGroup);

            addToDatabaseButton = new Button();
            addToDatabaseButton.setText("Pridat do databazy");
            addToDatabaseButton.setLayoutX(addLinkButton.getLayoutX() + 315);
            addToDatabaseButton.setLayoutY(addLinkButton.getLayoutY());
            addToDatabaseButton.setOnAction(event -> { useFullLinksController.addLinkGroup(); });
            root.getChildren().add(addToDatabaseButton);

            isNewGroupCreatorShow = true;
        }

    }

}
