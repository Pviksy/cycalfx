package com.pviksy.cycalfx;

import com.pviksy.cycalfx.Entities.*;
import com.pviksy.cycalfx.GUI.*;
import com.pviksy.cycalfx.Service.Dater;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    BorderPane root = new BorderPane();

    @Override
    public void start(Stage stage) throws IOException {

        int initialMonth = Dater.getCurrentMonthNumber();
        Top top = new Top(initialMonth, this::updateCenterContent);
        //root.setTop(top); // switch between months here
        CalendarGrid calendarGrid = new CalendarGrid();
        //root.setCenter(calendarGrid);

        TimespanController timespanController = new TimespanController();
        SelectTimespan toggleTimespan = new SelectTimespan(timespanController);
        AnchorPane wrapper = new AnchorPane(toggleTimespan);
        AnchorPane.setTopAnchor(toggleTimespan, 10.0);
        AnchorPane.setLeftAnchor(toggleTimespan, 10.0);
        root.setCenter(wrapper);


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