package project.view.calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import project.controller.calendar.AnchorPaneNode;
import project.controller.calendar.CalendarController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import project.controller.calendar.CalendarTagController;
import project.controller.calendar.EventDetail;
import project.model.calendar.CalendarDatabase;
import project.model.calendar.OneCellRecord;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * class that create tags and make calendar bigger or smaller
 */
public class CalendarViewCreatingThings {

    private Map<String, String> tagsWithColor = new HashMap<>();
    ArrayList<Label> labelTags = new ArrayList<>();

    //number to calendar
    private int widthOfCalendar = 750;
    private int heightOfCalendar = 487;
    private int leftUpCornerX = 209;
    private int leftUpCornerY = 170;
    private AnchorPane root;
    private Label fistTag;

    private boolean canAddNote = false;

    private TextField LastShownTextFieldNote = new TextField();
    private Button lastShownButton = new Button();
    private ComboBox lastShownComboBox = new ComboBox();

    //objects connected to calendar
    private CalendarController calendarController;
    private CalendarTagController calendarTagController;
    private CalendarDatabase calendarDatabase;

    //menu to tags
    private TextField newNoteName;
    ContextMenu contextMenu = new ContextMenu();
    MenuItem item1 = new MenuItem("Premenovat");
    MenuItem item2 = new MenuItem("Vymazať");
    MenuItem item3 = new MenuItem("Sprava tagov");


    Menu parentMenu = new Menu("Zmena farby");

    MenuItem blueColor = new MenuItem("Modrá");
    MenuItem purpleColor = new MenuItem("Fialová");
    MenuItem greenColor = new MenuItem("Zelená");
    MenuItem yellowColor = new MenuItem("Žltá");
    MenuItem redColor = new MenuItem("Červená");


    /**
     * construktor to set menu
     */
    public CalendarViewCreatingThings() {
        parentMenu.getItems().addAll(redColor,greenColor,blueColor,purpleColor,yellowColor);
        contextMenu.getItems().addAll(item1, item2, item3, parentMenu);
    }

    public void setFistTag(Label fistTag) {
        this.fistTag = fistTag;
    }

    public void setCreateTagController(CalendarTagController calendarTagController) {
        this.calendarTagController = calendarTagController;
    }

    public void setCalendarController(CalendarController calendarController, AnchorPane root) {
        this.calendarController = calendarController;
        this.root = root;
    }

    public void setCalendarDatabase(CalendarDatabase calendarDatabase) {
        this.calendarDatabase = calendarDatabase;
    }

    /**
     * delete all tags, and create new from database
     * @param root window pane
     * @param calendarDatabase database with every information connected with calendar
     */
    public void createTags(AnchorPane root,CalendarDatabase calendarDatabase) {

        for(Label label:labelTags)
        {
            root.getChildren().remove(label);
        }

        double yPosition = fistTag.getLayoutY() + 70;
        double xPosition = fistTag.getLayoutX();
        tagsWithColor = calendarDatabase.getTagsWithColor();

        for ( Map.Entry<String, String> entry : tagsWithColor.entrySet() ) {

            Label tag = new Label(entry.getKey());

            tag.setTranslateX(xPosition);
            tag.setTranslateY(yPosition);


            String backgroundColor = "-fx-background-color: " + entry.getValue();
            tag.setStyle(backgroundColor);
            tag.setPadding(new Insets(3 ,5 ,3,5));

            tag.setOnMouseClicked(event -> {
             if (event.getButton() == MouseButton.SECONDARY)
                {
                    setContextMenu(tag);
                    contextMenu.show(tag, event.getScreenX(), event.getScreenY());
                }

            });

            labelTags.add(tag);

            root.getChildren().add(tag);

            yPosition += 30;
        }
    }

    /**
     * there is months that start on saturday or sunday and has 31 day so they
     * need 8 rows to show not only 7
     * @return
     */
    public ArrayList<AnchorPaneNode> makeCalendarBigger(AnchorPane root, GridPane calendar) {

        return createCalendar(root,calendar,"bigger");
    }

    /**
     * Create new calendar where size is normal 7x5
     * @param root where is calendar
     * @param calendar old calendar to remove it from pane
     * @return new array of every cell in calendar
     */
    public ArrayList<AnchorPaneNode> makeCalendarNormalSize(AnchorPane root, GridPane calendar)
    {
        return createCalendar(root,calendar,"normal");
    }

    public ArrayList<AnchorPaneNode> makeCalendarLitter(AnchorPane root, GridPane calendar)
    {
        return createCalendar(root,calendar,"little");
    }

    public ArrayList<AnchorPaneNode> createCalendar(AnchorPane root, GridPane calendar, String type)
    {
        int collums = 0;
        int row = 0;
        if(type.equals("little"))
        {
            collums = 7;
            row = 4;
        }
        else if(type.equals("normal")){
            collums = 7;
            row = 5;
        }
        else if(type.equals("bigger")){
            collums = 7;
            row = 6;
        }

        root.getChildren().remove(calendar);

        calendar = new GridPane();
        calendar.setPrefSize(widthOfCalendar, heightOfCalendar);
        calendar.setLayoutX(leftUpCornerX);
        calendar.setLayoutY(leftUpCornerY);
        calendar.setGridLinesVisible(true);

        int offsetToCenterX = widthOfCalendar/collums;
        int offsetToCenterY = heightOfCalendar/row;
        int centerOfFistCellY = leftUpCornerY + (heightOfCalendar/row)/2;
        int centerOfFistCellX = leftUpCornerX + (widthOfCalendar/collums)/2;

        ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(row*collums);
        calendar.getChildren().removeAll();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < collums; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();

                ap.setCenterX(centerOfFistCellX);
                ap.setCenterY(centerOfFistCellY);

                ap.setTypeOfMonth(type);

                ap.setPrefSize(200, 200);
                calendar.add(ap, j, i);

                allCalendarDays.add(ap);
                centerOfFistCellX += offsetToCenterX;
            }
            centerOfFistCellX = leftUpCornerX + (widthOfCalendar/collums)/2;
            centerOfFistCellY += offsetToCenterY;
        }

        root.getChildren().add(calendar);
        return allCalendarDays;
    }

    /**
     * remove lastShown textfiled of notes from root and create a new one
     * @param x of centre of node
     * @param y of centre of node
     * @param root
     * @param date
     */
    public void createSettingNote(int x, int y, AnchorPane root, LocalDate date)
    {
        root.getChildren().remove(LastShownTextFieldNote);
        root.getChildren().remove(lastShownButton);
        root.getChildren().remove(lastShownComboBox);

        int textFieldWidth = 150;
        int textFieldHeight = 26;

        TextField textFieldNote = new TextField();
        textFieldNote.setPrefSize(textFieldWidth, textFieldHeight);
        textFieldNote.setLayoutX(x-(textFieldWidth/2)-20);
        textFieldNote.setLayoutY(y-80);


        Button saveNoteButton = new Button();
        saveNoteButton.setText("+");
        saveNoteButton.setPrefSize(40, textFieldHeight);
        saveNoteButton.setLayoutX(x+15);
        saveNoteButton.setLayoutY(y-50); //than the text field +30

        ComboBox tagsChoice = new ComboBox();
        ObservableList<String> tags = FXCollections.observableArrayList(calendarDatabase.getTagsWithColor().keySet());
        tagsChoice.setPrefSize(110, textFieldHeight);
        tagsChoice.setItems(tags);
        tagsChoice.setLayoutX(x-95);
        tagsChoice.setLayoutY(y-50);


        saveNoteButton.setOnMouseClicked(e -> {
            if (canAddNote) {
                calendarDatabase.addToEvents(date, textFieldNote.getText(), (String) tagsChoice.getValue());
                removeLastShownNote(root);
                calendarController.updateCalendar(calendarController.getCurrentMonth());
            }
        });


        textFieldNote.textProperty().addListener((obs, oldText, newText) -> {
            try {
                changeButtonColor(textFieldNote, saveNoteButton);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // ...
        });
        
        lastShownComboBox = tagsChoice;
        LastShownTextFieldNote = textFieldNote;
        lastShownButton = saveNoteButton;

        root.getChildren().add(tagsChoice);
        root.getChildren().add(textFieldNote);
        root.getChildren().add(saveNoteButton);


    }

    /**
     * if is something in textfield, change color to green and user can save new event
     */
    private void changeButtonColor(TextField textFieldNote, Button addButton) {

        if(!textFieldNote.getText().equals(""))
            {
                addButton.setStyle("-fx-background-color:  #f4ed71");
                canAddNote = true;
            }
        else
            {
                addButton.setStyle("-fx-background-color: #d0d0d0");
            }
    }

    public void removeLastShownNote(AnchorPane root)
    {
        root.getChildren().remove(lastShownComboBox);
        root.getChildren().remove(LastShownTextFieldNote);
        root.getChildren().remove(lastShownButton);
    }

    /**
     * set to every cell of calendar right date
     * @param currentMonth month to show
     */
    public void setUpDays(YearMonth currentMonth, ArrayList<AnchorPaneNode> allCalendarDays, AnchorPane root )
    {

        LocalDate calendarDate = LocalDate.of(currentMonth.getYear(), currentMonth.getMonthValue(), 1);
        // Dial back the day until it is Monday
        while (!calendarDate.getDayOfWeek().toString().equals("MONDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Populate the calendar with day numbers
        boolean dayIsInMonth = false;
        int showedDays=1;
        for (AnchorPaneNode ap : allCalendarDays) {

            if (calendarDate.getDayOfMonth() == 1 && dayIsInMonth == false) {
                showedDays = 1;
                dayIsInMonth = true;
            }
            //if calendar create every day in month start mode not in month
            if (showedDays > calendarDate.getDayOfMonth() || dayIsInMonth == false) {
                ap.getStylesheets().remove("daysInMonth.css");
                ap.getStylesheets().add("/daysFromAnotherMonth.css");
                ap.getStyleClass().add("pane");
            }
            else {
                ap.getStylesheets().remove("/daysFromAnotherMonth.css");
                ap.getStylesheets().add("daysInMonth.css");
                ap.getStyleClass().add("pane");
                ap.setInMonth(true);
                //if cell has event show it
                OneCellRecord event = calendarDatabase.findEventByDate(calendarDate);
                if(event!=null)
                {
                    createLabelWithEvent(ap, event, root);
                }
            }

            if (ap.getChildren().size() != 0) {
//                ap.getChildren().remove(0);
            }

            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            ap.setDate(calendarDate);
            ap.setTopAnchor(txt, 5.0);
            ap.setLeftAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            calendarDate = calendarDate.plusDays(1);
            showedDays++;
        }
    }

    /**
     * create label for every event that is planned on that date inside calendar
     * @param cell
     * @param root
     */
    public void createLabelWithEvent(AnchorPaneNode cell, OneCellRecord oneCellRecord, AnchorPane root) {

        int YdistanceFromCenter = 0;
        if (cell.getTypeOfMonth().equals("little")) {
            YdistanceFromCenter = 45;
        } else if (cell.getTypeOfMonth().equals("normal")) {
            YdistanceFromCenter = 35;
        } else if (cell.getTypeOfMonth().equals("bigger")) {
            YdistanceFromCenter = 25;
        }

        int yOffset = 10;
        for (OneCellRecord.Event event : oneCellRecord.getEveryEvent()) {
            Label eventLabel = new Label(event.getEventName());
            eventLabel.setLayoutX(cell.getCenterX() - 50);
            eventLabel.setLayoutY(cell.getCenterY() - YdistanceFromCenter + yOffset);

            eventLabel.setOnMouseClicked(e -> {
                try {
                    showEditEvent(oneCellRecord, event, calendarController);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });

            String colorToEvent = calendarDatabase.findColorToTag(event.getTag());
            String styleSheet = "-fx-background-color: " + colorToEvent;

            eventLabel.setStyle(styleSheet);
            eventLabel.setPrefWidth(widthOfCalendar / 7 - 6);
            eventLabel.setPadding(new Insets(0, 0, 0, 3));

            root.getChildren().add(eventLabel);
            yOffset += 20;


        }
    }

    public void showEditEvent(OneCellRecord event, OneCellRecord.Event eventName, CalendarController calendarController) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/view/calendar/eventDetail.fxml"));
        Parent root2 = (Parent) loader.load();
        EventDetail controller = loader.getController();
        controller.initData(stage, event, eventName, calendarController, calendarDatabase);
        Scene scene = new Scene(root2);
        stage.setTitle("Pridat tag");
        stage.setScene(scene);
        stage.show();
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
                calendarTagController.setNewNoteName(newNoteName);
                calendarController.setNewNoteName(newNoteName);

                newNoteName.setOnKeyPressed(new EventHandler<KeyEvent>() {

                    @Override
                    public void handle(KeyEvent event) {
                        if(event.getCode().equals(KeyCode.ENTER)) {
                            calendarTagController.renameNote(currentLabel);
                        }
                    }
                });
            }
        });
        item2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                calendarTagController.deleteNote(currentLabel);
            }
        });

        item3.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    calendarController.openWindowToAddNewTag();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

//        childMenuItem1 childMenuItem2,childMenuItem3,childMenuItem4,childMenuItem5,childMenuItem6
        blueColor.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            calendarTagController.changeColor(currentLabel,"Modrá");
        }

    });
        purpleColor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                calendarTagController.changeColor(currentLabel,"Fialová");
            }

        });
        yellowColor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                calendarTagController.changeColor(currentLabel,"Žltá");
            }

        });
        redColor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                calendarTagController.changeColor(currentLabel,"Červená");
            }

        });
        greenColor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                calendarTagController.changeColor(currentLabel,"Zelená");
            }

        });
    }



}