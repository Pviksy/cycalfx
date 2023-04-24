package com.pviksy.cycalfx.gui.timespan;

import javafx.scene.Node;

public interface TimespanStrategy {
    Node getView();
    void increment();
    void decrement();
}
