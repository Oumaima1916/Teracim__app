package com.myapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    // جاي من main_view.fxml : <StackPane fx:id="rootStack" ...>
    @FXML
    private StackPane rootStack;

    @FXML
    private void goToLogin(ActionEvent event) {
        System.out.println("Login button clicked!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Terracim");
        alert.setHeaderText(null);
        alert.setContentText("Login button works!");
        alert.show();
    }

    @FXML
    private void goToRegister(ActionEvent event) {
        System.out.println("Register button clicked!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Terracim");
        alert.setHeaderText(null);
        alert.setContentText("Register button works!");
        alert.show();
    }

    // هادي كتتعيط ملي تضغط على "Commencer avec nous"
    @FXML
    private void showForm(ActionEvent event) {
        try {
            Parent form = FXMLLoader.load(
                    getClass().getResource("/views/client_form.fxml")
            );

            // نبدّلو الكونتنت ديال rootStack بالفورم
            rootStack.getChildren().setAll(form);

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible d'ouvrir le formulaire");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
}
