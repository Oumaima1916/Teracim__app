package com.myapp.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Controller for the Chef dashboard.
 */
public class ChefDashboardController {

    private MainController mainController;

    @FXML
    private BorderPane rootPane;    // fx:id="rootPane" sur le <BorderPane> dans le FXML

    @FXML
    private VBox rootContainer;     // fx:id="rootContainer" sur le VBox dans le center

    @FXML
    private Button addBtn;          // fx:id="addBtn" sur le bouton "Ajouter un projet"

    /**
     * Méthode d'initialisation unique.
     */
    @FXML
    private void initialize() {
        System.out.println("ChefDashboardController initialized");

        // --- Charger le CSS (optionnel si déjà déclaré dans le FXML) ---
        try {
            URL cssUrl = getClass().getResource("/views/chefchantier/chef_chantier.css");
            if (cssUrl != null && rootPane != null) {
                rootPane.getStylesheets().add(cssUrl.toExternalForm());
                System.out.println("Applied stylesheet: " + cssUrl);
            } else {
                System.err.println("Stylesheet not found: /views/chefchantier/chef_chantier.css");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // --- Binding programmatique pour le bouton "Ajouter un projet" ---
        if (addBtn != null) {
            addBtn.setOnAction(evt -> {
                System.out.println("DEBUG: addBtn clicked (programmatic handler)");
                openAddProject(new ActionEvent(addBtn, null));
            });
        } else {
            System.out.println("DEBUG: addBtn is null (no fx:id or not injected)");
        }
    }

    /**
     * Called by MainController after loading the dashboard.
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Handler FXML pour le bouton "Ajouter un projet" (onAction="#openAddProject").
     * Ouvre le formulaire dans une fenêtre modale.
     */
    @FXML
    private void openAddProject(ActionEvent event) {
        System.out.println("DEBUG: openAddProject called (modal-forced)");

        String resource = "/views/chefchantier/add_project.fxml";
        URL url = getClass().getResource(resource);
        if (url == null) {
            System.err.println("DEBUG ERROR: FXML resource not found -> " + resource);
            // On peut utiliser showError ici (on est déjà sur le thread FX)
            showError("Resource not found", "Fichier FXML manquant : " + resource);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(url);
            Parent form = loader.load();

            // Trouver la fenêtre parente (owner) à partir de l'event
            Stage owner = null;
            if (event != null && event.getSource() instanceof Node node &&
                    node.getScene() != null && node.getScene().getWindow() instanceof Stage) {
                owner = (Stage) node.getScene().getWindow();
            }

            Stage dialog = new Stage();
            if (owner != null) dialog.initOwner(owner);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Ajouter un projet");
            dialog.setScene(new Scene(form));
            dialog.showAndWait();

            System.out.println("DEBUG: modal shown OK");

        } catch (IOException e) {
            System.err.println("DEBUG ERROR: failed to load FXML");
            e.printStackTrace();
            showError("Erreur de chargement",
                    "Erreur lors du chargement du formulaire.\nVoir console pour détails.");
        } catch (Exception e) {
            System.err.println("DEBUG ERROR: unexpected exception");
            e.printStackTrace();
            showError("Erreur", "Erreur inattendue : " + e.getMessage());
        }
    }

    /**
     * Clic sur "Paramètres" dans la topbar.
     */
    @FXML
    private void openParametres(MouseEvent event) {
        loadIntoCenter("/views/chefchantier/chef_parametres.fxml");
    }

    /**
     * Clic sur "Messages" dans la topbar (si tu as mis onMouseClicked="#openMessages").
     */
    @FXML
    private void openMessages(MouseEvent event) {
        loadIntoCenter("/views/chefchantier/messages_chef.fxml");
    }

    /**
     * Charge un FXML dans le center du BorderPane (ou dans rootContainer si rootPane est null).
     */
    private void loadIntoCenter(String resourcePath) {
        try {
            URL url = getClass().getResource(resourcePath);
            if (url == null) {
                // fallback ClassLoader
                String p = resourcePath.startsWith("/") ? resourcePath.substring(1) : resourcePath;
                url = Thread.currentThread().getContextClassLoader().getResource(p);
            }

            if (url == null) {
                String msg = "FXML resource not found: " + resourcePath;
                System.err.println(msg);
                showError("Ressource introuvable", msg);
                return;
            }

            System.out.println("Loading FXML resource: " + resourcePath + " -> " + url);
            FXMLLoader loader = new FXMLLoader(url);
            Parent view = loader.load();

            // Optionnel : passer mainController aux contrôleurs enfants qui l’implémentent
            Object childController = loader.getController();
            if (childController instanceof HasMainController hasMain) {
                hasMain.setMainController(mainController);
            }

            if (rootPane != null) {
                rootPane.setCenter(view);
            } else if (rootContainer != null) {
                rootContainer.getChildren().clear();
                rootContainer.getChildren().add(view);
            } else {
                showError("Erreur interne",
                        "rootPane et rootContainer introuvables (fx:id manquant dans le FXML).");
            }

        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur de chargement", "Impossible de charger la page : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur", "Erreur inattendue : " + e.getMessage());
        }
    }

    private void showError(String title, String message) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle(title);
            a.setHeaderText(null);
            a.setContentText(message);
            try {
                if (rootPane != null && rootPane.getScene() != null && rootPane.getScene().getWindow() != null) {
                    a.initOwner(rootPane.getScene().getWindow());
                }
            } catch (Exception ignored) {}
            a.showAndWait();
        });
    }

    public interface HasMainController {
        void setMainController(MainController mainController);
    }
}
