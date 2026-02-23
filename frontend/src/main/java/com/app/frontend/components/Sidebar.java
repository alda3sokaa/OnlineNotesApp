package com.app.frontend.components;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.geometry.Bounds;
import javafx.util.Duration;

public class Sidebar {

    public static class NoteItem {
        private String id;
        private String title;

        public NoteItem(String id, String title) {
            this.id = id;
            this.title = title;
        }
        public String getId() {
            return id;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    private BorderPane root;
    private TextField searchField;
    private VBox notesListContainer;
    private ListView<NoteItem> listView;
    private NoteSelectionListener listener;

    // 👇 1. طلعنا الأزرار لفوق عشان MainApp يقدر يوصلهم 👇
    private Button newBtn;
    private Button saveBtn;
    private Button deleteBtn;

    public void setNoteSelectionListener(NoteSelectionListener listener) {
        this.listener = listener;
    }

    public Sidebar(){
        root = new BorderPane();
        buildTop();
        buildCenter();
    }

    private void buildTop() {

        VBox topContainer = new VBox(10);
        topContainer.setPadding(new Insets(10, 10, 20, 10));
        root.setTop(topContainer);
    }

    private void buildCenter() {
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        FontAwesomeIconView settingsIcon = new FontAwesomeIconView(FontAwesomeIcon.GEAR);
        settingsIcon.setGlyphSize(26);
        settingsIcon.getStyleClass().add("sidebar-icon");

        Button settingsButton = new Button();
        settingsButton.setGraphic(settingsIcon);
        settingsButton.getStyleClass().add("sidebar-button");

        Button noteListBtn = new Button();
        FontAwesomeIconView noteIcon = new FontAwesomeIconView(FontAwesomeIcon.LIST);
        noteIcon.setGlyphSize(26);
        noteIcon.getStyleClass().add("sidebar-icon");

        noteListBtn.setGraphic(noteIcon);
        noteListBtn.getStyleClass().add("sidebar-button");

        searchField = new TextField();
        searchField.setPromptText("Search");

        listView = new ListView<>();
        listView.setPrefHeight(200);

        // الربط مع كلاس ريان (NoteCard)
        listView.setCellFactory(param -> new ListCell<NoteItem>() {
            @Override
            protected void updateItem(NoteItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(new NoteCard(item));
                }
            }
        });

        listView.setOnMouseClicked(event -> {
            NoteItem SelectedNote = listView.getSelectionModel().getSelectedItem();
            if(SelectedNote != null && listener != null) {
                listener.onNoteSelected(SelectedNote.getId());
            }
        });

        VBox noteContent = new VBox(10, searchField, listView);
        noteContent.setPadding(new Insets(10));

        noteContent.setVisible(false);
        noteContent.setManaged(false);

        noteListBtn.setOnAction(e -> {
            boolean isVisible = noteContent.isVisible();
            noteContent.setVisible(!isVisible);
            noteContent.setManaged(!isVisible);
        });

        // زر New
        FontAwesomeIconView newIcon = new FontAwesomeIconView(FontAwesomeIcon.FILE_TEXT);
        newIcon.setGlyphSize(26);
        newIcon.getStyleClass().add("sidebar-icon");
        newBtn = new Button(); // 👈 لاحظ شلنا كلمة Button من هون
        newBtn.setGraphic(newIcon);
        newBtn.getStyleClass().add("sidebar-button");

        // زر Save
        FontAwesomeIconView saveIcon = new FontAwesomeIconView(FontAwesomeIcon.SAVE);
        saveIcon.setGlyphSize(26);
        saveIcon.getStyleClass().add("sidebar-icon");
        saveBtn = new Button(); // 👈 وهون كمان
        saveBtn.setGraphic(saveIcon);
        saveBtn.getStyleClass().add("sidebar-button");

        // زر Delete
        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.ERASER);
        deleteIcon.setGlyphSize(26);
        deleteIcon.getStyleClass().add("sidebar-icon");
        deleteBtn = new Button(); // 👈 وهون كمان
        deleteBtn.setGraphic(deleteIcon);
        deleteBtn.getStyleClass().add("sidebar-button");

        // زر Share
        FontAwesomeIconView shareIcon = new FontAwesomeIconView(FontAwesomeIcon.USER);
        shareIcon.setGlyphSize(26);
        shareIcon.getStyleClass().add("sidebar-icon");
        Button shareBtn = new Button();
        shareBtn.setGraphic(shareIcon);
        shareBtn.getStyleClass().add("sidebar-button");

        VBox buttonBar = new VBox(10, noteListBtn, newBtn, deleteBtn, saveBtn, shareBtn, spacer, settingsButton);
        buttonBar.setPadding(new Insets(10));

        VBox centerBox = new VBox(15);
        centerBox.getChildren().addAll(noteContent, buttonBar);
        root.setCenter(centerBox);

        addFixedTooltip(noteListBtn, "Note List");
        addFixedTooltip(deleteBtn, "Delete");
        addFixedTooltip(saveBtn, "Save");
        addFixedTooltip(newBtn, "New");
        addFixedTooltip(shareBtn, "Share");
        addFixedTooltip(settingsButton, "Settings");
    }

    private void addFixedTooltip(Button btn, String text) {
        Tooltip tooltip = new Tooltip(text);
        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setHideDelay(Duration.ZERO);
        tooltip.setShowDuration(Duration.INDEFINITE);

        btn.setOnMouseEntered(e -> {
            Bounds bounds = btn.localToScreen(btn.getBoundsInLocal());
            double x = bounds.getMaxX() + 8;
            double y = bounds.getMinY() + (bounds.getHeight() / 2);
            tooltip.show(btn, x, y);
        });

        btn.setOnMouseExited(e -> tooltip.hide());
    }

    public BorderPane getView(){ return root; }
    public VBox getNotesListContainer(){ return notesListContainer; }
    public TextField getSearchField(){ return searchField; }

    // 👇 2. هدول الـ Getters اللي رح يستخدمهم MainApp عشان يبرمج الأزرار 👇
    public Button getNewBtn() { return newBtn; }
    public Button getSaveBtn() { return saveBtn; }
    public Button getDeleteBtn() { return deleteBtn; }

    public interface NoteSelectionListener{
        void onNoteSelected(String noteId);
    }

    public void bindNotes(ObservableList<NoteItem> notesFromLogic){
        listView.setItems(notesFromLogic);
    }
}