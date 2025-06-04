package com.dashboard.desktopapp;

import com.dashboard.desktopapp.appsession.AppSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeNavController {

    @FXML
    private BorderPane content;

    @FXML
    protected void onMenuBtnClick() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homenav-view.fxml"));
            Parent root = fxmlLoader.load();

            // Get the screen's width and height
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double screenWidth = screenBounds.getWidth();
            double screenHeight = screenBounds.getHeight();

            // Get the current stage
            Stage stage = (Stage) content.getScene().getWindow();

            // Create a new scene with the loaded content (root)
            Scene newScene = new Scene(root, screenWidth, screenHeight);

            // Set the new scene to the stage and maximize it
            stage.setScene(newScene);
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onLogoutBtnClick() {
        try {
            AppSession.setJwtToken(null);
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = fxmlLoader.load();

            // Get the screen's width and height
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double screenWidth = screenBounds.getWidth();
            double screenHeight = screenBounds.getHeight();

            // Get the current stage
            Stage stage = (Stage) content.getScene().getWindow();

            // Create a new scene with the loaded content (root)
            Scene newScene = new Scene(root, screenWidth, screenHeight);

            // Set the new scene to the stage and maximize it
            stage.setScene(newScene);
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onUsersBtnClick() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("users-view.fxml"));
            Parent root = fxmlLoader.load();

            // Get the screen's width and height
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double screenWidth = screenBounds.getWidth();
            double screenHeight = screenBounds.getHeight();

            // Get the current stage
            Stage stage = (Stage) content.getScene().getWindow();

            // Create a new scene with the loaded content (root)
            Scene newScene = new Scene(root, screenWidth, screenHeight);

            // Set the new scene to the stage and maximize it
            stage.setScene(newScene);
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onContainersBtnClick() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("containers-view.fxml"));
            Parent root = fxmlLoader.load();

            // Get the screen's width and height
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double screenWidth = screenBounds.getWidth();
            double screenHeight = screenBounds.getHeight();

            // Get the current stage
            Stage stage = (Stage) content.getScene().getWindow();

            // Create a new scene with the loaded content (root)
            Scene newScene = new Scene(root, screenWidth, screenHeight);

            // Set the new scene to the stage and maximize it
            stage.setScene(newScene);
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onBucketsBtnClick() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("buckets-view.fxml"));
            Parent root = fxmlLoader.load();

            // Get the screen's width and height
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double screenWidth = screenBounds.getWidth();
            double screenHeight = screenBounds.getHeight();

            // Get the current stage
            Stage stage = (Stage) content.getScene().getWindow();

            // Create a new scene with the loaded content (root)
            Scene newScene = new Scene(root, screenWidth, screenHeight);

            // Set the new scene to the stage and maximize it
            stage.setScene(newScene);
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onStatsBtnClick() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("statistics-view.fxml"));
            Parent root = fxmlLoader.load();

            // Get the screen's width and height
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double screenWidth = screenBounds.getWidth();
            double screenHeight = screenBounds.getHeight();

            // Get the current stage
            Stage stage = (Stage) content.getScene().getWindow();

            // Create a new scene with the loaded content (root)
            Scene newScene = new Scene(root, screenWidth, screenHeight);

            // Set the new scene to the stage and maximize it
            stage.setScene(newScene);
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onHistoryBtnClick() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("history-view.fxml"));
            Parent root = fxmlLoader.load();

            // Get the screen's width and height
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double screenWidth = screenBounds.getWidth();
            double screenHeight = screenBounds.getHeight();

            // Get the current stage
            Stage stage = (Stage) content.getScene().getWindow();

            // Create a new scene with the loaded content (root)
            Scene newScene = new Scene(root, screenWidth, screenHeight);

            // Set the new scene to the stage and maximize it
            stage.setScene(newScene);
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onProfileBtnClick() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile-view.fxml"));
            Parent root = fxmlLoader.load();

            // Get the screen's width and height
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double screenWidth = screenBounds.getWidth();
            double screenHeight = screenBounds.getHeight();

            // Get the current stage
            Stage stage = (Stage) content.getScene().getWindow();

            // Create a new scene with the loaded content (root)
            Scene newScene = new Scene(root, screenWidth, screenHeight);

            // Set the new scene to the stage and maximize it
            stage.setScene(newScene);
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
