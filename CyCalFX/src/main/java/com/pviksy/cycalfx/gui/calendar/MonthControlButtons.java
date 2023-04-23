package com.pviksy.cycalfx.gui.calendar;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class MonthControlButtons extends HBox {

    public MonthControlButtons() {
        Button decrement = new Button("Decrement");
        Button increment = new Button("Increment");

        getChildren().addAll(decrement, increment);
    }
}
