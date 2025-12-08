package com.myapp.controller;

import javafx.fxml.FXML;

public class ChefDashboardController {

    // Référence vers MainController (pour revenir à la home, etc.)
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void initialize() {
        // Initialisation du dashboard (chargement de données, etc.)
        // Pour l'instant tu peux laisser vide
    }

    // Plus tard tu pourras ajouter :
    // - méthodes pour ouvrir un document
    // - afficher les tâches, les messages, etc.
}
