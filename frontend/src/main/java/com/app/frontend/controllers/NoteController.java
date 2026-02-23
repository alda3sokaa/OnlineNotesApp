package com.app.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class NoteController {

    @FXML private Button btnNew;
    @FXML private Button btnSave;
    @FXML private Button btnDelete;

    private boolean noteActive = false;

    @FXML
    public void initialize() {
        btnNew.setOnAction(e -> handleNew());
        btnSave.setOnAction(e -> handleSave());
        btnDelete.setOnAction(e -> handleDelete());

        updateButtons();
    }

    private void handleNew() {
        clearForm();
        noteActive = true;
        updateButtons();
    }

    private void handleSave() {
        saveNote();
        noteActive = false;
        updateButtons();
    }

    private void handleDelete() {
        deleteNote();
        clearForm();
        noteActive = false;
        updateButtons();
    }

    private void updateButtons() {
        btnSave.setDisable(!noteActive);
        btnDelete.setDisable(!noteActive);
    }

    private void clearForm() {}
    private void saveNote() {}
    private void deleteNote() {}
}