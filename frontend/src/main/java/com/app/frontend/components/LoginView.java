package com.app.frontend.components;

import com.app.frontend.models.User;
import com.app.frontend.services.ApiService;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginView extends VBox {

    private final ApiService apiService = new ApiService();
    // Defined here to allow MainApp to handle navigation
    private final Hyperlink registerLink = new Hyperlink("Create Account");

    public LoginView(Runnable onLoginSuccess) {
        setSpacing(15);
        setAlignment(Pos.CENTER);

        // Styling
        try {
            this.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("Style file not found!");
        }

        this.getStyleClass().add("login-page");
        Label title = new Label("NoteTo");
        title.getStyleClass().add("login-title");

        // Input Fields
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.getStyleClass().add("login-field");
        emailField.setMaxWidth(200);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.getStyleClass().add("login-field");
        passwordField.setMaxWidth(200);

        // Feedback Label
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #ff4444;"); // Bright red for errors

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("login-button");

        // Login Logic
        loginButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if (email.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Please enter email and password!");
                return;
            }

            errorLabel.setText("Logging in...");

            try {
                User loggedInUser = apiService.login(email, password);
                System.out.println("✅ Login Successful! Welcome: " + loggedInUser.getUsername());

                // Trigger the app switch
                onLoginSuccess.run();

            } catch (Exception ex) {
                System.out.println("⚠️ Login Failed: " + ex.getMessage());
                errorLabel.setText("Invalid login credentials!");
            }
        });

        // Add all elements to the view
        getChildren().addAll(
                title, emailField, passwordField, loginButton, errorLabel, registerLink
        );
    }

    // Getter for MainApp to use for navigation to RegisterView
    public Hyperlink getRegisterLink() {
        return registerLink;
    }
}