package com.app.frontend.components;

import com.app.frontend.models.Note;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Sidebar {
    private BorderPane root;
    private TextField searchField;
    private VBox notesListContainer;
    private Button newBtn;

    public Sidebar() {
        root = new BorderPane();

        root.getStyleClass().add("sidebar");

        buildTop();
        buildCenter();
        buildBottom();
    }

    private void buildTop() {
        Label title = new Label("My Notes");

        title.getStyleClass().add("app-title");

        searchField = new TextField();
        searchField.setPromptText("Search...");

        searchField.getStyleClass().add("search-field");

        VBox topContainer = new VBox(10);
        topContainer.getChildren().addAll(title, searchField);
        topContainer.setPadding(new Insets(15));

        root.setTop(topContainer);
    }

    private void buildCenter() {

        newBtn = new Button("New Note +");
        newBtn.setMaxWidth(Double.MAX_VALUE);

        newBtn.getStyleClass().add("new-button");

        notesListContainer = new VBox(10);
        notesListContainer.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(notesListContainer);
        scrollPane.setFitToWidth(true);

        scrollPane.getStyleClass().add("notes-scroll-pane");

        VBox centerBox = new VBox(15, newBtn, scrollPane);
        centerBox.setPadding(new Insets(10));

        root.setCenter(centerBox);


        addDummyData();
    }

    private void addDummyData() {
        NoteCard cardRenderer = new NoteCard();


        notesListContainer.getChildren().add(cardRenderer.createCard(new Note("Project Idea : Finish the JavaFX app UI.")));
        notesListContainer.getChildren().add(cardRenderer.createCard(new Note("Shopping :Milk, Bread, Coffee.")));
        notesListContainer.getChildren().add(cardRenderer.createCard(new Note("Gym:Leg day at 5 PM.")));
    }

    private void buildBottom() {
        Button settingsButton = new Button("Settings");
        settingsButton.setMaxWidth(Double.MAX_VALUE);

        settingsButton.getStyleClass().add("settings-button");

        VBox bottomBox = new VBox(settingsButton);
        bottomBox.setPadding(new Insets(15));

        root.setBottom(bottomBox);
    }

    public BorderPane getView() {
        return root;
    }

    public Button getNewButton() {
        return newBtn;
    }
}