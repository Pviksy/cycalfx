package com.pviksy.cycalfx.data;

import com.pviksy.cycalfx.app.Main;
import com.pviksy.cycalfx.data.entities.Race;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.util.ArrayList;

public class DataModel {

    private final DataAccessLayer db = new DataAccessLayer("CyCalFX23");
    private final ArrayList<Race> races = db.getAllRaces();
    private final ImageCache imageCache = db.loadImageCache();

    //private final ListProperty<Race> filteredRaces = new SimpleListProperty<>(FXCollections.observableArrayList());

    public ArrayList<Race> getRaces() {
        return races;
    }

    public ImageCache getImageCache() {
        return imageCache;
    }
}
