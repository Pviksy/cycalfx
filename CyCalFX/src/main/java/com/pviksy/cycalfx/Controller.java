package com.pviksy.cycalfx;

import com.pviksy.cycalfx.GUI.CalendarGrid;

public class Controller {

    DataAccessLayer db = new DataAccessLayer("CyCalFX23");

    public CalendarGrid loadCalendarGrid() {


        return new CalendarGrid();
    }
}
