package com.myapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ProjectDetailsController {

    @FXML private Label lblTitle;
    @FXML private Label lblClient;
    @FXML private Label lblLocation;
    @FXML private Label lblDescription;

    @FXML private VBox tasksContainer;
    @FXML private Label emptyLabel;

    @FXML
    private void initialize() {
        refreshEmptyState();
    }

    public void setProjectData(String title, String client, String location, String desc) {
        lblTitle.setText(title);
        lblClient.setText("Client : " + client);
        lblLocation.setText("Localisation : " + location);
        lblDescription.setText(desc);
    }

    private void refreshEmptyState() {
        emptyLabel.setVisible(tasksContainer.getChildren().isEmpty());
    }

    @FXML
    private void onAddTask() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/chefchantier/add_task.fxml")
            );
            Parent view = loader.load();

            BorderPane root =
                    (BorderPane) lblTitle.getScene().getRoot();
            root.setCenter(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}