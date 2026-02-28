package com.app.frontend.components;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.InlineCssTextArea;

import java.util.Optional;

public class Editor {

    private Label wordCountLabel;
    private InlineCssTextArea contentArea;
    private VBox container;
    private TextField titleField;
    private ScrollPane scrollPane;

    public Editor() {

        // TITLE
        this.titleField = new TextField();
        this.titleField.setPromptText("Note Title...");
        this.titleField.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-background-color: transparent;" +
                        "-fx-prompt-text-fill: #bdc3c7;" +
                        "-fx-border-color: #333333;" +
                        "-fx-border-width: 0 0 2 0;" +
                        "-fx-padding: 0 0 10 0;"
        );

        // ✅ RICHTEXT AREA
        this.contentArea = new InlineCssTextArea();
        this.contentArea.setWrapText(true);

        // WORD COUNT
        this.contentArea.textProperty().addListener((obs, oldText, newText) -> {
            int words = newText.trim().isEmpty() ? 0 : newText.trim().split("\\s+").length;
            wordCountLabel.setText("words: " + words);
        });

        this.contentArea.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-fill: white;" +
                        "-fx-background-color: #1e1e1e;"
        );

        // ENTER → "-"
        this.contentArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Platform.runLater(() -> {
                    contentArea.insertText(contentArea.getCaretPosition(), "- ");
                });
            }
        });

        // ✅ RichText scroll
        VirtualizedScrollPane<InlineCssTextArea> richScroll =
                new VirtualizedScrollPane<>(contentArea);

        // CONTAINER
        this.container = new VBox(15);
        this.container.getChildren().addAll(titleField, richScroll);
        this.container.setPadding(new Insets(30));
        this.container.setStyle(
                "-fx-background-color: #1e1e1e;" +
                        "-fx-border-color: #ffffff;" +
                        "-fx-border-width: 0 0 0 2;"
        );

        // WORD LABEL
        this.wordCountLabel = new Label("words: 0");
        this.wordCountLabel.setStyle(
                "-fx-text-fill: #7f8c8d;" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;"
        );

        // ROOT
        javafx.scene.layout.StackPane root = new javafx.scene.layout.StackPane();
        root.getChildren().addAll(container, wordCountLabel);

        javafx.scene.layout.StackPane.setAlignment(wordCountLabel, Pos.BOTTOM_RIGHT);
        javafx.scene.layout.StackPane.setMargin(wordCountLabel, new Insets(0, 5, 5, 0));

        // SCROLL
        this.scrollPane = new ScrollPane(root);
        this.scrollPane.setFitToWidth(true);
        this.scrollPane.setFitToHeight(true);
        this.scrollPane.setStyle(
                "-fx-background: #1e1e1e;" +
                        "-fx-border-color: transparent;" +
                        "-fx-viewport-background: #1e1e1e;"
        );
    }

    // --- GETTERS ---

    public ScrollPane getView() {
        return scrollPane;
    }

    public String getTitle() {
        return titleField.getText();
    }

    public String getContent() {
        return contentArea.getText();
    }

    public InlineCssTextArea getTextArea() { // ✅ önemli
        return contentArea;
    }

    // --- SETTERS ---

    public void setNote(String title, String content) {
        titleField.setText(title);
        contentArea.replaceText(content); // ✅ değişti
    }

    public void clear() {
        titleField.clear();
        contentArea.replaceText("");
        wordCountLabel.setText("words: 0");
    }

    // --- ALERTS ---

    public void showSaveAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Note saved successfully!");
        alert.showAndWait();
    }

    public boolean showDeleteConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public void showAboutApp() {
        Stage aboutStage = new Stage();
        aboutStage.setTitle("About Online Notes");

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #1e1e1e; -fx-padding: 30;");

        Label titleLabel = new Label("Online Notes Application");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");

        Label teamLabel = new Label("Frontend:");
        teamLabel.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 14px; -fx-font-weight: bold;");

        Label membersLabel = new Label(
                "1. Oubay Ajlouni\n2. Hassan Ibrahim\n3. Ale isa\n4. Sedra\n5. Liwaa\n6. Hadi\n7. Rayan"
        );
        membersLabel.setStyle("-fx-text-fill: #ecf0f1;");

        layout.getChildren().addAll(titleLabel, teamLabel, membersLabel);

        Scene scene = new Scene(layout, 350, 450);
        aboutStage.setScene(scene);
        aboutStage.show();
    }

    public void saveNote() {
        if (titleField.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Cannot save a note without a title!");
            alert.showAndWait();
        } else {
            showSaveAlert();
        }
    }

}