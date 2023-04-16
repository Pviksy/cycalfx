package com.pviksy.cycalfx;

import com.pviksy.cycalfx.GUI.*;
import com.pviksy.cycalfx.GUI.Timespan.*;
import com.pviksy.cycalfx.Service.Dater;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    BorderPane root = new BorderPane();

    @Override
    public void start(Stage stage) throws IOException {

        int initialMonth = Dater.getCurrentMonthNumber();
        Top top = new Top(initialMonth, this::updateCenterContent);
        //root.setTop(top); // switch between months here
        CalendarGrid calendarGrid = new CalendarGrid();
        //root.setCenter(calendarGrid);

        SelectTimespan toggleTimespan = new SelectTimespan(new TimespanController());
        root.setTop(toggleTimespan);


        root.setStyle("-fx-background-color: #212832;");
        root.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("CyCalFX");
        stage.show();
    }

    private void updateCenterContent() {
        CalendarGrid calendarGrid = new CalendarGrid();

        root.setCenter(calendarGrid); // grid of the new month
    }

    public static void main(String[] args) {
        launch();
    }
}