package com.dashboard.desktopapp.components;

import com.dashboard.desktopapp.dtos.container.request.CreateContainerRequestDTO;
import com.dashboard.desktopapp.dtos.container.request.UpdateContainerRequestDTO;
import com.dashboard.desktopapp.interfaces.PageRefresh;
import com.dashboard.desktopapp.models.Container;
import com.dashboard.desktopapp.dtos.container.response.GetAllContainersResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

public class ContainersModalController {

    @FXML
    private VBox modal;

    @FXML
    private TextField id;
    @FXML
    private TextField capacity;
    @FXML
    private TextField containerLocation;
    @FXML
    private TextField currentVolume;
    @FXML
    private Label errorLabel;

    private PageRefresh reloadController;

    @FXML
    public void initialize() {
        capacity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                capacity.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        currentVolume.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                capacity.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

    }


    public void setReloadController(PageRefresh controller) {
        this.reloadController = controller;
    }

    public void setViewContainerInfo(GetAllContainersResponseDTO.Container container) {
        this.id.setText(container.getId().toString());
        this.capacity.setText(container.getCapacity().toString());
        this.containerLocation.setText(container.getLocalization());
        this.currentVolume.setText(container.getCurrentVolumeLevel().toString());
    }

    public void setEditContainerInfo(GetAllContainersResponseDTO.Container container) {
        this.id.setText(container.getId().toString());
        this.capacity.setText(container.getCapacity().toString());
        this.containerLocation.setText(container.getLocalization());
        this.currentVolume.setText(container.getCurrentVolumeLevel().toString());
    }

    @FXML
    public void onCancelBtnClick() {
        closeModal();
    }

    @FXML
    public void onSaveBtnClick() throws IOException {
        if (capacity.getText().isEmpty() || containerLocation.getText().isEmpty() || containerLocation.getText().isEmpty() || currentVolume.getText().isEmpty()) {
            toggleErrorLabel(true);
            return; // Don't proceed with the request
        } else {

            toggleErrorLabel(false);
        }
        String url = String.format("http://localhost:8080/api/containers/update");
        int responseCode = 0;

        try {
            // Prepare the connection
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            // Add Authorization header if needed
//            if (AppSession.getToken() != null) {
//                connection.setRequestProperty("Authorization", "Bearer " + AppSession.getToken());
//            }

            // Create JSON body
            UpdateContainerRequestDTO.Container container = new UpdateContainerRequestDTO.Container();
            container.setId(Long.parseLong(id.getText()));
            container.setCapacity(new BigDecimal(capacity.getText()));
            container.setLocalization(containerLocation.getText());
            container.setCurrentVolumeLevel(new BigDecimal(currentVolume.getText()));

            String jsonBody = String.format("{\n" +
                    "  \"container\": {\n" +
                    "    \"id\": %s,\n" +
                    "    \"capacity\": %s,\n" +
                    "    \"localization\": \"%s\",\n" +
                    "    \"currentVolumeLevel\": %s\n" +
                    "  }\n" +
                    "}", container.getId(), container.getCapacity(), container.getLocalization(), container.getCurrentVolumeLevel());

            // Write the body to the output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonBody.getBytes("utf-8");
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

    @FXML
    public void onCreateBtnClick() throws IOException {
        if (capacity.getText().isEmpty() || containerLocation.getText().isEmpty()) {
            toggleErrorLabel(true);
            return; // Don't proceed with the request
        } else {

            toggleErrorLabel(false);
        }
        String url = String.format("http://localhost:8080/api/containers");
        int responseCode = 0;

        try {
            // Prepare the connection
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            // Add Authorization header if needed
//            if (AppSession.getToken() != null) {
//                connection.setRequestProperty("Authorization", "Bearer " + AppSession.getToken());
//            }

            // Create JSON body
            CreateContainerRequestDTO container = new CreateContainerRequestDTO();
            container.setCapacity(new BigDecimal(capacity.getText()));
            container.setLocalization(containerLocation.getText());

            String jsonBody = String.format("{\n" +
                    "  \"capacity\": %s,\n" +
                    "  \"currentVolumeLevel\": 0,\n" +
                    "  \"localization\": \"%s\"\n" +
                    "}", container.getCapacity(), container.getLocalization());

            // Write the body to the output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonBody.getBytes("utf-8");
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

    public void toggleErrorLabel(boolean isVisible) {
        errorLabel.setVisible(isVisible);
    }
}
