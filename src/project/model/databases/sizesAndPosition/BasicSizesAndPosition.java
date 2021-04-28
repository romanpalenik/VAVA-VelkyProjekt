package project.model.databases.sizesAndPosition;

public class BasicSizesAndPosition {

    private static int textFieldWidth = 150;
    private static int textFieldHeight = 26;
    private static int buttonWidth = 40;
    private static int comboBoxWidth = 110;
    private static int gapBetweenObjects = 20;
    private static double widthOfMenu;


    public static int getTextFieldWidth() {
        return textFieldWidth;
    }

    public static int getTextFieldHeight() {
        return textFieldHeight;
    }

    public static int getButtonWidth() {
        return buttonWidth;
    }

    public static int getComboBoxWidth() {
        return comboBoxWidth;
    }

    public static int getGapBetweenObjects() { return gapBetweenObjects;    }

    public static double getWidthOfMenu() {
        return widthOfMenu;
    }

    public static void setWidthOfMenu(double widthOfMenu) {
        BasicSizesAndPosition.widthOfMenu = widthOfMenu;
    }
}
