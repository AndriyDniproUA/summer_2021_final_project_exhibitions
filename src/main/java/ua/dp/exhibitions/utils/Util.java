package ua.dp.exhibitions.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
}











