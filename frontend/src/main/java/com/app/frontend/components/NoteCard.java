package com.app.frontend.components;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class NoteCard extends VBox {

    public NoteCard(Sidebar.NoteItem item) {
        // 1. المساحات الداخلية والخارجية
        this.setPadding(new Insets(12, 15, 12, 15));
        this.setSpacing(6);


        String defaultStyle = "-fx-background-color: #2b2b2b; " +
                "-fx-background-radius: #2a2a2a; " +
                "-fx-border-color: #a8a4a4; " +
                "-fx-border-radius: 8; " +
                "-fx-border-width: 1;";


        String hoverStyle = "-fx-background-color: #3a3a3a; " +
                "-fx-background-radius: 8; " +
                "-fx-border-color: #5a5a5a; " +
                "-fx-border-radius: 8; " +
                "-fx-border-width: 1;";


        this.setStyle(defaultStyle);
        this.setCursor(Cursor.HAND);


        this.setOnMouseEntered(e -> this.setStyle(hoverStyle));
        this.setOnMouseExited(e -> this.setStyle(defaultStyle));


        Label titleLabel = new Label(item.toString());
        titleLabel.setStyle("-fx-text-fill: #fafafa; -fx-font-weight: bold; -fx-font-size: 15px;");


        Label idLabel = new Label("ملاحظة رقم: " + item.getId());
        idLabel.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 12px;");

        this.getChildren().addAll(titleLabel, idLabel);
    }
}