package com.pviksy.cycalfx.gui.calendar;

import com.pviksy.cycalfx.app.Main;
import com.pviksy.cycalfx.data.DataModel;
import com.pviksy.cycalfx.data.entities.Race;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;

public class MonthDayContainer extends VBox {

    private final DataModel dataModel;

    public MonthDayContainer(Label day, List<Race> races, DataModel dataModel) {
        this.dataModel = dataModel;

        VBox allRacesToday = new VBox();
        allRacesToday.setSpacing(10);
        allRacesToday.getStyleClass().add("race-container");

        for (Race race : races) {
            VBox raceInfoContainer = new VBox();

            ImageView flag = createFlag(race);
            Label category = new Label(race.getCategory_id());
            Label name = new Label(race.getName());
            ImageView profileIcon = createProfileIcon(race);
            Label distance = createDistanceLabel(race);

            HBox rowOne = new HBox();
            rowOne.setSpacing(5);
            rowOne.getChildren().addAll(name);

            HBox rowTwo = new HBox();
            rowTwo.setSpacing(5);
            rowTwo.setAlignment(Pos.CENTER_LEFT);
            rowTwo.getChildren().addAll(flag, category, profileIcon, distance);
            HBox.setMargin(rowTwo, new Insets(3, 0, 6, 1));

            raceInfoContainer.getChildren().addAll(rowOne, rowTwo);
            allRacesToday.getChildren().add(raceInfoContainer);
        }

        getChildren().addAll(day, allRacesToday);
        getStyleClass().add("calendar-cell");
        setAlignment(Pos.TOP_CENTER);
    }

    private ImageView createFlag(Race race) {

        ImageView flag = new ImageView();
        String flagImageURL = race.getFlag();
        Image flagImage = dataModel.getImageCache().getImage(flagImageURL);
        flag.setImage(flagImage);
        flag.setFitHeight(16);
        flag.setPreserveRatio(true);

        return flag;
    }

    private ImageView createProfileIcon(Race race) {

        ImageView profileIcon = new ImageView();
        if (race.getProfileIcon() != null) {
            String profileIconImageURL = "https://firstcycling.com/" + race.getProfileIcon();
            Image profileIconImage = dataModel.getImageCache().getImage(profileIconImageURL);
            profileIcon.setImage(profileIconImage);
            profileIcon.setFitHeight(14);
            profileIcon.setPreserveRatio(true);
        }

        return profileIcon;
    }

    private Label createDistanceLabel(Race race) {

        Label distance = new Label();
        if (race.getDistance() != 0) {
            String distanceContent = race.getDistance() + " km";
            distance.setText(distanceContent);
        }

        return distance;
    }

}
