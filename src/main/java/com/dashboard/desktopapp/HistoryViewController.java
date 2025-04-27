package com.dashboard.desktopapp;

import com.dashboard.desktopapp.dtos.bucket.response.GetMunicipalityDepositsResponseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HistoryViewController {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    // FXML Fields
    @FXML
    private BorderPane content;
    @FXML
    private TableView<ContainerDepositEntry> historyTable;
    @FXML
    private TableColumn<GetMunicipalityDepositsResponseDTO.Container, Long> idColumn;
    @FXML
    private TableColumn<GetMunicipalityDepositsResponseDTO.Container, String> locationColumn;
    @FXML
    private TableColumn<GetMunicipalityDepositsResponseDTO, Float> depositAmountColumn;
    @FXML
    private TableColumn<GetMunicipalityDepositsResponseDTO, String> dateTimeColumn;
    @FXML
    private TableColumn<GetMunicipalityDepositsResponseDTO.Municipality, String> municipalityNifColumn;

    @FXML
    public void initialize() {
        int columnCount = 5;

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        depositAmountColumn.setCellValueFactory(new PropertyValueFactory<>("depositAmount"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        municipalityNifColumn.setCellValueFactory(new PropertyValueFactory<>("nif"));

        // Responsive widths
        historyTable.getColumns().forEach(column -> {
            column.prefWidthProperty().bind(historyTable.widthProperty().divide(columnCount));
        });

        // Populate table
        List<ContainerDepositEntry> entries = getDepositHistory().stream().map(deposit -> {
            ContainerDepositEntry entry = new ContainerDepositEntry();
            entry.setId(deposit.getContainer().getId());
            entry.setLocation(deposit.getContainer().getLocalization());
            entry.setDepositAmount(deposit.getDepositAmount());

            LocalDateTime localDateTime = LocalDateTime.ofInstant(
                    deposit.getDepositTimestamp(), ZoneId.systemDefault()
            );
            entry.setDateTime(localDateTime.format(formatter));

            entry.setNif(deposit.getMunicipality().getNif());
            return entry;
        }).toList();

        historyTable.getItems().addAll(entries);
    }

    public List<GetMunicipalityDepositsResponseDTO> getDepositHistory() {
        // Define the API endpoint
        String url = "http://localhost:8080/api/buckets/deposits/municipality";
        List<GetMunicipalityDepositsResponseDTO> deposits = new ArrayList<>();

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

                // Parse the JSON response to GetMunicipalityDepositsResponseDTO
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                deposits = objectMapper.readValue(
                        response.toString(),
                        new TypeReference<>() {
                        }
                );
            } else {
                System.out.println("Error: Unable to fetch data. HTTP code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deposits; // Return the parsed containers list
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

    @Data
    public static class ContainerDepositEntry {
        private Long id;
        private String location;
        private BigDecimal depositAmount;
        private String dateTime;
        private String nif;
    }
}
