/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.control.Button
 *  javafx.scene.control.PasswordField
 *  javafx.scene.control.TextArea
 *  javafx.scene.control.TextField
 *  javafx.stage.Stage
 *  javafx.stage.Window
 */
package ru.func.ftputil;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.net.ftp.FTPClient;
import ru.func.ftputil.Main;

public class Controller {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
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
            FTPClient client = Main.getInstance().getClient();
            try {
                if (this.hostInput.getText().isEmpty()) {
                    return;
                }
                this.loggerView.appendText(this.getPreDate() + "Попытка соеденения по стандартному порту.");
                try {
                    Main.getInstance().getClient().connect(this.hostInput.getText());
                    return;
                }
                catch (Exception ignored) {
                    client.connect(this.hostInput.getText(), 2221);
                    return;
                }
                finally {
                    if (client.login(this.userInput.getText(), this.passwordInput.getText())) {
                        client.enterLocalPassiveMode();
                        client.setFileType(2);
                        client.enterLocalPassiveMode();
                        client.setAutodetectUTF8(true);
                        this.openConnectionButton.getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(this.getClass().getResource("/ru/func/ftputil/app.fxml"));
                        loader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene((Parent)loader.getRoot()));
                        stage.showAndWait();
                    } else {
                        this.loggerView.appendText(this.getPreDate() + "Неверные данные.");
                    }
                }
            }
            catch (IOException ex) {
                this.loggerView.appendText(this.getPreDate() + "Ошибка подключения: " + ex.getMessage());
                ex.printStackTrace();
                return;
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
                }
            }
        }
        );
    }

    private String getPreDate() {
        Date date = new Date();
        return "\n[" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + "] ";
    }
}

