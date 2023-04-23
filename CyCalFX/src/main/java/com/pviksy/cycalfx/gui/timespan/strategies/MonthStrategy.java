package com.pviksy.cycalfx.gui.timespan.strategies;

import com.pviksy.cycalfx.gui.calendar.CalendarModel;
import com.pviksy.cycalfx.gui.calendar.views.MonthView;
import com.pviksy.cycalfx.gui.timespan.TimespanStrategy;

public class MonthStrategy implements TimespanStrategy {

    private final CalendarModel calendarModel;

    public MonthStrategy(CalendarModel calendarModel) {
        this.calendarModel = calendarModel;
    }

    @Override
    public void decrement() {
        calendarModel.decrementMonth();
    }

    @Override
    public void increment() {
        calendarModel.incrementMonth();
    }
}
