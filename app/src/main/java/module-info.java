module com.example.app {
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
    requires java.desktop;
    requires java.sql;

    opens com.example.app.Controllers to javafx.fxml;
    opens com.example.app.Controllers.Admin to javafx.fxml;
    opens com.example.app.Controllers.Lecturer to javafx.fxml;

    opens com.example.app to javafx.fxml;
    exports com.example.app;
    exports com.example.app.Controllers.Admin to javafx.fxml;
}