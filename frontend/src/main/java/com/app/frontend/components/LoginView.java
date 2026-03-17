package com.app.frontend.components;

import com.app.frontend.models.User;
import com.app.frontend.services.ApiService;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginView extends VBox {

    // استدعاء السيرفيس اللي بيحكي مع الباكند
    private final ApiService apiService = new ApiService();

    public LoginView(Runnable onLoginSuccess) {
        setSpacing(15);
        setAlignment(Pos.CENTER);

        this.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        this.getStyleClass().add("login-page");
        Label title = new Label("NoteTo");

        title.getStyleClass().add("login-title");

        TextField emailField = new TextField(); // سميته إيميل عشان الباكند بيطلب إيميل
        emailField.setPromptText("Email");
        emailField.getStyleClass().add("login-field");
        emailField.setMaxWidth(200);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.getStyleClass().add("login-field");
        passwordField.setMaxWidth(200);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;"); // عشان يطلع الإيرور باللون الأحمر

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("login-button");

        // 🚀 الأكشن الحقيقي للزر بعد الربط مع السيرفر
        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            if(email.isEmpty() || password.isEmpty()){
                errorLabel.setText("الرجاء إدخال الإيميل وكلمة المرور!");
                return;
            }
            errorLabel.setText(""); // تفريغ الخطأ القديم

            try {
                User loggedInUser = apiService.login(email, password);

                System.out.println("✅ Login Successful! Welcome: " + loggedInUser.getUsername());

                onLoginSuccess.run();

            } catch (Exception ex) {
                System.out.println("⚠️ فشل الدخول: " + ex.getMessage());
                errorLabel.setText("بيانات الدخول خاطئة!");
            }
        });

        Hyperlink registerLink = new Hyperlink("Create Account");

        registerLink.setOnAction(e -> {

            RegisterView registerView = new RegisterView(onLoginSuccess);

            if (getScene() != null) {
                getScene().setRoot(registerView);
            } else {
                System.out.println("⚠️ Scene is null, using stage reference if available");
            }
        });
        getChildren().addAll(
                title, emailField, passwordField, loginButton, errorLabel, registerLink
        );
    }
}