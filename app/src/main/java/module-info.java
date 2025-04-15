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
    requires java.sql;
    
    // Add required modules for image processing
    requires java.desktop;
    requires javafx.swing;

    opens com.example.app to javafx.fxml;
    exports com.example.app;

    // Export Controllers package
    exports com.example.app.Controllers;
    opens com.example.app.Controllers to javafx.fxml;

    // Export controller subpackages
    exports com.example.app.Controllers.TechnicalOfficer;
    opens com.example.app.Controllers.TechnicalOfficer to javafx.fxml;
    
    exports com.example.app.Controllers.Lecturer;
    opens com.example.app.Controllers.Lecturer to javafx.fxml;
    
    exports com.example.app.Controllers.Student;
    opens com.example.app.Controllers.Student to javafx.fxml;
    
    exports com.example.app.Controllers.Admin;
    opens com.example.app.Controllers.Admin to javafx.fxml;
    
    // Open Models package to JavaFX for PropertyValueFactory reflection
    exports com.example.app.Models;
    opens com.example.app.Models to javafx.base, javafx.fxml, javafx.controls;
}