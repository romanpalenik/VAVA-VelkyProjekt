package projekt.controler.calendar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import projekt.model.CalendarDatabase;
import projekt.view.calendar.CalendarViewCreatingThings;


import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;


public class CalendarController {

    @FXML
    private AnchorPane root;
    @FXML
    private GridPane calendar;
    @FXML
    private Label month;
    @FXML
    private Label year;
    @FXML
    private Button monthForward;
    @FXML
    private Button monthBefore;
    @FXML
    private Label firstTag;
    @FXML
    private TextField tagName;

    private Stage stage = new Stage();
    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    private YearMonth currentMonth = YearMonth.now();
    CalendarViewCreatingThings calendarView = new CalendarViewCreatingThings();
    CalendarDatabase calendarDatabase = new CalendarDatabase();

    private Dictionary<Integer, String> monthsDict = new Hashtable<Integer, String>();


    public void initialize()
    {
        AnchorPaneNode.setRoot(root);
        AnchorPaneNode.setCalendarView(calendarView);
        calendarView.setCalendarController(this);
        calendarView.setCalendarDatabase(calendarDatabase);

        updateCalendar(currentMonth);

        initMonthDictionary();
        year.setText(String.valueOf(currentMonth.getYear()));
        month.setText(monthsDict.get(currentMonth.getMonthValue()));

        calendarView.setUpDays(currentMonth, allCalendarDays, root);
        monthForward.setText(String.valueOf(currentMonth.plusMonths(1)));
        monthBefore.setText(String.valueOf(currentMonth.minusMonths(1)));

        calendarView.createTags(root,firstTag, calendarDatabase);

        root.setOnMouseClicked(this::removeLastShownNote);

    }

    //------------------------------------------------------
    //DICTIONARY NA MESIACE
    //------------------------------------------------------
    private void initMonthDictionary(){
        monthsDict.put(1,"Január");
        monthsDict.put(2,"Február");
        monthsDict.put(3,"Marec");
        monthsDict.put(4,"Apríl");
        monthsDict.put(5,"Máj");
        monthsDict.put(6,"Jún");
        monthsDict.put(7,"Júl");
        monthsDict.put(8,"August");
        monthsDict.put(9,"September");
        monthsDict.put(10,"Október");
        monthsDict.put(11,"November");
        monthsDict.put(12,"December");
    }
    //------------------------------------------------------

    public YearMonth getCurrentMonth() {
        return currentMonth;
    }

    public void changeMonthUp()
    {

        currentMonth = currentMonth.plusMonths(1);

        updateCalendar(currentMonth);


        month.setText(monthsDict.get(currentMonth.getMonthValue()));
        year.setText(String.valueOf(currentMonth.getYear()));

        monthForward.setText(String.valueOf(currentMonth.plusMonths(1)));
        monthBefore.setText(String.valueOf(currentMonth.minusMonths(1)));
    }

    public void changeMonthDown()
    {
        currentMonth = currentMonth.minusMonths(1);

        updateCalendar(currentMonth);

        month.setText(monthsDict.get(currentMonth.getMonthValue()));
        year.setText(String.valueOf(currentMonth.getYear()));

        monthForward.setText(String.valueOf(currentMonth.plusMonths(1)));
        monthBefore.setText(String.valueOf(currentMonth.minusMonths(1)));
    }

    /**
     * check if month needs more space than 7x5 or less
     * and create a new one, with
     */
    public void updateCalendar(YearMonth currentMonth)
    {
        //check if calendar needs bigger or smaller, if neither than set it to normal size
        LocalDate calendarDate = LocalDate.of(currentMonth.getYear(), currentMonth.getMonthValue(), 1);
        int sizeOfMonth = currentMonth.lengthOfMonth();
        if(((calendarDate.getDayOfWeek().toString().equals("SATURDAY") || calendarDate.getDayOfWeek().toString().equals("SUNDAY"))
        && sizeOfMonth == 31) || calendarDate.getDayOfWeek().toString().equals("SUNDAY") && sizeOfMonth==30)
        {
            allCalendarDays = calendarView.makeCalendarBigger(root,calendar);
        }
        else if((calendarDate.getDayOfWeek().toString().equals("MONDAY") && sizeOfMonth==28))
        {
            allCalendarDays = calendarView.makeCalendarLitter(root,calendar);
        }
        else{
            allCalendarDays = calendarView.makeCalendarNormalSize(root,calendar);

        }
        calendarView.setUpDays(currentMonth,allCalendarDays,root);
    }

    /**
     * opens new window when you can edit or create tag
     * @throws IOException
     */
    public void openWindowToAddNewTag() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/projekt/view/createTag.fxml"));
        Parent root2 = (Parent) loader.load();
        CreateTagController controller = loader.getController();
        controller.initData(calendarDatabase,calendarView, stage,root,firstTag, this);
        Scene scene = new Scene(root2);
        stage.setTitle("Pridat tag");
        stage.setScene(scene);
        stage.show();
    }

    public void removeLastShownNote(MouseEvent event)
    {
        //if is outside of calendar
        if(event.getX() > calendar.getLayoutX() && event.getX() < calendar.getLayoutX()+calendar.getPrefWidth() &&
        event.getY()>calendar.getLayoutY() && event.getY()<calendar.getLayoutY()+calendar.getPrefHeight())
        {

        }
        else {
            calendarView.removeLastShownNote(root);
        }
    }


}


