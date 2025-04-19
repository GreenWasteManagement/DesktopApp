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
        if (municipality != null) {
            System.out.println("Editing user ID: " + municipality.getId());
        } else if (smas != null) {
            System.out.println("Editing smas ID: " + smas.getId());
        } else if (bucket != null) {
            System.out.println("Editing bucket ID: " + bucket.getId());
        } else if (container != null) {
            System.out.println("Editing container ID: " + container.getId());
        }
        try {
            // Load the modal's FXML
            String fxmlPath = String.format("/com/dashboard/desktopapp/components/%s-view-modal.fxml", modalType);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent modalRoot = fxmlLoader.load();
            municipalityController = fxmlLoader.getController();
            municipalityController.setUserInfo(municipality);

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
        if (municipality != null) {
            System.out.println("Editing user ID: " + municipality.getId());
        } else if (smas != null) {
            System.out.println("Editing smas ID: " + smas.getId());
        } else if (bucket != null) {
            System.out.println("Editing bucket ID: " + bucket.getId());
        } else if (container != null) {
            System.out.println("Editing container ID: " + container.getId());
        }
        try {
            // Load the modal's FXML
            String fxmlPath = String.format("/com/dashboard/desktopapp/components/%s-edit-modal.fxml", modalType);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
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

    @FXML
    public void onDeleteBtnClick() {
        if (municipality != null) {
            System.out.println("Editing user ID: " + municipality.getId());
        } else if (smas != null) {
            System.out.println("Editing smas ID: " + smas.getId());
        } else if (bucket != null) {
            System.out.println("Editing bucket ID: " + bucket.getId());
        } else if (container != null) {
            System.out.println("Editing container ID: " + container.getId());
        }
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
