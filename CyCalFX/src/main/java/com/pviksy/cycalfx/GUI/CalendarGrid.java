package com.pviksy.cycalfx.GUI;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class CalendarGrid extends GridPane {

    /*
    In the constructor, it can be determined how many weeks etc the grid
    should be able to contain. This can depend on the information retrieved
    from the database, or be more static.

     */

    public CalendarGrid() {
        Random rnd = new Random();

        int numRows = 6;
        int numCols = 7;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(75, 75);

                // Generate a random color for the cell
                Color color = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                Rectangle rect = new Rectangle(75, 75, color);
                cell.getChildren().add(rect);

                // Add the cell to the grid pane
                add(cell, col, row);
            }
        }

        this.setAlignment(Pos.TOP_CENTER);
    }

}
