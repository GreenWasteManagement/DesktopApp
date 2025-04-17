package com.dashboard.desktopapp.components;

import com.dashboard.desktopapp.BucketsListController;
import com.dashboard.desktopapp.ContainersListController;
import com.dashboard.desktopapp.UsersListController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class EditButtonsController {

    private UsersListController.User user;
    private BucketsListController.Bucket bucket;
    private ContainersListController.Container container;
    private String modalType;
    @FXML
    private HBox buttons;

    public void setUser(UsersListController.User user) {
        this.user = user;
    }
    public void setBucket(BucketsListController.Bucket bucket) {
        this.bucket = bucket;
    }
    public void setContainer(ContainersListController.Container container) {
        this.container = container;
    }
    public void setModalType(String modalType) {
        this.modalType = modalType;
    }

    @FXML
    public void onViewBtnClick() {
        if (user != null) {
            System.out.println("Editing user ID: " + user.getId());
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
        if (user != null) {
            System.out.println("Editing user ID: " + user.getId());
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
        if (user != null) {
            System.out.println("Editing user ID: " + user.getId());
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
