package com.myapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class ContactController {

    @FXML
    public void goHome(ActionEvent event) {
        goToPage(event, "/views/main/main_view.fxml");
    }

    @FXML
    public void goAbout(MouseEvent event) {
        goToPage(event, "/views/about.fxml");
    }

    @FXML
    public void handleEnvoyer(ActionEvent event) {
        System.out.println("Formulaire envoyé !");
    }

    private void goToPage(Object eventSource, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) (eventSource instanceof ActionEvent ? ((ActionEvent) eventSource).getSource() : ((MouseEvent) eventSource).getSource())).getScene().getWindow();

            double currentWidth = stage.getScene().getWidth();
            double currentHeight = stage.getScene().getHeight();
            boolean wasMaximized = stage.isMaximized();

            Scene scene = new Scene(root, currentWidth, currentHeight);
            stage.setScene(scene);
            if (wasMaximized) stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}