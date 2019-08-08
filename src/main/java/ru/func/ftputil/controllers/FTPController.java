package ru.func.ftputil.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTPClient;

public class FTPController extends AbstractLoggedController {

    private final FileChooser fileChooser = new FileChooser();

    private final DirectoryChooser directoryChooser = new DirectoryChooser();

    private File sendFile;

    private File getFile;

    private Stage stage;

    private FTPClient client;

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
        log("Соединение прошло успешно.");
        localFileButton.setOnAction(event -> sendFile = fileChooser.showOpenDialog(stage));
        localDirButton.setOnAction(event -> getFile = directoryChooser.showDialog(stage));

        pushFileButton.setOnAction(event -> {
            try {
                sendFileToServerByPath(dirInput.getText(), sendFile.getPath());
            } catch (IOException e) {
                log("Ошибка загрузки: " + e.getMessage());
                e.printStackTrace();
            }
        });

        getFileButton.setOnAction(event -> {
            try {
                getFileFromServerByPath(fileInput.getText(), getFile.getPath());
            } catch (IOException e) {
                log("Ошибка выгрузки: " + e.getMessage());
                e.printStackTrace();
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
           log("Началась загрузка файла на сервер.");

           if (client.storeFile(uploadDir + "/" + path.split("/")[path.split("/").length - 1], inputStream)) {
               log("Загрузка завершена успешно.");
           }
       }
    }

    private void getFileFromServerByPath(String path, String loadDir) throws IOException {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(loadDir + "/" + path.split("/")[path.split("/").length - 1]))) {
            log("Начало выгрузки...");
            if (client.retrieveFile(path, outputStream)) {
                log("Файл ушспешно скачан.");
            }
        }
    }
}

