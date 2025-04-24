package com.dashboard.desktopapp;

import com.dashboard.desktopapp.components.ConfirmationModalController;
import com.dashboard.desktopapp.components.SMASModalController;
import com.dashboard.desktopapp.dtos.container.response.GetAllContainersResponseDTO;
import com.dashboard.desktopapp.dtos.user.response.GetAllMunicipalitiesAndBucketsResponseDTO;
import com.dashboard.desktopapp.dtos.user.response.GetAllSmasResponseDTO;
import com.dashboard.desktopapp.interfaces.PageRefresh;
import com.dashboard.desktopapp.models.Bucket;
import com.dashboard.desktopapp.models.Municipality;
import com.dashboard.desktopapp.models.SMAS;
import com.dashboard.desktopapp.components.EditButtonsController;
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
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsersListController implements PageRefresh {
    @FXML
    private BorderPane content;

    @FXML
    private TableView<GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData> usersTable;
    @FXML
    private TableColumn<GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData, Long> idColumn;
    @FXML
    private TableColumn<GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData, String> usernameColumn;
    @FXML
    private TableColumn<GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData, String> nameColumn;
    @FXML
    private TableColumn<GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData, String> ccColumn;
    @FXML
    private TableColumn<GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData, String> phoneColumn;
    @FXML
    private TableColumn<GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData, Void> actionsColumn;

    @FXML
    private TableView<GetAllSmasResponseDTO.SmasData> smasTable;
    @FXML
    private TableColumn<GetAllSmasResponseDTO.SmasData, Long> smasidColumn;
    @FXML
    private TableColumn<GetAllSmasResponseDTO.SmasData, String> smasusernameColumn;
    @FXML
    private TableColumn<GetAllSmasResponseDTO.SmasData, String> smasnameColumn;
    @FXML
    private TableColumn<GetAllSmasResponseDTO.SmasData, String> smasccColumn;
    @FXML
    private TableColumn<GetAllSmasResponseDTO.SmasData, String> smasphoneColumn;
    @FXML
    private TableColumn<GetAllSmasResponseDTO.SmasData, Void> smasactionsColumn;

    private EditButtonsController controller;

    @Override
    public void refreshPage() {
        usersTable.getItems().clear();
        smasTable.getItems().clear();
        initialize();
    }

    @FXML
    public void initialize() {
        int columnCount = 6;

        // === USERS TABLE SETUP ===
        idColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getUser().getId()));

        usernameColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getUser().getUsername()));

        nameColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getUser().getName()));

        ccColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getMunicipality().getNif()));

        phoneColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getUser().getPhoneNumber()));
        smasidColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getUser().getId()));

        smasusernameColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getUser().getUsername()));

        smasnameColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getUser().getName()));

        smasccColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getSmas().getCitizenCardCode()));

        smasphoneColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getUser().getPhoneNumber()));


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
                        controller.setReloadController(UsersListController.this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData user = getTableView().getItems().get(getIndex());
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
                        controller.setReloadController(UsersListController.this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                GetAllSmasResponseDTO.SmasData user = getTableView().getItems().get(getIndex());
                controller.setSmas(user);
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
        usersTable.getItems().addAll(getAllMunicipalities());

        // === SMAS TABLE SETUP ===
        smasTable.getColumns().forEach(column -> {
            column.prefWidthProperty().bind(smasTable.widthProperty().divide(columnCount));
        });

        smasTable.getItems().addAll(getAllSmas());
    }

    public List<GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData> getAllMunicipalities() {
        // Define the API endpoint
        String url = "http://localhost:8080/api/users/get/municipalities/buckets";
        List<GetAllMunicipalitiesAndBucketsResponseDTO.MunicipalityData> municipalityData = Collections.emptyList();

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

                // Parse the JSON response to GetAllMunicipalitiesAndBucketsResponseDTO
                ObjectMapper objectMapper = new ObjectMapper();
                GetAllMunicipalitiesAndBucketsResponseDTO responseDTO = objectMapper.readValue(response.toString(), GetAllMunicipalitiesAndBucketsResponseDTO.class);

                // Get the municipalities list from the response DTO
                municipalityData = responseDTO.getMunicipalities();
            } else {
                System.out.println("Error: Unable to fetch data. HTTP code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return municipalityData; // Return the parsed containers list
    }

    public List<GetAllSmasResponseDTO.SmasData> getAllSmas() {
        // Define the API endpoint
        String url = "http://localhost:8080/api/users/get/smas";
        List<GetAllSmasResponseDTO.SmasData> smasData = null;

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

                // Parse the JSON response to GetAllSmasResponseDTO
                ObjectMapper objectMapper = new ObjectMapper();
                GetAllSmasResponseDTO responseDTO = objectMapper.readValue(response.toString(), GetAllSmasResponseDTO.class);

                // Get the smas list from the response DTO
                smasData = responseDTO.getSmasList();
            } else {
                System.out.println("Error: Unable to fetch data. HTTP code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return smasData; // Return the parsed containers list
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
            SMASModalController controller = fxmlLoader.getController();
            controller.setReloadController(UsersListController.this);
            
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
