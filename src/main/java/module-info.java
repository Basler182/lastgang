module eu.schk.lastgang {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens eu.schk.lastgang to javafx.fxml;
    exports eu.schk.lastgang;
    exports eu.schk.lastgang.model;
    opens eu.schk.lastgang.model to javafx.fxml;
    exports eu.schk.lastgang.util;
    opens eu.schk.lastgang.util to javafx.fxml;
    exports eu.schk.lastgang.controller;
    opens eu.schk.lastgang.controller to javafx.fxml;
}