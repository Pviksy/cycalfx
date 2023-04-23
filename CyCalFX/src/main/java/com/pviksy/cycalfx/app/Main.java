package com.pviksy.cycalfx.app;

import com.pviksy.cycalfx.data.DataAccessLayer;
import com.pviksy.cycalfx.data.entities.Race;
import com.pviksy.cycalfx.gui.calendar.CalendarModel;
import com.pviksy.cycalfx.gui.calendar.MonthView;
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
import javafx.scene.control.Button;
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
    private final CalendarModel calendarModel = new CalendarModel();
    private final MonthView monthView = new MonthView(this, calendarModel);
    private Label selectedMonth = new Label(String.valueOf(calendarModel.getDate().getMonth()));

    @Override
    public void start(Stage stage) {

        calendarModel.registerDateObserver(this);

        selectedMonth.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        root.setStyle("-fx-background-color: #212832;");
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());


        SelectTimespan selectTimespanToggleButton = new SelectTimespan(new TimespanController(this));

        TimespanStrategy strategy = new MonthStrategy(monthView);
        TimespanControlButtons timespanControlButtons = new TimespanControlButtons(strategy);

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

    private HBox createRightTop() {
        HBox rightTop = new HBox();
        rightTop.setMinWidth(300);
        rightTop.setAlignment(Pos.CENTER);

        rightTop.getChildren().add(new Label("right"));

        return rightTop;
    }

    public ArrayList<Race> getRaces() {
        return races;
    }

    public ImageCache getImageCache() {
        return imageCache;
    }

    public MonthView getMonthView() {
        return monthView;
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void onDateChanged(LocalDate newDate) {
        selectedMonth.setText(String.valueOf(newDate.getMonth()));
    }
}