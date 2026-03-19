package com.app.frontend.controllers;

import com.app.frontend.models.User;
import com.app.frontend.services.ApiService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    private final ApiService apiService = new ApiService();

    @FXML
    public void handleLoginAction(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both email and password!");
            return;
        }

        try {
            User loggedInUser = apiService.login(email, password);

            System.out.println("Login Successful! Welcome: " + loggedInUser.getUsername());


        } catch (Exception e) {
            showAlert("Login Failed", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}