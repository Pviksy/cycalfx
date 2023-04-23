package com.pviksy.cycalfx.gui.calendar;

public class WeekView {


    public void incrementWeek() {
        setDate(date.plusMonths(1));
        updateContent(main.getRaces());

        System.out.println(date);
    }

    public void decrementWeek() {
        setDate(date.minusWeeks(1));
        updateContent(main.getRaces());

        System.out.println(date);
    }
}
