package com.dashboard.desktopapp.components;

import com.dashboard.desktopapp.dtos.bucket.response.GetAllBucketsResponseDTO;
import com.dashboard.desktopapp.dtos.container.request.CreateContainerRequestDTO;
import com.dashboard.desktopapp.dtos.container.request.UpdateContainerRequestDTO;
import com.dashboard.desktopapp.dtos.container.response.GetAllContainersResponseDTO;
import com.dashboard.desktopapp.dtos.user.request.CreateMunicipalityRequestDTO;
import com.dashboard.desktopapp.dtos.user.request.UpdateMunicipalityRequestDTO;
import com.dashboard.desktopapp.dtos.user.response.GetAllMunicipalitiesAndBucketsResponseDTO;
import com.dashboard.desktopapp.interfaces.PageRefresh;
import com.dashboard.desktopapp.models.Municipality;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class MunicipalityModalController {

    @FXML
    private VBox modal;

    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private TextField cc;
    @FXML
    private TextField nif;
    @FXML
    private TextField userType;
    @FXML
    private TextField address;
    @FXML
    private TextField floorDetails;
    @FXML
    private TextField floorNumber;
    @FXML
    private TextField doorNumber;
    @FXML
    private TextField street;
    @FXML
    private TextField postalCode;
    @FXML
    private TextField county;
    @FXML
    private TextField district;
    @FXML
    private ComboBox<GetAllBucketsResponseDTO.Bucket> availableBuckets;
    @FXML
    private ListView<GetAllMunicipalitiesAndBucketsResponseDTO.Bucket> buckets;
    @FXML
    private Label errorLabel;

    private Long postalCodeId;
    private Long addressId;

    private PageRefresh reloadController;

    public void setReloadController(PageRefresh controller) {
        this.reloadController = controller;
    }

    @FXML
    public void initialize() {
        if (phone != null) {
            phone.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("^\\d+$")) {
                    phone.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
        }
        if (nif != null) {
            nif.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("^\\d+$")) {
                    nif.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
        }
        if (cc != null) {
            cc.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("^\\d+$")) {
                    cc.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
        }
        if (floorNumber != null) {
            floorNumber.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("^\\d+$")) {
                    floorNumber.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
        }
        if (doorNumber != null) {
            doorNumber.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("^\\d+$")) {
                    doorNumber.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
        }
        if (postalCode != null) {
            postalCode.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("^\\d{0,4}-?\\d{0,3}$")) {
                    postalCode.setText(newValue.replaceAll("[^\\d-]", ""));
                }
            });
        }
    }


    public String setAddress(GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData municipality) {
        String address = String.format("%s, %d, %d, %s, %s, %s, %s",
                municipality.getAddress().getStreet(), municipality.getAddress().getDoorNumber(), municipality.getAddress().getFloorNumber(),
                municipality.getAddress().getFloorDetails(), municipality.getPostalCode().getPostalCode(), municipality.getPostalCode().getCounty(),
                municipality.getPostalCode().getDistrict());
        return address;
    }

    public void setViewUserInfo(GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData municipality) {
        this.id.setText(municipality.getUser().getId().toString());
        this.name.setText(municipality.getUser().getName());
        this.username.setText(municipality.getUser().getUsername());
        this.email.setText(municipality.getUser().getEmail());
        this.phone.setText(municipality.getUser().getPhoneNumber());
        this.nif.setText(municipality.getMunicipality().getNif());
        this.cc.setText(municipality.getMunicipality().getCitizenCardCode());
        this.userType.setText(municipality.getUser().getRole());
        this.address.setText(setAddress(municipality));
        ObservableList<GetAllMunicipalitiesAndBucketsResponseDTO.Bucket> items = FXCollections.observableArrayList(municipality.getBuckets());
        buckets.setItems(items);
        buckets.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(GetAllMunicipalitiesAndBucketsResponseDTO.Bucket bucket, boolean empty) {
                super.updateItem(bucket, empty);
                if (empty || bucket == null) {
                    setText(null);
                } else {
                    setText(String.format(
                            "ID: %d | Capacidade: %.2f Kg",
                            bucket.getId(),
                            bucket.getCapacity()
                    ));
                }
            }
        });
    }

    public void setEditUserInfo(GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData municipality) {
        this.id.setText(municipality.getUser().getId().toString());
        this.name.setText(municipality.getUser().getName());
        this.username.setText(municipality.getUser().getUsername());
        this.email.setText(municipality.getUser().getEmail());
        this.phone.setText(municipality.getUser().getPhoneNumber());
        this.cc.setText(municipality.getMunicipality().getCitizenCardCode());
        this.nif.setText(municipality.getMunicipality().getNif());
        this.userType.setText(municipality.getUser().getRole());
        this.floorDetails.setText(municipality.getAddress().getFloorDetails());
        this.floorNumber.setText(municipality.getAddress().getFloorNumber().toString());
        this.doorNumber.setText(municipality.getAddress().getDoorNumber().toString());
        this.street.setText(municipality.getAddress().getStreet());
        this.postalCode.setText(municipality.getPostalCode().getPostalCode());
        this.county.setText(municipality.getPostalCode().getCounty());
        this.district.setText(municipality.getPostalCode().getDistrict());
        ObservableList<GetAllMunicipalitiesAndBucketsResponseDTO.Bucket> items = FXCollections.observableArrayList(municipality.getBuckets());
        buckets.setItems(items);
        buckets.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(GetAllMunicipalitiesAndBucketsResponseDTO.Bucket bucket, boolean empty) {
                super.updateItem(bucket, empty);
                if (empty || bucket == null) {
                    setText(null);
                } else {
                    setText(String.format(
                            "ID: %d | Capacidade: %.2f Kg",
                            bucket.getId(),
                            bucket.getCapacity()
                    ));
                }
            }
        });
        List<GetAllBucketsResponseDTO.Bucket> filteredBuckets = getAllBuckets().stream()
                .filter(bucket -> !bucket.getIsAssociated()) // Filter the buckets that already have an association
                .collect(Collectors.toList());
        ObservableList<GetAllBucketsResponseDTO.Bucket> buckets = FXCollections.observableArrayList(filteredBuckets);
        availableBuckets.setItems(buckets);
        availableBuckets.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(GetAllBucketsResponseDTO.Bucket bucket, boolean empty) {
                super.updateItem(bucket, empty);
                if (empty || bucket == null) {
                    setText(null);
                } else {
                    setText(String.format(
                            "ID: %s | Capacidade: %s Kg",
                            bucket.getId(),
                            bucket.getCapacity()
                    ));
                }
            }
        });
        availableBuckets.setConverter(new StringConverter<>() {
            @Override
            public String toString(GetAllBucketsResponseDTO.Bucket bucket) {
                if (bucket == null) {
                    return "";
                }
                return String.format("ID: %s | Capacidade: %s Kg", bucket.getId(), bucket.getCapacity());
            }

            @Override
            public GetAllBucketsResponseDTO.Bucket fromString(String s) {
                return null;
            }
        });
        this.postalCodeId = municipality.getPostalCode().getId();
        this.addressId = municipality.getAddress().getId();
    }

    @FXML
    public void onCancelBtnClick() {
        closeModal();
    }

    @FXML
    public void onSaveBtnClick() throws IOException {
        if (username.getText().isEmpty() || name.getText().isEmpty() || phone.getText().isEmpty() || email.getText().isEmpty() ||
                nif.getText().isEmpty() || userType.getText().isEmpty() || street.getText().isEmpty() || doorNumber.getText().isEmpty() ||
                floorDetails.getText().isEmpty() || floorNumber.getText().isEmpty() || postalCode.getText().isEmpty() || county.getText().isEmpty() ||
                district.getText().isEmpty()) {
            toggleErrorLabel(true);
            return; // Don't proceed with the request
        } else {

            toggleErrorLabel(false);
        }
        String url = String.format("http://localhost:8080/api/users/update-full-municipality");
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
            UpdateMunicipalityRequestDTO municipality = new UpdateMunicipalityRequestDTO();

            // Create and set Municipality info
            UpdateMunicipalityRequestDTO.Municipality municipalityInfo = new UpdateMunicipalityRequestDTO.Municipality();
            municipalityInfo.setId(Long.parseLong(id.getText()));
            municipalityInfo.setCitizenCardCode(cc.getText());
            municipalityInfo.setNif(nif.getText());
            municipality.setMunicipality(municipalityInfo);
            // Create and set User info
            UpdateMunicipalityRequestDTO.User userInfo = new UpdateMunicipalityRequestDTO.User();
            userInfo.setId(Long.parseLong(id.getText()));
            userInfo.setName(name.getText());
            userInfo.setUsername(username.getText());
            userInfo.setEmail(email.getText());
            userInfo.setPhoneNumber(phone.getText());
            municipality.setUser(userInfo);
            // Create and set Address info
            UpdateMunicipalityRequestDTO.Address addressInfo = new UpdateMunicipalityRequestDTO.Address();
            addressInfo.setId(addressId);
            addressInfo.setStreet(street.getText());
            addressInfo.setFloorNumber(Integer.parseInt(floorNumber.getText()));
            addressInfo.setDoorNumber(Integer.parseInt(doorNumber.getText()));
            addressInfo.setFloorDetails(floorDetails.getText());
            municipality.setAddress(addressInfo);
            // Create and set PostalCode info
            UpdateMunicipalityRequestDTO.PostalCode postalCodeInfo = new UpdateMunicipalityRequestDTO.PostalCode();
            postalCodeInfo.setId(postalCodeId);
            postalCodeInfo.setPostalCode(postalCode.getText());
            postalCodeInfo.setCounty(county.getText());
            postalCodeInfo.setDistrict(district.getText());
            municipality.setPostalCode(postalCodeInfo);


            String jsonBody = String.format("{\n" +
                            "  \"user\": {\n" +
                            "    \"id\": %d,\n" +
                            "    \"name\": \"%s\",\n" +
                            "    \"username\": \"%s\",\n" +
                            "    \"email\": \"%s\",\n" +
                            "    \"phoneNumber\": \"%s\"\n" +
                            "  },\n" +
                            "  \"address\": {\n" +
                            "    \"id\": %d,\n" +
                            "    \"floorDetails\": \"%s\",\n" +
                            "    \"floorNumber\": %d,\n" +
                            "    \"doorNumber\": %d,\n" +
                            "    \"street\": \"%s\"\n" +
                            "  },\n" +
                            "  \"postalCode\": {\n" +
                            "    \"id\": %d,\n" +
                            "    \"postalCode\": \"%s\",\n" +
                            "    \"county\": \"%s\",\n" +
                            "    \"district\": \"%s\"\n" +
                            "  },\n" +
                            "  \"municipality\": {\n" +
                            "    \"id\": %d,\n" +
                            "    \"citizenCardCode\": \"%s\",\n" +
                            "    \"nif\": \"%s\"\n" +
                            "  }\n" +
                            "}",
                    userInfo.getId(),
                    userInfo.getName(),
                    userInfo.getUsername(),
                    userInfo.getEmail(),
                    userInfo.getPhoneNumber(),

                    addressInfo.getId(),
                    addressInfo.getFloorDetails(),
                    addressInfo.getFloorNumber(),
                    addressInfo.getDoorNumber(),
                    addressInfo.getStreet(),

                    postalCodeInfo.getId(),
                    postalCodeInfo.getPostalCode(),
                    postalCodeInfo.getCounty(),
                    postalCodeInfo.getDistrict(),

                    municipalityInfo.getId(),
                    municipalityInfo.getCitizenCardCode(),
                    municipalityInfo.getNif()
            );

            if (!availableBuckets.getSelectionModel().isEmpty()){
                createBucketAssociation(availableBuckets.getSelectionModel().getSelectedItem().toString(), userInfo.getId().toString());
            }

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

    public List<GetAllBucketsResponseDTO.Bucket> getAllBuckets() {
        // Define the API endpoint
        String url = "http://localhost:8080/api/buckets";
        List<GetAllBucketsResponseDTO.Bucket> buckets = null;

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

                // Parse the JSON response to GetAllBucketsResponseDTO
                ObjectMapper objectMapper = new ObjectMapper();
                GetAllBucketsResponseDTO responseDTO = objectMapper.readValue(response.toString(), GetAllBucketsResponseDTO.class);

                // Get the buckets list from the response DTO
                buckets = responseDTO.getBuckets();
            } else {
                System.out.println("Error: Unable to fetch data. HTTP code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buckets; // Return the parsed buckets list
    }

    public int createBucketAssociation(String bucketId, String municipalityId) {
        String url = String.format("http://localhost:8080/api/buckets/bucket-association");
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
        return responseCode;
    }
}
