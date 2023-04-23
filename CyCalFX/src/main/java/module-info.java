module com.pviksy.cycalfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires jtds;

    exports com.pviksy.cycalfx.gui.timespan;
    exports com.pviksy.cycalfx.gui.calendar;

    exports com.pviksy.cycalfx.data;
    opens com.pviksy.cycalfx.data to javafx.fxml;

    exports com.pviksy.cycalfx.data.entities;
    opens com.pviksy.cycalfx.data.entities to javafx.fxml;

    exports com.pviksy.cycalfx.app;
    opens com.pviksy.cycalfx.app to javafx.fxml;
}