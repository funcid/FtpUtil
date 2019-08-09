package ru.func.ftputil.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.func.ftputil.services.FtpService;
import ru.func.ftputil.services.exceptions.*;

import java.io.IOException;

public class Controller extends AbstractLoggedController {

    private final Logger log = LoggerFactory.getLogger(Controller.class);

    private static final int SECOND_DEFAULT_PORT = 2221;

    private FtpService service;

    private Stage stage;

    @FXML
    private TextField hostInput;

    @FXML
    private Button openConnectionButton;

    @FXML
    private TextField userInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Button clearButton;

    public Controller() {
        setLogger(log);
    }

    @FXML
    void initialize() {
        openConnectionButton.setOnAction(event -> {
            try {
                if (hostInput.getText().isEmpty())
                    return;

                openConnect();
            } catch (final FtpConnectException e) {
                log("Ошибка подключения: " + e.getMessage());
                log.error("", e);
            } catch (final FtpLoginException e) {
                log("Ошибка авторизации: " + e.getMessage());
                log.error("", e);
            } catch (final FtpSetupConnectionException e) {
                log("Ошибка настройки типа данных: " + e.getMessage());
                log.error("", e);
            } catch (final FtpServiceException e) {
                log("Не известная ошибка: " + e.getMessage());
                log.error("", e);
            } finally {
                try {
                    service.disconnect();
                } catch (final FtpDisconnectException e) {
                    log("Ошибка авто-отключения: " + e.getMessage());
                    log.error("", e);
                }
            }
        });

        clearButton.setOnAction(event -> loggerView.clear());
    }

    public void setFtpService(final FtpService service) {
        this.service = service;
    }

    public void setStage(final Stage stage) {
        this.stage = stage;
    }

    private void openConnect() throws FtpServiceException {
        log("Попытка соеденения по стандартному порту.");
        connect(hostInput.getText());

        if (service.login(userInput.getText(), passwordInput.getText())) {
            service.setupConnection();

            final FXMLLoader loader;
            try {
                loader = new FXMLLoader(getClass().getResource("/views/app.fxml"));
                loader.load();
            } catch (IOException e) {
                log("Ошибка инициализации окна: " + e.getMessage());
                log.error("", e);

                service.disconnect();
                return;
            }

            openConnectionButton.getScene().getWindow().hide();

            final FTPController controller = loader.getController();
            controller.setStage(stage);
            controller.setService(service);

            final Stage stage = new Stage();
            stage.setScene(new Scene(loader.getRoot()));
            stage.showAndWait();
        } else
            log("Неверные данные.");
    }

    private void connect(final String host) throws FtpConnectException {
        try {
            service.connect(host);
        } catch (FtpConnectException e1) {
            service.connect(host, SECOND_DEFAULT_PORT);
        }
    }
}
