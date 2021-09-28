package ua.dp.exhibitions.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class Util {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public static LocalDate convertStringToLocalDate(String date) {
        return LocalDate.parse(date, formatter);
    }

    public static String convertLocalDateToString(LocalDate date) {
        return date.format(formatter);
    }

    public static int[] convertStringArrayToIntArray(String[] input) {
        int[] output = new int[input.length];

        for (int i = 0; i < output.length; i++) {
            output[i] = Integer.parseInt(input[i]);
        }
        return output;
    }

    public static String internationalizeMessage (HttpServletRequest request, String key){

        HttpSession session = request.getSession();
        String currentLocaleName = (String)session.getAttribute("currentLocale");

        Locale currentLocale=null;
        if (currentLocaleName==null){
            currentLocale= new Locale("en");
        } else {
            currentLocale= new Locale(currentLocaleName);
        }
        ResourceBundle rb = ResourceBundle.getBundle("labels",currentLocale);
        return rb.getString(key);
    }
}











