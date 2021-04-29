package project.controller;

import project.controller.calendar.CalendarController;

import java.util.Locale;
import java.util.ResourceBundle;

public interface Internationalization {

    default ResourceBundle changeLanguage(){
        Locale locale;
        ResourceBundle bundle;
        if(CalendarController.language.equals("EN")){
            locale = new Locale("en");
            bundle = ResourceBundle.getBundle("/project/Bundle", locale);
        }
        else{
            locale = new Locale("sk");
            bundle = ResourceBundle.getBundle("/project/Bundle", locale);
        }
        return bundle;
    }

}
