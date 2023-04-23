package com.pviksy.cycalfx.gui.timespan.strategies;

import com.pviksy.cycalfx.gui.calendar.views.MonthView;
import com.pviksy.cycalfx.gui.timespan.TimespanStrategy;

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
