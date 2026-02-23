
package com.app.frontend.components;

import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyCode;
import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;

public class Editor {
    private TextArea contentArea;
    private ScrollPane scrollPane;
    private VBox container; //notlarin yazilacagi yer
    private TextField titleField; //konu basligin yazilacagi yer

    public Editor() {
        this.titleField = new TextField();
        this.titleField.setPromptText("Subject title");
        this.titleField.setStyle("-fx-font-size: 28px;" +
                "-fx-text-fill: white;" +
                "-fx-background-color: transparent;" +
                "-fx-border-color: transparent;" +
                "-fx-focus-color:transparent;" +
                "-fx-faint-focus-color: transparent;"
                );

        this.contentArea = new TextArea();
        this.contentArea.setWrapText(true);
        this.contentArea.setStyle("-fx-font-size: 18px;" +
                "-fx-text-fill: white;" +
                "-fx-background-color: transparent;" +
                "-fx-control-inner-background: transparent;" +
                "-fx-background-insets: 0;" +
                "-fx-background-radius: 0;" +
                "-fx-border-color: transparent;" +
                "-fx-focus-color:transparent;" +
                "-fx-faint-focus-color: transparent;"
        );

        this.contentArea.setPrefHeight(1000);

        this.contentArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Platform.runLater(() -> {
                    int caretPosition = contentArea.getCaretPosition();
                    contentArea.insertText(caretPosition, "- ");
                });
            }
        });

        this.container = new VBox(10);
        this.container.getChildren().addAll(titleField, contentArea);
        this.container.setPadding(new Insets(30));
        this.container.setStyle("-fx-background-color:#1e1e1e;");

        this.scrollPane = new ScrollPane(container);
        this.scrollPane.setFitToWidth(true);
        this.scrollPane.setFitToHeight(true);
        this.scrollPane.setStyle("-fx-background-color: #1e1e1e;" +
                " -fx-background:#1e1e1e;" +
                " -fx-border-color: transparent;" +
                "-fx-viewport-background: #1e1e1e"
        );
    }

    public ScrollPane getView() {
        return scrollPane;
    }


}
