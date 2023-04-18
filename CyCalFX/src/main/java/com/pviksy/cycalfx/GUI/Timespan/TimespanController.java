package com.pviksy.cycalfx.GUI.Timespan;

import com.pviksy.cycalfx.Main;
import javafx.scene.control.Label;

public class TimespanController implements SelectTimespan.TimespanSelectionListener {

    private final Main main;

    public TimespanController(Main main) {
        this.main = main;
    }

    @Override
    public void onTimespanSelected(Timespan timespan) {
        System.out.println("Selected timespan: " + timespan);

        switch (timespan) {
            case DAY -> main.updateCenterContent(new Label("Day"));
            case WEEK -> main.updateCenterContent(new Label("Week"));
            case MONTH -> main.updateCenterContent(main.getMonthView());
            case YEAR -> main.updateCenterContent(new Label("Year"));
            default -> System.out.println("Invalid selection");
        }
    }


}
