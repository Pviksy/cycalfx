package com.pviksy.cycalfx.gui.calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarModel {
    private LocalDate date = LocalDate.now();

    public interface DateObserver {
        void onDateChanged(LocalDate newDate);
    }

    private final List<DateObserver> dateObservers = new ArrayList<>();

    public void registerDateObserver(DateObserver observer) {
        dateObservers.add(observer);
    }

    public void incrementMonth() {
        setDate(date.plusMonths(1));
    }

    public void decrementMonth() {
        setDate(date.minusMonths(1));
    }

    // Implement similar methods for incrementing and decrementing days, weeks, and years.

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
}
