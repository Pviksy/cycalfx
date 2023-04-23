package com.pviksy.cycalfx.gui.calendar;

import com.pviksy.cycalfx.data.entities.Race;
import com.pviksy.cycalfx.app.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class MonthView extends GridPane {

    /*
    In the constructor, it can be determined how many weeks etc the grid
    should be able to contain. This can depend on the information retrieved
    from the database, or be more static.
     */

    private final CalendarModel calendarModel;
    private final Main main;

    public MonthView(Main main, CalendarModel calendarModel) {
        this.main = main;
        this.calendarModel = calendarModel;

        updateContent(main.getRaces());

        setHgap(10);
        setVgap(10);
        this.setAlignment(Pos.TOP_CENTER);
    }

    private void updateContent(ArrayList<Race> races) {

        getChildren().clear();

        int dayOfMonth = calendarModel.getDate().getDayOfMonth();
        int monthLength = calendarModel.getDate().lengthOfMonth();
        LocalDate firstDayOfMonth = calendarModel.getDate().minusDays(dayOfMonth - 1);
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

        int daysInCalendar = (int) ChronoUnit.DAYS.between(firstMondayOfCalendar, lastSundayOfCalendar) + 1; // fixes problem occuring when changing between years
        System.out.println("       daysInCalendar: " + daysInCalendar);

        int weeksToDisplay = daysInCalendar / 7;
        System.out.println("       weeksToDisplay: " + weeksToDisplay);

        LocalDate dateIterator = firstMondayOfCalendar;

        for (int weeks = 0; weeks < weeksToDisplay + 1; weeks++) { // could be either 4, 5 or 6 depending on where days land in the weeks
            for (int days = 0; days < 7; days++) {
                int CELL_WIDTH = 190;
                int CELL_HEIGHT = 30;
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
                    dayLabel.setPrefSize(CELL_WIDTH, 30); // Set the preferred width and height
                    dayLabel.setAlignment(Pos.CENTER); // Center the text within the label
                    dayLabel.getStyleClass().add("calendar-cell");
                } else {
                    Label date = new Label(Integer.toString(dateIterator.getDayOfMonth()));

                    LocalDate finalDateIterator = dateIterator;
                    List<Race> filteredRaces = races.stream()
                            .filter(race -> {
                                LocalDate startDateLocalDate = race.getStartDate().toLocalDate();
                                return startDateLocalDate.equals(finalDateIterator);
                            })
                            .toList();


                    MonthDayContainer monthDayContainer = new MonthDayContainer(date, filteredRaces, main);
                    monthDayContainer.getStyleClass().add("month-day-container");

                    VBox container = new VBox();
                    container.setPrefSize(CELL_WIDTH, CELL_HEIGHT);
                    container.getStyleClass().add("calendar-cell");
                    container.getChildren().addAll(date, monthDayContainer);
                    container.getStyleClass().add("calendar-cell");
                    container.setAlignment(Pos.TOP_CENTER); // Center the components within the VBox

                    add(container, days, weeks);
                    dateIterator = dateIterator.plusDays(1);
                }
            }
        }
    }


    public void decrementMonth() {
        calendarModel.decrementMonth();
        updateContent(main.getRaces());
    }

    public void incrementMonth() {
        calendarModel.incrementMonth();
        updateContent(main.getRaces());
    }
}
