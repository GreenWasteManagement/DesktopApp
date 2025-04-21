package com.dashboard.desktopapp.components;

import com.dashboard.desktopapp.models.SMAS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

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
    private TextField workerId;
    @FXML
    private TextField position;

    public String setAddress(SMAS smas) {
        String address = String.format("%s, %d, %d, %s, %s, %s, %s",
                smas.getStreet(), smas.getDoorNumber(), smas.getFloorNumber(), smas.getFloorDetails(), smas.getPostalCode(), smas.getCounty(), smas.getDistrict());
        return address;
    }

    public void setViewUserInfo(SMAS smas) {
        this.id.setText(smas.getId().toString());
        this.name.setText(smas.getName());
        this.username.setText(smas.getUsername());
        this.email.setText(smas.getEmail());
        this.phone.setText(smas.getPhone());
        this.cc.setText(smas.getCc().toString());
        this.nif.setText(smas.getNif().toString());
        this.userType.setText(smas.getUserType());
        this.address.setText(setAddress(smas));
        this.workerId.setText(smas.getWorkerId());
        this.position.setText(smas.getPosition());
    }

    public void setEditUserInfo(SMAS smas) {
        this.id.setText(smas.getId().toString());
        this.name.setText(smas.getName());
        this.username.setText(smas.getUsername());
        this.email.setText(smas.getEmail());
        this.phone.setText(smas.getPhone());
        this.cc.setText(smas.getCc().toString());
        this.nif.setText(smas.getNif().toString());
        this.userType.setText(smas.getUserType());
        this.floorDetails.setText(smas.getFloorDetails());
        this.floorNumber.setText(smas.getFloorNumber().toString());
        this.doorNumber.setText(smas.getDoorNumber().toString());
        this.street.setText(smas.getStreet());
        this.postalCode.setText(smas.getPostalCode());
        this.county.setText(smas.getCounty());
        this.district.setText(smas.getDistrict());
        this.workerId.setText(smas.getWorkerId());
        this.position.setText(smas.getPosition());
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

