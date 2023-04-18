package com.pviksy.cycalfx;

import com.pviksy.cycalfx.GUI.Calendar.CalendarGrid;

public class Controller {

    DataAccessLayer db = new DataAccessLayer("CyCalFX23");

    public CalendarGrid loadCalendarGrid() {


        return new CalendarGrid();
    }
}
