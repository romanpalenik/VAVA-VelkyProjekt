package controler;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.AnchorPaneNode;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;


public class Controller {

    @FXML
    private AnchorPane root;
    @FXML
    private GridPane calendar;
    @FXML
    private Label month;
    @FXML
    private Label year;


    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    private YearMonth currentMonth = YearMonth.now();
    private boolean specialMonth = false;

    public void initialize()
    {

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(200,200);
                calendar.add(ap,j,i);
                allCalendarDays.add(ap);
            }
        }
        setUpDays(currentMonth);
    }

    public void setUpDays(YearMonth currentMonth)
    {
        for (AnchorPaneNode ap : allCalendarDays) {
//            ap.getStylesheets().add("daysInMonth.css");
//            ap.getStylesheets().add("daysFromAnotherMonth.css");
//            ap.getStyleClass().remove("pane");
        }

        LocalDate calendarDate = LocalDate.of(currentMonth.getYear(), currentMonth.getMonthValue(), 1);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("MONDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Populate the calendar with day numbers
        boolean stop = false;
        int i=1;
        for (AnchorPaneNode ap : allCalendarDays) {

            if (calendarDate.getDayOfMonth() == 1 && stop == false) {
                i = 1;
                stop = true;
            }

            if (i > calendarDate.getDayOfMonth() || stop == false) {
                ap.getStylesheets().remove("daysInMonth.css");
                ap.getStylesheets().add("daysFromAnotherMonth.css");
                ap.getStyleClass().add("pane");
            }
            else {
                ap.getStylesheets().remove("daysFromAnotherMonth.css");
                ap.getStylesheets().add("daysInMonth.css");
                ap.getStyleClass().add("pane");
            }

            if (ap.getChildren().size() != 0) {
                ap.getChildren().remove(0);
            }
            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            ap.setDate(calendarDate);
            ap.setTopAnchor(txt, 5.0);
            ap.setLeftAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            calendarDate = calendarDate.plusDays(1);
            i++;
        }
    }

    public void changeMonthUp()
    {

        currentMonth = currentMonth.plusMonths(1);

        checkMonthSize(currentMonth);
        setUpDays(currentMonth);

        month.setText(String.valueOf(currentMonth.getMonthValue()));
        year.setText(String.valueOf(currentMonth.getYear()));
    }

    public void changeMonthDown()
    {
        currentMonth = currentMonth.minusMonths(1);

        checkMonthSize(currentMonth);
        setUpDays(currentMonth);

        month.setText(String.valueOf(currentMonth.getMonthValue()));
        year.setText(String.valueOf(currentMonth.getYear()));

    }

    /**
     * there is months that start on saturday or sunday and has 31 day so they
     * need 8 rows to show not only 7
     */
    public void checkMonthSize(YearMonth currentMonth)
    {
        LocalDate calendarDate = LocalDate.of(currentMonth.getYear(), currentMonth.getMonthValue(), 1);
        int sizeOfMonth = currentMonth.lengthOfMonth();
        if(((calendarDate.getDayOfWeek().toString().equals("SATURDAY") || calendarDate.getDayOfWeek().toString().equals("SUNDAY"))
        && sizeOfMonth == 31) || calendarDate.getDayOfWeek().toString().equals("SUNDAY") && sizeOfMonth==30)
        {
            root.getChildren().remove(calendar);
            specialMonth = true;
            for(int i=0;i<7;i++)
            {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(0,81);
                calendar.add(ap,i,5);
                allCalendarDays.add(ap);
            }
            root.getChildren().add(calendar);

        }
        else if(specialMonth ==true) {

            root.getChildren().remove(calendar);

            GridPane calendar = new GridPane();
            calendar.setPrefSize(749,487);
            calendar.setLayoutX(209);
            calendar.setLayoutY(142);
            calendar.setGridLinesVisible(true);

            allCalendarDays = new ArrayList<>(35);
            calendar.getChildren().removeAll();

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 7; j++) {
                    AnchorPaneNode ap = new AnchorPaneNode();
                    ap.setPrefSize(200, 200);
                    calendar.add(ap, j, i);
                    allCalendarDays.add(ap);
                }

            }
            root.getChildren().add(calendar);
        }

    }
}


