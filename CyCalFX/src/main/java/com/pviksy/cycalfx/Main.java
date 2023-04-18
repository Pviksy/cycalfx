package com.pviksy.cycalfx;

import com.pviksy.cycalfx.GUI.*;
import com.pviksy.cycalfx.GUI.Calendar.CalendarGrid;
import com.pviksy.cycalfx.GUI.Timespan.*;
import com.pviksy.cycalfx.Service.Dater;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private final BorderPane root = new BorderPane();

    @Override
    public void start(Stage stage) {

        int initialMonth = Dater.getCurrentMonthNumber();
        Top top = new Top(initialMonth, this::updateCenterContent);
        //root.setTop(top); // switch between months here

        SelectTimespan toggleTimespan = new SelectTimespan(new TimespanController(this));
        root.setTop(toggleTimespan);

        CalendarGrid calendarGrid = new CalendarGrid();
        root.setCenter(calendarGrid);

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