module com.cmht.cmht {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.j;

    opens com.cmht.main to javafx.fxml;
    opens com.cmht.models to javafx.base;
    exports com.cmht.main;
}