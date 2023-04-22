package com.pviksy.cycalfx.GUI.Calendar;

import com.pviksy.cycalfx.Main;
import com.pviksy.cycalfx.Entities.Race;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;

public class MonthDayContainer extends VBox {

    private final Main main;

    public MonthDayContainer(Label day, List<Race> races, Main main) {
        this.main = main;

        VBox allRacesToday = new VBox();
        allRacesToday.setSpacing(10);

        for (Race race : races) {
            VBox raceInfoContainer = new VBox();
            //raceInfoContainer.setStyle("-fx-background-color: #222831;");

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
    }

    private ImageView createFlag(Race race) {

        ImageView flag = new ImageView();
        String flagImageURL = race.getFlag();
        Image flagImage = main.getImageCache().getImage(flagImageURL);
        flag.setImage(flagImage);
        flag.setFitHeight(16);
        flag.setPreserveRatio(true);

        return flag;
    }

    private ImageView createProfileIcon(Race race) {

        ImageView profileIcon = new ImageView();
        if (race.getProfileIcon() != null) {
            String profileIconImageURL = "https://firstcycling.com/" + race.getProfileIcon();
            Image profileIconImage = main.getImageCache().getImage(profileIconImageURL);
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
