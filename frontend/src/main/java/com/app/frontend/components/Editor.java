package com.app.frontend.components;

import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyCode;
import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;

public class Editor {
    private javafx.scene.control.Label wordCountLabel;
    private TextArea contentArea;
    private ScrollPane scrollPane;
    private VBox container;
    private TextField titleField;

    public Editor() {
        // 1. Address field setup (white and gray style)
        //Gray underline to separate the title and content
        this.titleField = new TextField();
        this.titleField.setPromptText("Note Title...");
        this.titleField.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #ffffff;" + // Comfortable dark navy text color
                        "-fx-background-color: transparent;" +
                        "-fx-prompt-text-fill: #bdc3c7;" +
                        "-fx-border-color: #333333;" +  //White font color
                        "-fx-border-width: 0 0 2 0;" +
                        "-fx-padding: 0 0 10 0;"
        );

        // 2. Content area setup (White style + Auto-bullet feature)
        this.contentArea = new TextArea();
        this.contentArea.setWrapText(true);
        this.contentArea.setPrefHeight(1000);
        this.contentArea.textProperty().addListener((obs, oldText, newText) -> {
            int words = newText.trim() .isEmpty() ? 0 : newText.trim() .split("\\s+").length;
            wordCountLabel.setText( "words:" +words );
        });
        this.contentArea.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-control-inner-background: #1e1e1e;" +
                        "-fx-background-color: #1e1e1e;" +
                        "-fx-border-color: transparent;" +
                        "-fx-focus-color: transparent;" +
                        "-fx-faint-focus-color: transparent;"
        );

        //  Auto-insert '-' when pressing Enter
        this.contentArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Platform.runLater(() -> {
                    int caretPosition = contentArea.getCaretPosition();
                    contentArea.insertText(caretPosition, "- ");
                });
            }
        });

        // 3. Inner Container (VBox)
        this.container = new VBox(15);
        this.container.getChildren().addAll(titleField, contentArea);
        this.container.setPadding(new Insets(30));
        this.container.setStyle("-fx-background-color: #1e1e1e;" +
                "-fx-border-color: #fffff;" + //White vertical font color
                "-fx-border-width: 0 0 0 2;");

        // 4. External structure ScrollPane
        this.scrollPane = new ScrollPane(container);
        this.scrollPane.setFitToWidth(true);
        this.scrollPane.setFitToHeight(true);
        this.scrollPane.setStyle(
                "-fx-background: #1e1e1e;" + // To ensure the background color behind the VBox
                        "-fx-border-color: transparent;" + //Hide the outer borders of the ScrollPane
                        "-fx-viewport-background: #1e1e1e;"
        );

        this.wordCountLabel = new javafx.scene.control.Label(" words : 0 ");
        this.wordCountLabel.setStyle(
                "-fx-text-fill: #7f8c8d;" +          // Set the text color to a calm gray to match the new design
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-color: transparent;" + // Hide the rectangle (make the background transparent)
                        "-fx-border-color: transparent;" +     // Hide borders
                        "-fx-padding: 5;"
        );
        //(Recommended)
        javafx.scene.layout.StackPane root = new javafx.scene.layout.StackPane();

        root.getChildren().addAll(container,wordCountLabel);


        javafx.geometry.Pos position = Pos.BOTTOM_RIGHT;
        javafx.scene.layout.StackPane.setAlignment(wordCountLabel,position);

        javafx.scene.layout.StackPane.setMargin(wordCountLabel, new javafx.geometry.Insets(0, 5, 5, 0));
         this.scrollPane.setContent(root);

    }

    // // --- Logic Binding Methods (Getters & Setters) ---

    public ScrollPane getView() {
        return scrollPane;
    }

    public String getTitle() {
        return titleField.getText();
    }

    public String getContent() {
        return contentArea.getText();
    }

    public void setNote(String title, String content) {
        titleField.setText(title);
        contentArea.setText(content);
    }

    // 2. Update clear method to include word counter
    public void clear() {
        titleField.clear();
        contentArea.clear();
        wordCountLabel.setText("words: 0");
    }

    public void showSaveAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Note saved successfully!"); // // Success Message
        alert.showAndWait();
    }

    public boolean showDeleteConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("This action cannot be undone."); // // Confirmation Message

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public void showAboutApp() {
        Stage aboutStage = new Stage();
        aboutStage.setTitle("About Online Notes");

        // // Layout Configuration
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #1e1e1e; -fx-padding: 30; -fx-border-color: #333333; -fx-border-width: 1;");

        // // Window Title
        Label titleLabel = new Label("Online Notes Application");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");

        // // Team Members
        Label teamLabel = new Label("Frontend:");
        teamLabel.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 14px; -fx-font-weight: bold;");

        Label membersLabel = new Label("1. Oubay Ajlouni \n2. Hassan Ibrahim\n3. Ale isa\n4. Sedra Sheıxh Alard\n5. Liwaa Alnassar\n6. Hadi\n7. Rayan ");
        membersLabel.setStyle("-fx-text-fill: #ecf0f1; -fx-font-size: 14px; -fx-line-spacing: 5;");
        membersLabel.setAlignment(Pos.CENTER);

        // // Add elements to the container
        layout.getChildren().addAll(titleLabel, teamLabel, membersLabel);

        // // Scene setup and window display
        Scene scene = new Scene(layout, 350, 450);
        aboutStage.setScene(scene);

        // aboutStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        aboutStage.show();
    }
    // 1. Add the method called by the Sidebar's Save button.
    public void saveNote() {
        // Check if the title exists before saving.
        if (titleField.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Cannot save a note without a title! ");
            alert.showAndWait();
        } else {
            showSaveAlert();
        }
    }

}