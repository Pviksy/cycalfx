package com.pviksy.cycalfx.gui.calendar.views;

import com.pviksy.cycalfx.data.entities.Race;
import com.pviksy.cycalfx.app.Main;
import com.pviksy.cycalfx.gui.calendar.CalendarModel;
import com.pviksy.cycalfx.gui.calendar.MonthDayContainer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class MonthView extends GridPane implements CalendarModel.DateObserver {

    private final Main main;
    private final CalendarModel calendarModel;

    public MonthView(Main main, CalendarModel calendarModel) {
        this.main = main;
        this.calendarModel = calendarModel;
        calendarModel.registerDateObserver(this);

        updateContent(main.getRaces());

        setHgap(10);
        setVgap(10);
        this.setAlignment(Pos.TOP_CENTER);
    }

    public void updateContent(ArrayList<Race> races) {

        getChildren().clear();

        int dayOfMonth = calendarModel.getDate().getDayOfMonth();
        int monthLength = calendarModel.getDate().lengthOfMonth();
        LocalDate firstDayOfMonth = calendarModel.getDate().minusDays(dayOfMonth - 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.plusDays(monthLength - 1);


        // find first day of the given month to show
        LocalDate firstMondayOfCalendar;
        if (firstDayOfMonth.getDayOfWeek() != DayOfWeek.MONDAY) {
            firstMondayOfCalendar = firstDayOfMonth.minusDays(firstDayOfMonth.getDayOfWeek().getValue() - 1);
        } else {
            firstMondayOfCalendar = firstDayOfMonth;
        }

        // find last day to show
        LocalDate lastSundayOfCalendar = firstDayOfMonth.plusMonths(1);
        if (lastSundayOfCalendar.getDayOfWeek() != DayOfWeek.SUNDAY) {
            lastSundayOfCalendar = lastSundayOfCalendar.plusDays(7 - lastSundayOfCalendar.getDayOfWeek().getValue());
        }

        int daysInCalendar = (int) ChronoUnit.DAYS.between(firstMondayOfCalendar, lastSundayOfCalendar) + 1; // fixes problem occuring when changing between years

        int weeksToDisplay = daysInCalendar / 7;

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

                    // find out if this container is necessary
                    VBox container = new VBox();
                    container.setPrefSize(CELL_WIDTH, CELL_HEIGHT);
                    container.getStyleClass().add("calendar-cell");
                    container.getChildren().addAll(date, monthDayContainer);
                    container.setAlignment(Pos.TOP_CENTER);

                    if (dateIterator.equals(calendarModel.getDate())) {
                        container.setStyle("-fx-border-color: -primary-color; -fx-border-width: 2;");
                             date.setStyle("-fx-text-fill: -primary-color;");
                    }

                    add(container, days, weeks);
                    dateIterator = dateIterator.plusDays(1);
                }
            }
        }
    }

    @Override
    public void onDateChanged(LocalDate newDate) {
        updateContent(main.getRaces());
    }
}