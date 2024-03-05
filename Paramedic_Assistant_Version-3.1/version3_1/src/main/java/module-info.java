module paramedic_assistant_version_3_1 {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens paramedic_assistant_version_3_1 to javafx.fxml;
    exports paramedic_assistant_version_3_1;
}
