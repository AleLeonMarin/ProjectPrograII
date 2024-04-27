module cr.ac.una {
    requires javafx.controls;
    requires javafx.fxml;

    opens cr.ac.una to javafx.fxml;
    exports cr.ac.una;
}
