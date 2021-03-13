package view;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.AnchorPaneNode;
import model.CalendarDatabase;

import java.util.ArrayList;

public class CreateTagsInCalendar {

    CalendarDatabase calendarDatabase = new CalendarDatabase();
    ArrayList<String> tags = new ArrayList<String>();

    public void createTags(AnchorPane root, Label fistTag) {
        double yPosition = fistTag.getLayoutY() + 100;
        double xPosition = fistTag.getLayoutX();
        tags = calendarDatabase.getTags();

        for (String tag : tags) {
            Label label = new Label(tag);
            label.setTranslateX(xPosition);
            label.setTranslateY(yPosition);

            root.getChildren().add(label);

            yPosition += 100;
        }
    }

    /**
     * there is months that start on saturday or sunday and has 31 day so they
     * need 8 rows to show not only 7
     */
    public void makeCalendarBigger(AnchorPane root, GridPane calendar, ArrayList<AnchorPaneNode> allCalendarDays) {

        root.getChildren().remove(calendar);
        for (int i = 0; i < 7; i++) {
            AnchorPaneNode ap = new AnchorPaneNode();
            ap.setPrefSize(0, 81);
            calendar.add(ap, i, 5);
            allCalendarDays.add(ap);
        }
        root.getChildren().add(calendar);

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
        calendar.setPrefSize(749,487);
        calendar.setLayoutX(209);
        calendar.setLayoutY(142);
        calendar.setGridLinesVisible(true);

        ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
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
        return allCalendarDays;
    }

}
