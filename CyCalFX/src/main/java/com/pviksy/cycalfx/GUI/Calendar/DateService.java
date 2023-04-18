package com.pviksy.cycalfx.GUI.Calendar;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateService {

    public static int getCurrentMonthNumber() {
        LocalDate currentDate = LocalDate.now();

        return currentDate.getMonthValue();
    }


    // this belongs to Top - it was for testing switching between months
    public static String getMonthName(int month) {
        // Assuming the year doesn't matter for getting the month name, so we use a fixed year (e.g., 2021)
        LocalDate dateWithGivenMonth = LocalDate.of(2021, month, 1);
        return dateWithGivenMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

}
