package ru.func.ftputil.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.func.ftputil.services.FtpService;
import ru.func.ftputil.services.exceptions.FtpRetrieveFileException;
import ru.func.ftputil.services.exceptions.FtpSendFileException;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FTPController extends AbstractLoggedController {

    private final FileChooser fileChooser = new FileChooser();

    private final DirectoryChooser directoryChooser = new DirectoryChooser();

    private File sendFile;

    private File getFile;

    private Stage stage;

    private FtpService service;

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
        pushFileButton.setOnAction(event -> sendFileToServerByPath(dirInput.getText(), sendFile.getPath()));
        getFileButton.setOnAction(event -> getFileFromServerByPath(fileInput.getText(), getFile.getPath()));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setService(FtpService service) {
        this.service = service;
    }

    private void sendFileToServerByPath(String uploadDir, String path) {
        File sendingFile = new File(path);
        if (!sendingFile.exists()) {
            log("Ошибка: файла \"" + path + "\" не существует");
            return;
        }

        log("Началась загрузка файла на сервер.");
        try {
            if (service.sendFile(uploadDir + "/" + sendingFile.getName(), sendingFile)) {
                log("Загрузка завершена успешно.");
            } else {
                log("Загрезка файла не удалась.");
            }
        } catch (FtpSendFileException e) {
            log("Загрезка файла не удалась. Причина: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void getFileFromServerByPath(String path, String loadDir) {
        File localFile = new File(loadDir, getFileNameFromPath(path));
        if (localFile.exists()) {
            log("Файл уже существует");
            return;
        }

        log("Начало выгрузки...");
        try {
            if (service.retrieveFile(localFile, path)) {
                log("Файл ушспешно скачан.");
            } else {
                log("Файл не выгружен.");
            }
        } catch (FtpRetrieveFileException e) {
            log("Ошибка выгрузки: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getFileNameFromPath(String path) {
        Matcher matcher = Pattern.compile("/(.+?)$").matcher(path);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new RuntimeException("Что-то пошло не так: не могу получить имя файла из \"" + path + "\"");
        }
    }
}

