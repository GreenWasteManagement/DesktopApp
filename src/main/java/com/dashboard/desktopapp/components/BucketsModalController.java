package com.dashboard.desktopapp.components;

import com.dashboard.desktopapp.dtos.bucket.request.BucketFullUpdateRequestDTO;
import com.dashboard.desktopapp.dtos.bucket.request.CreateBucketRequestDTO;
import com.dashboard.desktopapp.dtos.bucket.response.BucketWithMunicipalityInfoDTO;
import com.dashboard.desktopapp.dtos.bucket.response.CreateBucketResponseDTO;
import com.dashboard.desktopapp.dtos.user.response.GetAllMunicipalitiesAndBucketsResponseDTO;
import com.dashboard.desktopapp.interfaces.PageRefresh;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BucketsModalController {
    Long excludedUserId;
    @FXML
    private VBox modal;
    @FXML
    private TextField id;
    @FXML
    private TextField capacity;
    @FXML
    private TextField associated;
    @FXML
    private TextField state;
    @FXML
    private ChoiceBox<String> editState;
    @FXML
    private TextField municipality;
    @FXML
    private ComboBox<GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData> municipalities;
    @FXML
    private Label errorLabel;
    private PageRefresh reloadController;

    public void setReloadController(PageRefresh controller) {
        this.reloadController = controller;
    }

    public void initialize() {
        if (capacity != null) {
            capacity.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("^\\d*\\.?\\d*$")) {
                    capacity.setText(oldValue); // restore the old valid value
                }
            });
        }
    }

    public void setViewBucketInfo(BucketWithMunicipalityInfoDTO bucket) {
        this.id.setText(bucket.getBucketId().toString());
        this.capacity.setText(bucket.getCapacity().toString());
        if (bucket.getIsAssociated()) {
            this.associated.setText("Sim");

            Optional<BucketWithMunicipalityInfoDTO.BucketMunicipalityDTO> activeAssociation =
                    bucket.getBucketMunicipalities()
                            .stream()
                            .filter(BucketWithMunicipalityInfoDTO.BucketMunicipalityDTO::getStatus)
                            .findFirst();
            if (activeAssociation.isPresent()) {
                this.state.setText("Associação ativa");
                this.municipality.setText(
                        activeAssociation.get().getMunicipality().getNif() + " - " +
                                activeAssociation.get().getMunicipality().getUser().getName()
                );
            } else {
                this.municipality.setText(bucket.getBucketMunicipalities().getLast().getMunicipality().getNif() + " - " +
                        bucket.getBucketMunicipalities().getLast().getMunicipality().getUser().getName());
                this.state.setText("Associação desativada");
            }
        } else {
            this.associated.setText("Não");
            this.state.setText("Sem associação");
            this.municipality.setText("");
        }
    }

    public void setEditBucketInfo(BucketWithMunicipalityInfoDTO bucket) {
        this.id.setText(bucket.getBucketId().toString());
        this.capacity.setText(bucket.getCapacity().toString());
        if (bucket.getIsAssociated()) {
            this.associated.setText("Sim");

            Optional<BucketWithMunicipalityInfoDTO.BucketMunicipalityDTO> activeAssociation =
                    bucket.getBucketMunicipalities()
                            .stream()
                            .filter(BucketWithMunicipalityInfoDTO.BucketMunicipalityDTO::getStatus)
                            .findFirst();
            if (activeAssociation.isPresent()) {
                excludedUserId = activeAssociation.get().getMunicipality().getUser().getId();
                this.state.setText("Associação ativa");
                this.municipality.setText(
                        activeAssociation.get().getMunicipality().getNif() + " - " +
                                activeAssociation.get().getMunicipality().getUser().getName()
                );
            } else {
                this.municipality.setText(bucket.getBucketMunicipalities().getLast().getMunicipality().getNif() + " - " +
                        bucket.getBucketMunicipalities().getLast().getMunicipality().getUser().getName());
                this.state.setText("Associação desativada");
                excludedUserId = null;
            }
        } else {
            this.associated.setText("Não");
            this.state.setText("Sem associação");
            this.municipality.setText("");
            excludedUserId = null;
        }

        List<GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData> data = getAllMunicipalities()
                .stream()
                .filter(m -> !m.getUser().getId().equals(excludedUserId))
                .toList();

        municipalities.setItems(FXCollections.observableArrayList(data));

        municipalities.setConverter(new StringConverter<>() {
            @Override
            public String toString(GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData object) {
                if (object == null) return "";
                return object.getMunicipality().getNif() + " - " + object.getUser().getName();
            }

            @Override
            public GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData fromString(String string) {
                return null; // Not used
            }
        });
    }

    public void setCreateBucketInfo() {
        List<GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData> data = getAllMunicipalities();
        municipalities.getItems().addAll(data);

        municipalities.setConverter(new StringConverter<>() {
            @Override
            public String toString(GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData object) {
                if (object == null) return "";
                return object.getMunicipality().getNif() + " - " + object.getUser().getName();
            }

            @Override
            public GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData fromString(String string) {
                return null; // Not needed since you're not filtering
            }
        });
    }

    @FXML
    public void onSaveBtnClick() throws IOException {
        if (capacity.getText().isEmpty()) {
            toggleErrorLabel(true);
            return; // Don't proceed with the request
        } else {

            toggleErrorLabel(false);
        }
        String url = "http://localhost:8080/api/buckets/update";
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
            String jsonBody;

            BucketFullUpdateRequestDTO bucketInfo = new BucketFullUpdateRequestDTO();
            BucketFullUpdateRequestDTO.BucketMunicipalityUpdate bucketMunicipalityInfo = new BucketFullUpdateRequestDTO.BucketMunicipalityUpdate();
            BucketFullUpdateRequestDTO.MunicipalityDTO bucketMunicipality = new BucketFullUpdateRequestDTO.MunicipalityDTO();
            BucketFullUpdateRequestDTO.MunicipalityDTO.UserDTO bucketUser = new BucketFullUpdateRequestDTO.MunicipalityDTO.UserDTO();

            bucketInfo.setBucketId(Long.parseLong(id.getText()));
            bucketInfo.setCapacity(new BigDecimal(capacity.getText()));
            if (Objects.equals(associated.getText(), "Sim")) {
                bucketInfo.setIsAssociated(true);

                // Same association
                jsonBody = String.format("{\n" +
                        "  \"bucket\": {\n" +
                        "    \"id\": %s,\n" +
                        "    \"capacity\": %s,\n" +
                        "    \"isAssociated\": %s\n" +
                        "  }\n" +
                        "}", bucketInfo.getBucketId(), bucketInfo.getCapacity(), bucketInfo.getIsAssociated());
                if (!municipalities.getSelectionModel().isEmpty()) {
                    // New association
                    GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData newMunicipality = municipalities.getSelectionModel().getSelectedItem();
                    bucketMunicipality.setId(newMunicipality.getMunicipality().getId());

                    createBucketAssociation(bucketInfo.getBucketId().toString(), bucketMunicipality.getId().toString());
                }
            } else {
                // Same association
                if (municipalities.getSelectionModel().isEmpty()) {
                    bucketInfo.setIsAssociated(false);

                    jsonBody = String.format("{\n" +
                            "  \"bucket\": {\n" +
                            "    \"id\": %s,\n" +
                            "    \"capacity\": %s,\n" +
                            "    \"isAssociated\": %s\n" +
                            "  }\n" +
                            "}", bucketInfo.getBucketId(), bucketInfo.getCapacity(), bucketInfo.getIsAssociated());
                } else {
                    bucketInfo.setIsAssociated(true);
                    jsonBody = String.format("{\n" +
                            "  \"bucket\": {\n" +
                            "    \"id\": %s,\n" +
                            "    \"capacity\": %s,\n" +
                            "    \"isAssociated\": %s\n" +
                            "  }\n" +
                            "}", bucketInfo.getBucketId(), bucketInfo.getCapacity(), bucketInfo.getIsAssociated());
                    // New association
                    GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData newMunicipality = municipalities.getSelectionModel().getSelectedItem();
                    bucketMunicipality.setId(newMunicipality.getMunicipality().getId());

                    createBucketAssociation(bucketInfo.getBucketId().toString(), bucketMunicipality.getId().toString());
                }
            }

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
        if (capacity.getText().isEmpty()) {
            toggleErrorLabel(true);
            return; // Don't proceed with the request
        } else {
            toggleErrorLabel(false);
        }
        String url = "http://localhost:8080/api/buckets/create";
        int responseCode = 0;
        String createdBucketId = null;

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
            CreateBucketRequestDTO.Bucket bucket = new CreateBucketRequestDTO.Bucket();
            bucket.setCapacity(new BigDecimal(capacity.getText()));
            boolean shouldAssociate = !municipalities.getSelectionModel().isEmpty();
            bucket.setIsAssociated(shouldAssociate);

            String jsonBody = String.format("{\n" +
                    "  \"bucket\": {\n" +
                    "    \"capacity\": %s,\n" +
                    "    \"isAssociated\": %s\n" +
                    "  }\n" +
                    "}", bucket.getCapacity(), bucket.getIsAssociated());

            // Write the body to the output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Capture the response code
            responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                // Read the response body
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                reader.close();

                // Map response to DTO
                ObjectMapper objectMapper = new ObjectMapper();
                CreateBucketResponseDTO responseDTO = objectMapper.readValue(responseBuilder.toString(), CreateBucketResponseDTO.class);

                // Extract bucket ID
                if (responseDTO.getBucket() != null) {
                    createdBucketId = responseDTO.getBucket().getId().toString();
                    System.out.println("Created Bucket ID: " + createdBucketId);
                } else {
                    System.out.println("Bucket info not found in response.");
                }
            } else {
                System.out.println("Error: Failed to create bucket. HTTP Code: " + responseCode);
            }
            connection.disconnect();
            if ((responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) && createdBucketId != null) {
                if (shouldAssociate) {
                    GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData selectedMunicipality =
                            municipalities.getSelectionModel().getSelectedItem();
                    responseCode = createBucketAssociation(createdBucketId, selectedMunicipality.getUser().getId().toString());
                }
            } else {
                System.out.println("Failed to create bucket. Skipping association.");
            }
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
    public void onCancelBtnClick() {
        closeModal();
    }

    public void closeModal() {
        Stage stage = (Stage) (modal.getScene().getWindow());
        stage.close();
    }

    public void toggleErrorLabel(boolean isVisible) {
        errorLabel.setVisible(isVisible);
    }

    public List<GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData> getAllMunicipalities() {
        // Define the API endpoint
        String url = "http://localhost:8080/api/users/get/municipalities/buckets";
        List<GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData> municipalityData = Collections.emptyList();

        try {
            // Create a URL object with the API endpoint
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // Timeout after 5 seconds
            connection.setReadTimeout(5000); // Timeout for reading response

            // Check if the response code is 200 (OK)
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Read the response data from the API
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse the JSON response to GetAllMunicipalitiesAndBucketsResponseDTO
                ObjectMapper objectMapper = new ObjectMapper();
                GetAllMunicipalitiesAndBucketsResponseDTO responseDTO = objectMapper.readValue(response.toString(), GetAllMunicipalitiesAndBucketsResponseDTO.class);

                // Get the municipalities list from the response DTO
                municipalityData = responseDTO.getMunicipalities();
            } else {
                System.out.println("Error: Unable to fetch data. HTTP code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return municipalityData; // Return the parsed containers list
    }

    public int createBucketAssociation(String bucketId, String municipalityId) {
        String url = "http://localhost:8080/api/buckets/bucket-association";
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

            String jsonBody = String.format("{\n" +
                    "  \"bucketId\": %s,\n" +
                    "  \"municipalityId\": %s\n" +
                    "}", bucketId, municipalityId);

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
        return responseCode;
    }
}
