package com.pviksy.cycalfx;

import com.pviksy.cycalfx.GUI.Calendar.MonthView;

public class Controller {

    DataAccessLayer db = new DataAccessLayer("CyCalFX23");

    public MonthView loadCalendarGrid() {


        return new MonthView(null);
    }
}
