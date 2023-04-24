package com.pviksy.cycalfx.gui.calendar.views;

import com.pviksy.cycalfx.app.Main;
import com.pviksy.cycalfx.data.DataModel;
import com.pviksy.cycalfx.gui.calendar.CalendarModel;

public class WeekView {

    private final DataModel dataModel;
    private final CalendarModel calendarModel;

    public WeekView(DataModel dataModel, CalendarModel calendarModel) {
        this.dataModel = dataModel;
        this.calendarModel = calendarModel;
    }

    public void updateContent() {
        System.out.println("WeekView updateContent called");
    }
}
