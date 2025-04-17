module com.dashboard.desktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.dashboard.desktopapp to javafx.fxml;
    exports com.dashboard.desktopapp;
    opens com.dashboard.desktopapp.components to javafx.fxml;
    exports com.dashboard.desktopapp.components to javafx.fxml;
}