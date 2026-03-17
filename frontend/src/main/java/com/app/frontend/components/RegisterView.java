package com.app.frontend.components;

import com.app.frontend.services.ApiService;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RegisterView extends VBox {

    // 1. استدعاء السيرفيس اللي بيحكي مع الباكند
    private final ApiService apiService = new ApiService();

    public RegisterView(Runnable goToLogin) {
        setSpacing(15);
        setAlignment(Pos.CENTER);

        // التأكد من تحميل التنسيقات
        try {
            this.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("CSS not found, skipping...");
        }

        this.getStyleClass().add("login-page");

        Label title = new Label("Create Account");
        title.getStyleClass().add("login-title");

        TextField usernameField = new TextField();
        usernameField.setPromptText("UserName");
        usernameField.setMaxWidth(200);
        usernameField.getStyleClass().add("login-field");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(200);
        emailField.getStyleClass().add("login-field");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(200);
        passwordField.getStyleClass().add("login-field");

        Button registerButton = new Button("Register");
        registerButton.getStyleClass().add("login-button");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #ff4444;"); // لون أحمر واضح للخطأ

        registerButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            // الفحوصات الأساسية (Validation)
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Please fill in all fields!");
                return;
            }
            if (username.length() < 3) {
                errorLabel.setText("Username must be at least 3 characters!");
                return;
            }
            if (!email.contains("@")) {
                errorLabel.setText("Please enter a valid email address!");
                return;
            }

            errorLabel.setText("Connecting to server...");

            // 2. محاولة إرسال البيانات للسيرفر
            try {
                apiService.register(username, email, password);

                System.out.println("✅ Registration Successful!");

                // بعد النجاح، بنطلعلوا تنبيه بسيط وبنرجعه لصفحة اللوجين
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Account created successfully! Please login.");
                alert.showAndWait();

                goToLogin.run(); // العودة للوجين

            } catch (Exception ex) {
                System.out.println("❌ Registration Failed: " + ex.getMessage());
                errorLabel.setText("Failed: " + ex.getMessage());
            }
        });

        Hyperlink loginLink = new Hyperlink("Already have an account? Login");
        loginLink.setOnAction(e -> goToLogin.run());

        getChildren().addAll(
                title, usernameField, emailField, passwordField, registerButton, errorLabel, loginLink
        );
    }
}