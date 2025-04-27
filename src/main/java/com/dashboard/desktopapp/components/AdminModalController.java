package com.dashboard.desktopapp.components;

import com.dashboard.desktopapp.dtos.user.request.UpdateAdminRequestDTO;
import com.dashboard.desktopapp.dtos.user.response.GetAdminByIdResponseDTO;
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

public class AdminModalController {

    @FXML
    private VBox modal;

    private Long id;
    private Long addressId;
    private Long postalCodeId;
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
    private Label errorLabel;

    private PageRefresh reloadController;

    public void setReloadController(PageRefresh controller) {
        this.reloadController = controller;
    }

    public void setEditUserInfo(GetAdminByIdResponseDTO admin) {
        id = admin.getUser().getId();
        addressId = admin.getAddress().getId();
        postalCodeId = admin.getPostalCode().getId();
        this.name.setText(admin.getUser().getName());
        this.username.setText(admin.getUser().getUsername());
        this.email.setText(admin.getUser().getEmail());
        this.phone.setText(admin.getUser().getPhoneNumber());
        this.cc.setText(admin.getAdmin().getCitizenCardCode());
        this.floorDetails.setText(admin.getAddress().getFloorDetails());
        this.floorNumber.setText(admin.getAddress().getFloorNumber().toString());
        this.doorNumber.setText(admin.getAddress().getDoorNumber().toString());
        this.street.setText(admin.getAddress().getStreet());
        this.postalCode.setText(admin.getPostalCode().getPostalCode());
        this.county.setText(admin.getPostalCode().getCounty());
        this.district.setText(admin.getPostalCode().getDistrict());
    }

    @FXML
    public void onCancelBtnClick() {
        closeModal();
    }

    @FXML
    public void onSaveBtnClick() throws IOException {
        if (username.getText().isEmpty() || name.getText().isEmpty() || phone.getText().isEmpty() || email.getText().isEmpty() ||
                street.getText().isEmpty() || doorNumber.getText().isEmpty() || floorDetails.getText().isEmpty() ||
                floorNumber.getText().isEmpty() || postalCode.getText().isEmpty() || county.getText().isEmpty() ||
                district.getText().isEmpty()) {
            toggleErrorLabel(true);
            return; // Don't proceed with the request
        } else {

            toggleErrorLabel(false);
        }
        String url = "http://localhost:8080/api/users/update-full-admin";
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
            UpdateAdminRequestDTO admin = new UpdateAdminRequestDTO();

            // Create and set the info
            UpdateAdminRequestDTO.Admin adminInfo = new UpdateAdminRequestDTO.Admin();
            adminInfo.setId(id);
            adminInfo.setCitizenCardCode(cc.getText());
            admin.setAdmin(adminInfo);
            // Create and set User info
            UpdateAdminRequestDTO.User userInfo = new UpdateAdminRequestDTO.User();
            userInfo.setId(id);
            userInfo.setName(name.getText());
            userInfo.setUsername(username.getText());
            userInfo.setEmail(email.getText());
            userInfo.setPhoneNumber(phone.getText());
            admin.setUser(userInfo);
            // Create and set Address info
            UpdateAdminRequestDTO.Address addressInfo = new UpdateAdminRequestDTO.Address();
            addressInfo.setId(addressId);
            addressInfo.setFloorDetails(floorDetails.getText());
            addressInfo.setFloorNumber(Integer.parseInt(floorNumber.getText()));
            addressInfo.setDoorNumber(Integer.parseInt(doorNumber.getText()));
            addressInfo.setStreet(street.getText());
            admin.setAddress(addressInfo);
            // Create and set PostalCode info
            UpdateAdminRequestDTO.PostalCode postalCodeInfo = new UpdateAdminRequestDTO.PostalCode();
            postalCodeInfo.setId(postalCodeId);
            postalCodeInfo.setPostalCode(postalCode.getText());
            postalCodeInfo.setCounty(county.getText());
            postalCodeInfo.setDistrict(district.getText());
            admin.setPostalCode(postalCodeInfo);


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
                            "  \"admin\": {\n" +
                            "    \"id\": %d,\n" +
                            "    \"citizenCardCode\": \"%s\"\n" +
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

                    adminInfo.getId(),
                    adminInfo.getCitizenCardCode()
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
