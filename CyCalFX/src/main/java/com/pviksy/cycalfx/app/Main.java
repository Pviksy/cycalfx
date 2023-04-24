package com.pviksy.cycalfx.app;

import com.pviksy.cycalfx.data.DataModel;
import com.pviksy.cycalfx.data.entities.Race;
import com.pviksy.cycalfx.gui.Top;
import com.pviksy.cycalfx.gui.calendar.CalendarModel;
import com.pviksy.cycalfx.gui.race.RaceSelectionListener;
import com.pviksy.cycalfx.gui.race.RaceView;
import com.pviksy.cycalfx.gui.race.RaceViewController;
import com.pviksy.cycalfx.gui.timespan.TimespanController;
import javafx.application.Application;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application implements CenterContentUpdater, RaceSelectionListener {

    private final BorderPane root = new BorderPane();
    private final CalendarModel calendarModel = new CalendarModel(new DataModel());

    @Override
    public void start(Stage stage) {

        TimespanController timespanController = new TimespanController(this, calendarModel);

        Top top = new Top(calendarModel, timespanController);
        root.setTop(top);

        setStage(stage, root);
    }

    private void setStage(Stage stage, Parent root) {
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

        Scene scene = new Scene(root);

        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("CyCalFX");
        stage.getIcons().add(new Image("file:src/main/resources/media/calendar.png"));
        stage.show();
    }

    @Override
    public void onRaceSelected(Race race) {

        // removed other related code 24/4

        RaceView raceView = new RaceView(race);
        updateCenterContent(raceView);
    }

    public void updateCenterContent(Node node) {
        root.setCenter(node);
    }

    public static void main(String[] args) {
        launch();
    }

}