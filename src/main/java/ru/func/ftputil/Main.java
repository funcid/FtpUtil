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
        primaryStage.setTitle("Проект Артема Царюка 10 класс");
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

