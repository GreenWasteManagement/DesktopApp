package com.dashboard.desktopapp;

import com.dashboard.desktopapp.components.AdminModalController;
import com.dashboard.desktopapp.models.Admin;
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

import java.io.IOException;

public class ProfileController {

    private AdminModalController adminController;

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

    public String setAddress(Admin admin) {
        String address = String.format("%s, %d, %d, %s, %s, %s, %s",
                admin.getStreet(), admin.getDoorNumber(), admin.getFloorNumber(), admin.getFloorDetails(), admin.getPostalCode(), admin.getCounty(), admin.getDistrict());
        return address;
    }

    @FXML
    public void initialize() {
        Admin mockUser = createMockUser();
        setViewUserInfo(mockUser);
    }

    public void setViewUserInfo(Admin admin) {
        this.name.setText(admin.getName());
        this.username.setText(admin.getUsername());
        this.email.setText(admin.getEmail());
        this.phone.setText(admin.getPhone());
        this.cc.setText(admin.getCc().toString());
        this.nif.setText(admin.getNif().toString());
        this.userType.setText(admin.getUserType());
        this.address.setText(setAddress(admin));
    }

    private Admin createMockUser() {
        return new Admin(
                1,
                "Test User",
                "testuser",
                "test@example.com",
                "910000000",
                12345678,
                999999999,
                "admin",
                "Frente",
                1,
                101,
                "Rua Central",
                "1000-000",
                "Lisboa",
                "Lisboa"
        );
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

    @FXML
    public void onEditBtnClick() {
        try {
            // Load the modal's FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("components/edit-profile-modal.fxml"));
            Parent modalRoot = fxmlLoader.load();
            adminController = fxmlLoader.getController();
            adminController.setEditUserInfo(createMockUser());

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
}
