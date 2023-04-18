package com.pviksy.cycalfx.GUI.Calendar;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class SwitchMonth extends HBox {

    public SwitchMonth() {
        Button decrement = new Button("Decrement");
        Button increment = new Button("Increment");

        getChildren().addAll(decrement, increment);
    }


}
