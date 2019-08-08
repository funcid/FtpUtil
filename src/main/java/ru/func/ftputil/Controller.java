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
                this.loggerView.appendText(this.getPreDate() + "\u041f\u043e\u043f\u044b\u0442\u043a\u0430 \u0441\u043e\u0435\u0434\u0435\u043d\u0435\u043d\u0438\u044f \u043f\u043e \u0441\u0442\u0430\u043d\u0434\u0430\u0440\u0442\u043d\u043e\u043c\u0443 \u043f\u043e\u0440\u0442\u0443.");
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
                        this.loggerView.appendText(this.getPreDate() + "\u041d\u0435\u0432\u0435\u0440\u043d\u044b\u0435 \u0434\u0430\u043d\u043d\u044b\u0435.");
                    }
                }
            }
            catch (IOException ex) {
                this.loggerView.appendText(this.getPreDate() + "\u041e\u0448\u0438\u0431\u043a\u0430 \u043f\u043e\u0434\u043a\u043b\u044e\u0447\u0435\u043d\u0438\u044f: " + ex.getMessage());
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
                    this.loggerView.appendText(this.getPreDate() + "\u041e\u0448\u0438\u0431\u043a\u0430 \u0430\u0432\u0442\u043e-\u0432\u044b\u0445\u043e\u0434\u0430: " + ex.getMessage());
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

