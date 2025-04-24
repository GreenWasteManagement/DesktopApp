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
    requires static lombok;
    requires org.json;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;
    requires jakarta.validation;

    opens com.dashboard.desktopapp to javafx.fxml;
    opens com.dashboard.desktopapp.components to javafx.fxml;
    opens com.dashboard.desktopapp.models to javafx.base;
    opens com.dashboard.desktopapp.dtos.user.request to com.fasterxml.jackson.databind;
    exports com.dashboard.desktopapp;
    exports com.dashboard.desktopapp.components to javafx.fxml;
    exports com.dashboard.desktopapp.models to javafx.base;
    exports com.dashboard.desktopapp.dtos.user.request;
    exports com.dashboard.desktopapp.dtos.user.response;
    exports com.dashboard.desktopapp.dtos.bucket.request;
    exports com.dashboard.desktopapp.dtos.bucket.response;
    exports com.dashboard.desktopapp.dtos.container.request;
    exports com.dashboard.desktopapp.dtos.container.response;
    exports com.dashboard.desktopapp.dtos.base;

}