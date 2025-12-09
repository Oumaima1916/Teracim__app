package com.myapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//les classes
public class ChefDashboardController {

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private VBox rootContainer;
    @FXML
    private void initialize() {
        System.out.println("ChefDashboardController initialized");
    }

    @FXML
    private void openAddProject(javafx.event.ActionEvent event) {
        System.out.println("Bouton 'Ajouter un projet' cliqué");
    }

    @FXML
    private void openParametres() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/chefchantier/chef_parametres.fxml")
            );
            Parent root = loader.load();

            Stage stage = (Stage) rootContainer.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (Exception e) {
            System.out.println("Erreur openParametres:");
            e.printStackTrace();
        }
    }
}
