/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  javafx.application.Application
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.stage.Stage
 */
package ru.func.ftputil;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTPClient;

public class Main
extends Application {
    private final FTPClient CLIENT = new FTPClient();
    private Stage STAGE;
    private static Main instance;

    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent)FXMLLoader.load((URL)this.getClass().getResource("sample.fxml"));
        primaryStage.setTitle("\u041f\u0440\u043e\u0435\u043a\u0442 \u0410\u0440\u0442\u0435\u043c\u0430 \u0426\u0430\u0440\u044e\u043a\u0430 10 \u043a\u043b\u0430\u0441\u0441");
        primaryStage.setScene(new Scene(root, 1080.0, 720.0));
        primaryStage.setResizable(false);
        primaryStage.show();
        this.STAGE = primaryStage;
        instance = this;
    }

    public static void main(String[] args) {
        Main.launch((String[])args);
    }

    public static Main getInstance() {
        return instance;
    }

    public FTPClient getClient() {
        return this.CLIENT;
    }

    public Stage getStage() {
        return this.STAGE;
    }
}

