module com.database_project {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.database_project to javafx.fxml;
    exports com.database_project;
}
