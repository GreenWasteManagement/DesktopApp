package com.dashboard.desktopapp.components;

import com.dashboard.desktopapp.dtos.container.response.GetAllContainersResponseDTO;
import com.dashboard.desktopapp.interfaces.PageRefresh;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;

public class UnloadButtonController {

    @Setter
    private GetAllContainersResponseDTO.Container container;

    @FXML
    private Button unloadButton;

    private PageRefresh reloadController;

    public void setReloadController(PageRefresh controller) {
        this.reloadController = controller;
    }

    @FXML
    public void onBtnClick() {
        try {
            // Load the modal's FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/dashboard/desktopapp/components/confirm-unload-modal.fxml"));
            Parent modalRoot = fxmlLoader.load();
            ConfirmUnloadModalController confirmController = fxmlLoader.getController();
            confirmController.setReloadController(reloadController);
            confirmController.setViewContainerInfo(container);

            // Create a new stage for the modal
            Stage modalStage = new Stage();
            modalStage.setTitle("");
            Image image = new Image(this.getClass().getResourceAsStream("/com/dashboard/desktopapp/images/APP_LOGO.png"));
            modalStage.getIcons().add(image);
            modalStage.setScene(new Scene(modalRoot));
            modalStage.initOwner(unloadButton.getScene().getWindow());
            modalStage.setResizable(false);
            modalStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
