module com.app.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.fontawesome;
    requires org.fxmisc.richtext;
    requires com.google.gson;


    opens com.app.frontend to javafx.fxml;
    exports com.app.frontend;


    exports com.app.frontend.components;
    exports com.app.frontend.models;
    exports com.app.frontend.services;
}