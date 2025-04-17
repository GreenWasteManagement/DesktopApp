package com.dashboard.desktopapp.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfirmationModalController {

    @FXML
    private VBox modal;
    @FXML
    private Label confirmationText;

    @FXML
    public void onCloseBtnClick() {
        Stage stage = (Stage) (modal.getScene().getWindow());
        stage.close();
    }

    public void setConfirmationText(float statusCode) {
        if (statusCode == 200 || statusCode == 201) {
            confirmationText.setText("Operação realizada com sucesso!");
        }else{
            confirmationText.setText("Ocorreu um erro ao realizar a operação!");
        }

    }
}
