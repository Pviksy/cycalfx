package com.pviksy.cycalfx;

import com.pviksy.cycalfx.Entities.Race;
import com.pviksy.cycalfx.GUI.Calendar.MonthView;
import com.pviksy.cycalfx.GUI.Timespan.*;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class Main extends Application {

    private final BorderPane root = new BorderPane();

    private final DataAccessLayer db = new DataAccessLayer("CyCalFX23");
    private final ArrayList<Race> races = db.getOneDayRaces();

    private MonthView monthView = new MonthView(races);

    @Override
    public void start(Stage stage) {
        root.setStyle("-fx-background-color: #212832;");
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());


        SelectTimespan selectTimespanToggleButton = new SelectTimespan(new TimespanController(this));
        root.setTop(selectTimespanToggleButton);

        root.setCenter(monthView);
        createIncAndDecButtons(monthView);


        Scene scene = new Scene(root, 800, 600);
        setStage(stage, scene);
    }

    private void setStage(Stage stage, Scene scene) {
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("CyCalFX");
        stage.getIcons().add(new Image("file:src/main/resources/media/calendar.png"));
        stage.show();
    }

    private void updateCenterContent() {
        // instance of monthView (monthview) should not be here. perhaps it needs to be in TimespanController but not sure

        MonthView monthView = new MonthView(races);

        root.setCenter(monthView); // grid of the new month
    }

    public void updateCenterContent(Node node) {
        root.setCenter(node);
    }

    private void createIncAndDecButtons(MonthView monthView) {
        Button decrement = new Button("Decrement");
        Button increment = new Button("Increment");

        decrement.setOnAction(event -> {
            monthView.decrementMonth();
            root.setCenter(monthView);
        });

        increment.setOnAction(event -> {
            monthView.incrementMonth();
            root.setCenter(monthView);
        });

        root.setLeft(decrement);
        root.setRight(increment);
    }

    public ArrayList<Race> getRaces() {
        return races;
    }

    public MonthView getMonthView() {
        return monthView;
    }

    public static void main(String[] args) {
        launch();
    }
}