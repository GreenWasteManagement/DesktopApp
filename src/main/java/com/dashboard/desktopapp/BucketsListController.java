package com.dashboard.desktopapp;

import com.dashboard.desktopapp.models.Bucket;
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

public class BucketsListController {

    @FXML
    private BorderPane content;

    @FXML
    private TableView<Bucket> bucketsTable;
    @FXML
    private TableColumn<Bucket, Integer> idColumn;
    @FXML
    private TableColumn<Bucket, Integer> capacityColumn;
    @FXML
    private TableColumn<Bucket, Integer> associatedColumn;
    @FXML
    private TableColumn<Bucket, Integer> municipalityColumn;
    @FXML
    private TableColumn<Bucket, Void> actionsColumn;


    private EditButtonsController controller;

    @FXML
    public void initialize() {
        int columnCount = 5;

        // === USERS TABLE SETUP ===
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        associatedColumn.setCellValueFactory(new PropertyValueFactory<>("associated"));
        municipalityColumn.setCellValueFactory(new PropertyValueFactory<>("municipality"));

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

                Bucket bucket = getTableView().getItems().get(getIndex());
                controller.setBucket(bucket);
                controller.setModalType("bucket");

                HBox hbox = new HBox(buttonComponent);
                hbox.setAlignment(Pos.CENTER);
                hbox.setPrefWidth(Double.MAX_VALUE);
                setGraphic(hbox);
            }
        });


        // Responsive column widths for usersTable
        bucketsTable.getColumns().forEach(column -> {
            column.prefWidthProperty().bind(bucketsTable.widthProperty().divide(columnCount));
        });

        // Populate with data
        bucketsTable.getItems().addAll(createMockBuckets());
    }

    private List<Bucket> createMockBuckets() {
        List<Bucket> buckets = new ArrayList<>();
        buckets.add(new Bucket(1, 50.0f, true, "Em uso", "Tio quim"));
        buckets.add(new Bucket(2, 75.5f, false, "Em uso", "Tio quim"));
        buckets.add(new Bucket(3, 100.0f, true, "Em uso", "Tio quim"));
        buckets.add(new Bucket(4, 25.0f, false, "Em uso", "Tio quim"));
        buckets.add(new Bucket(5, 60.0f, true, "Em uso", "Tio quim"));
        buckets.add(new Bucket(6, 80.0f, false, "Em uso", "Tio quim"));
        return buckets;
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("components/bucket-create-modal.fxml"));
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
