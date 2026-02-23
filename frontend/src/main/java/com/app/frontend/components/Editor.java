package com.app.frontend.components;

import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Editor {
    private VBox view;
    private TextField titleField;
    private TextArea contentArea;

    public Editor() {
        titleField = new TextField();
        titleField.setPromptText("Note Title ... ");

        titleField.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px; -fx-text-fill: #a8a4a4; -fx-prompt-text-fill: #aaaaaa;");

        contentArea = new TextArea();
        contentArea.setPromptText("Write your note ... ");
        contentArea.setWrapText(true);
        contentArea.setPrefHeight(500);

        contentArea.setStyle("-fx-font-size: 14px; -fx-text-fill: #ffffff; -fx-prompt-text-fill: #aaaaaa; -fx-control-inner-background: #2b2b2b;");

        view = new VBox(15, titleField, contentArea);
        view.setPadding(new Insets(20));
        // توحيد لون الخلفية للمحرر كامل
        view.setStyle("-fx-background-color: #2b2b2b;");
    }

    public VBox getView() { return view; }

    // 👇 هدول الدوال اللي رح يخلونا نتحكم بالنص من برا المحرر
    public String getTitle() { return titleField.getText(); }
    public String getContent() { return contentArea.getText(); }

    public void setNote(String title, String content) {
        titleField.setText(title);
        contentArea.setText(content);
    }

    public void clear() {
        titleField.clear();
        contentArea.clear();
    }
}