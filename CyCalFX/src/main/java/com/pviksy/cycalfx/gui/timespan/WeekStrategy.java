package com.pviksy.cycalfx.gui.timespan;

import com.pviksy.cycalfx.gui.calendar.WeekView;

public class WeekStrategy implements TimespanStrategy {

    private final WeekView weekView;

    public WeekStrategy(WeekView weekView) {
        this.weekView = weekView;
    }

    @Override
    public void increment() {

    }

    @Override
    public void decrement() {

    }
}
