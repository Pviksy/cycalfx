package com.pviksy.cycalfx;

import com.pviksy.cycalfx.Entities.Race;
import com.pviksy.cycalfx.GUI.Calendar.MonthView;
import com.pviksy.cycalfx.GUI.MonthSelectMenu.MonthSelectMenu;
import com.pviksy.cycalfx.GUI.Timespan.*;
import com.pviksy.cycalfx.Service.ImageCache;
import javafx.application.Application;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Main extends Application implements MonthView.DateObserver {

    private final BorderPane root = new BorderPane();
    private final DataAccessLayer db = new DataAccessLayer("CyCalFX23");
    private final ArrayList<Race> races = db.getAllRaces();
    private final ListProperty<Race> filteredRaces = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ImageCache imageCache = db.loadImageCache();
    private final MonthView monthView = new MonthView(this);
    private Label selectedMonth = new Label(String.valueOf(monthView.getDate().getMonth()));

    @Override
    public void start(Stage stage) {
        monthView.registerDateObserver(this);
        root.setStyle("-fx-background-color: #212832;");
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());


        SelectTimespan selectTimespanToggleButton = new SelectTimespan(new TimespanController(this));

        HBox topContainer = new HBox();
        topContainer.getChildren().addAll(
                createLeftTop(),
                createDecButton(monthView),
                selectTimespanToggleButton,
                createIncButton(monthView),
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

    private Button createDecButton(MonthView monthView) {
        Button decrement = new Button("Decrement");

        decrement.setOnAction(event -> {
            monthView.decrementMonth();
            root.setCenter(monthView);
        });

        return decrement;
    }

    private Button createIncButton(MonthView monthView) {
        Button increment = new Button("Increment");

        increment.setOnAction(event -> {
            monthView.incrementMonth();
            root.setCenter(monthView);
        });

        return increment;
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