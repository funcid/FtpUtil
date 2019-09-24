package ru.func.ftputil.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.func.ftputil.services.FtpService;
import ru.func.ftputil.services.exceptions.FtpRetrieveFileException;
import ru.func.ftputil.services.exceptions.FtpSendFileException;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author func 30.08.2019
 */
public class FTPController extends AbstractLoggedController {

    private static final Logger log = LoggerFactory.getLogger(FTPController.class);

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
    private Button showButton;

    @FXML
    private TextField serverDirInput;

    @FXML
    private Button clearButton;

    @FXML
    private Button deleteFileButton;

    public FTPController() {
        setLogger(log);
    }

    @FXML
    void initialize() {
        log("Соединение прошло успешно.");

        localFileButton.setOnAction(event -> sendFile = fileChooser.showOpenDialog(stage));
        localDirButton.setOnAction(event -> getFile = directoryChooser.showDialog(stage));
        pushFileButton.setOnAction(event -> sendFileToServerByPath(dirInput.getText(), sendFile.getPath()));
        getFileButton.setOnAction(event -> getFileFromServerByPath(fileInput.getText(), getFile.getPath()));

        showButton.setOnAction(event -> {
            log(">> Доступные директории: \n" +
                    Arrays.toString(service.listDirNames(serverDirInput.getText()))
                            .replace(", ", "\n    - DIR: ")
                            .replace("[", "\n    - DIR: ")
                            .replace("]", "\n")
            );
            log(">> Доступные файлы: \n" +
                    Arrays.toString(service.listFileNames(serverDirInput.getText()))
                            .replace(", ", "\n    - FILE: ")
                            .replace("[", "\n    - FILE: ")
                            .replace("]", "\n")
            );
        });

        clearButton.setOnAction(event -> loggerView.clear());
        deleteFileButton.setOnAction(event -> removeServerFile(fileInput.getText()));
    }

    void setStage(final Stage stage) {
        this.stage = stage;
    }

    void setService(final FtpService service) {
        this.service = service;
    }

    private void sendFileToServerByPath(final String uploadDir, final String path) {
        final File sendingFile = new File(path);
        if (!sendingFile.exists()) {
            log("Ошибка: файла \"" + path + "\" не существует");
            return;
        }

        log("Началась загрузка файла на сервер.");
        try {
            final long startTime = System.currentTimeMillis();
            log(service.sendFile(uploadDir + "/" + sendingFile.getName(), sendingFile) ? "Загрузка завершена успешно. (" + (System.currentTimeMillis() - startTime)/1000D + "s)." : "Загрезка файла не удалась.");
        } catch (final FtpSendFileException e) {
            log("Загрезка файла не удалась. Причина: " + e.getMessage());
            log.error("", e);
        }
    }

    private void getFileFromServerByPath(final String path, final String loadDir) {
        final File localFile = new File(loadDir, getFileNameFromPath(path));
        if (localFile.exists()) {
            log("Файл уже существует");
            return;
        }

        final long startTime = System.currentTimeMillis();
        log("Начало выгрузки...");
        try {
            log(service.retrieveFile(localFile, path) ? "Файл успешно скачан (" + (System.currentTimeMillis() - startTime)/1000D + "s)." : "Файл не выгружен.");
        } catch (final FtpRetrieveFileException e) {
            log("Ошибка выгрузки: " + e.getMessage());
            log.error("", e);
        }
    }

    private void removeServerFile(final String path) {
        final long startTime = System.currentTimeMillis();
        log(service.removeServerFile(path) ? "Файл был успешно удален (" + (System.currentTimeMillis() - startTime)/1000D + "s)." : "Ошибка удаления файла.");
    }

    private String getFileNameFromPath(final String path) {
        final Matcher matcher = Pattern.compile("/(.+?)$").matcher(path);
        if (matcher.find())
            return matcher.group(1);
        else
            throw new RuntimeException("Что-то пошло не так: не могу получить имя файла из \"" + path + "\"");
    }
}

