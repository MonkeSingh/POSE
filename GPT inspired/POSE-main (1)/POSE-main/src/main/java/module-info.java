module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.xml;
    opens assets.textures;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}