package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SecretaireParametresController implements Initializable {

    @FXML private Label navAccueil, navMessages, navGestion;
    @FXML private ComboBox<String> languageComboBox;
    @FXML private ToggleGroup themeGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML private void switchToAccueil() throws IOException { loadPage("/views/secretaire/dashboard_secretaire.fxml", navAccueil); }
    @FXML private void switchToMessages() throws IOException { loadPage("/views/secretaire/messages_secretaire.fxml", navMessages); }
    @FXML private void switchToGestion() throws IOException { loadPage("/views/secretaire/gestion_secretaire.fxml", navGestion); }

    private void loadPage(String path, Label label) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) label.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}