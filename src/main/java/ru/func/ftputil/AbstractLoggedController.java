package ru.func.ftputil;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public abstract class AbstractLoggedController {

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());

    @FXML
    protected TextArea loggerView;

    void log(String text) {
        loggerView.appendText("[" + DTF.format(Instant.now()) + "] " + text + "\n");
    }
}
