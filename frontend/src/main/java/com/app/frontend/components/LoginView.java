package com.app.frontend.components;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginView extends VBox {

public  LoginView(Runnable onLoginSuccess) {
    setSpacing(15);
    setAlignment(Pos.CENTER);


    Label title=new Label("NoteTo");

    TextField usernameField=new TextField();
    usernameField.setPromptText("UserName");
    usernameField.setMaxWidth(200);

    PasswordField passwordField=new PasswordField();
    passwordField.setPromptText("Password");
    passwordField.setMaxWidth(200);

    Button loginButton=new Button("Login");

    Label errorLabel = new Label();
    loginButton.setOnAction(e -> {

        String username = usernameField.getText();
        String password = passwordField.getText();

        if(username.isEmpty() || password.isEmpty()){
            errorLabel.setText("Please enter username and password!");
            return;
        }
        errorLabel.setText("");

        System.out.println("Login attempt:");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        onLoginSuccess.run();

    });

    Hyperlink registerLink=new Hyperlink("Create Account");

    registerLink.setOnAction(e -> {

        RegisterView registerView = new RegisterView(() -> {
            LoginView loginView = new LoginView(onLoginSuccess);
            getScene().setRoot(loginView);
        });

        getScene().setRoot(registerView);

    });
     getChildren().addAll(
             title,usernameField,passwordField,loginButton,errorLabel,registerLink
     );
}
}
