package ru.func.ftputil;

import java.io.IOException;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTPClient;

public class Controller {

    private FTPClient client;

    private Stage stage;

    @FXML
    private TextArea loggerView;

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

                loggerView.appendText(getPreDate() + "Попытка соеденения по стандартному порту.");

                try {
                    client.connect(hostInput.getText());
                } catch (Exception ignored) {
                    client.connect(hostInput.getText(), 2221);
                } finally {
                    if (client.login(userInput.getText(), passwordInput.getText())) {
                        client.enterLocalPassiveMode();
                        client.setFileType(2);
                        client.enterLocalPassiveMode();
                        client.setAutodetectUTF8(true);

                        openConnectionButton.getScene().getWindow().hide();

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/app.fxml"));
                        loader.load();

                        FTPController controller = loader.getController();
                        controller.setStage(stage);
                        controller.setClient(client);

                        Stage stage = new Stage();
                        stage.setScene(new Scene(loader.getRoot()));
                        stage.showAndWait();
                    } else {
                        loggerView.appendText(getPreDate() + "Неверные данные.");
                    }
                }
            } catch (IOException ex) {
                loggerView.appendText(getPreDate() + "Ошибка подключения: " + ex.getMessage());
                ex.printStackTrace();
            }
            finally {
                try {
                    if (client.isConnected()) {
                        client.logout();
                        client.disconnect();
                    }
                } catch (IOException ex) {
                    loggerView.appendText(getPreDate() + "Ошибка авто-выхода: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
    }

    public void setClient(FTPClient client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private String getPreDate() {
        Date date = new Date();
        return "\n[" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + "] ";
    }
}
