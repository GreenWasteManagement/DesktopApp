package com.dashboard.desktopapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Add CSS stylesheet
        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);

        // Set app icon
        Image image = new Image(this.getClass().getResourceAsStream("images/APP_LOGO.png"));
        stage.getIcons().add(image);

        stage.setTitle("Viana Circular");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}