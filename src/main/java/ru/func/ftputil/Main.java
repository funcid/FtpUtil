package ru.func.ftputil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTPClient;
import ru.func.ftputil.controllers.Controller;

public class Main extends Application {
    private static long startTime;

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/sample.fxml"));
        Parent root = loader.load();

        Controller controller = loader.getController();
        controller.setStage(primaryStage);
        controller.setClient(new FTPClient());

        primaryStage.setTitle("Проект Артема Царюка 10 класс");
        primaryStage.setScene(new Scene(root, 1080.0, 720.0));
        primaryStage.setResizable(false);
        primaryStage.show();

        long endTime = System.currentTimeMillis();
        controller.log(String.format(
                "Запуск программы, завершенно (%.2fs).",
                (endTime - startTime) / 1000D
        ));
    }

    public static void main(String[] args) {
        startTime = System.currentTimeMillis();
        Main.launch((String[])args);
    }
}

