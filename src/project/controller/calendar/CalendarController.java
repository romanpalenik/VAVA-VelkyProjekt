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
import project.controller.Main;
import project.model.databases.CalendarDatabase;
import project.view.calendar.CalendarViewCreatingThings;

import java.io.IOException;
import java.time.LocalDate;
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
    private AnchorPane menuFMXL; //this anchor is anchor pane with menu
    @FXML
    private Label darkSideWhenMenu;


    private boolean isMenuShown = false;
    private Stage stage = new Stage();
    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    private YearMonth currentMonth = YearMonth.now();

    private CalendarViewCreatingThings calendarView = new CalendarViewCreatingThings();
    private CalendarDatabase calendarDatabase = new CalendarDatabase();
    private CalendarTagController calendarTagController = new CalendarTagController();

    private TextField newNoteName;

    public void setNewNoteName(TextField newNoteName) {
        this.newNoteName = newNoteName;
    }

    private Dictionary<Integer, String> monthsDict = new Hashtable<Integer, String>();


    public void initialize() throws IOException {

        darkFilterWhileMenu();
        darkSideWhenMenu.setVisible(false);

        //Section to set connect all object connected to calendar and tags
        AnchorPaneNode.setRoot(root);
        AnchorPaneNode.setCalendarView(calendarView);
        calendarView.setCalendarController(this, root);
        calendarView.setCalendarDatabase(calendarDatabase);
        calendarView.setCreateTagController(calendarTagController);

        calendarTagController.setCalendarController(this);
        calendarTagController.setCalendarDatabase(calendarDatabase);
        calendarTagController.setCalendarView(calendarView);

        calendarView.setFistTag(firstTag);
        calendarTagController.initData(calendarDatabase, calendarView, null, root,firstTag, this);

        initCalendar();

        root.setOnMouseClicked(this::removeAllThingsByClicked);
    }

    /**
     * call to set up everything connected to calendar
     */
    private void initCalendar()
    {
        updateCalendar(currentMonth);

        initMonthDictionary();
        year.setText(String.valueOf(currentMonth.getYear()));
        month.setText(monthsDict.get(currentMonth.getMonthValue()));

        calendarView.setUpDays(currentMonth, allCalendarDays, root);
        monthForward.setText(String.valueOf(currentMonth.plusMonths(1)));
        monthBefore.setText(String.valueOf(currentMonth.minusMonths(1)));

        calendarView.createTags(root, calendarDatabase);
    }

    private void removeAllThingsByClicked(MouseEvent mouseEvent) {
        hideMenu(mouseEvent);
        removeLastShownNote(mouseEvent);
        root.getChildren().remove(newNoteName);

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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/view/calendar/createTag.fxml"));
        Parent root2 = (Parent) loader.load();
        CalendarTagController controller = loader.getController();
        controller.initData(calendarDatabase,calendarView, stage,root,firstTag, this);
        Scene scene = new Scene(root2);
        stage.setTitle("Pridat tag");
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

    /**
     * show menu, if is menu already shown hide it
     * @throws IOException
     */
    public void showMenu() throws IOException {

        if (isMenuShown)
        {
            hideMenu(null);
            return;
        }

        root.getChildren().add(darkSideWhenMenu);
        root.getChildren().add(loadFMXLMenu());

        isMenuShown = true;
        darkSideWhenMenu.setVisible(true);

    }

    private void hideMenu(MouseEvent mouseEvent) {

        root.getChildren().remove(darkSideWhenMenu);
        isMenuShown = false;
        try {
            root.getChildren().remove(menuFMXL);
            darkSideWhenMenu.setVisible(false);
        }
        catch (NullPointerException e)
        {

        }
    }


    /**
     * load anchor pane with menu buttons
     * @return menu
     */
    public AnchorPane loadFMXLMenu() throws IOException {

        menuFMXL = FXMLLoader.load(Main.class.getResource("/project/view/mainMenu.fxml"));
        menuFMXL.setLayoutY(75);
        return menuFMXL;
    }

    public Label darkFilterWhileMenu() throws IOException {
        darkSideWhenMenu = FXMLLoader.load(Main.class.getResource("/project/view/darkFilterWhileMenu.fxml"));
        darkSideWhenMenu.setLayoutX(174);
        return darkSideWhenMenu;
    }
}

