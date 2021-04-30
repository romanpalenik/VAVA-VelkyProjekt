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
import project.controller.calendar.CalendarController;
import project.model.databases.LinkGroup;
import project.model.databases.LinkDatabase;
import project.model.databases.sizesAndPosition.BasicSizesAndPosition;
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
    private TextField newNoteName;
    private TextField newGroup;
    private Separator separator1;
    private Button addToDatabaseButton;
    private Label groupTitle;

    private CalendarSizesAndPositonOfObjects calendarSizes = new CalendarSizesAndPositonOfObjects();
    private UseFullLinksController useFullLinksController;
    private LinkDatabase linkDatabase;

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

    ContextMenu contextMenu2 = new ContextMenu();
    MenuItem item12 = new MenuItem();


    public UseFullLinksView(UseFullLinksController useFullLinksController, LinkDatabase linkDatabase, AnchorPane root, Label firstTagForLinkGroups, Button addLinkButton, Button addGroupButton, Label groupTitle) {
        this.root = root;
        this.useFullLinksController = useFullLinksController;
        this.linkDatabase = linkDatabase;
        this.firstTagForLinkGroups = firstTagForLinkGroups;
        this.addLinkButton = addLinkButton;
        this.addGroupButton = addGroupButton;
        this.groupTitle = groupTitle;

        addLinkButton.setOnAction((EventHandler) event -> {
                showAddLink();
        });
        addGroupButton.setOnAction((EventHandler) event -> {
            showAddGroup();
        });
        contextMenu.getItems().addAll(item1, item2, item3);
        contextMenu2.getItems().addAll(item12);

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
                    try {
                        useFullLinksController.changeLinkGroup(tag);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });

            tag.setOnMouseEntered(event -> useFullLinksController.OnMouseEnteredChangeColor(tag));
            tag.setOnMouseExited(event -> useFullLinksController.OnMouseLeaveChangeColor(tag));

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
        }

        double yPosition = groupTitle.getLayoutY() + 30;
        double xPosition = BasicSizesAndPosition.getWidthOfMenu() + BasicSizesAndPosition.getGapBetweenObjects();
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
                    try {
                        useFullLinksController.renameLink(arrOfStr[0]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            newLinkName.textProperty().addListener((observable, oldValue, newValue) -> newLinkName.setPrefSize(lengthOfWord(newValue), currentLabel.getHeight()));

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
                    try {
                        useFullLinksController.changeLink(arrOfStr[0]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

            newLink.textProperty().addListener((observable, oldValue, newValue) ->
                    newLink.setPrefSize(lengthOfWord(newLink.getText()), currentLabel.getHeight()));
        });

        item3.setOnAction(event ->
        {
            String[] arrOfStr = currentLabel.getText().split(": ");
            try {
                useFullLinksController.deleteNote(arrOfStr[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        if(isNewGroupCreatorShow) {
            hideAddGroupCreator();
        }
        if(isNewLinkCreatorShow) {
            hideAddLinkCreator();
        }
        else {

            groupTitle.setLayoutY(groupTitle.getLayoutY() + 70);
            for (Label label : labelTags)
            {
                label.setLayoutY(label.getLayoutY() + 70);
            }

            newNoteName = new TextField();
            newNoteName.setLayoutX(BasicSizesAndPosition.getWidthOfMenu() + BasicSizesAndPosition.getGapBetweenObjects());
            newNoteName.setPrefWidth(BasicSizesAndPosition.getTextFieldWidth());
            newNoteName.setLayoutY(addLinkButton.getLayoutY());
            useFullLinksController.setNewLinkName(newNoteName);

            if(CalendarController.language.equals("SK")){
                newNoteName.setPromptText("názov linku");
            }
            else{
                newNoteName.setPromptText("link name");
            }
            root.getChildren().add(newNoteName);

            newLink = new TextField();
            newLink.setPromptText("link");
            newLink.setPrefWidth(BasicSizesAndPosition.getTextFieldWidth());
            newLink.setLayoutX(newNoteName.getLayoutX() + newNoteName.getPrefWidth() + BasicSizesAndPosition.getGapBetweenObjects());
            newLink.setLayoutY(addLinkButton.getLayoutY());

            root.getChildren().add(newLink);
            useFullLinksController.setNewLink(newLink);

            addToDatabaseButton = new Button();
            if(CalendarController.language.equals("SK")){
                addToDatabaseButton.setText("Pridať do databázy");
            }
            else{
                addToDatabaseButton.setText("Add to database");
            }
            addToDatabaseButton.setLayoutX(newLink.getLayoutX() + newLink.getPrefWidth() + BasicSizesAndPosition.getGapBetweenObjects());
            addToDatabaseButton.setLayoutY(addLinkButton.getLayoutY());
            root.getChildren().add(addToDatabaseButton);
            addToDatabaseButton.setOnAction(event -> {
                try {
                    useFullLinksController.addToLinks();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            separator1 = new Separator();
            separator1.setLayoutX(BasicSizesAndPosition.getWidthOfMenu());
            separator1.setLayoutY(addLinkButton.getLayoutY() + 50);
            separator1.setPrefWidth(1000);
            root.getChildren().add(separator1);

            isNewLinkCreatorShow = true;
        }

    }

    public void hideAddLinkCreator()
    {
        groupTitle.setLayoutY(groupTitle.getLayoutY() - 70);
        for(Label label:labelTags)
        {
            label.setLayoutY(label.getLayoutY() - 70);
        }
        root.getChildren().remove(newLinkName);
        root.getChildren().remove(newNoteName);
        root.getChildren().remove(separator1);
        root.getChildren().remove(newLink);
        root.getChildren().remove(addToDatabaseButton);
        isNewLinkCreatorShow = false;
    }




    public void showAddGroup()
    {
        if(isNewLinkCreatorShow) {
            hideAddLinkCreator();
        }
        if(isNewGroupCreatorShow) {
            hideAddGroupCreator();
        }

        else {

            groupTitle.setLayoutY(groupTitle.getLayoutY() + 70);
            for (Label label : labelTags)
            {
                label.setLayoutY(label.getLayoutY() + 70);
            }

            newGroup = new TextField();
            if(CalendarController.language.equals("SK")){
                newGroup.setPromptText("názov skupiny");
            }
            else{
                newGroup.setPromptText("group name");
            }
            newGroup.setLayoutX(CalendarSizesAndPositonOfObjects.getWidthOfMenu() + BasicSizesAndPosition.getGapBetweenObjects());
            newGroup.setPrefWidth(calendarSizes.getTextFieldWidth());
            newGroup.setLayoutY(addLinkButton.getLayoutY());
            root.getChildren().add(newGroup);
            useFullLinksController.setNewLinkGroup(newGroup);

            //set button next to text field
            addToDatabaseButton = new Button();
            if(CalendarController.language.equals("SK")){
                addToDatabaseButton.setText("Pridať do databázy");
            }
            else{
                addToDatabaseButton.setText("Add to database");
            }
            addToDatabaseButton.setLayoutX(newGroup.getLayoutX() + newGroup.getPrefWidth() + BasicSizesAndPosition.getGapBetweenObjects() );
            addToDatabaseButton.setLayoutY(addLinkButton.getLayoutY());

            addToDatabaseButton.setOnAction(event -> {
                try {
                    useFullLinksController.addLinkGroup();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            root.getChildren().add(addToDatabaseButton);

            separator1 = new Separator();
            separator1.setLayoutX(CalendarSizesAndPositonOfObjects.getWidthOfMenu());
            separator1.setLayoutY(addLinkButton.getLayoutY() + 50);
            separator1.setPrefWidth(1000);
            root.getChildren().add(separator1);

            isNewGroupCreatorShow = true;
        }

    }

    public void hideAddGroupCreator()
    {
        groupTitle.setLayoutY(groupTitle.getLayoutY() - 70);
        for(Label label:labelTags)
        {
            label.setLayoutY(label.getLayoutY() - 70);
        }
        root.getChildren().remove(newGroup);
        root.getChildren().remove(separator1);
        root.getChildren().remove(addToDatabaseButton);
        isNewGroupCreatorShow = false;
    }

}
