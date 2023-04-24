package com.pviksy.cycalfx.gui.race;

import com.pviksy.cycalfx.app.CenterContentUpdater;

public class RaceViewController {

    // not sure of the exact way to implement this
    private final CenterContentUpdater centerContentUpdater;

    public RaceViewController(CenterContentUpdater centerContentUpdater) {
        this.centerContentUpdater = centerContentUpdater;
    }

    public void updateContent(RaceView raceView) {
        centerContentUpdater.updateCenterContent(raceView);
    }

}
