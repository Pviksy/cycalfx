package com.pviksy.cycalfx.gui.timespan;

import com.pviksy.cycalfx.app.CenterContentUpdater;
import com.pviksy.cycalfx.app.Main;
import com.pviksy.cycalfx.gui.calendar.CalendarModel;
import com.pviksy.cycalfx.gui.timespan.strategies.MonthStrategy;
import com.pviksy.cycalfx.gui.timespan.strategies.WeekStrategy;
import javafx.scene.control.Label;

public class TimespanController implements SelectTimespan.TimespanSelectionListener {

    private final CenterContentUpdater centerContentUpdater;
    private final CalendarModel calendarModel;
    private TimespanStrategy timespanStrategy;


    public TimespanController(CenterContentUpdater centerContentUpdater, CalendarModel calendarModel) {
        this.centerContentUpdater = centerContentUpdater;
        this.calendarModel = calendarModel;
    }

    @Override
    public void onTimespanSelected(Timespan timespan) {
        System.out.println("Selected timespan: " + timespan);

        switch (timespan) {
            case DAY -> {
                //timespanStrategy = new DayStrategy(calendarModel);
                centerContentUpdater.updateCenterContent(new Label("Day"));
            }
            case WEEK -> {
                timespanStrategy = new WeekStrategy(calendarModel);
                centerContentUpdater.updateCenterContent(new Label("Week"));
            }
            case MONTH -> {
                timespanStrategy = new MonthStrategy(calendarModel);
                centerContentUpdater.updateCenterContent(calendarModel.getMonthView());
            }
            case YEAR -> {
                //timespanStrategy = new YearStrategy(calendarModel);
                centerContentUpdater.updateCenterContent(new Label("Year"));
            }
            default -> System.out.println("Invalid selection");
        }
    }

    public TimespanStrategy getTimespanStrategy() {
        return timespanStrategy;
    }
}
