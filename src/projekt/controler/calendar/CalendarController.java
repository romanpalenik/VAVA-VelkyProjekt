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
import java.time.YearMonth;
import java.util.ArrayList;


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

    public void initialize()
    {
        AnchorPaneNode.setRoot(root);
        AnchorPaneNode.setCalendarView(calendarView);
        calendarView.setCalendarController(this);
        calendarView.setCalendarDatabase(calendarDatabase);

        checkMonthSize(currentMonth);

        calendarView.setUpDays(currentMonth, allCalendarDays, root);
        monthForward.setText(String.valueOf(currentMonth.plusMonths(1)));
        monthBefore.setText(String.valueOf(currentMonth.minusMonths(1)));

        calendarView.createTags(root,firstTag, calendarDatabase);

        root.setOnMouseClicked(this::removeLastShownNote);

    }

    public YearMonth getCurrentMonth() {
        return currentMonth;
    }

    public void changeMonthUp()
    {

        currentMonth = currentMonth.plusMonths(1);

        checkMonthSize(currentMonth);


        month.setText(String.valueOf(currentMonth.getMonthValue()));
        year.setText(String.valueOf(currentMonth.getYear()));

        monthForward.setText(String.valueOf(currentMonth.plusMonths(1)));
        monthBefore.setText(String.valueOf(currentMonth.minusMonths(1)));
    }

    public void changeMonthDown()
    {
        currentMonth = currentMonth.minusMonths(1);

        checkMonthSize(currentMonth);

        month.setText(String.valueOf(currentMonth.getMonthValue()));
        year.setText(String.valueOf(currentMonth.getYear()));

        monthForward.setText(String.valueOf(currentMonth.plusMonths(1)));
        monthBefore.setText(String.valueOf(currentMonth.minusMonths(1)));
    }

    /**
     * check if month needs more space than 7x5
     * if yes, call it special month
     */
    public void checkMonthSize(YearMonth currentMonth)
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
        controller.initData(calendarDatabase,calendarView, stage,root,firstTag);
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


