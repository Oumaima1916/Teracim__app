package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class GestionController implements Initializable {

    // Navigation Labels
    @FXML private Label navAccueil, navMessages, navParametres;

    // Table and Columns
    @FXML private TableView<User> comptesTable;
    @FXML private TableColumn<User, String> colNom;
    @FXML private TableColumn<User, String> colRole;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, String> colStatus;
    @FXML private TableColumn<User, Void> colActions;

    // Button
    @FXML private Button btnAddUser;

    // Observable List pour les données
    private ObservableList<User> usersList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        loadSampleData();
        setupButtonActions();
    }

    /**
     * Configuration des colonnes de la table
     */
    private void setupTableColumns() {
        // Colonne Utilisateur avec Avatar + Nom
        colNom.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colNom.setCellFactory(column -> new TableCell<User, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    User user = getTableView().getItems().get(getIndex());

                    // Créer un HBox avec avatar + nom
                    HBox hbox = new HBox(12);
                    hbox.setAlignment(Pos.CENTER_LEFT);

                    // Avatar circulaire avec initiales
                    Circle avatar = new Circle(20);
                    avatar.setFill(Color.web("#0082AC"));
                    avatar.setStroke(Color.web("#e1e8ed"));
                    avatar.setStrokeWidth(2);

                    // Texte avec nom complet
                    Label nameLabel = new Label(user.getFullName());
                    nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #2c3e50;");

                    hbox.getChildren().addAll(avatar, nameLabel);
                    setGraphic(hbox);
                }
            }
        });

        // Colonne Rôle
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colRole.setCellFactory(column -> new TableCell<User, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Label roleLabel = new Label(item);
                    roleLabel.setStyle(getRoleStyle(item));
                    setGraphic(roleLabel);
                }
            }
        });

        // Colonne Email
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setCellFactory(column -> new TableCell<User, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 13px;");
                }
            }
        });

        // Colonne Statut avec badge coloré
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setCellFactory(column -> new TableCell<User, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Label statusLabel = new Label(item);
                    statusLabel.setStyle(getStatusStyle(item));
                    setGraphic(statusLabel);
                }
            }
        });

        // Colonne Actions avec boutons Edit/Delete
        colActions.setCellFactory(column -> new TableCell<User, Void>() {
            private final Button editBtn = new Button("Modifier");
            private final Button deleteBtn = new Button("Supprimer");

            {
                editBtn.setStyle("-fx-background-color: #0082AC; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px; -fx-padding: 6px 14px; -fx-background-radius: 6px; -fx-font-weight: bold;");
                deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px; -fx-padding: 6px 14px; -fx-background-radius: 6px; -fx-font-weight: bold;");

                editBtn.setOnMouseEntered(e -> editBtn.setStyle("-fx-background-color: #006d91; -fx-text-fill: white; -fx-background-radius: 6px; -fx-cursor: hand; -fx-font-size: 12px; -fx-padding: 6px 14px; -fx-font-weight: bold;"));
                editBtn.setOnMouseExited(e -> editBtn.setStyle("-fx-background-color: #0082AC; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px; -fx-padding: 6px 14px; -fx-background-radius: 6px; -fx-font-weight: bold;"));

                deleteBtn.setOnMouseEntered(e -> deleteBtn.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-background-radius: 6px; -fx-cursor: hand; -fx-font-size: 12px; -fx-padding: 6px 14px; -fx-font-weight: bold;"));
                deleteBtn.setOnMouseExited(e -> deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px; -fx-padding: 6px 14px; -fx-background-radius: 6px; -fx-font-weight: bold;"));

                editBtn.setOnAction(e -> {
                    User user = getTableView().getItems().get(getIndex());
                    handleEditUser(user);
                });

                deleteBtn.setOnAction(e -> {
                    User user = getTableView().getItems().get(getIndex());
                    handleDeleteUser(user);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(8, editBtn, deleteBtn);
                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);
                }
            }
        });
    }

    /**
     * Charger des données d'exemple
     */
    private void loadSampleData() {
        usersList.clear();

        // Ajouter des utilisateurs d'exemple
        usersList.add(new User("John Smith", "Admin", "john.smith@gmail.com", "Actif"));
        usersList.add(new User("Olivia Bennett", "Secrétaire", "olivia@gmail.com", "Inactif"));
        usersList.add(new User("Daniel Warren", "Client", "daniel@gmail.com", "Banni"));
        usersList.add(new User("Chloe Hayes", "Client", "chloe@gmail.com", "En attente"));
        usersList.add(new User("Marcus Reed", "Chef Chantier", "marcus@gmail.com", "Suspendu"));
        usersList.add(new User("Isabelle Clark", "Directeur", "isabelle@gmail.com", "Actif"));
        usersList.add(new User("Lucas Mitchell", "Client", "lucas@gmail.com", "Actif"));
        usersList.add(new User("Mark Wilburg", "Secrétaire", "mark@gmail.com", "Banni"));

        comptesTable.setItems(usersList);
    }

    /**
     * Configuration des actions des boutons
     */
    private void setupButtonActions() {
        if (btnAddUser != null) {
            btnAddUser.setOnAction(e -> handleAddUser());
        }
    }

    /**
     * Gérer l'ajout d'un utilisateur
     */
    @FXML
    private void handleAddUser() {
        try {
            // Créer une boîte de dialogue personnalisée
            Dialog<User> dialog = new Dialog<>();
            dialog.setTitle("Ajouter un utilisateur");
            dialog.setHeaderText("Remplissez les informations de l'utilisateur");

            // Boutons OK et Annuler avec style personnalisé
            ButtonType btnAjouter = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
            ButtonType btnAnnuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(btnAjouter, btnAnnuler);

            // Créer les champs du formulaire
            GridPane grid = new GridPane();
            grid.setHgap(15);
            grid.setVgap(15);
            grid.setPadding(new javafx.geometry.Insets(25, 25, 20, 25));
            grid.setStyle("-fx-background-color: white;");

            // Labels avec style
            Label lblNom = new Label("Nom complet:");
            lblNom.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50; -fx-font-weight: bold;");

            Label lblEmail = new Label("Email:");
            lblEmail.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50; -fx-font-weight: bold;");

            Label lblRole = new Label("Rôle:");
            lblRole.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50; -fx-font-weight: bold;");

            Label lblStatut = new Label("Statut:");
            lblStatut.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50; -fx-font-weight: bold;");

            // Champs avec style
            TextField nameField = new TextField();
            nameField.setPromptText("Nom complet");
            nameField.setPrefWidth(350);
            nameField.setStyle("-fx-font-size: 13px; -fx-padding: 10px; -fx-border-color: #e1e8ed; -fx-border-radius: 5px; -fx-background-radius: 5px;");

            TextField emailField = new TextField();
            emailField.setPromptText("Email");
            emailField.setPrefWidth(350);
            emailField.setStyle("-fx-font-size: 13px; -fx-padding: 10px; -fx-border-color: #e1e8ed; -fx-border-radius: 5px; -fx-background-radius: 5px;");

            ComboBox<String> roleCombo = new ComboBox<>();
            roleCombo.getItems().addAll("Admin", "Secrétaire", "Chef Chantier", "Directeur", "Client");
            roleCombo.setPromptText("Sélectionner un rôle");
            roleCombo.setPrefWidth(350);
            roleCombo.setStyle("-fx-font-size: 13px;");

            ComboBox<String> statusCombo = new ComboBox<>();
            statusCombo.getItems().addAll("Actif", "Inactif", "En attente", "Suspendu", "Banni");
            statusCombo.setValue("Actif");
            statusCombo.setPrefWidth(350);
            statusCombo.setStyle("-fx-font-size: 13px;");

            grid.add(lblNom, 0, 0);
            grid.add(nameField, 1, 0);
            grid.add(lblEmail, 0, 1);
            grid.add(emailField, 1, 1);
            grid.add(lblRole, 0, 2);
            grid.add(roleCombo, 1, 2);
            grid.add(lblStatut, 0, 3);
            grid.add(statusCombo, 1, 3);

            dialog.getDialogPane().setContent(grid);

            // Style de la DialogPane
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.setStyle("-fx-background-color: white; -fx-border-color: #e1e8ed;");

            // Style des boutons
            javafx.scene.Node ajouterButton = dialogPane.lookupButton(btnAjouter);
            ajouterButton.setStyle("-fx-background-color: #0082AC; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10px 25px; -fx-background-radius: 6px; -fx-cursor: hand;");

            javafx.scene.Node annulerButton = dialogPane.lookupButton(btnAnnuler);
            annulerButton.setStyle("-fx-background-color: #ecf0f1; -fx-text-fill: #2c3e50; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10px 25px; -fx-background-radius: 6px; -fx-cursor: hand; -fx-border-color: #bdc3c7; -fx-border-width: 1px; -fx-border-radius: 6px;");

            // Convertir le résultat
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == btnAjouter) {
                    if (!nameField.getText().isEmpty() && !emailField.getText().isEmpty() && roleCombo.getValue() != null) {
                        return new User(nameField.getText(), roleCombo.getValue(), emailField.getText(), statusCombo.getValue());
                    }
                }
                return null;
            });

            // Afficher et attendre la réponse
            dialog.showAndWait().ifPresent(user -> {
                usersList.add(user);
                showSuccessMessage("Utilisateur ajouté avec succès!");
            });

        } catch (Exception e) {
            showErrorMessage("Erreur lors de l'ajout: " + e.getMessage());
        }
    }

    /**
     * Gérer la modification d'un utilisateur
     */
    private void handleEditUser(User user) {
        try {
            // Créer une boîte de dialogue personnalisée
            Dialog<User> dialog = new Dialog<>();
            dialog.setTitle("Modifier l'utilisateur");
            dialog.setHeaderText("Modifier les informations de: " + user.getFullName());

            // Boutons
            ButtonType btnEnregistrer = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
            ButtonType btnAnnuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(btnEnregistrer, btnAnnuler);

            // Créer les champs du formulaire avec valeurs actuelles
            GridPane grid = new GridPane();
            grid.setHgap(15);
            grid.setVgap(15);
            grid.setPadding(new javafx.geometry.Insets(25, 25, 20, 25));
            grid.setStyle("-fx-background-color: white;");

            // Labels avec style
            Label lblNom = new Label("Nom complet:");
            lblNom.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50; -fx-font-weight: bold;");

            Label lblEmail = new Label("Email:");
            lblEmail.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50; -fx-font-weight: bold;");

            Label lblRole = new Label("Rôle:");
            lblRole.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50; -fx-font-weight: bold;");

            Label lblStatut = new Label("Statut:");
            lblStatut.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50; -fx-font-weight: bold;");

            TextField nameField = new TextField(user.getFullName());
            nameField.setPrefWidth(350);
            nameField.setStyle("-fx-font-size: 13px; -fx-padding: 10px; -fx-border-color: #e1e8ed; -fx-border-radius: 5px; -fx-background-radius: 5px;");

            TextField emailField = new TextField(user.getEmail());
            emailField.setPrefWidth(350);
            emailField.setStyle("-fx-font-size: 13px; -fx-padding: 10px; -fx-border-color: #e1e8ed; -fx-border-radius: 5px; -fx-background-radius: 5px;");

            ComboBox<String> roleCombo = new ComboBox<>();
            roleCombo.getItems().addAll("Admin", "Secrétaire", "Chef Chantier", "Directeur", "Client");
            roleCombo.setValue(user.getRole());
            roleCombo.setPrefWidth(350);
            roleCombo.setStyle("-fx-font-size: 13px;");

            ComboBox<String> statusCombo = new ComboBox<>();
            statusCombo.getItems().addAll("Actif", "Inactif", "En attente", "Suspendu", "Banni");
            statusCombo.setValue(user.getStatus());
            statusCombo.setPrefWidth(350);
            statusCombo.setStyle("-fx-font-size: 13px;");

            grid.add(lblNom, 0, 0);
            grid.add(nameField, 1, 0);
            grid.add(lblEmail, 0, 1);
            grid.add(emailField, 1, 1);
            grid.add(lblRole, 0, 2);
            grid.add(roleCombo, 1, 2);
            grid.add(lblStatut, 0, 3);
            grid.add(statusCombo, 1, 3);

            dialog.getDialogPane().setContent(grid);

            // Style de la DialogPane
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.setStyle("-fx-background-color: white; -fx-border-color: #e1e8ed;");

            // Style des boutons
            javafx.scene.Node enregistrerButton = dialogPane.lookupButton(btnEnregistrer);
            enregistrerButton.setStyle("-fx-background-color: #0082AC; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10px 25px; -fx-background-radius: 6px; -fx-cursor: hand;");

            javafx.scene.Node annulerButton = dialogPane.lookupButton(btnAnnuler);
            annulerButton.setStyle("-fx-background-color: #ecf0f1; -fx-text-fill: #2c3e50; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10px 25px; -fx-background-radius: 6px; -fx-cursor: hand; -fx-border-color: #bdc3c7; -fx-border-width: 1px; -fx-border-radius: 6px;");

            // Convertir le résultat
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == btnEnregistrer) {
                    user.setFullName(nameField.getText());
                    user.setEmail(emailField.getText());
                    user.setRole(roleCombo.getValue());
                    user.setStatus(statusCombo.getValue());
                    return user;
                }
                return null;
            });

            // Afficher et attendre la réponse
            dialog.showAndWait().ifPresent(updatedUser -> {
                comptesTable.refresh();
                showSuccessMessage("Utilisateur modifié avec succès!");
            });

        } catch (Exception e) {
            showErrorMessage("Erreur lors de la modification: " + e.getMessage());
        }
    }

    /**
     * Gérer la suppression d'un utilisateur
     */
    private void handleDeleteUser(User user) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText("Supprimer l'utilisateur: " + user.getFullName());
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cet utilisateur?\n\nEmail: " + user.getEmail() + "\nRôle: " + user.getRole());

        // Style de la DialogPane
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: white;");

        // Personnaliser les boutons
        ButtonType btnSupprimer = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnAnnuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(btnSupprimer, btnAnnuler);

        // Style des boutons
        javafx.scene.Node supprimerButton = dialogPane.lookupButton(btnSupprimer);
        supprimerButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10px 25px; -fx-background-radius: 6px; -fx-cursor: hand;");

        javafx.scene.Node annulerButton = dialogPane.lookupButton(btnAnnuler);
        annulerButton.setStyle("-fx-background-color: #ecf0f1; -fx-text-fill: #2c3e50; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 10px 25px; -fx-background-radius: 6px; -fx-cursor: hand; -fx-border-color: #bdc3c7; -fx-border-width: 1px; -fx-border-radius: 6px;");

        alert.showAndWait().ifPresent(response -> {
            if (response == btnSupprimer) {
                usersList.remove(user);
                showSuccessMessage("Utilisateur supprimé avec succès!");
            }
        });
    }

    /**
     * Afficher un message de succès
     */
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Style personnalisé
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: white;");

        alert.showAndWait();
    }

    /**
     * Afficher un message d'erreur
     */
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Obtenir le style pour le badge de rôle
     */
    private String getRoleStyle(String role) {
        String baseStyle = "-fx-background-radius: 8px; -fx-padding: 5px 12px; -fx-font-size: 12px; -fx-font-weight: 600;";

        switch (role.toLowerCase()) {
            case "admin":
                return baseStyle + " -fx-background-color: rgba(155, 89, 182, 0.15); -fx-text-fill: #9b59b6;";
            case "secrétaire":
                return baseStyle + " -fx-background-color: rgba(52, 152, 219, 0.15); -fx-text-fill: #3498db;";
            case "chef chantier":
                return baseStyle + " -fx-background-color: rgba(230, 126, 34, 0.15); -fx-text-fill: #e67e22;";
            case "directeur":
                return baseStyle + " -fx-background-color: rgba(192, 57, 43, 0.15); -fx-text-fill: #c0392b;";
            case "client":
                return baseStyle + " -fx-background-color: rgba(149, 165, 166, 0.15); -fx-text-fill: #7f8c8d;";
            default:
                return baseStyle + " -fx-background-color: rgba(149, 165, 166, 0.15); -fx-text-fill: #7f8c8d;";
        }
    }

    /**
     * Obtenir le style pour le badge de statut
     */
    private String getStatusStyle(String status) {
        String baseStyle = "-fx-background-radius: 12px; -fx-padding: 6px 15px; -fx-font-size: 12px; -fx-font-weight: bold;";

        switch (status.toLowerCase()) {
            case "actif":
                return baseStyle + " -fx-background-color: rgba(39, 174, 96, 0.15); -fx-text-fill: #27ae60;";
            case "inactif":
                return baseStyle + " -fx-background-color: rgba(149, 165, 166, 0.15); -fx-text-fill: #95a5a6;";
            case "suspendu":
                return baseStyle + " -fx-background-color: rgba(243, 156, 18, 0.15); -fx-text-fill: #f39c12;";
            case "en attente":
                return baseStyle + " -fx-background-color: rgba(52, 152, 219, 0.15); -fx-text-fill: #3498db;";
            case "banni":
                return baseStyle + " -fx-background-color: rgba(231, 76, 60, 0.15); -fx-text-fill: #e74c3c;";
            default:
                return baseStyle + " -fx-background-color: rgba(149, 165, 166, 0.15); -fx-text-fill: #95a5a6;";
        }
    }

    // ========== NAVIGATION ==========

    @FXML
    private void switchToAccueil() throws IOException {
        loadPage("/views/secretaire/dashboard_secretaire.fxml", navAccueil);
    }

    @FXML
    private void switchToMessages() throws IOException {
        loadPage("/views/secretaire/messages_secretaire.fxml", navMessages);
    }

    @FXML
    private void switchToParametres() throws IOException {
        loadPage("/views/secretaire/parametres_secretaire.fxml", navParametres);
    }

    private void loadPage(String path, Label label) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    // ========== INNER CLASS: USER MODEL ==========

    public static class User {
        private String fullName;
        private String role;
        private String email;
        private String status;

        public User(String fullName, String role, String email, String status) {
            this.fullName = fullName;
            this.role = role;
            this.email = email;
            this.status = status;
        }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}