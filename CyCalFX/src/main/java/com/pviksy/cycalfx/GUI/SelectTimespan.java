package com.pviksy.cycalfx.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class SelectTimespan extends HBox {

    // note: its possible to untoggle the button by pressing it when its highlighted

    private TimespanSelectionListener listener;

    public SelectTimespan(TimespanSelectionListener listener) {
        this.listener = listener;
        this.setAlignment(Pos.CENTER);
        this.getStyleClass().add("toggle-container");

        ToggleGroup group = new ToggleGroup();

        ToggleButton dayButton = createToggleButton("Day", group);
        ToggleButton weekButton = createToggleButton("Week", group);
        ToggleButton monthButton = createToggleButton("Month", group);
        ToggleButton yearButton = createToggleButton("Year", group);

        this.getChildren().addAll(dayButton, weekButton, monthButton, yearButton);

    }

    private ToggleButton createToggleButton(String text, ToggleGroup group) {
        ToggleButton button = new ToggleButton(text);
        button.setToggleGroup(group);
        button.getStyleClass().add("custom-toggle-button");

        button.selectedProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                listener.onTimespanSelected(text);
            }
        });

        return button;
    }
}
