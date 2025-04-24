package com.dashboard.desktopapp;

import com.dashboard.desktopapp.components.AdminModalController;
import com.dashboard.desktopapp.dtos.user.response.GetAdminByIdResponseDTO;
import com.dashboard.desktopapp.dtos.user.response.GetAllSmasResponseDTO;
import com.dashboard.desktopapp.models.Admin;
import com.dashboard.desktopapp.appsession.AppSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ProfileController {

    @FXML
    private BorderPane content;

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

    public String setAddress(GetAdminByIdResponseDTO admin) {
        String address = String.format("%s, %d, %d, %s, %s, %s, %s",
                admin.getStreet(), admin.getDoorNumber(), admin.getFloorNumber(), admin.getFloorDetails(), admin.getPostalCode(), admin.getCounty(), admin.getDistrict());
        return address;
    }

    @FXML
    public void initialize() {
        String sessionToken = AppSession.getToken();
        setViewUserInfo(getProfileInfo(sessionToken));
    }

    public void setViewUserInfo(GetAdminByIdResponseDTO admin) {
        this.name.setText(admin.getUser().getName());
        this.username.setText(admin.getUser().getUsername());
        this.email.setText(admin.getUser().getEmail());
        this.phone.setText(admin.getUser().getPhoneNumber());
        this.cc.setText(admin.getAdmin().getCitizenCardCode());
        this.userType.setText(admin.getUser().getRole());
        this.address.setText(setAddress(admin));
    }

    @FXML
    public void onEditBtnClick() {
        try {
            // Load the modal's FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("components/edit-profile-modal.fxml"));
            Parent modalRoot = fxmlLoader.load();
            AdminModalController adminController = fxmlLoader.getController();
            adminController.setEditUserInfo(getProfileInfo());

            // Create a new stage for the modal
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

    public GetAdminByIdResponseDTO getProfileInfo(String sessionToken) {
        // Define the API endpoint
        String baseUrl = "http://localhost:8080/api/users/get/admin/";
        String url = baseUrl + sessionToken;

        GetAdminByIdResponseDTO adminData = null;

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

                // Parse the JSON response to GetAdminByIdResponseDTO
                ObjectMapper objectMapper = new ObjectMapper();
                GetAdminByIdResponseDTO responseDTO = objectMapper.readValue(response.toString(), GetAdminByIdResponseDTO.class);

                adminData = responseDTO;
            } else {
                System.out.println("Error: Unable to fetch data. HTTP code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return adminData; // Return the parsed containers list
    }

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
}
