package com.pviksy.cycalfx.gui.timespan;

import com.pviksy.cycalfx.gui.calendar.MonthView;

public class MonthStrategy implements TimespanStrategy {

    private final MonthView monthView;

    public MonthStrategy(MonthView monthView) {
        this.monthView = monthView;
    }

    @Override
    public void increment() {
        monthView.incrementMonth();
    }

    @Override
    public void decrement() {
        monthView.decrementMonth();
    }
}
