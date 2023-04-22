package com.pviksy.cycalfx;

import com.pviksy.cycalfx.Entities.Race;
import com.pviksy.cycalfx.GUI.Calendar.MonthView;
import com.pviksy.cycalfx.GUI.MonthSelectMenu.MonthSelectMenu;
import com.pviksy.cycalfx.GUI.Timespan.*;
import com.pviksy.cycalfx.Service.ImageCache;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Objects;

public class Main extends Application {

    private final BorderPane root = new BorderPane();
    private final DataAccessLayer db = new DataAccessLayer("CyCalFX23");
    private final ArrayList<Race> races = db.getAllRaces();
    private final ImageCache imageCache = db.loadImageCache();
    private final MonthView monthView = new MonthView(this);

    @Override
    public void start(Stage stage) {
        root.setStyle("-fx-background-color: #212832;");
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());


        SelectTimespan selectTimespanToggleButton = new SelectTimespan(new TimespanController(this));

        VBox topContainer = new VBox();
        topContainer.getChildren().addAll(selectTimespanToggleButton, createIncAndDecButtons(monthView));
        root.setTop(topContainer);

        root.setCenter(monthView);
        root.setRight(new MonthSelectMenu());
        Pane left = new Pane();
        left.setStyle("-fx-background-color: transparent;");
        left.setMinWidth(200); //to center the calendar
        root.setLeft(left);

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

    private HBox createIncAndDecButtons(MonthView monthView) {
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

        HBox buttons = new HBox(decrement, increment);

        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(0, 0, 10, 0));

        return buttons;
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
}