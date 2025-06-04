package com.dashboard.desktopapp;

import com.dashboard.desktopapp.appsession.AppSession;
import com.dashboard.desktopapp.dtos.user.request.LoginRequestDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class StartController {

    @FXML
    private BorderPane content;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;

    @FXML
    protected void onAccessBtnClick() {
        try {
            // Create and fill the login DTO
            LoginRequestDTO loginDTO = new LoginRequestDTO();
            loginDTO.setEmail(email.getText());
            loginDTO.setPassword(password.getText());

            // Serialize DTO to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(loginDTO);

            // Send POST request
            URL url = new URL("http://localhost:8080/api/users/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestBody.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }

                    // Parse response and store token
                    JsonNode jsonNode = objectMapper.readTree(response.toString());
                    String token = jsonNode.get("token").asText();

                    if(AppSession.setTokenIfAllowed(token)){
                        // Load main view
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homenav-view.fxml"));
                        Parent root = fxmlLoader.load();

                        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                        Stage stage = (Stage) content.getScene().getWindow();
                        Scene newScene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());

                        stage.setScene(newScene);
                        stage.setMaximized(true);
                        stage.show();
                    }else {
                        showLoginFailedModal();
                    }
                }
            } else {
                showLoginFailedModal();
            }

        } catch (IOException e) {
            e.printStackTrace();
            showLoginFailedModal();
        }
    }

    private void showLoginFailedModal() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("components/failed-login-modal.fxml"));
            Parent modalRoot = fxmlLoader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("");
            Image image = new Image(this.getClass().getResourceAsStream("images/APP_LOGO.png"));
            modalStage.getIcons().add(image);
            modalStage.setScene(new Scene(modalRoot));
            modalStage.initOwner(content.getScene().getWindow());
            modalStage.setResizable(false);
            modalStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
