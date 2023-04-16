package com.pviksy.cycalfx.GUI.Timespan;

public class TimespanController implements TimespanSelectionListener {
    @Override
    public void onTimespanSelected(String timespan) {
        System.out.println("Selected timespan: " + timespan);
        // Update the contents of another container or take other actions
    }
}
