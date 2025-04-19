package com.dashboard.desktopapp.components;

import com.dashboard.desktopapp.models.Bucket;
import com.dashboard.desktopapp.models.Municipality;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

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
    private ListView<com.dashboard.desktopapp.models.Bucket> buckets;

    public void setUserInfo(Municipality municipality) {
        this.id.setText(municipality.getId().toString());
        this.name.setText(municipality.getName());
        this.username.setText(municipality.getUsername());
        this.email.setText(municipality.getEmail());
        this.phone.setText(municipality.getPhone());
        this.cc.setText(municipality.getCc().toString());
        this.nif.setText(municipality.getNif().toString());
        this.userType.setText(municipality.getUserType());
        this.address.setText(municipality.getAddress());
        ObservableList<com.dashboard.desktopapp.models.Bucket> items = FXCollections.observableArrayList(municipality.getBucketList());
        buckets.setItems(items);
        buckets.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(com.dashboard.desktopapp.models.Bucket bucket, boolean empty) {
                super.updateItem(bucket, empty);
                if (empty || bucket == null) {
                    setText(null);
                } else {
                    setText(String.format(
                            "ID: %d | Capacidade: %.2f | Estado: %s",
                            bucket.getId(),
                            bucket.getCapacity(),
                            bucket.getState()
                    ));
                }
            }
        });

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

    public static class Bucket {
        private final Integer id;
        private final Float capacity;
        private final Boolean associated;
        private final String municipality;

        public Bucket(Integer id, Float capacity, Boolean associated, String municipality) {
            this.id = id;
            this.capacity = capacity;
            this.associated = associated;
            this.municipality = municipality;
        }

        public Integer getId() { return id; }
        public Float getCapacity() { return capacity; }
        public Boolean getAssociated() { return associated; }
        public String getMunicipality() { return municipality; }
    }
}
