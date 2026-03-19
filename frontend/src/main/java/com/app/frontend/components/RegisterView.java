package com.app.frontend.components;

import com.app.frontend.services.ApiService;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RegisterView extends VBox {

    private final ApiService apiService = new ApiService();
    // تعريف الرابط كـ متغير كلاس عشان نقدر نعمله Getter
    private final Hyperlink loginLink = new Hyperlink("Already have an account? Login");

    public RegisterView(Runnable onRegisterSuccess) {
        setSpacing(15);
        setAlignment(Pos.CENTER);

        try {
            this.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        } catch (Exception e) {}

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
        errorLabel.setStyle("-fx-text-fill: #ff4444;");

        registerButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Please fill in all fields!");
                return;
            }

            errorLabel.setText("Connecting to server...");

            try {
                apiService.register(username, email, password);

                // ✅ في حالة النجاح فقط نفتح التطبيق
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Account created successfully!");
                    alert.showAndWait();
                    onRegisterSuccess.run();
                });

            } catch (Exception ex) {
                errorLabel.setText("Failed: " + ex.getMessage());
            }
        });

        // ملاحظة: ما حطينا SetOnAction هون للرابط، رح نخليه للـ MainApp
        getChildren().addAll(
                title, usernameField, emailField, passwordField, registerButton, errorLabel, loginLink
        );
    }

    // 🚀 هاد هو "المفتاح" اللي نسيناه
    public Hyperlink getLoginLink() {
        return loginLink;
    }
}