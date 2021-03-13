package controler;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import view.CalendarViewCreatingThings;

import java.time.LocalDate;

/**
 * Create an anchor pane that can store additional data.
 */
public class AnchorPaneNode extends AnchorPane {

    // Date associated with this pane
    private LocalDate date;
    private static AnchorPane root;
    private static CalendarViewCreatingThings calendarView;
    private boolean isInMonth;

    public static void setCalendarView(CalendarViewCreatingThings calendarView) {
        AnchorPaneNode.calendarView = calendarView;
    }

    public static void setRoot(AnchorPane root) {
        AnchorPaneNode.root = root;
    }

    private int centerX;
    private int centerY;

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public void setInMonth(boolean inMonth) {
        isInMonth = inMonth;
    }

    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     * @param children children of the anchor pane
     */
    public AnchorPaneNode(Node... children) {
        super(children);
        // Add action handler for mouse clicked
        this.setOnMouseClicked(e -> {
            onClicked();
        });


    }

    public void onClicked()
    {
        if (isInMonth) {
            calendarView.createSettingNote(this.centerX, this.centerY, root, date);
        }
    }

    public void addNewEvent()
    {

    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
