package com.dashboard.desktopapp;

import com.dashboard.desktopapp.dtos.bucket.response.BucketMunicipalityContainerCountResponseDTO;
import com.dashboard.desktopapp.dtos.container.response.ContainerUnloadingCountResponseDTO;
import com.dashboard.desktopapp.dtos.container.response.GetAllContainersResponseDTO;
import com.dashboard.desktopapp.dtos.user.response.CountMunicipalityUsersResponseDTO;
import com.dashboard.desktopapp.dtos.user.response.TotalUnloadedQuantityResponseDTO;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class StatsViewController {

    @FXML
    private BorderPane content;
    @FXML
    private TableView<GetAllContainersResponseDTO.Container> containersTable;
    @FXML
    private TableColumn<GetAllContainersResponseDTO.Container, Integer> idColumn;
    @FXML
    private TableColumn<GetAllContainersResponseDTO.Container, Float> capacityColumn;
    @FXML
    private TableColumn<GetAllContainersResponseDTO.Container, String> locationColumn;
    @FXML
    private TableColumn<GetAllContainersResponseDTO.Container, Integer> depositsColumn;
    @FXML
    private TableColumn<GetAllContainersResponseDTO.Container, Double> totalTonsColumn;
    @FXML
    private Label depositCount;
    @FXML
    private Label unloadsCount;
    @FXML
    private Label municipalityCount;
    @FXML
    private Label totalTons;

    @FXML
    public void initialize() {
        int columnCount = 5;

        // === CONTAINERS TABLE SETUP ===
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("localization"));
        depositsColumn.setCellValueFactory(cellData -> {
            int depositsCount = cellData.getValue().getBucketMunicipalityContainers() != null
                    ? cellData.getValue().getBucketMunicipalityContainers().size()
                    : 0;
            return new ReadOnlyObjectWrapper<>(depositsCount);
        });

        totalTonsColumn.setCellValueFactory(cellData -> {
            double totalUnloaded = 0.0;

            if (cellData.getValue().getContainerUnloadings() != null) {
                totalUnloaded = cellData.getValue().getContainerUnloadings().stream()
                        .mapToDouble(unloading -> unloading.getUnloadedQuantity() != null ? unloading.getUnloadedQuantity().doubleValue() / 1000 : 0.0)
                        .sum();
            }

            return new ReadOnlyObjectWrapper<>(totalUnloaded);
        });


        // Responsive column widths for usersTable
        containersTable.getColumns().forEach(column -> {
            column.prefWidthProperty().bind(containersTable.widthProperty().divide(columnCount));
        });

        // Populate with data
        containersTable.getItems().addAll(getAllContainers());

        depositCount.setText(getBucketDepositCount().toString());
        unloadsCount.setText(getContainerUnloadingCount().toString());
        municipalityCount.setText(getMunicipalityCount().toString());
        totalTons.setText(String.valueOf(getTotalUnloads() / 1000));
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

    public Long getBucketDepositCount() {
        // Define the API endpoint
        String url = "http://localhost:8080/api/buckets/buckets-deposits/count";
        Long count = null;

        try {
            // Create a URL object with the API endpoint
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // Timeout after 5 seconds
            connection.setReadTimeout(5000);    // Timeout for reading response

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

                // Parse the JSON response to BucketMunicipalityContainerCountResponseDTO
                ObjectMapper objectMapper = new ObjectMapper();
                BucketMunicipalityContainerCountResponseDTO responseDTO = objectMapper.readValue(response.toString(), BucketMunicipalityContainerCountResponseDTO.class);

                // Extract the count value
                count = responseDTO.getCount();
            } else {
                System.out.println("Error: Unable to fetch count. HTTP code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count; // Return the count value
    }

    public Long getContainerUnloadingCount() {
        // Define the API endpoint
        String url = "http://localhost:8080/api/containers/count";
        Long count = null;

        try {
            // Create a URL object with the API endpoint
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // Timeout after 5 seconds
            connection.setReadTimeout(5000);    // Timeout for reading response

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

                // Parse the JSON response to ContainerUnloadingCountResponseDTO
                ObjectMapper objectMapper = new ObjectMapper();
                ContainerUnloadingCountResponseDTO responseDTO = objectMapper.readValue(response.toString(), ContainerUnloadingCountResponseDTO.class);

                // Extract the count value
                count = responseDTO.getCount();
            } else {
                System.out.println("Error: Unable to fetch count. HTTP code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count; // Return the count value
    }

    public Long getMunicipalityCount() {
        // Define the API endpoint
        String url = "http://localhost:8080/api/users/count/municipality";
        Long count = null;

        try {
            // Create a URL object with the API endpoint
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // Timeout after 5 seconds
            connection.setReadTimeout(5000);    // Timeout for reading response

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

                // Parse the JSON response to CountMunicipalityUsersResponseDTO
                ObjectMapper objectMapper = new ObjectMapper();
                CountMunicipalityUsersResponseDTO responseDTO = objectMapper.readValue(response.toString(), CountMunicipalityUsersResponseDTO.class);

                // Extract the count value
                count = responseDTO.getCount();
            } else {
                System.out.println("Error: Unable to fetch count. HTTP code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count; // Return the count value
    }

    public Long getTotalUnloads() {
        // Define the API endpoint
        String url = "http://localhost:8080/api/containers/total-unloaded-quantity";
        Long total = null;

        try {
            // Create a URL object with the API endpoint
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // Timeout after 5 seconds
            connection.setReadTimeout(5000);    // Timeout for reading response

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

                // Parse the JSON response to TotalUnloadedQuantityResponseDTO
                ObjectMapper objectMapper = new ObjectMapper();
                TotalUnloadedQuantityResponseDTO responseDTO = objectMapper.readValue(response.toString(), TotalUnloadedQuantityResponseDTO.class);

                // Extract the total value
                total = responseDTO.getTotalUnloadedQuantity();
            } else {
                System.out.println("Error: Unable to fetch count. HTTP code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return total; // Return the total value
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
}
