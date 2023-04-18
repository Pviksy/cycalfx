package com.pviksy.cycalfx.GUI.Calendar;

import com.pviksy.cycalfx.Entities.Race;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MonthDayContainer extends VBox {

    public MonthDayContainer(Label day, List<Race> races) {

        VBox allRacesToday = new VBox();

        for (Race race : races) {
            VBox raceInfoContainer = new VBox();
            raceInfoContainer.setSpacing(5);

            HBox rowOne = new HBox();
            rowOne.setSpacing(5);

            ImageView flag = new ImageView();
            flag.setFitHeight(16);
            flag.setPreserveRatio(true);
            flag.setImage(new Image(race.getFlag()));

            Label category = new Label(race.getCategory_id());
            Label name = new Label(race.getName());

            ImageView logo = new ImageView();
            if (race.getLogo() != null) {
                Image logoImage = new Image(race.getLogo());
                logo.setImage(logoImage);
                logo.setFitWidth(120);
                logo.setPreserveRatio(true);
            }

            rowOne.getChildren().addAll(flag, category);
            raceInfoContainer.getChildren().addAll(rowOne, name, logo);
            allRacesToday.getChildren().add(raceInfoContainer);
        }

        getChildren().addAll(day, allRacesToday);
    }

}
