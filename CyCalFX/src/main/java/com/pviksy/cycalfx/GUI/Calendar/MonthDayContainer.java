package com.pviksy.cycalfx.GUI.Calendar;

import com.pviksy.cycalfx.Main;
import com.pviksy.cycalfx.Entities.Race;
import com.pviksy.cycalfx.Service.ImageCache;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;

public class MonthDayContainer extends VBox {

    public MonthDayContainer(Label day, List<Race> races, Main main) {

        VBox allRacesToday = new VBox();
        allRacesToday.setSpacing(10);

        for (Race race : races) {
            VBox raceInfoContainer = new VBox();
            raceInfoContainer.setSpacing(1);
            //raceInfoContainer.setStyle("-fx-background-color: #222831;");

            ImageView flag = new ImageView();
            String flagImageURL = race.getFlag();
            Image flagImage = main.getImageCache().getImage(flagImageURL);
            flag.setImage(flagImage);
            flag.setFitHeight(16);
            flag.setPreserveRatio(true);

            Label category = new Label(race.getCategory_id());

            HBox rowOne = new HBox();
            rowOne.setSpacing(5);
            rowOne.getChildren().addAll(flag, category);


            ImageView profileIcon = new ImageView();
            if (race.getProfileIcon() != null) {
                String profileIconImageURL = "https://firstcycling.com/" + race.getProfileIcon();
                Image profileIconImage = main.getImageCache().getImage(profileIconImageURL);
                profileIcon.setImage(profileIconImage);
                profileIcon.setFitHeight(14);
                profileIcon.setPreserveRatio(true);
            }

            Label distance = new Label();
            if (race.getDistance() != 0) {
                String distanceContent = race.getDistance() + " km";
                distance.setText(distanceContent);
            }

            HBox rowTwo = new HBox();
            rowTwo.setSpacing(5);
            HBox.setMargin(profileIcon, new Insets(1, 0, 0, 1));
            rowTwo.getChildren().addAll(profileIcon, distance);


            Label name = new Label(race.getName());

            raceInfoContainer.getChildren().addAll(rowOne, name, rowTwo);
            allRacesToday.getChildren().add(raceInfoContainer);
        }

        getChildren().addAll(day, allRacesToday);
    }

}
