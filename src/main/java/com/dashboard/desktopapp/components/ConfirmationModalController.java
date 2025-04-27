package com.dashboard.desktopapp.components;

import com.dashboard.desktopapp.interfaces.PageRefresh;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfirmationModalController {

    @FXML
    private VBox modal;
    @FXML
    private Label confirmationText;

    private PageRefresh reloadController;

    public void setReloadController(PageRefresh controller) {
        this.reloadController = controller;
    }

    @FXML
    public void onCloseBtnClick() {
        Stage stage = (Stage) modal.getScene().getWindow();
        stage.close();

        // Trigger reload
        if (reloadController != null) {
            reloadController.refreshPage();
        }
    }

    public void setConfirmationText(int statusCode) {
        if (statusCode == 200 || statusCode == 201) {
            confirmationText.setText("Operação realizada com sucesso!");
        } else {
            confirmationText.setText("Ocorreu um erro ao realizar a operação!");
        }

    }
}
