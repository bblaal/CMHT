package com.cmht.main;

import com.cmht.utility.PrinterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage loginStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        loginStage.setTitle("Log In - Restro PoS");
        loginStage.setScene(scene);
        loginStage.show();
    }

    public static void main(String[] args) {
        launch();

        //PrinterController.print("Hello, this is a test print.");
    }
}