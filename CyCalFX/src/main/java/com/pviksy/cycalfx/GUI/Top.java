package com.pviksy.cycalfx.GUI;

import com.pviksy.cycalfx.Service.Dater;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class Top extends StackPane {

    private int month;

    public Top(int month, Runnable updateCenterContentCallback) {
        this.month = month;

        Label selectedMonth = new Label();

        String monthName = Dater.getMonthName(month); // should only be loaded the first time the program is run
        selectedMonth.setText(monthName);

        Button previousMonth = new Button();
            previousMonth.setOnAction(event -> {
                this.month = this.month > 1 ? this.month - 1 : 12;
                selectedMonth.setText(Dater.getMonthName(this.month));
                updateCenterContentCallback.run();
            });

        Button nextMonth = new Button();
            nextMonth.setOnAction(event -> {
                this.month = this.month < 12 ? this.month + 1 : 1;
                selectedMonth.setText(Dater.getMonthName(this.month));
                updateCenterContentCallback.run();
            });

        previousMonth.setText("Previous");
        nextMonth.setText("Next");

        HBox monthControls = new HBox();
        monthControls.setAlignment(Pos.CENTER);
        monthControls.setSpacing(5);
        monthControls.getChildren().addAll(
                previousMonth,
                selectedMonth,
                nextMonth);

        this.getChildren().add(monthControls);
    }



}
