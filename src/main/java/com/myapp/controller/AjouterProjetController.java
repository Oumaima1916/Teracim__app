package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AjouterProjetController {

    @FXML
    private TextField nomProjetField;

    @FXML
    private TextField clientField;

    @FXML
    private TextField villeField;

    @FXML
    private TextField adresseField;

    @FXML
    private ComboBox<String> etatCombo;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button enregistrerButton;

    @FXML
    private Button retourButton;

    @FXML
    public void initialize() {
        //etat
        if (etatCombo != null) {
            etatCombo.getItems().addAll("En cours", "Terminé", "En pause");
        }
    }

    @FXML
    private void onEnregistrerClicked() {
        // test – ghir nchofo l-form kaykhddem
        System.out.println("Projet = " + nomProjetField.getText());
        System.out.println("Client = " + clientField.getText());
        System.out.println("Ville = " + villeField.getText());
        System.out.println("Adresse = " + adresseField.getText());
        System.out.println("Etat = " + etatCombo.getValue());
        System.out.println("Description = " + descriptionArea.getText());
    }

    @FXML
    private void onRetourClicked() {
        Stage stage = (Stage) retourButton.getScene().getWindow();
        stage.close();
    }
}