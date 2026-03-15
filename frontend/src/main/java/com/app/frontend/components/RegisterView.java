package com.app.frontend.components;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RegisterView extends VBox {

    public RegisterView(Runnable goToLogin) {
        setSpacing(15);
        setAlignment(Pos.CENTER);

        this.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());


        this.getStyleClass().add("login-page");

        Label title=new Label("Create Account");
        TextField usernameField=new TextField();
        usernameField.setPromptText("UserName");
        usernameField.setMaxWidth(200);
        TextField emailField=new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(200);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(200);

        usernameField.getStyleClass().add("login-field");
        emailField.getStyleClass().add("login-field");
        passwordField.getStyleClass().add("login-field");

        Button registerButton = new Button("Register");

        registerButton.getStyleClass().add("login-button");
        Label errorLabel = new Label();
        registerButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if(username.isEmpty() || email.isEmpty() || password.isEmpty()){
                errorLabel.setText("Please fill in all fields!");
                return;
            }

            if(username.length() < 3){
                errorLabel.setText("Username must be at least 3 characters!");
                return;
            }

            if(!email.contains("@") || !email.contains(".")){
                errorLabel.setText("Please enter a valid email address!");
                return;
            }

            if(password.length() < 8){
                errorLabel.setText("Password must be at least 8 characters!");
                return;
            }

            errorLabel.setText("");

            System.out.println("Register attempt:");
            System.out.println("Username: " + username);
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);

        });

        Hyperlink loginLink = new Hyperlink("Already have an account? Login");

        loginLink.setOnAction(e -> {

            LoginView loginView = new LoginView(goToLogin);

            getScene().setRoot(loginView);

        });

        getChildren().addAll(
                title,usernameField,emailField,passwordField,registerButton,errorLabel,loginLink
        );


    }
}
