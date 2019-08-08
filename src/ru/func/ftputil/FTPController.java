/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.fxml.FXML
 *  javafx.scene.control.Button
 *  javafx.scene.control.TextArea
 *  javafx.scene.control.TextField
 *  javafx.stage.DirectoryChooser
 *  javafx.stage.FileChooser
 *  javafx.stage.Stage
 *  javafx.stage.Window
 */
package ru.func.ftputil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.net.ftp.FTPClient;
import ru.func.ftputil.Main;

public class FTPController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextArea loggerView;
    @FXML
    private TextField dirInput;
    @FXML
    private Button localFileButton;
    @FXML
    private Button pushFileButton;
    @FXML
    private TextField fileInput;
    @FXML
    private Button localDirButton;
    @FXML
    private Button getFileButton;
    private final FileChooser fileChooser = new FileChooser();
    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private File sendFile;
    private File getFile;

    @FXML
    void initialize() {
        this.loggerView.appendText(this.getPreDate() + "\u0421\u043e\u0435\u0434\u0438\u043d\u0435\u043d\u0438\u0435 \u043f\u0440\u043e\u0448\u043b\u043e \u0443\u0441\u043f\u0435\u0448\u043d\u043e.");
        this.localFileButton.setOnAction(event -> {
            this.sendFile = this.fileChooser.showOpenDialog((Window)Main.getInstance().getStage());
        }
        );
        this.localDirButton.setOnAction(event -> {
            this.getFile = this.directoryChooser.showDialog((Window)Main.getInstance().getStage());
        }
        );
        this.pushFileButton.setOnAction(event -> {
            try {
                this.sendFileToServerByPath(this.dirInput.getText(), this.sendFile.getPath());
                return;
            }
            catch (IOException e) {
                e.printStackTrace();
                this.loggerView.appendText(this.getPreDate() + "\u041e\u0448\u0438\u0431\u043a\u0430 \u0437\u0430\u0433\u0440\u0443\u0437\u043a\u0438.");
            }
        }
        );
        this.getFileButton.setOnAction(event -> {
            try {
                this.getFileFromServerByPath(this.fileInput.getText(), this.getFile.getPath());
                return;
            }
            catch (IOException e) {
                this.loggerView.appendText(this.getPreDate() + "\u041e\u0448\u0438\u0431\u043a\u0430 \u0432\u044b\u0433\u0440\u0443\u0437\u043a\u0438.");
            }
        }
        );
    }

    private void sendFileToServerByPath(String uploadDir, String path) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(path));
        this.loggerView.appendText(this.getPreDate() + "\u041d\u0430\u0447\u0430\u043b\u0430\u0441\u044c \u0437\u0430\u0433\u0440\u0443\u0437\u043a\u0430 \u0444\u0430\u0439\u043b\u0430 \u043d\u0430 \u0441\u0435\u0440\u0432\u0435\u0440.");
        if (Main.getInstance().getClient().storeFile(uploadDir + "/" + path.split("/")[path.split("/").length - 1], inputStream)) {
            this.loggerView.appendText(this.getPreDate() + "\u0417\u0430\u0433\u0440\u0443\u0437\u043a\u0430 \u0437\u0430\u0432\u0435\u0440\u0448\u0435\u043d\u0430 \u0443\u0441\u043f\u0435\u0448\u043d\u043e.");
        }
        inputStream.close();
    }

    private void getFileFromServerByPath(String path, String loadDir) throws IOException {
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(loadDir + "/" + path.split("/")[path.split("/").length - 1]));
        this.loggerView.appendText(this.getPreDate() + "\u041d\u0430\u0447\u0430\u043b\u043e \u0432\u044b\u0433\u0440\u0443\u0437\u043a\u0438...");
        if (Main.getInstance().getClient().retrieveFile(path, outputStream)) {
            this.loggerView.appendText(this.getPreDate() + "\u0424\u0430\u0439\u043b \u0443\u0448\u0441\u043f\u0435\u0448\u043d\u043e \u0441\u043a\u0430\u0447\u0430\u043d.");
        }
        outputStream.close();
    }

    private String getPreDate() {
        Date date = new Date();
        return "\n[" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + "] ";
    }
}

