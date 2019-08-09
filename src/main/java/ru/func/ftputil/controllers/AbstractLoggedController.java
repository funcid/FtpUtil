package ru.func.ftputil.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.slf4j.Logger;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public abstract class AbstractLoggedController {

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());

    @FXML
    protected TextArea loggerView;

    private Logger logger;

    public void log(String text) {
        logger.info(text);
        loggerView.appendText("[" + DTF.format(Instant.now()) + "] " + text + "\n");
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
