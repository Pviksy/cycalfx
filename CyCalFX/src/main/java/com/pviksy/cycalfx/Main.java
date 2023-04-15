package com.pviksy.cycalfx;

import com.pviksy.cycalfx.Entities.*;
import com.pviksy.cycalfx.GUI.*;
import com.pviksy.cycalfx.Service.Dater;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    BorderPane root = new BorderPane();

    @Override
    public void start(Stage stage) throws IOException {


        int initialMonth = Dater.getCurrentMonthNumber();
        Top top = new Top(initialMonth, this::updateCenterContent);

        root.setTop(top); // switch between months here
        root.setBottom(new Label("Bottom"));
        root.setLeft(new Label("Left"));
        root.setRight(new Label("Right"));

        CalendarGrid calendarGrid = new CalendarGrid();
        root.setCenter(calendarGrid);

        //DataAccessLayer db = new DataAccessLayer("CyCalFX23");
        //ArrayList<Race> races = db.getRaces(1, 2023);
        //for (Race race : races) {
        //    System.out.println(race);
        //}

        root.setStyle("-fx-background-color: #333333;");
        root.getStylesheets().add(getClass().getResource("/resono.css").toExternalForm());

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