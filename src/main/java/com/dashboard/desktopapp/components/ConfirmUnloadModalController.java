package com.dashboard.desktopapp.components;

import com.dashboard.desktopapp.appsession.AppSession;
import com.dashboard.desktopapp.dtos.container.response.GetAllContainersResponseDTO;
import com.dashboard.desktopapp.interfaces.PageRefresh;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ConfirmUnloadModalController {
    String sessionToken = AppSession.getJwtToken();
    @FXML
    private VBox modal;
    @FXML
    private Label id;
    @FXML
    private Label capacity;
    @FXML
    private Label containerLocation;
    @FXML
    private Label currentVolume;
    private Long containerId;

    private PageRefresh reloadController;

    public void setReloadController(PageRefresh controller) {
        this.reloadController = controller;
    }

    public void setViewContainerInfo(GetAllContainersResponseDTO.Container container) {
        this.id.setText(String.format("ID: " + container.getId().toString()));
        this.capacity.setText(String.format("Capacidade: " + container.getCapacity().toString()));
        this.containerLocation.setText(String.format("Localização: " + container.getLocalization()));
        this.currentVolume.setText(String.format("Volume atual: " + container.getCurrentVolumeLevel().toString()));
        containerId = container.getId();
    }

    @FXML
    public void onCancelBtnClick() {
        closeModal();
    }

    @FXML
    public void onConfirmBtnClick() throws IOException {
        String url = "http://localhost:8080/api/containers/unloading";
        int responseCode = 0;

        try {
            // Prepare the connection
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            // Add Authorization header if needed
            if (AppSession.getJwtToken() != null) {
                connection.setRequestProperty("Authorization", "Bearer " + AppSession.getJwtToken());
            }

            Map<String, Object> userInfo = AppSession.decodeJWT(sessionToken);

            String jsonBody = String.format("{\n" +
                    "  \"smasId\": %s,\n" +
                    "  \"containerId\": %s\n" +
                    "}", userInfo.get("id"), containerId);

            // Write the body to the output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Capture the response code
            responseCode = connection.getResponseCode();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            responseCode = 500;
        }

        // __LOGIC FOR CLOSING MODAL AND SHOWING CONFIRMATION MODAL__
        closeModal();
        // Load the modal's FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/dashboard/desktopapp/components/confirmation-modal.fxml"));
        Parent modalRoot = fxmlLoader.load();

        // Create a new stage for the modal
        Stage modalStage = new Stage();
        modalStage.setTitle("");
        Image image = new Image(this.getClass().getResourceAsStream("/com/dashboard/desktopapp/images/APP_LOGO.png"));
        modalStage.getIcons().add(image);
        modalStage.setScene(new Scene(modalRoot));
        modalStage.initOwner(modal.getScene().getWindow());
        modalStage.setResizable(false);
        modalStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
        // Pass the message to the function for confirmation modal
        ConfirmationModalController controller = fxmlLoader.getController();
        controller.setConfirmationText(responseCode); // Pass the request status code to set the text
        controller.setReloadController(reloadController);
        modalStage.showAndWait();
    }


    public void closeModal() {
        Stage stage = (Stage) (modal.getScene().getWindow());
        stage.close();
    }

}