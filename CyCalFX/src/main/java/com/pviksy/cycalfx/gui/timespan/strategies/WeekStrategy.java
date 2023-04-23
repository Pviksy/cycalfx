package com.pviksy.cycalfx.gui.timespan.strategies;

import com.pviksy.cycalfx.gui.calendar.views.WeekView;
import com.pviksy.cycalfx.gui.timespan.TimespanStrategy;

public class WeekStrategy implements TimespanStrategy {

    private final WeekView weekView;

    public WeekStrategy(WeekView weekView) {
        this.weekView = weekView;
    }

    @Override
    public void decrement() {
        weekView.decrementWeek();
    }

    @Override
    public void increment() {
        weekView.incrementWeek();
    }
}
