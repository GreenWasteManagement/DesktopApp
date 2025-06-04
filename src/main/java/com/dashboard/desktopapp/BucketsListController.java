package com.dashboard.desktopapp;

import com.dashboard.desktopapp.appsession.AppSession;
import com.dashboard.desktopapp.components.BucketsModalController;
import com.dashboard.desktopapp.components.EditButtonsController;
import com.dashboard.desktopapp.dtos.bucket.response.BucketWithMunicipalityInfoDTO;
import com.dashboard.desktopapp.interfaces.PageRefresh;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.beans.property.ReadOnlyObjectWrapper;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class BucketsListController implements PageRefresh {

    @FXML
    private BorderPane content;

    @FXML
    private TableView<BucketWithMunicipalityInfoDTO> bucketsTable;
    @FXML
    private TableColumn<BucketWithMunicipalityInfoDTO, Integer> idColumn;
    @FXML
    private TableColumn<BucketWithMunicipalityInfoDTO, Integer> capacityColumn;
    @FXML
    private TableColumn<BucketWithMunicipalityInfoDTO, String> associatedColumn;
    @FXML
    private TableColumn<BucketWithMunicipalityInfoDTO, Void> actionsColumn;

    private EditButtonsController controller;

    @Override
    public void refreshPage() {
        bucketsTable.getItems().clear();
        initialize();
    }

    @FXML
    public void initialize() {
        int columnCount = 4;

        // === USERS TABLE SETUP ===
        idColumn.setCellValueFactory(new PropertyValueFactory<>("bucketId"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        associatedColumn.setCellValueFactory(cellData -> {
            boolean isAssociated = cellData.getValue().getIsAssociated();
            String displayText = isAssociated ? "Sim" : "Não";
            return new ReadOnlyObjectWrapper<>(displayText);
        });

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
                        controller.setReloadController(BucketsListController.this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                BucketWithMunicipalityInfoDTO bucket = getTableView().getItems().get(getIndex());
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
        bucketsTable.getItems().addAll(getAllBuckets());
    }

    public List<BucketWithMunicipalityInfoDTO> getAllBuckets() {
        // Define the API endpoint
        String url = "http://localhost:8080/api/buckets/with-municipalities";
        List<BucketWithMunicipalityInfoDTO> buckets = null;

        try {
            // Create a URL object with the API endpoint
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // Timeout after 5 seconds
            connection.setReadTimeout(5000); // Timeout for reading response

            // Check if the response code is 200 (OK)
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Read the response data from the API
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse the JSON response to BucketWithMunicipalityInfoDTO
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                buckets = objectMapper.readValue(
                        response.toString(),
                        new TypeReference<>() {
                        }
                );
                //BucketWithMunicipalityInfoDTO responseDTO = objectMapper.readValue(response.toString(), BucketWithMunicipalityInfoDTO.class);

                // Get the buckets list from the response DTO
                //buckets = responseDTO;
            } else {
                System.out.println("Error: Unable to fetch data. HTTP code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buckets; // Return the parsed buckets list
    }

    @FXML
    protected void onMenuBtnClick() {
        try {
            Map<String, Object> userInfo = AppSession.decodeJWT(AppSession.getJwtToken());
            if ("ADMIN".equals(userInfo.get("role"))) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homenav-view.fxml"));
                Parent root = fxmlLoader.load();

                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                double screenWidth = screenBounds.getWidth();
                double screenHeight = screenBounds.getHeight();

                Stage stage = (Stage) content.getScene().getWindow();
                Scene newScene = new Scene(root, screenWidth, screenHeight);

                stage.setScene(newScene);
                stage.setMaximized(true);
                stage.show();
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homenav-view-smas.fxml"));
                Parent root = fxmlLoader.load();

                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                double screenWidth = screenBounds.getWidth();
                double screenHeight = screenBounds.getHeight();

                Stage stage = (Stage) content.getScene().getWindow();
                Scene newScene = new Scene(root, screenWidth, screenHeight);

                stage.setScene(newScene);
                stage.setMaximized(true);
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onLogoutBtnClick() {
        try {
            AppSession.setJwtToken(null);
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
            BucketsModalController controller = fxmlLoader.getController();
            controller.setReloadController(BucketsListController.this);

            // Create a new stage for the modal
            Stage modalStage = new Stage();
            modalStage.setTitle("");
            Image image = new Image(this.getClass().getResourceAsStream("images/APP_LOGO.png"));
            modalStage.getIcons().add(image);
            modalStage.setScene(new Scene(modalRoot));
            modalStage.initOwner(content.getScene().getWindow());
            modalStage.setResizable(false);
            modalStage.initModality(javafx.stage.Modality.WINDOW_MODAL);

            controller.setCreateBucketInfo();

            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
