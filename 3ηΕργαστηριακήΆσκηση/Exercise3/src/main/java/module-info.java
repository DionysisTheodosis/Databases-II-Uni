module com.icsd.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires javafx.swing;
    requires javafx.media;
    requires javafx.web;
    requires org.xerial.sqlitejdbc;
    requires java.base;
    
    opens com.icsd.main to javafx.fxml, javafx.graphics;
    opens com.icsd.controller to javafx.fxml; 
    opens com.icsd.dao to org.xerial.sqlitejdbc;
    exports com.icsd.main;
}