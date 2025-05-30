package com.dashboard.desktopapp.components;

import com.dashboard.desktopapp.dtos.user.request.CreateSmasRequestDTO;
import com.dashboard.desktopapp.dtos.user.request.UpdateSmasRequestDTO;
import com.dashboard.desktopapp.dtos.user.response.GetAllSmasResponseDTO;
import com.dashboard.desktopapp.interfaces.PageRefresh;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SMASModalController {

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
    private TextField workerId;
    @FXML
    private TextField position;
    @FXML
    private TextField password;
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
        if (workerId != null) {
            workerId.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("^\\d+$")) {
                    workerId.setText(newValue.replaceAll("[^\\d-]", ""));
                }
            });
        }
    }

    public String setAddress(GetAllSmasResponseDTO.SmasData smas) {
        String address = String.format("%s, %d, %d, %s, %s, %s, %s",
                smas.getAddress().getStreet(), smas.getAddress().getDoorNumber(), smas.getAddress().getFloorNumber(), smas.getAddress().getFloorDetails(), smas.getPostalCode().getPostalCode(), smas.getPostalCode().getCounty(), smas.getPostalCode().getDistrict());
        return address;
    }

    public void setViewUserInfo(GetAllSmasResponseDTO.SmasData smas) {
        this.id.setText(smas.getUser().getId().toString());
        this.name.setText(smas.getUser().getName());
        this.username.setText(smas.getUser().getUsername());
        this.email.setText(smas.getUser().getEmail());
        this.phone.setText(smas.getUser().getPhoneNumber());
        this.cc.setText(smas.getSmas().getCitizenCardCode());
        this.userType.setText(smas.getUser().getRole());
        this.address.setText(setAddress(smas));
        this.workerId.setText(smas.getSmas().getEmployeeCode());
        this.position.setText(smas.getSmas().getPosition());
    }

    public void setEditUserInfo(GetAllSmasResponseDTO.SmasData smas) {
        this.id.setText(smas.getUser().getId().toString());
        this.name.setText(smas.getUser().getName());
        this.username.setText(smas.getUser().getUsername());
        this.email.setText(smas.getUser().getEmail());
        this.phone.setText(smas.getUser().getPhoneNumber());
        this.cc.setText(smas.getSmas().getCitizenCardCode());
        this.userType.setText(smas.getUser().getRole());
        this.floorDetails.setText(smas.getAddress().getFloorDetails());
        this.floorNumber.setText(smas.getAddress().getFloorNumber().toString());
        this.doorNumber.setText(smas.getAddress().getDoorNumber().toString());
        this.street.setText(smas.getAddress().getStreet());
        this.postalCode.setText(smas.getPostalCode().getPostalCode());
        this.county.setText(smas.getPostalCode().getCounty());
        this.district.setText(smas.getPostalCode().getDistrict());
        this.workerId.setText(smas.getSmas().getEmployeeCode());
        this.position.setText(smas.getSmas().getPosition());
        this.postalCodeId = smas.getUser().getId();
        this.addressId = smas.getUser().getId();
    }

    @FXML
    public void onCancelBtnClick() {
        closeModal();
    }

    @FXML
    public void onSaveBtnClick() throws IOException {
        if (username.getText().isEmpty() || name.getText().isEmpty() || phone.getText().isEmpty() || email.getText().isEmpty() ||
                cc.getText().isEmpty() || userType.getText().isEmpty() || street.getText().isEmpty() || doorNumber.getText().isEmpty() ||
                floorDetails.getText().isEmpty() || floorNumber.getText().isEmpty() || postalCode.getText().isEmpty() || county.getText().isEmpty() ||
                district.getText().isEmpty()) {
            toggleErrorLabel(true);
            return; // Don't proceed with the request
        } else {

            toggleErrorLabel(false);
        }
        String url = "http://localhost:8080/api/users/update-full-smas";
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
            UpdateSmasRequestDTO smas = new UpdateSmasRequestDTO();

            // Create and set SMAS info
            UpdateSmasRequestDTO.Smas smasInfo = new UpdateSmasRequestDTO.Smas();
            smasInfo.setId(Long.parseLong(id.getText()));
            smasInfo.setCitizenCardCode(cc.getText());
            smasInfo.setEmployeeCode(workerId.getText());
            smasInfo.setPosition(position.getText());
            smas.setSmas(smasInfo);
            // Create and set User info
            UpdateSmasRequestDTO.User userInfo = new UpdateSmasRequestDTO.User();
            userInfo.setId(Long.parseLong(id.getText()));
            userInfo.setName(name.getText());
            userInfo.setUsername(username.getText());
            userInfo.setEmail(email.getText());
            userInfo.setPhoneNumber(phone.getText());
            smas.setUser(userInfo);
            // Create and set Address info
            UpdateSmasRequestDTO.Address addressInfo = new UpdateSmasRequestDTO.Address();
            addressInfo.setId(addressId);
            addressInfo.setStreet(street.getText());
            addressInfo.setFloorNumber(Integer.parseInt(floorNumber.getText()));
            addressInfo.setDoorNumber(Integer.parseInt(doorNumber.getText()));
            addressInfo.setFloorDetails(floorDetails.getText());
            smas.setAddress(addressInfo);
            // Create and set PostalCode info
            UpdateSmasRequestDTO.PostalCode postalCodeInfo = new UpdateSmasRequestDTO.PostalCode();
            postalCodeInfo.setId(postalCodeId);
            postalCodeInfo.setPostalCode(postalCode.getText());
            postalCodeInfo.setCounty(county.getText());
            postalCodeInfo.setDistrict(district.getText());
            smas.setPostalCode(postalCodeInfo);


            String jsonBody = String.format("{\n" +
                            "  \"user\": {\n" +
                            "    \"id\": \"%d\",\n" +
                            "    \"name\": \"%s\",\n" +
                            "    \"username\": \"%s\",\n" +
                            "    \"email\": \"%s\",\n" +
                            "    \"phoneNumber\": \"%s\"\n" +
                            "  },\n" +
                            "  \"address\": {\n" +
                            "    \"id\": \"%d\",\n" +
                            "    \"floorDetails\": \"%s\",\n" +
                            "    \"floorNumber\": %d,\n" +
                            "    \"doorNumber\": %d,\n" +
                            "    \"street\": \"%s\"\n" +
                            "  },\n" +
                            "  \"postalCode\": {\n" +
                            "    \"id\": \"%d\",\n" +
                            "    \"postalCode\": \"%s\",\n" +
                            "    \"county\": \"%s\",\n" +
                            "    \"district\": \"%s\"\n" +
                            "  },\n" +
                            "  \"smas\": {\n" +
                            "    \"id\": \"%d\",\n" +
                            "    \"citizenCardCode\": \"%s\",\n" +
                            "    \"employeeCode\": \"%s\",\n" +
                            "    \"position\": \"%s\"\n" +
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

                    smasInfo.getId(),
                    smasInfo.getCitizenCardCode(),
                    smasInfo.getEmployeeCode(),
                    smasInfo.getPosition()
            );


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
        if (username.getText().isEmpty() || name.getText().isEmpty() || phone.getText().isEmpty() || email.getText().isEmpty() ||
                cc.getText().isEmpty() || street.getText().isEmpty() || doorNumber.getText().isEmpty() ||
                floorDetails.getText().isEmpty() || floorNumber.getText().isEmpty() || postalCode.getText().isEmpty() || county.getText().isEmpty() ||
                district.getText().isEmpty() || workerId.getText().isEmpty() || position.getText().isEmpty() || password.getText().isEmpty()) {
            toggleErrorLabel(true);
            return; // Don't proceed with the request
        } else {

            toggleErrorLabel(false);
        }
        String url = "http://localhost:8080/api/users/create/smas";
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
            CreateSmasRequestDTO smas = new CreateSmasRequestDTO();

            // Create and set SMAS info
            CreateSmasRequestDTO.Smas smasInfo = new CreateSmasRequestDTO.Smas();
            smasInfo.setCitizenCardCode(cc.getText());
            smasInfo.setEmployeeCode(workerId.getText());
            smasInfo.setPosition(position.getText());
            // Create and set User info
            CreateSmasRequestDTO.User userInfo = new CreateSmasRequestDTO.User();
            userInfo.setName(name.getText());
            userInfo.setUsername(username.getText());
            userInfo.setPassword(password.getText());
            userInfo.setEmail(email.getText());
            userInfo.setPhoneNumber(phone.getText());
            smas.setUser(userInfo);
            // Create and set Address info
            CreateSmasRequestDTO.Address addressInfo = new CreateSmasRequestDTO.Address();
            addressInfo.setStreet(street.getText());
            addressInfo.setFloorNumber(Integer.parseInt(floorNumber.getText()));
            addressInfo.setDoorNumber(Integer.parseInt(doorNumber.getText()));
            addressInfo.setFloorDetails(floorDetails.getText());
            smas.setAddress(addressInfo);
            // Create and set PostalCode info
            CreateSmasRequestDTO.PostalCode postalCodeInfo = new CreateSmasRequestDTO.PostalCode();
            postalCodeInfo.setPostalCode(postalCode.getText());
            postalCodeInfo.setCounty(county.getText());
            postalCodeInfo.setDistrict(district.getText());
            smas.setPostalCode(postalCodeInfo);


            String jsonBody = String.format("{\n" +
                            "  \"user\": {\n" +
                            "    \"name\": \"%s\",\n" +
                            "    \"username\": \"%s\",\n" +
                            "    \"password\": \"%s\",\n" +
                            "    \"email\": \"%s\",\n" +
                            "    \"phoneNumber\": \"%s\"\n" +
                            "  },\n" +
                            "  \"smas\": {\n" +
                            "    \"citizenCardCode\": \"%s\",\n" +
                            "    \"employeeCode\": \"%s\",\n" +
                            "    \"position\": \"%s\"\n" +
                            "  },\n" +
                            "  \"address\": {\n" +
                            "    \"floorDetails\": \"%s\",\n" +
                            "    \"floorNumber\": %d,\n" +
                            "    \"doorNumber\": %d,\n" +
                            "    \"street\": \"%s\"\n" +
                            "  },\n" +
                            "  \"postalCode\": {\n" +
                            "    \"postalCode\": \"%s\",\n" +
                            "    \"county\": \"%s\",\n" +
                            "    \"district\": \"%s\"\n" +
                            "  }\n" +
                            "}",

                    userInfo.getName(),
                    userInfo.getUsername(),
                    userInfo.getPassword(),
                    userInfo.getEmail(),
                    userInfo.getPhoneNumber(),

                    smasInfo.getCitizenCardCode(),
                    smasInfo.getEmployeeCode(),
                    smasInfo.getPosition(),

                    addressInfo.getFloorDetails(),
                    addressInfo.getFloorNumber(),
                    addressInfo.getDoorNumber(),
                    addressInfo.getStreet(),

                    postalCodeInfo.getPostalCode(),
                    postalCodeInfo.getCounty(),
                    postalCodeInfo.getDistrict()
            );

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

