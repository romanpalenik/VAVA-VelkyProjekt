package controler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.AnchorPaneNode;
import model.CalendarDatabase;
import view.calendarViewCreatingThings;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;


public class calendarController {

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
    private boolean specialMonth = false;
    calendarViewCreatingThings calendarView = new calendarViewCreatingThings();
    CalendarDatabase calendarDatabase = new CalendarDatabase();

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
        monthForward.setText(String.valueOf(currentMonth.plusMonths(1)));
        monthBefore.setText(String.valueOf(currentMonth.minusMonths(1)));

        calendarView.createTags(root,firstTag, calendarDatabase);
    }

    /**
     * set to every cell of calendar right date
     * @param currentMonth month to show
     */
    public void setUpDays(YearMonth currentMonth)
    {
        monthForward.setText(String.valueOf(currentMonth.plusMonths(1)));
        monthBefore.setText(String.valueOf(currentMonth.minusMonths(1)));

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
            showedDays++;
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
     * check if month needs more space than 7x5
     * if yes, call it special month
     */
    public void checkMonthSize(YearMonth currentMonth)
    {
        LocalDate calendarDate = LocalDate.of(currentMonth.getYear(), currentMonth.getMonthValue(), 1);
        int sizeOfMonth = currentMonth.lengthOfMonth();
        if(((calendarDate.getDayOfWeek().toString().equals("SATURDAY") || calendarDate.getDayOfWeek().toString().equals("SUNDAY"))
        && sizeOfMonth == 31) || calendarDate.getDayOfWeek().toString().equals("SUNDAY") && sizeOfMonth==30)
        {
            specialMonth = true;
            calendarView.makeCalendarBigger(root,calendar,allCalendarDays);
        }
        else if(specialMonth ==true) {
            allCalendarDays = calendarView.makeCalendarNormalSize(root,calendar);
            specialMonth = false;
        }
    }

    /**
     * opens new window when you can edit or create tag
     * @throws IOException
     */
    public void openWindowToAddNewTag() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/createTag.fxml"));
        Parent root2 = (Parent) loader.load();
        CreateTagController controller = loader.getController();
        controller.initData(calendarDatabase,calendarView, stage,root,firstTag);
        Scene scene = new Scene(root2);
        stage.setTitle("Pridat tag");
        stage.setScene(scene);
        stage.show();
    }

}


