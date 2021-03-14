package view;

import controler.CalendarController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import controler.AnchorPaneNode;
import javafx.scene.text.Text;
import model.CalendarDatabase;
import model.OneCellRecord;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

/**
 * class that create tags and make calendar bigger or smaller
 */
public class CalendarViewCreatingThings {

    ArrayList<String> tags = new ArrayList<>();
    ArrayList<Label> labelTags = new ArrayList<>();
    private int widthOfCalendar = 750;
    private int heightOfCalendar = 487;
    private int leftUpCornerX = 209;
    private int leftUpCornerY = 142;

    private TextField LastShownTextFieldNote = new TextField();
    private Button lastShownButton = new Button();

    private CalendarController calendarController;

    public void setCalendarController(CalendarController calendarController) {
        this.calendarController = calendarController;
    }
    private CalendarDatabase calendarDatabase;

    public void setCalendarDatabase(CalendarDatabase calendarDatabase) {
        this.calendarDatabase = calendarDatabase;
    }

    /**
     * delete all tags, and create new from array
     * @param root window pane
     * @param fistTag tag by to navigate position
     * @param calendarDatabase database with every information connected with calendar
     */
    public void createTags(AnchorPane root, Label fistTag,CalendarDatabase calendarDatabase) {

        for(Label label:labelTags)
        {
            root.getChildren().remove(label);
        }

        double yPosition = fistTag.getLayoutY() + 100;
        double xPosition = fistTag.getLayoutX();
        tags = calendarDatabase.getTags();

        for (String tag : tags) {
            Label label = new Label(tag);
            label.setTranslateX(xPosition);
            label.setTranslateY(yPosition);

            labelTags.add(label);

            root.getChildren().add(label);

            yPosition += 100;
        }
    }

    /**
     * there is months that start on saturday or sunday and has 31 day so they
     * need 8 rows to show not only 7
     * @return
     */
    public ArrayList<AnchorPaneNode> makeCalendarBigger(AnchorPane root, GridPane calendar) {


        root.getChildren().remove(calendar);

        calendar = new GridPane();
        calendar.setPrefSize(widthOfCalendar, heightOfCalendar);
        calendar.setLayoutX(leftUpCornerX);
        calendar.setLayoutY(leftUpCornerY);
        calendar.setGridLinesVisible(true);

        ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(28);
        calendar.getChildren().removeAll();

        int offsetToCenter = widthOfCalendar/7;
        int centerOfFistCellY = leftUpCornerY + (heightOfCalendar/6)/2;
        int centerOfFistCellX = leftUpCornerX + (widthOfCalendar/7)/2;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(200, 200);
                calendar.add(ap, j, i);
                allCalendarDays.add(ap);
            }

        }

        root.getChildren().add(calendar);
        return allCalendarDays;
    }

    /**
     * Create new calendar where size is normal 7x5
     * @param root where is calendar
     * @param calendar old calendar to remove it from pane
     * @return new array of every cell in calendar
     */
    public ArrayList<AnchorPaneNode> makeCalendarNormalSize(AnchorPane root, GridPane calendar)
    {

        root.getChildren().remove(calendar);

        calendar = new GridPane();
        calendar.setPrefSize(widthOfCalendar, heightOfCalendar);
        calendar.setLayoutX(leftUpCornerX);
        calendar.setLayoutY(leftUpCornerY);
        calendar.setGridLinesVisible(true);

        int offsetToCenterX = widthOfCalendar/7;
        int offsetToCenterY = heightOfCalendar/5;
        int centerOfFistCellY = leftUpCornerY + (heightOfCalendar/5)/2;
        int centerOfFistCellX = leftUpCornerX + (widthOfCalendar/7)/2;

        ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
        calendar.getChildren().removeAll();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();

                ap.setCenterX(centerOfFistCellX);
                ap.setCenterY(centerOfFistCellY);

                ap.setRoot(root);

                ap.setPrefSize(200, 200);
                calendar.add(ap, j, i);
                allCalendarDays.add(ap);
                centerOfFistCellX += offsetToCenterX;
            }
            centerOfFistCellX = leftUpCornerX + (widthOfCalendar/7)/2;
            centerOfFistCellY += offsetToCenterY;
        }
        root.getChildren().add(calendar);
        return allCalendarDays;
    }

    public ArrayList<AnchorPaneNode> makeCalendarLitter(AnchorPane root, GridPane calendar)
    {

        root.getChildren().remove(calendar);

        calendar = new GridPane();
        calendar.setPrefSize(widthOfCalendar, heightOfCalendar);
        calendar.setLayoutX(leftUpCornerX);
        calendar.setLayoutY(leftUpCornerY);
        calendar.setGridLinesVisible(true);

        int offsetToCenter = widthOfCalendar/7;
        int centerOfFistCellY = leftUpCornerY + (heightOfCalendar/4)/2;
        int centerOfFistCellX = leftUpCornerX + (widthOfCalendar/7)/2;

        ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(28);
        calendar.getChildren().removeAll();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(200, 200);
                calendar.add(ap, j, i);
                allCalendarDays.add(ap);
            }

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

        int textFieldWidth = 150;
        int textFieldHeight = 26;

        TextField textFieldNote = new TextField();
        textFieldNote.setPrefSize(textFieldWidth, textFieldHeight);
        textFieldNote.setLayoutX(x-(textFieldWidth/2)-20);
        textFieldNote.setLayoutY(y-80);


        Button saveNoteButton = new Button();
        saveNoteButton.setText("Pridat");
        saveNoteButton.setPrefSize(70, textFieldHeight);
        saveNoteButton.setLayoutX(x-15);
        saveNoteButton.setLayoutY(y-50); //than the text field +30

        saveNoteButton.setOnMouseClicked(e -> {

            calendarDatabase.addToEvents(date,textFieldNote.getText());
            removeLastShownNote(root);
            calendarController.checkMonthSize(calendarController.getCurrentMonth());

        });

        LastShownTextFieldNote = textFieldNote;
        lastShownButton = saveNoteButton;

        root.getChildren().add(textFieldNote);
        root.getChildren().add(saveNoteButton);


    }

    public void removeLastShownNote(AnchorPane root)
    {
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
     * create label for every event that is planned on that date
     * @param cell
     * @param root
     */
    public void createLabelWithEvent(AnchorPaneNode cell, OneCellRecord oneCellRecord, AnchorPane root)
    {
        int yOffset = 8;
        for(String event:oneCellRecord.getEveryEvent())
        {
            Label eventLabel = new Label(event);
            eventLabel.setLayoutX(cell.getCenterX()-50);
            eventLabel.setLayoutY(cell.getCenterY()-40+yOffset);
            root.getChildren().add(eventLabel);
            yOffset +=14;
        }
    }
}