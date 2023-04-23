package com.pviksy.cycalfx.gui.timespan;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class TimespanControlButtons {

    private Button decrementButton;
    private Button incrementButton;
    private TimespanController timespanController;

    public TimespanControlButtons(TimespanController timespanController) {
        decrementButton = new Button("Decrement");
        incrementButton = new Button("Increment");

        decrementButton.setOnAction(event -> {
            timespanController.getTimespanStrategy().decrement();
        });

        incrementButton.setOnAction(event -> {
            timespanController.getTimespanStrategy().increment();
        });
    }

    // getters allow the buttons to be retrieved seperately so they can be placed on either side of the top center thing
    public Button getDecrementButton() {
        return decrementButton;
    }

    public Button getIncrementButton() {
        return incrementButton;
    }
}
