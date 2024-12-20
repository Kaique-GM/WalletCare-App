module com.financeX {
    requires javafx.controls;
    requires java.sql;
    requires javafx.fxml;

    opens com.financeX to javafx.fxml;
    opens com.financeX.controllers to javafx.fxml;
    opens com.financeX.controllers.Entries to javafx.fxml;
    opens com.financeX.controllers.Expenses to javafx.fxml;
    exports com.financeX;
}
