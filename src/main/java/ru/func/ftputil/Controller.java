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
        this.openConnectionButton.setOnAction(event -> {
            try {
                if (this.hostInput.getText().isEmpty()) {
                    return;
                }
                this.loggerView.appendText(this.getPreDate() + "Попытка соеденения по стандартному порту.");
                try {
                    client.connect(this.hostInput.getText());
                }
                catch (Exception ignored) {
                    client.connect(this.hostInput.getText(), 2221);
                }
                finally {
                    if (client.login(this.userInput.getText(), this.passwordInput.getText())) {
                        client.enterLocalPassiveMode();
                        client.setFileType(2);
                        client.enterLocalPassiveMode();
                        client.setAutodetectUTF8(true);
                        this.openConnectionButton.getScene().getWindow().hide();

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/app.fxml"));
                        loader.load();

                        FTPController controller = loader.getController();
                        controller.setStage(stage);
                        controller.setClient(client);

                        Stage stage = new Stage();
                        stage.setScene(new Scene(loader.getRoot()));
                        stage.showAndWait();
                    } else {
                        this.loggerView.appendText(this.getPreDate() + "Неверные данные.");
                    }
                }
            }
            catch (IOException ex) {
                this.loggerView.appendText(this.getPreDate() + "Ошибка подключения: " + ex.getMessage());
                ex.printStackTrace();
            }
            finally {
                try {
                    if (client.isConnected()) {
                        client.logout();
                        client.disconnect();
                    }
                }
                catch (IOException ex) {
                    this.loggerView.appendText(this.getPreDate() + "Ошибка авто-выхода: " + ex.getMessage());
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

