package com.myapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SecretaireDashboardController implements Initializable {

    @FXML private Label navAccueil, navMessages, navParametres, navGestion;
    @FXML private BarChart<String, Number> presenceChart;
    @FXML private TableView<Appointment> appointmentsTable;
    @FXML private TableColumn<Appointment, String> colHeure, colClient, colObjet, colMontant, colStatus;
    @FXML private TableColumn<Appointment, Void> colActions;
    @FXML private VBox docsContainer, updatesContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupBlueChart();
        setupTable();
        loadDocsAndUpdates();
    }

    // --- Navigation (Paths msl7in 100%) ---

    @FXML private void switchToMessages() throws IOException {
        changeScene("/views/secretaire/messages_secretaire.fxml", navMessages);
    }

    @FXML private void switchToAccueil() throws IOException {
        changeScene("/views/secretaire/dashboard_secretaire.fxml", navAccueil);
    }

    @FXML private void switchToParametres() throws IOException {
        changeScene("/views/secretaire/parametres_secretaire.fxml", navParametres);
    }

    @FXML private void switchToGestion() throws IOException {
        // Hna fiksina l-path bach i-welli kiy-dkhlek nichan
        changeScene("/views/secretaire/gestion_secretaire.fxml", navGestion);
    }

    private void changeScene(String fxmlPath, Label label) {
        try {
            URL resource = getClass().getResource(fxmlPath);
            if (resource == null) {
                System.err.println("ERREUR : Milaf FXML ma-l9itouch f had l-path: " + fxmlPath);
                return;
            }
            Parent root = FXMLLoader.load(resource);
            Stage stage = (Stage) label.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- Logic dyal l-Chart (Ma-9isnahch) ---
    private void setupBlueChart() {
        presenceChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("8:00", 12));
        series.getData().add(new XYChart.Data<>("9:00", 28));
        series.getData().add(new XYChart.Data<>("10:00", 35));
        series.getData().add(new XYChart.Data<>("11:00", 38));
        series.getData().add(new XYChart.Data<>("12:00", 25));
        series.getData().add(new XYChart.Data<>("14:00", 32));
        series.getData().add(new XYChart.Data<>("15:00", 35));
        series.getData().add(new XYChart.Data<>("16:00", 28));
        presenceChart.getData().add(series);
    }

    // --- Logic dyal l-Tableau (Ma-9isnahch) ---
    private void setupTable() {
        colHeure.setCellValueFactory(new PropertyValueFactory<>("heure"));
        colClient.setCellValueFactory(new PropertyValueFactory<>("client"));
        colObjet.setCellValueFactory(new PropertyValueFactory<>("objet"));
        colMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        colStatus.setCellFactory(column -> new TableCell<Appointment, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) setGraphic(null);
                else {
                    Label label = new Label(status);
                    label.setPrefWidth(110); label.setAlignment(Pos.CENTER);
                    if (status.contains("Confirmé")) label.setStyle("-fx-background-color: #d4edda; -fx-text-fill: #155724; -fx-padding: 5 10; -fx-background-radius: 15; -fx-font-weight: bold;");
                    else label.setStyle("-fx-background-color: #fff3cd; -fx-text-fill: #856404; -fx-padding: 5 10; -fx-background-radius: 15; -fx-font-weight: bold;");
                    setGraphic(label);
                }
            }
        });

        colActions.setCellFactory(column -> new TableCell<Appointment, Void>() {
            private final Button btnEdit = new Button("✏️");
            private final Button btnDelete = new Button("🗑️");
            private final HBox box = new HBox(8, btnEdit, btnDelete);
            { box.setAlignment(Pos.CENTER); btnEdit.setStyle("-fx-background-color: #0082AC; -fx-text-fill: white; -fx-background-radius: 5;"); btnDelete.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 5;"); }
            @Override protected void updateItem(Void item, boolean empty) { super.updateItem(item, empty); setGraphic(empty ? null : box); }
        });

        ObservableList<Appointment> data = FXCollections.observableArrayList(
                new Appointment("09:00", "M. Ahmed Mansouri", "Diagnostic Villa", "500 DH", "✓ Confirmé"),
                new Appointment("10:00", "M. Hassan Alami", "Villa Casablanca", "850 DH", "✓ Confirmé"),
                new Appointment("11:30", "Mme Salma Radi", "Consultation", "300 DH", "⏰ En attente"),
                new Appointment("14:00", "Mme Fatima Zahra", "Rénovation Appt", "1,200 DH", "⏰ En attente"),
                new Appointment("15:30", "M. Youssef Benani", "Construction Bureau", "2,500 DH", "✓ Confirmé"),
                new Appointment("16:45", "Mme Khadija Bennani", "Extension Maison", "3,200 DH", "✓ Confirmé"),
                new Appointment("17:30", "M. Omar Filali", "Expertise Sol", "1,000 DH", "✓ Confirmé"),
                new Appointment("18:15", "Mme Hind Jabri", "Modification Plan", "450 DH", "⏰ En attente"),
                new Appointment("19:00", "M. Karim Tazi", "Rapport Final", "600 DH", "✓ Confirmé")
        );
        appointmentsTable.setItems(data);
    }

    private void loadDocsAndUpdates() {
        docsContainer.getChildren().clear();
        addListItem(docsContainer, "Contrat_Entreprise.pdf", "07 Nov");
        addListItem(docsContainer, "Rapport_11Nov.pdf", "11 Nov");
        addListItem(docsContainer, "Rapport_10Nov.pdf", "10 Nov");
        addListItem(docsContainer, "Devis_Villa.pdf", "03 Nov");
        addListItem(docsContainer, "Facture_Materiaux.pdf", "02 Nov");

        updatesContainer.getChildren().clear();
        addListItem(updatesContainer, "Fondations Bloc B", "07 Nov");
        addListItem(updatesContainer, "Coulage béton A", "06 Nov");
        addListItem(updatesContainer, "Début ferraillage", "05 Nov");
        addListItem(updatesContainer, "Installation coffrage", "04 Nov");
        addListItem(updatesContainer, "Peinture Façade", "03 Nov");
    }

    private void addListItem(VBox container, String title, String date) {
        HBox item = new HBox(12); item.setAlignment(Pos.CENTER_LEFT);
        Region dot = new Region(); dot.getStyleClass().add("blue-square-dot");
        VBox texts = new VBox(2);
        Label t = new Label(title); t.setStyle("-fx-text-fill: #1a2a3a; -fx-font-weight: bold; -fx-font-size: 13;");
        Label d = new Label(date); d.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 10;");
        texts.getChildren().addAll(t, d); item.getChildren().addAll(dot, texts);
        container.getChildren().add(item);
    }

    public static class Appointment {
        private String heure, client, objet, montant, status;
        public Appointment(String h, String c, String o, String m, String s) { this.heure = h; this.client = c; this.objet = o; this.montant = m; this.status = s; }
        public String getHeure() { return heure; }
        public String getClient() { return client; }
        public String getObjet() { return objet; }
        public String getMontant() { return montant; }
        public String getStatus() { return status; }

    }
}