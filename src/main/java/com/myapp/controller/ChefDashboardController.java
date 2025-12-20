package com.myapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.InputStream;
import java.io.IOException;
import java.net.URL;

public class ChefDashboardController {

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML private BorderPane rootPane;
    @FXML private VBox rootContainer;
    @FXML private GridPane projectsGrid;
    @FXML private Button addBtn;
    @FXML private ImageView notifIcon;

    private javafx.scene.Node savedCenterNode = null;

    @FXML
    private void initialize() {
        if (addBtn != null) {
            addBtn.setOnAction(this::openAddProject);
        }
    }

    @FXML
    private void openAddProject(ActionEvent event) {
        try {
            if (savedCenterNode == null) {
                savedCenterNode = rootPane.getCenter();
            }

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/chefchantier/add_project.fxml")
            );
            Parent form = loader.load();

            Object controller = loader.getController();
            if (controller instanceof AjouterProjetController apc) {
                apc.setParentController(this);
            }

            rootPane.setCenter(form);

        } catch (Exception e) {
            showError("Erreur", e.getMessage());
        }
    }

    public void addProjectCard(String title, String client, String city, String description) {
        VBox card = new VBox();
        card.getStyleClass().add("project-card");

        card.getChildren().addAll(
                new Label(title),
                new Label("Client : " + client),
                new Label("Ville : " + city),
                new Label(description)
        );

        int count = projectsGrid.getChildren().size();
        projectsGrid.add(card, count % 2, count / 2);

        restoreCenter();
    }

    // ✅ التعديل الوحيد هنا
    public void restoreCenter() {
        if (rootPane != null && savedCenterNode != null) {
            rootPane.setCenter(savedCenterNode);
            savedCenterNode = null;
        }
    }

    @FXML
    private void openMessages(MouseEvent event) {
        loadIntoCenter("/views/chefchantier/messages_chef.fxml");
    }

    @FXML
    private void openParametres(MouseEvent event) {
        loadIntoCenter("/views/chefchantier/chef_parametres.fxml");
    }

    private void loadIntoCenter(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent view = loader.load();
            rootPane.setCenter(view);
        } catch (Exception e) {
            showError("Erreur", e.getMessage());
        }
    }

    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.show();
    }
    @FXML
    private void goToHome(javafx.scene.input.MouseEvent event) {
        if (mainController != null) {
            mainController.showHome();
        }
    }

}
