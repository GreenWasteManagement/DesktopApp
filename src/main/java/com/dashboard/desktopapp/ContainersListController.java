package com.dashboard.desktopapp;

import com.dashboard.desktopapp.models.Container;
import com.dashboard.desktopapp.components.EditButtonsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContainersListController {

    @FXML
    private BorderPane content;

    @FXML
    private TableView<Container> containersTable;
    @FXML
    private TableColumn<Container, Integer> idColumn;
    @FXML
    private TableColumn<Container, Float> capacityColumn;
    @FXML
    private TableColumn<Container, String> locationColumn;
    @FXML
    private TableColumn<Container, Void> actionsColumn;

    private EditButtonsController controller;

    @FXML
    public void initialize() {
        int columnCount = 4;

        // === USERS TABLE SETUP ===
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

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

                Container container = getTableView().getItems().get(getIndex());
                controller.setContainer(container);
                controller.setModalType("container");

                HBox hbox = new HBox(buttonComponent);
                hbox.setAlignment(Pos.CENTER);
                hbox.setPrefWidth(Double.MAX_VALUE);
                setGraphic(hbox);
            }
        });


        // Responsive column widths for usersTable
        containersTable.getColumns().forEach(column -> {
            column.prefWidthProperty().bind(containersTable.widthProperty().divide(columnCount));
        });

        // Populate with data
        containersTable.getItems().addAll(createMockContainers());
    }

    private List<Container> createMockContainers() {
        List<Container> containers = new ArrayList<>();
        containers.add(new Container(1, 120.0f, "38.7169, -9.1399" ,35.0f));
        containers.add(new Container(2, 90.0f, "41.1579, -8.6291", 44.5f));
        containers.add(new Container(3, 100.0f, "40.6405, -8.6538", 15.7f));
        containers.add(new Container(4, 110.0f, "39.7444, -8.8076", 8.9f));
        containers.add(new Container(5, 95.0f, "37.0194, -7.9304", 96.4f));
        containers.add(new Container(6, 130.0f, "38.5244, -8.8926", 55.0f));
        return containers;
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("components/container-create-modal.fxml"));
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
