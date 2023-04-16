package com.pviksy.cycalfx.GUI.Timespan;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class SelectTimespan extends HBox {

    // note: its possible to untoggle the button by pressing it when its highlighted

    private TimespanSelectionListener listener;

    public SelectTimespan(TimespanSelectionListener listener) {
        this.listener = listener;

        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.getStyleClass().add("toggle-container");

        ToggleGroup group = new ToggleGroup();
        ToggleButton dayButton = createToggleButton("Day", group);
        ToggleButton weekButton = createToggleButton("Week", group);
        ToggleButton monthButton = createToggleButton("Month", group);
        ToggleButton yearButton = createToggleButton("Year", group);

        container.getChildren().addAll(dayButton, weekButton, monthButton, yearButton);
        getChildren().add(container);

        /*
        was wrapped in anchorpane but seemed unnecesary. forgot why it was necessary earlier
        AnchorPane anchorPane = new AnchorPane(container);
        AnchorPane.setTopAnchor(container, 10.0);
        AnchorPane.setLeftAnchor(container, 10.0);
         */

        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));
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
