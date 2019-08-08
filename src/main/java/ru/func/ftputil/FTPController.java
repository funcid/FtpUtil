package ru.func.ftputil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTPClient;

public class FTPController {

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

    private Stage stage;
    private FTPClient client;


    @FXML
    void initialize() {
        this.loggerView.appendText(this.getPreDate() + "Соединение прошло успешно.");
        this.localFileButton.setOnAction(event -> {
            this.sendFile = this.fileChooser.showOpenDialog(stage);
        }
        );
        this.localDirButton.setOnAction(event -> {
            this.getFile = this.directoryChooser.showDialog(stage);
        }
        );
        this.pushFileButton.setOnAction(event -> {
            try {
                this.sendFileToServerByPath(this.dirInput.getText(), this.sendFile.getPath());
            }
            catch (IOException e) {
                e.printStackTrace();
                this.loggerView.appendText(this.getPreDate() + "Ошибка загрузки.");
            }
        }
        );
        this.getFileButton.setOnAction(event -> {
            try {
                this.getFileFromServerByPath(this.fileInput.getText(), this.getFile.getPath());
            }
            catch (IOException e) {
                this.loggerView.appendText(this.getPreDate() + "Ошибка выгрузки.");
            }
        }
        );
    }

    private void sendFileToServerByPath(String uploadDir, String path) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(path));
        this.loggerView.appendText(this.getPreDate() + "Началась загрузка файла на сервер.");
        if (client.storeFile(uploadDir + "/" + path.split("/")[path.split("/").length - 1], inputStream)) {
            this.loggerView.appendText(this.getPreDate() + "Загрузка завершена успешно.");
        }
        inputStream.close();
    }

    private void getFileFromServerByPath(String path, String loadDir) throws IOException {
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(loadDir + "/" + path.split("/")[path.split("/").length - 1]));
        this.loggerView.appendText(this.getPreDate() + "Начало выгрузки...");
        if (client.retrieveFile(path, outputStream)) {
            this.loggerView.appendText(this.getPreDate() + "Файл ушспешно скачан.");
        }
        outputStream.close();
    }

    private String getPreDate() {
        Date date = new Date();
        return "\n[" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + "] ";
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setClient(FTPClient client) {
        this.client = client;
    }
}

