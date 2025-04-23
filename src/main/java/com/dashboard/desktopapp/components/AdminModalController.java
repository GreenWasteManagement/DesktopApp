package com.dashboard.desktopapp.components;

import com.dashboard.desktopapp.models.Admin;
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

public class AdminModalController {

    @FXML
    private VBox modal;

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
    private TextField newPassword;
    @FXML
    private TextField currPassword;
    @FXML
    private Label errorLabel;

    public void setEditUserInfo(Admin admin) {
        this.name.setText(admin.getName());
        this.username.setText(admin.getUsername());
        this.email.setText(admin.getEmail());
        this.phone.setText(admin.getPhone());
        this.cc.setText(admin.getCc().toString());
        this.nif.setText(admin.getNif().toString());
        this.userType.setText(admin.getUserType());
        this.floorDetails.setText(admin.getFloorDetails());
        this.floorNumber.setText(admin.getFloorNumber().toString());
        this.doorNumber.setText(admin.getDoorNumber().toString());
        this.street.setText(admin.getStreet());
        this.postalCode.setText(admin.getPostalCode());
        this.county.setText(admin.getCounty());
        this.district.setText(admin.getDistrict());
    }

    @FXML
    public void onCancelBtnClick() {
        closeModal();
    }

    @FXML
    public void onSaveBtnClick() throws IOException {

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
        controller.setConfirmationText(201); // Pass the request status code to set the text
        modalStage.showAndWait();
    }

    public void closeModal() {
        Stage stage = (Stage) (modal.getScene().getWindow());
        stage.close();
    }
}
