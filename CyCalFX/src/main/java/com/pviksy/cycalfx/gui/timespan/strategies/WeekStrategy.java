package com.pviksy.cycalfx.gui.timespan.strategies;

import com.pviksy.cycalfx.gui.calendar.CalendarModel;
import com.pviksy.cycalfx.gui.calendar.views.WeekView;
import com.pviksy.cycalfx.gui.timespan.TimespanStrategy;
import javafx.scene.Node;

public class WeekStrategy implements TimespanStrategy {

    private final CalendarModel calendarModel;

    public WeekStrategy(CalendarModel calendarModel) {
        this.calendarModel = calendarModel;
    }

    @Override
    public Node getView() {
        return calendarModel.getWeekView();
    }

    @Override
    public void decrement() {
        calendarModel.decrementWeek();
    }

    @Override
    public void increment() {
        calendarModel.incrementWeek();
    }
}
