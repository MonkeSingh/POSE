module com.example.challangeweek {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.challangeweek to javafx.fxml;
    exports com.example.challangeweek;
}