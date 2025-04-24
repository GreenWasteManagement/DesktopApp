package com.dashboard.desktopapp;

import com.dashboard.desktopapp.components.EditButtonsController;
import com.dashboard.desktopapp.dtos.bucket.response.GetBucketMunicipalityByIdResponseDTO;
import com.dashboard.desktopapp.dtos.container.response.GetAllContainersResponseDTO;
import com.dashboard.desktopapp.dtos.user.response.GetMunicipalityByIdResponseDTO;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HistoryViewController {

    @Data
    public static class ContainerWithMunicipality {
        private Long id;
        private String location;
        private float depositAmount;
        private String dateTime;
        private String municipalityName;
        private String municipalityNIF;
    }

    // FXML Fields
    @FXML
    private BorderPane content;
    @FXML
    private TableView<ContainerWithMunicipality> historyTable;
    @FXML
    private TableColumn<ContainerWithMunicipality, Long> idColumn;
    @FXML
    private TableColumn<ContainerWithMunicipality, String> locationColumn;
    @FXML
    private TableColumn<ContainerWithMunicipality, Float> depositAmountColumn;
    @FXML
    private TableColumn<ContainerWithMunicipality, String> dateTimeColumn;

    @FXML
    public void initialize() {
        int columnCount = 6;

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        depositAmountColumn.setCellValueFactory(new PropertyValueFactory<>("depositAmount"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        // Responsive widths
        historyTable.getColumns().forEach(column -> {
            column.prefWidthProperty().bind(historyTable.widthProperty().divide(columnCount));
        });

        // Populate table
        historyTable.getItems().addAll(getEnrichedUnloadingHistory());
    }

    public List<ContainerWithMunicipality> getEnrichedUnloadingHistory() {
        List<ContainerWithMunicipality> result = new ArrayList<>();

        // Get the list of containers using the getAllContainersResponseDTO method
        List<GetAllContainersResponseDTO> allContainers = getAllContainersResponseDTO();

        // Loop through each container and retrieve associated municipality data
        for (GetAllContainersResponseDTO.Container container : allContainers) {
            for(GetAllContainersResponseDTO.BucketMunicipalityContainer deposits : allContainers)
                Long associationId = container.getBucketMunicipalityContainers().getFirst().getAssociationId();
                GetMunicipalityByIdResponseDTO municipalityDTO = getMunicipalityByAssociationId(associationId);

                ContainerWithMunicipality entry = new ContainerWithMunicipality();
                entry.setId(container.getId());
                entry.setLocation(container.getLocalization());
                entry.setDepositAmount(container.getBucketMunicipalityContainers());
                entry.setDateTime(container..toString()); // Assuming `timestamp` is of type Instant
                entry.setMunicipalityName(municipalityDTO.getUser().getUsername());
                entry.setMunicipalityNIF(municipalityDTO.getMunicipality().getNif());

                result.add(entry);
        }

        return result;
    }

    public List<GetAllContainersResponseDTO> getAllContainersResponseDTO() {
        // Define the API endpoint
        String url = "http://localhost:8080/api/containers";
        List<GetAllContainersResponseDTO> containers = new ArrayList<>();

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

                // Parse the JSON response to a list of containers
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                GetAllContainersResponseDTO responseDTO = objectMapper.readValue(response.toString(), GetAllContainersResponseDTO.class);

                containers = responseDTO.getContainers(); // Assuming 'getContainers' gives you a List of Container objects
            } else {
                System.out.println("Error: Unable to fetch data. HTTP code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return containers; // Return the list of containers
    }



    public GetMunicipalityByIdResponseDTO getMunicipalityByAssociationId(Long associationId) {
        String bucketMunicipalityURL = "http://localhost:8080/api/buckets/buckets-municipalities/by-id";
        String baseMunicipalityURL = "http://localhost:8080/api/users/get/municipality/";
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Step 1: Create JSON request body
            String requestBody = "{\"id\": " + associationId + "}";

            // Setup the POST connection for bucketMunicipalityURL
            HttpURLConnection bucketConnection = (HttpURLConnection) new URL(bucketMunicipalityURL).openConnection();
            bucketConnection.setRequestMethod("POST");
            bucketConnection.setRequestProperty("Content-Type", "application/json");
            bucketConnection.setDoOutput(true);
            bucketConnection.setConnectTimeout(5000);
            bucketConnection.setReadTimeout(5000);

            // Send the JSON body
            try (OutputStream os = bucketConnection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read and process the response
            if (bucketConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(bucketConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                GetBucketMunicipalityByIdResponseDTO bucketResponse =
                        objectMapper.readValue(response.toString(), GetBucketMunicipalityByIdResponseDTO.class);
                Long userId = bucketResponse.getBucketMunicipality().getUserId();
                bucketConnection.disconnect();

                // Step 2: Use the userId to fetch Municipality data
                String municipalityURL = baseMunicipalityURL + userId;
                HttpURLConnection municipalityConnection = (HttpURLConnection) new URL(municipalityURL).openConnection();
                municipalityConnection.setRequestMethod("POST");
                municipalityConnection.setConnectTimeout(5000);
                municipalityConnection.setReadTimeout(5000);

                if (municipalityConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader muniReader = new BufferedReader(new InputStreamReader(municipalityConnection.getInputStream()));
                    StringBuilder muniResponse = new StringBuilder();
                    while ((line = muniReader.readLine()) != null) {
                        muniResponse.append(line);
                    }
                    muniReader.close();

                    GetMunicipalityByIdResponseDTO municipality =
                            objectMapper.readValue(muniResponse.toString(), GetMunicipalityByIdResponseDTO.class);

                    municipalityConnection.disconnect();
                    return municipality;
                } else {
                    System.out.println("Failed to fetch municipality. HTTP code: " + municipalityConnection.getResponseCode());
                }
            } else {
                System.out.println("Failed to fetch bucket municipality. HTTP code: " + bucketConnection.getResponseCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Return null in case of failure
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
