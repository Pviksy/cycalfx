module com.pviksy.cycalfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires jtds;

    opens com.pviksy.cycalfx to javafx.fxml;
    exports com.pviksy.cycalfx;
    exports com.pviksy.cycalfx.GUI;
    exports com.pviksy.cycalfx.Entities;
}