package com.pviksy.cycalfx.app;

import com.pviksy.cycalfx.data.DataAccessLayer;
import com.pviksy.cycalfx.data.entities.Race;
import com.pviksy.cycalfx.gui.calendar.CalendarModel;
import com.pviksy.cycalfx.gui.calendar.views.MonthView;
import com.pviksy.cycalfx.gui.calendar.views.WeekView;
import com.pviksy.cycalfx.gui.monthselect.MonthSelectMenu;
import com.pviksy.cycalfx.gui.timespan.*;
import com.pviksy.cycalfx.data.ImageCache;
import javafx.application.Application;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Main extends Application implements CalendarModel.DateObserver {

    private final BorderPane root = new BorderPane();
    private final DataAccessLayer db = new DataAccessLayer("CyCalFX23");
    private final ArrayList<Race> races = db.getAllRaces();
    private final ListProperty<Race> filteredRaces = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ImageCache imageCache = db.loadImageCache();
    private final CalendarModel calendarModel = new CalendarModel(this);
    private Label selectedMonth = new Label(String.valueOf(calendarModel.getDate().getMonth()));

    @Override
    public void start(Stage stage) {

        calendarModel.registerDateObserver(this);

        selectedMonth.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        root.setStyle("-fx-background-color: #212832;");
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

        TimespanController timespanController = new TimespanController(this, calendarModel);

        SelectTimespan selectTimespanToggleButton = new SelectTimespan(timespanController);
        TimespanControlButtons timespanControlButtons = new TimespanControlButtons(timespanController);


        HBox topContainer = new HBox();
        topContainer.getChildren().addAll(
                createLeftTop(),
                timespanControlButtons.getDecrementButton(),
                selectTimespanToggleButton,
                timespanControlButtons.getIncrementButton(),
                createRightTop());
        topContainer.setAlignment(Pos.CENTER);
        root.setTop(topContainer);


        Pane left = new Pane();
        left.setStyle("-fx-background-color: transparent;");
        left.setMinWidth(200); //to center the calendar
        root.setLeft(left);
        root.setRight(new MonthSelectMenu());
        Scene scene = new Scene(root);
        setStage(stage, scene);
    }

    private void setStage(Stage stage, Scene scene) {
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("CyCalFX");
        stage.getIcons().add(new Image("file:src/main/resources/media/calendar.png"));
        stage.show();
    }

    public void updateCenterContent(Node node) {
        root.setCenter(node);
    }

    private HBox createLeftTop() {
        HBox leftTop = new HBox();
        leftTop.setMinWidth(300);
        leftTop.setAlignment(Pos.CENTER);
        leftTop.getChildren().add(selectedMonth);

        return leftTop;
    }

    private Label label = new Label("Initial text");

    private HBox createRightTop() {
        HBox rightTop = new HBox();
        rightTop.setMinWidth(300);
        rightTop.setAlignment(Pos.CENTER);

        rightTop.getChildren().add(label);

        return rightTop;
    }

    public void updateTopRightLabel(String text) {
        label.setText(text);
    }

    public ArrayList<Race> getRaces() {
        return races;
    }

    public ImageCache getImageCache() {
        return imageCache;
    }


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void onDateChanged(LocalDate newDate) {
        selectedMonth.setText(String.valueOf(newDate.getMonth()));
    }
}