module com.pviksy.cycalfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.pviksy.cycalfx to javafx.fxml;
    exports com.pviksy.cycalfx;
}