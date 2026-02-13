package com.app.frontend.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class AppToolbar extends HBox {

    private Button newBtn;
    private Button saveBtn;
    private Button deleteBtn;

    public AppToolbar() {
        this.getStyleClass().add("app-toolbar");

        newBtn = new Button("New");
        newBtn.getStyleClass().add("toolbar-button");

        saveBtn = new Button("Save");
        saveBtn.getStyleClass().add("toolbar-button");
        saveBtn.getStyleClass().add("save-button");

        deleteBtn = new Button("Delete");
        deleteBtn.getStyleClass().add("toolbar-button");
        deleteBtn.getStyleClass().add("delete-button");

        this.getChildren().addAll(newBtn, saveBtn, deleteBtn);

        this.setSpacing(10);
        this.setPadding(new Insets(10));
    }

    public Button getNewBtn(){
        return newBtn;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }
}