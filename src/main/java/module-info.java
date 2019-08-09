module ftputil {
    requires javafx.controls;
    requires javafx.fxml;
    requires slf4j.api;
    requires org.apache.logging.log4j;
    requires commons.net;

    exports ru.func.ftputil;
    opens ru.func.ftputil.controllers to javafx.fxml;
}