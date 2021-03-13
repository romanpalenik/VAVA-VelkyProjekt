package view;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.AnchorPaneNode;
import model.CalendarDatabase;

import java.util.ArrayList;

/**
 * class that create tags and make calendar bigger or smaller
 */
public class calendarViewCreatingThings {

    ArrayList<String> tags = new ArrayList<>();
    ArrayList<Label> labelTags = new ArrayList<>();

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
        calendar.setPrefSize(749, 487);
        calendar.setLayoutX(209);
        calendar.setLayoutY(142);
        calendar.setGridLinesVisible(true);

        ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(28);
        calendar.getChildren().removeAll();

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

    public ArrayList<AnchorPaneNode> makeCalendarLitter(AnchorPane root, GridPane calendar)
    {

        root.getChildren().remove(calendar);

        calendar = new GridPane();
        calendar.setPrefSize(749,487);
        calendar.setLayoutX(209);
        calendar.setLayoutY(142);
        calendar.setGridLinesVisible(true);

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

}
