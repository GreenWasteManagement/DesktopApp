package com.dashboard.desktopapp.components;

import com.dashboard.desktopapp.models.Municipality;
import com.dashboard.desktopapp.models.SMAS;
import com.dashboard.desktopapp.models.Bucket;
import com.dashboard.desktopapp.models.Container;
import com.dashboard.desktopapp.components.MunicipalityModalController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class EditButtonsController {

    private Municipality municipality;
    private SMAS smas;
    private Bucket bucket;
    private Container container;
    private String modalType;
    private MunicipalityModalController municipalityController;
    private SMASModalController smasController;
    private BucketsModalController bucketController;
    private ContainersModalController containerController;

    @FXML
    private HBox buttons;

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }
    public void setSMAS(SMAS smas) {
        this.smas = smas;
    }
    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
    }
    public void setContainer(Container container) {
        this.container = container;
    }
    public void setModalType(String modalType) {
        this.modalType = modalType;
    }

    @FXML
    public void onViewBtnClick() {
        try {
            // Load the modal's FXML
            String fxmlPath = String.format("/com/dashboard/desktopapp/components/%s-view-modal.fxml", modalType);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent modalRoot = fxmlLoader.load();
            if (municipality != null) {
                System.out.println("Editing user ID: " + municipality.getId());
                municipalityController = fxmlLoader.getController();
                municipalityController.setViewUserInfo(municipality);
            } else if (smas != null) {
                System.out.println("Editing smas ID: " + smas.getId());
                smasController = fxmlLoader.getController();
                smasController.setViewUserInfo(smas);
            } else if (bucket != null) {
                System.out.println("Editing bucket ID: " + bucket.getId());
                bucketController = fxmlLoader.getController();
                bucketController.setViewBucketInfo(bucket);
            } else if (container != null) {
                System.out.println("Editing container ID: " + container.getId());
                containerController = fxmlLoader.getController();
                containerController.setViewContainerInfo(container);
            }

            // Create a new stage for the modal
            Stage modalStage = new Stage();
            modalStage.setTitle("");
            Image image = new Image(this.getClass().getResourceAsStream("/com/dashboard/desktopapp/images/APP_LOGO.png"));
            modalStage.getIcons().add(image);
            modalStage.setScene(new Scene(modalRoot));
            modalStage.initOwner(buttons.getScene().getWindow());
            modalStage.setResizable(false);
            modalStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onEditBtnClick() {
        try {
            // Load the modal's FXML
            String fxmlPath = String.format("/com/dashboard/desktopapp/components/%s-edit-modal.fxml", modalType);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent modalRoot = fxmlLoader.load();
            if (municipality != null) {
                System.out.println("Editing user ID: " + municipality.getId());
                municipalityController = fxmlLoader.getController();
                municipalityController.setEditUserInfo(municipality);
            } else if (smas != null) {
                System.out.println("Editing smas ID: " + smas.getId());
                smasController = fxmlLoader.getController();
                smasController.setEditUserInfo(smas);
            } else if (bucket != null) {
                System.out.println("Editing bucket ID: " + bucket.getId());
                bucketController = fxmlLoader.getController();
                bucketController.setEditBucketInfo(bucket);
            } else if (container != null) {
                System.out.println("Editing container ID: " + container.getId());
                containerController = fxmlLoader.getController();
                containerController.setEditContainerInfo(container);
            }

            // Create a new stage for the modal
            Stage modalStage = new Stage();
            modalStage.setTitle("");
            Image image = new Image(this.getClass().getResourceAsStream("/com/dashboard/desktopapp/images/APP_LOGO.png"));
            modalStage.getIcons().add(image);
            modalStage.setScene(new Scene(modalRoot));
            modalStage.initOwner(buttons.getScene().getWindow());
            modalStage.setResizable(false);
            modalStage.initModality(javafx.stage.Modality.WINDOW_MODAL);

            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onDeleteBtnClick() {
        try {
            // Load the modal's FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/dashboard/desktopapp/components/confirm-delete-modal.fxml"));
            Parent modalRoot = fxmlLoader.load();

            // Create a new stage for the modal
            Stage modalStage = new Stage();
            modalStage.setTitle("");
            Image image = new Image(this.getClass().getResourceAsStream("/com/dashboard/desktopapp/images/APP_LOGO.png"));
            modalStage.getIcons().add(image);
            modalStage.setScene(new Scene(modalRoot));
            modalStage.initOwner(buttons.getScene().getWindow());
            modalStage.setResizable(false);
            modalStage.initModality(javafx.stage.Modality.WINDOW_MODAL);

            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
