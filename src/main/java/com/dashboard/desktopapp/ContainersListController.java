package com.dashboard.desktopapp;

import com.dashboard.desktopapp.appsession.AppSession;
import com.dashboard.desktopapp.components.ContainersModalController;
import com.dashboard.desktopapp.components.EditButtonsController;
import com.dashboard.desktopapp.components.UnloadButtonController;
import com.dashboard.desktopapp.dtos.container.response.GetAllContainersResponseDTO;
import com.dashboard.desktopapp.interfaces.PageRefresh;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

public class ContainersListController implements PageRefresh {

    String sessionToken = AppSession.getJwtToken();
    @FXML
    private BorderPane content;
    @FXML
    private TableView<GetAllContainersResponseDTO.Container> containersTable;
    @FXML
    private TableColumn<GetAllContainersResponseDTO.Container, Integer> idColumn;
    @FXML
    private TableColumn<GetAllContainersResponseDTO.Container, Float> capacityColumn;
    @FXML
    private TableColumn<GetAllContainersResponseDTO.Container, String> currentVolumeColumn;
    @FXML
    private TableColumn<GetAllContainersResponseDTO.Container, String> locationColumn;
    @FXML
    private TableColumn<GetAllContainersResponseDTO.Container, Void> actionsColumn;
    private EditButtonsController controller;
    private UnloadButtonController unloadController;

    @Override
    public void refreshPage() {
        containersTable.getItems().clear();
        initialize();
    }

    @FXML
    public void initialize() {
        int columnCount = 5;

        // === CONTAINERS TABLE SETUP ===
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        currentVolumeColumn.setCellValueFactory(new PropertyValueFactory<>("currentVolumeLevel"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("localization"));

        // Edit button column
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private Parent buttonComponent;
            private Parent unloadButtonComponent;

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
                        controller.setReloadController(ContainersListController.this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                GetAllContainersResponseDTO.Container container = getTableView().getItems().get(getIndex());
                controller.setContainer(container);
                controller.setModalType("container");

                Map<String, Object> userInfo = AppSession.decodeJWT(sessionToken);
                if ("SMAS".equals(userInfo.get("role"))) {
                    if (unloadButtonComponent == null) {
                        try {
                            FXMLLoader unloadLoader = new FXMLLoader(getClass().getResource("components/unload-button.fxml"));
                            unloadButtonComponent = unloadLoader.load();
                            unloadController = unloadLoader.getController();
                            unloadController.setReloadController(ContainersListController.this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    unloadController.setContainer(container);

                    HBox hbox = new HBox(16, unloadButtonComponent, buttonComponent);
                    hbox.setAlignment(Pos.CENTER);
                    hbox.setPrefWidth(Double.MAX_VALUE);
                    setGraphic(hbox);
                } else {
                    HBox hbox = new HBox(buttonComponent);
                    hbox.setAlignment(Pos.CENTER);
                    hbox.setPrefWidth(Double.MAX_VALUE);
                    setGraphic(hbox);
                }
            }
        });


        // Responsive column widths for usersTable
        containersTable.getColumns().forEach(column -> {
            column.prefWidthProperty().bind(containersTable.widthProperty().divide(columnCount));
        });

        // Populate with data
        containersTable.getItems().addAll(getAllContainers());
    }

    public List<GetAllContainersResponseDTO.Container> getAllContainers() {
        // Define the API endpoint
        String url = "http://localhost:8080/api/containers";
        List<GetAllContainersResponseDTO.Container> containers = null;

        try {
            // Create a URL object with the API endpoint
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // Timeout after 5 seconds
            connection.setReadTimeout(5000); // Timeout for reading response
            if (AppSession.getJwtToken() != null) {
                connection.setRequestProperty("Authorization", "Bearer " + AppSession.getJwtToken());
            }

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

                // Parse the JSON response to GetAllContainersResponseDTO
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                GetAllContainersResponseDTO responseDTO = objectMapper.readValue(response.toString(), GetAllContainersResponseDTO.class);

                // Get the containers list from the response DTO
                containers = responseDTO.getContainers();
            } else {
                System.out.println("Error: Unable to fetch data. HTTP code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return containers; // Return the parsed containers list
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("components/container-create-modal.fxml"));
            Parent modalRoot = fxmlLoader.load();
            ContainersModalController containersController = fxmlLoader.getController();
            containersController.setReloadController(ContainersListController.this);

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
