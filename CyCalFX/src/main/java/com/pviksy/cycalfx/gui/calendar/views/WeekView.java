package com.pviksy.cycalfx.gui.calendar.views;

import com.pviksy.cycalfx.app.Main;
import com.pviksy.cycalfx.gui.calendar.CalendarModel;

public class WeekView {


    private final Main main;
    private final CalendarModel calendarModel;

    public WeekView(Main main, CalendarModel calendarModel) {
        this.main = main;
        this.calendarModel = calendarModel;
    }

    public void updateContent() {
        main.updateTopRightLabel(calendarModel.getDate().toString());
        System.out.println("WeekView updateContent called");
    }
}
