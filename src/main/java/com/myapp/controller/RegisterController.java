package com.myapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    // Champs du formulaire
    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;

    // Pour la navigation
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    // Bouton "Retour" → home
    @FXML
    private void goBack(ActionEvent event) {
        if (mainController != null) {
            mainController.showHome();
        } else {
            System.out.println("mainController est null (setMainController non appelé).");
        }
    }

    // Bouton "S'inscrire"
    @FXML
    private void handleRegister(ActionEvent event) {

        String fullName = fullNameField.getText();
        String email    = emailField.getText();
        String phone    = phoneField.getText();
        String pwd      = passwordField.getText();
        String confirm  = confirmPasswordField.getText();

        // Vérifs basiques
        if (fullName.isBlank() || email.isBlank() || pwd.isBlank() || confirm.isBlank()) {
            showAlert(Alert.AlertType.WARNING,
                    "Champs manquants",
                    "Veuillez remplir au minimum : Nom, E-mail et Mot de passe.");
            return;
        }

        if (!email.contains("@")) {
            showAlert(Alert.AlertType.WARNING,
                    "E-mail invalide",
                    "Veuillez saisir une adresse e-mail valide.");
            return;
        }

        if (pwd.length() < 6) {
            showAlert(Alert.AlertType.WARNING,
                    "Mot de passe trop court",
                    "Le mot de passe doit contenir au moins 6 caractères.");
            return;
        }

        if (!pwd.equals(confirm)) {
            showAlert(Alert.AlertType.WARNING,
                    "Confirmation incorrecte",
                    "La confirmation du mot de passe ne correspond pas.");
            return;
        }

        // TODO: enregistrer vraiment l'utilisateur (BD / fichier / API…)
        System.out.println("Nouveau compte chef :");
        System.out.println("Nom   : " + fullName);
        System.out.println("Email : " + email);
        System.out.println("Tél   : " + phone);

        // Message OK
        showAlert(Alert.AlertType.INFORMATION,
                "Inscription",
                "Compte créé (mock). Vous pouvez vous connecter.");

        // Nettoyer un peu le formulaire
        passwordField.clear();
        confirmPasswordField.clear();

        // Redirection vers la page de login
        if (mainController != null) {
            mainController.showLogin(null);
        }
    }

    // Boîte de dialogue utilitaire
    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}
