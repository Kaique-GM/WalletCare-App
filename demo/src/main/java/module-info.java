module com.financeX {
    requires javafx.controls;
    requires java.sql;
    requires javafx.fxml;

    opens com.financeX to javafx.fxml;
    opens com.financeX.controllers.Entries to javafx.fxml;
    opens com.financeX.controllers.Expenses to javafx.fxml;
    opens com.financeX.controllers.MenuButtons to javafx.fxml;
    opens com.financeX.controllers.Validation to javafx.fxml;
    opens com.financeX.controllers.MainViews to javafx.fxml;

    opens com.financeX.model.entities to javafx.base;

    exports com.financeX;
}
