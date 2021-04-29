package project.controller.calendar;

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
import project.controller.AplicationWindow;
import project.controller.Internationalization;
import project.model.databases.CalendarDatabase;
import project.view.calendar.CalendarViewCreatingThings;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;


public class CalendarController extends AplicationWindow implements Internationalization {

    @FXML
    private AnchorPane root;
    @FXML
    private GridPane calendar;
    @FXML
    private Button monthBefore;
    @FXML
    private Button monthForward;
    @FXML
    private Label month;
    @FXML
    private Label year;
    @FXML
    private Label firstTag;
    @FXML
    private Button addTag;
    @FXML
    private Label titleLbl;
    @FXML
    private Button menuButton;
    @FXML
    private Button languageButton;

    public static String language;

    private boolean isMenuShown = false;
    private Stage stage = new Stage();
    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    private YearMonth currentMonth = YearMonth.now();

    private CalendarViewCreatingThings calendarView = new CalendarViewCreatingThings();
    private CalendarDatabase calendarDatabase = new CalendarDatabase();
    private CalendarTagController calendarTagController = new CalendarTagController();

    private TextField newNoteName;

    public CalendarController() throws IOException, ClassNotFoundException {
    }

    public void setNewNoteName(TextField newNoteName) {
        this.newNoteName = newNoteName;
    }

    private Dictionary<Integer, String> monthsDict = new Hashtable<Integer, String>();


    public void initialize() throws IOException {

        super.start(root, menuButton);


        //Section to set connect all object connected to calendar and tags
        AnchorPaneNode.setCalendarView(calendarView);
        calendarView.setCalendarController(this, root);
        calendarView.setCalendarDatabase(calendarDatabase);
        calendarView.setCreateTagController(calendarTagController);

        calendarTagController.setCalendarController(this);
        calendarTagController.setCalendarDatabase(calendarDatabase);
        calendarTagController.setCalendarView(calendarView);

        calendarView.setFirstTag(firstTag);
        calendarTagController.initData(calendarDatabase, calendarView, null, root,firstTag, this);

        initCalendar();
        calendarView.createTags(root, calendarDatabase);
        root.setOnMouseClicked(this::removeAllThingsByClicked);

    }

    /**
     * call to set up everything connected to calendar
     */
    private void initCalendar()
    {
        updateCalendar(currentMonth);

        if(languageButton.getText().equals("EN")){
            initMonthDictionarySK();
            language = "SK";
        }else{
            initMonthDictionaryEN();
            language = "EN";
        }

        year.setText(String.valueOf(currentMonth.getYear()));
        month.setText(monthsDict.get(currentMonth.getMonthValue()));

        calendarView.setUpDays(currentMonth, allCalendarDays, root);
        monthForward.setText(String.valueOf(currentMonth.plusMonths(1)));
        monthBefore.setText(String.valueOf(currentMonth.minusMonths(1)));

    }

    private void removeAllThingsByClicked(MouseEvent mouseEvent) {
        super.hideMenu(mouseEvent);
        removeLastShownNote(mouseEvent);
        root.getChildren().remove(newNoteName);

    }

    //------------------------------------------------------
    //DICTIONARY NA MESIACE
    //------------------------------------------------------
    private void initMonthDictionarySK(){
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
    private void initMonthDictionaryEN(){
        monthsDict.put(1,"January");
        monthsDict.put(2,"February");
        monthsDict.put(3,"March");
        monthsDict.put(4,"April");
        monthsDict.put(5,"May");
        monthsDict.put(6,"June");
        monthsDict.put(7,"July");
        monthsDict.put(8,"August");
        monthsDict.put(9,"September");
        monthsDict.put(10,"October");
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
     * @throws IOException if fxml is not find
     */
    public void openWindowToAddNewTag() throws IOException {
        ResourceBundle bundle = this.changeLanguage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/view/calendar/createTag.fxml"), bundle);
        Parent root2 = loader.load();
        CalendarTagController controller = loader.getController();
        controller.initData(calendarDatabase,calendarView, stage,root,firstTag, this);
        Scene scene = new Scene(root2);
        stage.setTitle("Pridať tag");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * hide section to create new event if is clicked outside calendar
     * @param event
     */
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

    @FXML
    void translate() {
        if(languageButton.getText().equals("EN")){
            language = "EN";
        }
        else{
            language = "SK";
        }
        ResourceBundle bundle = this.changeLanguage();
        titleLbl.setText(bundle.getString("calendarTitle"));
        addTag.setText(bundle.getString("tagManagement"));
        languageButton.setText(bundle.getString("language"));
        initCalendar();
    }


}


