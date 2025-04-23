package com.dashboard.desktopapp.components;

import com.dashboard.desktopapp.models.Bucket;
import com.dashboard.desktopapp.models.Municipality;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class BucketsModalController {
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
    private TextField municipality;
    @FXML
    private ComboBox<Municipality> municipalities;
    @FXML
    private Label errorLabel;


    public void setViewBucketInfo(Bucket bucket) {
        this.id.setText(bucket.getId().toString());
        this.capacity.setText(bucket.getCapacity().toString());
        this.associated.setText(bucket.getAssociated().toString());
        this.state.setText(bucket.getState());
        this.municipality.setText(bucket.getMunicipality());
    }

    public void setEditBucketInfo(Bucket bucket) {
        this.id.setText(bucket.getId().toString());
        this.capacity.setText(bucket.getCapacity().toString());
        this.associated.setText(bucket.getAssociated().toString());
        this.state.setText(bucket.getState());
        this.municipality.setText(bucket.getMunicipality());
        this.municipalities.setItems(FXCollections.observableArrayList());
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

    @FXML
    public void onCreateBtnClick() throws IOException {

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
