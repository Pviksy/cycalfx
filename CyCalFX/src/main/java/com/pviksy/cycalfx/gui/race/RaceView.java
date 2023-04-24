package com.pviksy.cycalfx.gui.race;

import com.pviksy.cycalfx.data.entities.Race;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RaceView extends VBox {

    private final Race race;

    public RaceView(Race race) {
        this.race = race;

        getChildren().addAll(new Label(race.getName()), new Label(race.getCategory_id()));
    }
}
