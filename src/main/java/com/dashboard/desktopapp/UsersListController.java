package com.dashboard.desktopapp;

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
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> ssnColumn;
    @FXML
    private TableColumn<User, String> phoneColumn;
    @FXML
    private TableColumn<User, Void> actionsColumn;

    @FXML
    private TableView<User> smasTable;
    @FXML
    private TableColumn<User, Integer> smasidColumn;
    @FXML
    private TableColumn<User, String> smasusernameColumn;
    @FXML
    private TableColumn<User, String> smasnameColumn;
    @FXML
    private TableColumn<User, String> smasssnColumn;
    @FXML
    private TableColumn<User, String> smasphoneColumn;
    @FXML
    private TableColumn<User, Void> smasactionsColumn;

    private EditButtonsController controller;

    @FXML
    public void initialize() {
        int columnCount = 6;

        // === USERS TABLE SETUP ===
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ssnColumn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        smasidColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        smasusernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        smasnameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        smasssnColumn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
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

                User user = getTableView().getItems().get(getIndex());
                controller.setUser(user);
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

                User user = getTableView().getItems().get(getIndex());
                controller.setUser(user);
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

        smasTable.getItems().addAll(createMockUsers());
    }


    private List<User> createMockUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "jdoe", "John Doe", "123-45-6789", "+351912345678"));
        users.add(new User(2, "asmith", "Alice Smith", "987-65-4321", "+351987654321"));
        users.add(new User(1, "jdoe", "John Doe", "123-45-6789", "+351912345678"));
        users.add(new User(2, "asmith", "Alice Smith", "987-65-4321", "+351987654321"));
        users.add(new User(1, "jdoe", "John Doe", "123-45-6789", "+351912345678"));
        users.add(new User(2, "asmith", "Alice Smith", "987-65-4321", "+351987654321"));
        users.add(new User(1, "jdoe", "John Doe", "123-45-6789", "+351912345678"));
        users.add(new User(2, "asmith", "Alice Smith", "987-65-4321", "+351987654321"));
        users.add(new User(1, "jdoe", "John Doe", "123-45-6789", "+351912345678"));
        users.add(new User(2, "asmith", "Alice Smith", "987-65-4321", "+351987654321"));
        users.add(new User(1, "jdoe", "John Doe", "123-45-6789", "+351912345678"));
        users.add(new User(2, "asmith", "Alice Smith", "987-65-4321", "+351987654321"));
        users.add(new User(1, "jdoe", "John Doe", "123-45-6789", "+351912345678"));
        users.add(new User(2, "asmith", "Alice Smith", "987-65-4321", "+351987654321"));
        users.add(new User(1, "jdoe", "John Doe", "123-45-6789", "+351912345678"));
        users.add(new User(2, "asmith", "Alice Smith", "987-65-4321", "+351987654321"));
        users.add(new User(1, "jdoe", "John Doe", "123-45-6789", "+351912345678"));
        users.add(new User(2, "asmith", "Alice Smith", "987-65-4321", "+351987654321"));
        users.add(new User(1, "jdoe", "John Doe", "123-45-6789", "+351912345678"));
        users.add(new User(2, "asmith", "Alice Smith", "987-65-4321", "+351987654321"));
        users.add(new User(1, "jdoe", "John Doe", "123-45-6789", "+351912345678"));
        users.add(new User(2, "asmith", "Alice Smith", "987-65-4321", "+351987654321"));
        users.add(new User(1, "jdoe", "John Doe", "123-45-6789", "+351912345678"));
        users.add(new User(2, "asmith", "Alice Smith", "987-65-4321", "+351987654321"));
        users.add(new User(1, "jdoe", "John Doe", "123-45-6789", "+351912345678"));
        return users;
    }

    // Simple POJO for mock users
    public static class User {
        private final Integer id;
        private final String username;
        private final String name;
        private final String ssn;
        private final String phone;

        public User(Integer id, String username, String name, String ssn, String phone) {
            this.id = id;
            this.username = username;
            this.name = name;
            this.ssn = ssn;
            this.phone = phone;
        }

        public Integer getId() { return id; }
        public String getUsername() { return username; }
        public String getName() { return name; }
        public String getSsn() { return ssn; }
        public String getPhone() { return phone; }
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
