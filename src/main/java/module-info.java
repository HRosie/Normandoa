module com.example.normand {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.normand to javafx.fxml;
    exports com.example.normand;

    exports com.example.normand.Controllers.SceneController.Others;
    opens com.example.normand.Controllers.SceneController.Others to javafx.fxml;

    exports com.example.normand.Controllers.SceneController.Owner;
    opens com.example.normand.Controllers.SceneController.Owner to javafx.fxml;

    exports com.example.normand.Models;
    opens com.example.normand.Models to javafx.fxml;

}