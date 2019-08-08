package ru.func.ftputil.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTPClient;
import ru.func.ftputil.exceptions.FtpConnectException;
import ru.func.ftputil.exceptions.FtpLoginException;
import ru.func.ftputil.exceptions.FtpSetTypeFileException;

public class Controller extends AbstractLoggedController {

    private static final int SECOND_DEFAULT_PORT = 2221;

    private static final int TYPE_FILE = 2;

    private FTPClient client;

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
    void initialize() {
        openConnectionButton.setOnAction(event -> {
            try {
                if (hostInput.getText().isEmpty()) {
                    return;
                }

                openConnect();
            } catch (FtpConnectException e) {
                log("Ошибка подключения: " + e.getMessage());
                e.printStackTrace();
            } catch (FtpLoginException e) {
                log("Ошибка авторизации: " + e.getMessage());
                e.printStackTrace();
            } catch (FtpSetTypeFileException e) {
                log("Ошибка настройки типа данных: " + e.getMessage());
                e.printStackTrace();
            } finally {
                disconnect();
            }
        });
    }

    public void setClient(FTPClient client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void openConnect() throws FtpConnectException, FtpLoginException, FtpSetTypeFileException {
        log("Попытка соеденения по стандартному порту.");
        connect(hostInput.getText());

        if (login(userInput.getText(), passwordInput.getText())) {
            client.enterLocalPassiveMode();
            setTypeFile();
            client.enterLocalPassiveMode();
            client.setAutodetectUTF8(true);

            FXMLLoader loader;
            try {
                loader = new FXMLLoader(getClass().getResource("/views/app.fxml"));
                loader.load();
            } catch (IOException e) {
                log("Ошибка инициализации окна: " + e.getMessage());
                e.printStackTrace();

                disconnect();
                return;
            }

            openConnectionButton.getScene().getWindow().hide();

            FTPController controller = loader.getController();
            controller.setStage(stage);
            controller.setClient(client);

            Stage stage = new Stage();
            stage.setScene(new Scene(loader.getRoot()));
            stage.showAndWait();
        } else {
            log("Неверные данные.");
        }
    }

    private void connect(String host) throws FtpConnectException {
        try {
            client.connect(host);
        } catch (IOException e1) {
            try {
                client.connect(host, SECOND_DEFAULT_PORT);
            } catch (IOException e2) {
                throw new FtpConnectException(e2);
            }
        }
    }

    private boolean login(String login, String password) throws FtpLoginException {
        try {
            return client.login(login, password);
        } catch (IOException e) {
            throw new FtpLoginException(e);
        }
    }

    private void setTypeFile() throws FtpSetTypeFileException {
        try {
            client.setFileType(TYPE_FILE);
        } catch (IOException e) {
            throw new FtpSetTypeFileException(e);
        }
    }

    private void disconnect() {
        try {
            if (client.isConnected()) {
                client.logout();
                client.disconnect();
            }
        } catch (IOException ex) {
            log("Ошибка авто-отключения: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
