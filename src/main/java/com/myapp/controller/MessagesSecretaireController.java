package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessagesSecretaireController implements Initializable {

    @FXML private Label navAccueil, navParametres, navGestion, chatUserName;
    @FXML private VBox chatContainer;
    @FXML private TextField messageField;
    @FXML private Button btnChef, btnDirecteur, btnClient;

    private final String activeStyle = "-fx-background-color: white; -fx-text-fill: #0082AC; -fx-background-radius: 30; -fx-padding: 10 25; -fx-font-weight: bold; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 0);";
    private final String inactiveStyle = "-fx-background-color: transparent; -fx-text-fill: #7f8c8d; -fx-padding: 10 25;";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadChefChat(); // I-بدا ب Chef de Chantier
    }

    // --- Conversations Exemples (WhatsApp Style) ---

    @FXML private void loadChefChat() {
        updateUI("Chef de Chantier", btnChef);
        addMessage("Chef de Chantier", "Bonjour Sara, le coulage du béton pour le Bloc B est terminé.", false);
        addMessage("Moi", "C'est noté. Avez-vous besoin du rapport de réception ?", true);
        addMessage("Chef de Chantier", "Oui, merci de me l'envoyer avant 16h.", false);
    }

    @FXML private void loadDirecteurChat() {
        updateUI("Directeur", btnDirecteur);
        addMessage("Directeur", "Bonjour, j'ai besoin du planning de la semaine prochaine.", false);
        addMessage("Moi", "Bonjour Monsieur, je suis en train de le finaliser.", true);
        addMessage("Directeur", "Parfait, ajoutez-y la réunion avec le client Mansouri.", false);
    }

    @FXML private void loadClientChat() {
        updateUI("Client", btnClient);
        addMessage("Client", "Bonjour, quel est l'état d'avancement de ma villa ?", false);
        addMessage("Moi", "Bonjour M. Ahmed, les fondations sont finies à 100%.", true);
        addMessage("Client", "Super ! On peut prévoir une visite de chantier ?", false);
    }

    private void updateUI(String name, Button activeBtn) {
        chatUserName.setText(name);
        chatContainer.getChildren().clear();
        btnChef.setStyle(inactiveStyle);
        btnDirecteur.setStyle(inactiveStyle);
        btnClient.setStyle(inactiveStyle);
        activeBtn.setStyle(activeStyle);
    }

    private void addMessage(String sender, String text, boolean isMe) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setMaxWidth(400);


        if (isMe) {
            label.setStyle("-fx-background-color: #0082AC; -fx-text-fill: white; -fx-padding: 10 15; -fx-background-radius: 15 15 0 15;");
        } else {
            label.setStyle("-fx-background-color: #f1f1f1; -fx-text-fill: #333; -fx-padding: 10 15; -fx-background-radius: 15 15 15 0;");
        }

        HBox bubble = new HBox(label);
        bubble.setAlignment(isMe ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        chatContainer.getChildren().add(bubble);
    }

    @FXML private void handleSendMessage() {
        String msg = messageField.getText();
        if (msg != null && !msg.trim().isEmpty()) {
            addMessage("Moi", msg, true);
            messageField.clear();
        }
    }

    // --- Navigation ---
    @FXML private void switchToAccueil() throws IOException { loadPage("/views/secretaire/dashboard_secretaire.fxml", navAccueil); }
    @FXML private void switchToParametres() throws IOException { loadPage("/views/secretaire/parametres_secretaire.fxml", navParametres); }
    @FXML private void switchToGestion() throws IOException { loadPage("/views/secretaire/gestion_secretaire.fxml", navGestion); }

    private void loadPage(String path, Label label) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}