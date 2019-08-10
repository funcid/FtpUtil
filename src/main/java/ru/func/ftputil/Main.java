package ru.func.ftputil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.func.ftputil.controllers.Controller;
import ru.func.ftputil.services.FtpService;
import ru.func.ftputil.services.FtpServiceFake;
import ru.func.ftputil.services.FtpServiceImpl;

public class Main extends Application {

    public void start(final Stage primaryStage) throws Exception {

        final long startTime = System.currentTimeMillis();

        final FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/sample.fxml"));
        final Parent root = loader.load();

        final Controller controller = loader.getController();
        controller.setStage(primaryStage);
        controller.setFtpService(Boolean.parseBoolean(System.getProperty("ftpfake", "false")) ? new FtpServiceFake() : new FtpServiceImpl());

        primaryStage.setTitle("Проект Артема Царюка 10 класс");
        primaryStage.setScene(new Scene(root, 1080.0, 720.0));
        primaryStage.setResizable(false);
        primaryStage.show();

        final long endTime = System.currentTimeMillis();
        controller.log(String.format(
                "Запуск программы, завершенно (%.2fs).",
                (endTime - startTime) / 1000D
        ));
    }

    public static void main(final String[] args) {
        Main.launch(args);
    }
}

