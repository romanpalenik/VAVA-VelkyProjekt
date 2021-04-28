package project.model.databases.sizesAndPosition;

public class CalendarSizesAndPositonOfObjects extends BasicSizesAndPosition{

    private int widthOfCalendar = 750;
    private int heightOfCalendar = 487;
    private int leftUpCornerX = 260;
    private int leftUpCornerY = 170;
    private int oneCalendarCell = 200;

    public int getOneCalendarCell() {
        return oneCalendarCell;
    }

    public int getWidthOfCalendar() {
        return widthOfCalendar;
    }

    public int getHeightOfCalendar() {
        return heightOfCalendar;
    }

    public int getLeftUpCornerX() {
        return leftUpCornerX;
    }

    public int getLeftUpCornerY() {
        return leftUpCornerY;
    }

    private int placeBetweenTwoTags = 10;

    public int getPlaceBetweenTwoTags() {
        return placeBetweenTwoTags;
    }

}
