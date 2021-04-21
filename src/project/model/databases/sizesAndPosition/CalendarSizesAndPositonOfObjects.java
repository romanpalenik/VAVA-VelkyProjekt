package project.model.databases.sizesAndPosition;

public class CalendarSizesAndPositonOfObjects {

    private int widthOfCalendar = 750;
    private int heightOfCalendar = 487;
    private int leftUpCornerX = 209;
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

    private int textFieldWidth = 150;
    private int textFieldHeight = 26;
    private int buttonWidth = 40;
    private int comboBoxWidth = 110;

    public int getComboBoxWidth() {
        return comboBoxWidth;
    }

    public int getButtonWidth() {
        return buttonWidth;
    }

    public int getTextFieldWidth() {
        return textFieldWidth;
    }

    public int getTextFieldHeight() {
        return textFieldHeight;
    }

    private int placeBetweenTwoTags = 10;

    public int getPlaceBetweenTwoTags() {
        return placeBetweenTwoTags;
    }
}
