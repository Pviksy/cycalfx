package com.pviksy.cycalfx.GUI.Calendar;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CalendarGrid extends GridPane {

    /*
    In the constructor, it can be determined how many weeks etc the grid
    should be able to contain. This can depend on the information retrieved
    from the database, or be more static.
     */

    private LocalDate date = LocalDate.now(); //.plusMonths(1);

    public CalendarGrid() {
        setGridLinesVisible(true);
        setHgap(10);
        setVgap(10);
        updateContent();


        this.setAlignment(Pos.CENTER);
    }

    private void updateContent() {
        getChildren().clear();

        int dayOfMonth = date.getDayOfMonth();
        int monthLength = date.lengthOfMonth();
        LocalDate firstDayOfMonth = date.minusDays(dayOfMonth - 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.plusDays(monthLength - 1);
        System.out.println("      firstDayOfMonth: " + firstDayOfMonth);
        System.out.println("       lastDayOfMonth: " + lastDayOfMonth);


        // find first day of the given month to show
        LocalDate firstMondayOfCalendar;
        if (firstDayOfMonth.getDayOfWeek() != DayOfWeek.MONDAY) {
            firstMondayOfCalendar = firstDayOfMonth.minusDays(firstDayOfMonth.getDayOfWeek().getValue() - 1);
        } else {
            firstMondayOfCalendar = firstDayOfMonth;
        }
        System.out.println("firstMondayOfCalendar: " + firstMondayOfCalendar);


        // find last day to show
        LocalDate lastSundayOfCalendar = firstDayOfMonth.plusMonths(1);
        if (lastSundayOfCalendar.getDayOfWeek() != DayOfWeek.SUNDAY) {
            lastSundayOfCalendar = lastSundayOfCalendar.plusDays(7 - lastSundayOfCalendar.getDayOfWeek().getValue());
        }
        System.out.println(" lastSundayOfCalendar: " + lastSundayOfCalendar);


        //int daysInCalendar = lastSundayOfCalendar.getDayOfYear() - firstMondayOfCalendar.getDayOfYear() + 1; // tutorial guy did not +1 but it didnt fit, so I did
        int daysInCalendar = (int) ChronoUnit.DAYS.between(firstMondayOfCalendar, lastSundayOfCalendar) + 1; // fixes problem occuring when changing between years
        System.out.println("       daysInCalendar: " + daysInCalendar);

        int weeksToDisplay = daysInCalendar / 7;
        System.out.println("       weeksToDisplay: " + weeksToDisplay);

        LocalDate dateIterator = firstMondayOfCalendar;

        for (int weeks = 0; weeks < weeksToDisplay + 1; weeks++) { // could be either 4, 5 or 6 depending on where days land in the weeks
            for (int days = 0; days < 7; days++) {
                if (weeks == 0) {
                    Label dayLabel = new Label();
                    switch (days) {
                        case 0 -> dayLabel.setText("Mon");
                        case 1 -> dayLabel.setText("Tue");
                        case 2 -> dayLabel.setText("Wed");
                        case 3 -> dayLabel.setText("Thu");
                        case 4 -> dayLabel.setText("Fri");
                        case 5 -> dayLabel.setText("Sat");
                        case 6 -> dayLabel.setText("Sun");
                    }
                    add(dayLabel, days, weeks);
                } else {
                    Label date = new Label();
                    date.setText(Integer.toString(dateIterator.getDayOfMonth()));

                    add(date, days, weeks);
                    dateIterator = dateIterator.plusDays(1);
                }
            }
        }
    }

    public void incrementMonth() {
        date = date.plusMonths(1);
        updateContent();

        System.out.println(date);
    }

    public void decrementMonth() {
        date = date.minusMonths(1);
        updateContent();

        System.out.println(date);
    }

}
