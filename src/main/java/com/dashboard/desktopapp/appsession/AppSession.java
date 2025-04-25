package com.dashboard.desktopapp.appsession;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;
import lombok.Getter;
import lombok.Setter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.io.IOException;
import java.util.Map;

public class AppSession {
    @Setter
    @Getter
    private static String token;

    public static Map<String, Object> decodeJWT(String jwt) {
        try {
            String[] parts = jwt.split("\\.");
            if (parts.length < 2) throw new IllegalArgumentException("Invalid JWT format");

            String payload = parts[1];
            byte[] decodedBytes = Base64.getUrlDecoder().decode(payload);
            String jsonPayload = new String(decodedBytes);

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonPayload, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isTokenExpired() {
        if (token == null || token.trim().isEmpty()) {
            return true;
        }

        try {
            // JWT format: header.payload.signature
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return true;
            }

            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
            JSONObject payload = new JSONObject(payloadJson);

            if (!payload.has("exp")) {
                return true; 
            }

            long exp = payload.getLong("exp");
            long now = System.currentTimeMillis() / 1000;

            return now >= exp;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static void handleTokenExpiration(Stage currentStage) {
        if (isTokenExpired()) {
            Platform.runLater(() -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(AppSession.class.getResource("login-view.fxml"));
                    Parent loginRoot = fxmlLoader.load();

                    // Get the screen's width and height
                    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                    double screenWidth = screenBounds.getWidth();
                    double screenHeight = screenBounds.getHeight();


                    Stage stage = (Stage) currentStage.getScene().getWindow();
                    Scene loginScene = new Scene(loginRoot, screenWidth, screenHeight);
                    currentStage.setScene(loginScene);
                    currentStage.setMaximized(true);
                    currentStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
