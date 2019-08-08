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

    private final FileChooser fileChooser = new FileChooser();

    private final DirectoryChooser directoryChooser = new DirectoryChooser();

    private File sendFile;

    private File getFile;

    private Stage stage;

    private FTPClient client;

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

    @FXML
    void initialize() {
        loggerView.appendText(getPreDate() + "Соединение прошло успешно.");
        localFileButton.setOnAction(event -> sendFile = fileChooser.showOpenDialog(stage));
        localDirButton.setOnAction(event -> getFile = directoryChooser.showDialog(stage));

        pushFileButton.setOnAction(event -> {
            try {
                sendFileToServerByPath(dirInput.getText(), sendFile.getPath());
            } catch (IOException e) {
                e.printStackTrace();
                loggerView.appendText(getPreDate() + "Ошибка загрузки.");
            }
        });

        getFileButton.setOnAction(event -> {
            try {
                getFileFromServerByPath(fileInput.getText(), getFile.getPath());
            } catch (IOException e) {
                loggerView.appendText(getPreDate() + "Ошибка выгрузки.");
            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setClient(FTPClient client) {
        this.client = client;
    }

    private void sendFileToServerByPath(String uploadDir, String path) throws IOException {
       try (FileInputStream inputStream = new FileInputStream(new File(path))){
           loggerView.appendText(getPreDate() + "Началась загрузка файла на сервер.");

           if (client.storeFile(uploadDir + "/" + path.split("/")[path.split("/").length - 1], inputStream)) {
               loggerView.appendText(getPreDate() + "Загрузка завершена успешно.");
           }
       }
    }

    private void getFileFromServerByPath(String path, String loadDir) throws IOException {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(loadDir + "/" + path.split("/")[path.split("/").length - 1]))) {
            loggerView.appendText(getPreDate() + "Начало выгрузки...");
            if (client.retrieveFile(path, outputStream)) {
                loggerView.appendText(getPreDate() + "Файл ушспешно скачан.");
            }
        }
    }

    private String getPreDate() {
        Date date = new Date();
        return "\n[" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + "] ";
    }
}

