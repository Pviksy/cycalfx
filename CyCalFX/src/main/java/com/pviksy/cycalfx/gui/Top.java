package com.pviksy.cycalfx.gui;

import com.pviksy.cycalfx.gui.calendar.CalendarModel;
import com.pviksy.cycalfx.gui.timespan.SelectTimespan;
import com.pviksy.cycalfx.gui.timespan.TimespanControlButtons;
import com.pviksy.cycalfx.gui.timespan.TimespanController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.time.LocalDate;

public class Top extends HBox implements CalendarModel.DateObserver {
    private final Label selectedMonth;

    public Top(CalendarModel calendarModel, TimespanController timespanController) {

        calendarModel.registerDateObserver(this);

        selectedMonth = new Label(String.valueOf(calendarModel.getDate().getMonth()));

        SelectTimespan selectTimespanToggleButton = new SelectTimespan(timespanController);
        TimespanControlButtons timespanControlButtons = new TimespanControlButtons(timespanController);

        setAlignment(Pos.CENTER);
        getChildren().addAll(
                createSelectedMonthContainer(),
                timespanControlButtons.getDecrementButton(),
                selectTimespanToggleButton,
                timespanControlButtons.getIncrementButton(),
                createSelectedMonthContainer());
    }

    private HBox createSelectedMonthContainer() {
        HBox leftTop = new HBox();
        leftTop.setMinWidth(300);
        leftTop.setAlignment(Pos.CENTER);
        leftTop.getChildren().add(selectedMonth);

        return leftTop;
    }

    @Override
    public void onDateChanged(LocalDate newDate) {
        selectedMonth.setText(String.valueOf(newDate.getMonth()));
    }
}