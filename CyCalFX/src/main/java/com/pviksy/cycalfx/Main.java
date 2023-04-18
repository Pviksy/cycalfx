package com.pviksy.cycalfx;

import com.pviksy.cycalfx.GUI.*;
import com.pviksy.cycalfx.GUI.Calendar.CalendarGrid;
import com.pviksy.cycalfx.GUI.Calendar.SwitchMonth;
import com.pviksy.cycalfx.GUI.Timespan.*;
import com.pviksy.cycalfx.GUI.Calendar.DateService;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private final BorderPane root = new BorderPane();

    @Override
    public void start(Stage stage) {

        int initialMonth = DateService.getCurrentMonthNumber();
        Top top = new Top(initialMonth, this::updateCenterContent);
        //root.setTop(top); // switch between months here

        SelectTimespan selectTimespanToggleButton = new SelectTimespan(new TimespanController(this));
        root.setTop(selectTimespanToggleButton);

        CalendarGrid calendarGrid = new CalendarGrid();
        root.setCenter(calendarGrid);

        Button decrement = new Button("Decrement");
        Button increment = new Button("Increment");

        decrement.setOnAction(event -> {
            calendarGrid.decrementMonth();
            root.setCenter(calendarGrid);
        });

        increment.setOnAction(event -> {
            calendarGrid.incrementMonth();
            root.setCenter(calendarGrid);
        });

        root.setLeft(decrement);
        root.setRight(increment);




        root.setStyle("-fx-background-color: #212832;");
        root.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        Scene scene = new Scene(root, 800, 600);

        stage.setScene(scene);
        stage.setTitle("CyCalFX");
        stage.getIcons().add(new Image("file:src/main/resources/media/calendar.png"));
        stage.show();
    }

    private void updateCenterContent() {
        CalendarGrid calendarGrid = new CalendarGrid();

        root.setCenter(calendarGrid); // grid of the new month
    }

    public void updateCenterContent(Node node) {
        root.setCenter(node);
    }

    public static void main(String[] args) {
        launch();
    }
}