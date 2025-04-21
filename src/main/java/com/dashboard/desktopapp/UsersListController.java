package com.dashboard.desktopapp;

import com.dashboard.desktopapp.models.Bucket;
import com.dashboard.desktopapp.models.Municipality;
import com.dashboard.desktopapp.models.SMAS;
import com.dashboard.desktopapp.components.EditButtonsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsersListController {
    @FXML
    private BorderPane content;

    @FXML
    private TableView<Municipality> usersTable;
    @FXML
    private TableColumn<Municipality, Integer> idColumn;
    @FXML
    private TableColumn<Municipality, String> usernameColumn;
    @FXML
    private TableColumn<Municipality, String> nameColumn;
    @FXML
    private TableColumn<Municipality, String> ccColumn;
    @FXML
    private TableColumn<Municipality, String> phoneColumn;
    @FXML
    private TableColumn<Municipality, Void> actionsColumn;

    @FXML
    private TableView<SMAS> smasTable;
    @FXML
    private TableColumn<SMAS, Integer> smasidColumn;
    @FXML
    private TableColumn<SMAS, String> smasusernameColumn;
    @FXML
    private TableColumn<SMAS, String> smasnameColumn;
    @FXML
    private TableColumn<SMAS, String> smasccColumn;
    @FXML
    private TableColumn<SMAS, String> smasphoneColumn;
    @FXML
    private TableColumn<SMAS, Void> smasactionsColumn;

    private EditButtonsController controller;

    @FXML
    public void initialize() {
        int columnCount = 6;

        // === USERS TABLE SETUP ===
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ccColumn.setCellValueFactory(new PropertyValueFactory<>("cc"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        smasidColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        smasusernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        smasnameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        smasccColumn.setCellValueFactory(new PropertyValueFactory<>("cc"));
        smasphoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // Edit button column
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private Parent buttonComponent;

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                    return;
                }

                if (buttonComponent == null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("components/edit-button.fxml"));
                        buttonComponent = loader.load();
                        controller = loader.getController();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Municipality user = getTableView().getItems().get(getIndex());
                controller.setMunicipality(user);
                controller.setModalType("municipality");
                HBox hbox = new HBox(buttonComponent);
                hbox.setAlignment(Pos.CENTER);
                hbox.setPrefWidth(Double.MAX_VALUE);
                setGraphic(hbox);
            }
        });

        smasactionsColumn.setCellFactory(param -> new TableCell<>() {
            private Parent buttonComponent;

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                    return;
                }

                if (buttonComponent == null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("components/edit-button.fxml"));
                        buttonComponent = loader.load();
                        controller = loader.getController();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                SMAS user = getTableView().getItems().get(getIndex());
                controller.setSMAS(user);
                controller.setModalType("smas");

                HBox hbox = new HBox(buttonComponent);
                hbox.setAlignment(Pos.CENTER);
                hbox.setPrefWidth(Double.MAX_VALUE);
                setGraphic(hbox);
            }
        });

        // Responsive column widths for usersTable
        usersTable.getColumns().forEach(column -> {
            column.prefWidthProperty().bind(usersTable.widthProperty().divide(columnCount));
        });

        // Populate with data
        usersTable.getItems().addAll(createMockUsers());

        // === SMAS TABLE SETUP ===
        smasTable.getColumns().forEach(column -> {
            column.prefWidthProperty().bind(smasTable.widthProperty().divide(columnCount));
        });

        smasTable.getItems().addAll(createMockSmas());
    }

    private List<Municipality> createMockUsers() {
        Bucket bucket1 = new Bucket(1, 50.0f, true, "Em uso","Lisbon");
        Bucket bucket2 = new Bucket(2, 75.5f, false, "Inutilizado", "Porto");

        List<Bucket> bucketList = new ArrayList<>();
        bucketList.add(bucket1);
        bucketList.add(bucket2);
        List<Municipality> users = new ArrayList<>();
        users.add(new Municipality(
                1,
                "Jane Doe",
                "jdoe",
                "jane@example.com",
                "912345678",
                12345678,
                987654321,
                "municipality",
                "Esq.",
                2,
                349,
                "Rua de Viana",
                "4900-555",
                "Viana do castelo",
                "Viana do castelo",
                bucketList
        ));

        return users;
    }

    private List<SMAS> createMockSmas() {
        List<SMAS> users = new ArrayList<>();
        users.add(new SMAS(
                2,
                "Jane Doe",
                "jdoe",
                "jane@example.com",
                "912345678",
                12345678,
                987654321,
                "municipality",
                "Esq.",
                2,
                349,
                "Rua de Viana",
                "4900-555",
                "Viana do castelo",
                "Viana do castelo",
                "18444",
                "Garbage Collector"
        ));

        return users;
    }

    @FXML
    protected void onMenuBtnClick() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homenav-view.fxml"));
            Parent root = fxmlLoader.load();

            // Get the screen's width and height
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double screenWidth = screenBounds.getWidth();
            double screenHeight = screenBounds.getHeight();

            // Get the current stage
            Stage stage = (Stage) content.getScene().getWindow();

            // Create a new scene with the loaded content (root)
            Scene newScene = new Scene(root, screenWidth, screenHeight);

            // Set the new scene to the stage and maximize it
            stage.setScene(newScene);
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onLogoutBtnClick() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = fxmlLoader.load();

            // Get the screen's width and height
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double screenWidth = screenBounds.getWidth();
            double screenHeight = screenBounds.getHeight();

            // Get the current stage
            Stage stage = (Stage) content.getScene().getWindow();

            // Create a new scene with the loaded content (root)
            Scene newScene = new Scene(root, screenWidth, screenHeight);

            // Set the new scene to the stage and maximize it
            stage.setScene(newScene);
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onCreateBtnClick() {
        try {
            // Load the modal's FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("components/smas-create-modal.fxml"));
            Parent modalRoot = fxmlLoader.load();

            // Create a new stage for the modal
            Stage modalStage = new Stage();
            modalStage.setTitle("");
            Image image = new Image(this.getClass().getResourceAsStream("images/APP_LOGO.png"));
            modalStage.getIcons().add(image);
            modalStage.setScene(new Scene(modalRoot));
            modalStage.initOwner(content.getScene().getWindow());
            modalStage.setResizable(false);
            modalStage.initModality(javafx.stage.Modality.WINDOW_MODAL);

            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
