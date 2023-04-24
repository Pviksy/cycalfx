package com.pviksy.cycalfx.gui.calendar;

import com.pviksy.cycalfx.app.Main;
import com.pviksy.cycalfx.data.DataModel;
import com.pviksy.cycalfx.gui.calendar.views.MonthView;
import com.pviksy.cycalfx.gui.calendar.views.WeekView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarModel {
    private LocalDate date = LocalDate.now();
    private final MonthView monthView;
    private final WeekView weekView;

    public interface DateObserver {
        void onDateChanged(LocalDate newDate);
    }

    public CalendarModel(DataModel dataModel) {
        this.monthView = new MonthView(dataModel, this);
        this.weekView = new WeekView(dataModel, this);
    }

    private final List<DateObserver> dateObservers = new ArrayList<>();

    public void registerDateObserver(DateObserver observer) {
        dateObservers.add(observer);
    }

    public void decrementMonth() {
        setDate(date.minusMonths(1));
    }

    public void incrementMonth() {
        setDate(date.plusMonths(1));
    }

    public void decrementWeek() {
        setDate(date.minusWeeks(1));
        System.out.println("decrement week called");
    }

    public void incrementWeek() {
        setDate(date.plusWeeks(1));
        System.out.println("Increment week called");
    }


    public void setDate(LocalDate date) {
        this.date = date;

        // Notify all observers
        for (DateObserver observer : dateObservers) {
            observer.onDateChanged(date);
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public MonthView getMonthView() {
        return monthView;
    }

    public WeekView getWeekView() {
        return weekView;
    }
}
