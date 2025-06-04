package com.dashboard.desktopapp.components;

import com.dashboard.desktopapp.appsession.AppSession;
import com.dashboard.desktopapp.dtos.container.request.CreateContainerRequestDTO;
import com.dashboard.desktopapp.dtos.container.request.UpdateContainerRequestDTO;
import com.dashboard.desktopapp.dtos.container.response.GetAllContainersResponseDTO;
import com.dashboard.desktopapp.interfaces.PageRefresh;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ContainersModalController {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
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
    @FXML
    private ListView<GetAllContainersResponseDTO.ContainerUnloading> containerUnloads;
    @FXML
    private ListView<GetAllContainersResponseDTO.BucketMunicipalityContainer> containerDeposits;
    private PageRefresh reloadController;

    @FXML
    public void initialize() {
        if (capacity != null) {
            capacity.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("^\\d*\\.?\\d*$")) {
                    capacity.setText(oldValue); // restore the old valid value
                }
            });
        }
        if (currentVolume != null) {
            currentVolume.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("^\\d*\\.?\\d*$")) {
                    currentVolume.setText(oldValue); // restore the old valid value
                }
            });
        }
    }

    public void setReloadController(PageRefresh controller) {
        this.reloadController = controller;
    }

    public void setViewContainerInfo(GetAllContainersResponseDTO.Container container) {
        this.id.setText(container.getId().toString());
        this.capacity.setText(container.getCapacity().toString());
        this.containerLocation.setText(container.getLocalization());
        this.currentVolume.setText(container.getCurrentVolumeLevel().toString());
        ObservableList<GetAllContainersResponseDTO.ContainerUnloading> unloadings = FXCollections.observableArrayList(container.getContainerUnloadings());
        containerUnloads.setItems(unloadings);
        containerUnloads.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(GetAllContainersResponseDTO.ContainerUnloading unloading, boolean empty) {
                super.updateItem(unloading, empty);
                if (empty || unloading == null) {
                    setText(null);
                } else {
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(
                            unloading.getUnloadingTimestamp(), ZoneId.systemDefault()
                    );
                    setText(String.format(
                            "Qnt: %.2f Kg | Data: %s | SMAS: %s",
                            unloading.getUnloadedQuantity(),
                            localDateTime.format(formatter),
                            unloading.getUserId()
                    ));
                }
            }
        });
        ObservableList<GetAllContainersResponseDTO.BucketMunicipalityContainer> deposits = FXCollections.observableArrayList(container.getBucketMunicipalityContainers());
        containerDeposits.setItems(deposits);
        containerDeposits.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(GetAllContainersResponseDTO.BucketMunicipalityContainer deposits, boolean empty) {
                super.updateItem(deposits, empty);
                if (empty || deposits == null) {
                    setText(null);
                } else {
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(
                            deposits.getDepositTimestamp(), ZoneId.systemDefault()
                    );
                    setText(String.format(
                            "Qnt: %.2f Kg | Data: %s",
                            deposits.getDepositAmount(),
                            localDateTime.format(formatter)
                    ));
                }
            }
        });
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
        String url = "http://localhost:8080/api/containers/update";
        int responseCode = 0;

        try {
            // Prepare the connection
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            // Add Authorization header if needed
            if (AppSession.getJwtToken() != null) {
                connection.setRequestProperty("Authorization", "Bearer " + AppSession.getJwtToken());
            }

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

    @FXML
    public void onCreateBtnClick() throws IOException {
        if (capacity.getText().isEmpty() || containerLocation.getText().isEmpty()) {
            toggleErrorLabel(true);
            return; // Don't proceed with the request
        } else {

            toggleErrorLabel(false);
        }
        String url = "http://localhost:8080/api/containers";
        int responseCode = 0;

        try {
            // Prepare the connection
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            if (AppSession.getJwtToken() != null) {
                connection.setRequestProperty("Authorization", "Bearer " + AppSession.getJwtToken());
            }

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

    public void toggleErrorLabel(boolean isVisible) {
        errorLabel.setVisible(isVisible);
    }
}
