package com.app.frontend.components;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.geometry.Bounds;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
public class Sidebar {

    private BorderPane root;
    private TextField searchField;
    private ListView<NoteItem> listView;
    private NoteSelectionListener listener;
    private FilteredList<NoteItem> filteredNotes;
    private Editor editor;

    private Button newBtn;
    private Button saveBtn;
    private Button deleteBtn;

    private final String NORMAL_COLOR = "#7f8c8d";
    private final String HOVER_COLOR = "#FFFFFF";

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

    public Sidebar(Editor editor) {
        this.editor = editor;
        this.root = new BorderPane();
        buildCenter();
        setupSearchFilter();
    }

    public void setNoteSelectionListener(NoteSelectionListener listener) {
        this.listener = listener;
    }

    public Sidebar() {
        root = new BorderPane();
        buildTop();
        buildCenter();
        setupSearchFilter();
    }

    private void buildTop() {

        VBox topContainer = new VBox(10);
        topContainer.setPadding(new Insets(10, 10, 20, 10));
        root.setTop(topContainer);
    }

    private void buildCenter() {
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // // Settings button - enabled to open the "About" window
        FontAwesomeIconView settingsIcon = new FontAwesomeIconView(FontAwesomeIcon.GEAR);
        settingsIcon.setGlyphSize(26);
        Button settingsButton = new Button();
        settingsButton.setGraphic(settingsIcon);
        settingsButton.getStyleClass().add("sidebar-button");
        setIconHoverEffect(settingsButton, settingsIcon);
        settingsButton.setOnAction(e -> {
            if (editor != null) editor.showAboutApp();
        });

        Button noteListBtn = new Button();
        FontAwesomeIconView noteIcon = new FontAwesomeIconView(FontAwesomeIcon.LIST);
        noteIcon.setGlyphSize(26);
        noteListBtn.setGraphic(noteIcon);
        noteListBtn.getStyleClass().add("sidebar-button");
        setIconHoverEffect(noteListBtn, noteIcon);

        searchField = new TextField();
        searchField.setPromptText("Search");
        listView = new ListView<>();
        listView.setPrefHeight(200);

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
            NoteItem selectedNote = listView.getSelectionModel().getSelectedItem();
            if (selectedNote != null && listener != null) {
                listener.onNoteSelected(selectedNote.getId());
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

        //  New
        FontAwesomeIconView newIcon = new FontAwesomeIconView(FontAwesomeIcon.FILE_TEXT);
        newIcon.setGlyphSize(26);
        newIcon.getStyleClass().add("sidebar-icon");
        newBtn = createSidebarButton(FontAwesomeIcon.FILE_TEXT, "New Note");
        newBtn.setOnAction(e -> {
            if (editor != null) {
                editor.clear();
                listView.getSelectionModel().clearSelection();
            }
        });
        deleteBtn = createSidebarButton(FontAwesomeIcon.ERASER, "Delete");
        deleteBtn.setOnAction(e -> {
            if (editor != null && editor.showDeleteConfirmation()) {
                NoteItem selected = listView.getSelectionModel().getSelectedItem();

                if (selected != null) {
                    // 1.// Delete from the original data source (The secret is accessing the "Source")
                    if (filteredNotes != null && filteredNotes.getSource() instanceof ObservableList) {
                        ((ObservableList<NoteItem>) filteredNotes.getSource()).remove(selected);
                    } else {
                        // // Fallback solution if FilteredList is not active
                        listView.getItems().remove(selected);
                    }

                    // 2. // Clear the editor text to prevent it from remaining displayed after deletion
                    editor.clear();

                    // 3. // Show the success message required in your tasks
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setTitle("Success");
                    success.setHeaderText(null);
                    success.setContentText("Note deleted successfully!");
                    success.showAndWait();
                }
            }
        });
        // ----------------------------------------------

        Button shareBtn = createSidebarButton(FontAwesomeIcon.USER, "Share");
        shareBtn.setOnAction(e -> openSharePopup());

        //pop-up
        shareBtn.setOnAction(e -> openSharePopup());
        saveBtn = createSidebarButton(FontAwesomeIcon.SAVE, "Save");
        saveBtn.setOnAction(e -> {
            if (editor != null) editor.saveNote();
        });
        VBox buttonBar = new VBox(10, noteListBtn, newBtn, deleteBtn, saveBtn, shareBtn, spacer, settingsButton);
        buttonBar.setPadding(new Insets(10));
        root.setCenter(new VBox(15, noteContent, buttonBar));


        addFixedTooltip(noteListBtn, "Note List");
        addFixedTooltip(settingsButton, "About App");
    }

    private Button createSidebarButton(FontAwesomeIcon iconName, String tooltipText) {
        FontAwesomeIconView icon = new FontAwesomeIconView(iconName);
        icon.setGlyphSize(26);
        Button btn = new Button();
        btn.setGraphic(icon);
        btn.getStyleClass().add("sidebar-button");
        setIconHoverEffect(btn, icon);
        addFixedTooltip(btn, tooltipText);
        return btn;
    }

    private void setIconHoverEffect(Button button, FontAwesomeIconView icon) {
        icon.setFill(Color.web(NORMAL_COLOR));
        button.setOnMouseEntered(e -> icon.setFill(Color.web(HOVER_COLOR)));
        button.setOnMouseExited(e -> icon.setFill(Color.web(NORMAL_COLOR)));
    }


    //Pop-up
    private void openSharePopup() {
        Stage popupStage = new Stage();
        popupStage.setTitle("Share");

        popupStage.initOwner(root.getScene().getWindow());
        popupStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setStyle("-fx-background-color: #1e1e1e;");
        Label label = new Label("Share with:");
        label.setStyle("-fx-text-fill: white;");
        TextField userField = new TextField();
        userField.setPromptText("UserName");
        //Error message
        Label errorLabel = new Label();
        userField.textProperty().addListener((obs, oldVal, newVal) -> {
            errorLabel.setText("");
        });

        Button senBtn = new Button("Send");
        userField.setOnAction(e -> senBtn.fire());

        senBtn.setOnAction(e -> {
            String user = userField.getText();
            if (user == null || user.trim().isEmpty()) {
                errorLabel.setText("Please enter a user");

            } else {
                System.out.println("Shared with:" + user);
                popupStage.close();
            }
        });
        layout.getChildren().addAll(label, userField, errorLabel, senBtn);

        Scene scene = new Scene(layout, 300, 150);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == javafx.scene.input.KeyCode.ESCAPE) {
                popupStage.close();
            }
        });

        popupStage.setScene(scene);

        popupStage.centerOnScreen();
        popupStage.showAndWait();
    }

    private void addFixedTooltip(Button btn, String text) {
        Tooltip tooltip = new Tooltip(text);
        tooltip.setShowDelay(Duration.ZERO);
        btn.setOnMouseEntered(e -> {
            Bounds bounds = btn.localToScreen(btn.getBoundsInLocal());
            tooltip.show(btn, bounds.getMaxX() + 8, bounds.getMinY() + (bounds.getHeight() / 2));
        });
        btn.setOnMouseExited(e -> tooltip.hide());
    }


    public BorderPane getView() {
        return root;
    }

    public Button getNewBtn() {
        return newBtn;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public interface NoteSelectionListener {
        void onNoteSelected(String noteId);
    }


    public void bindNotes(ObservableList<NoteItem> notesFromLogic) {
        filteredNotes = new FilteredList<>(notesFromLogic, p -> true);
        listView.setItems(filteredNotes);
    }


    private void setupSearchFilter() {
        searchField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (filteredNotes == null) return;
            filteredNotes.setPredicate(note -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return note.toString().toLowerCase().contains(lowerCaseFilter);
            });
        });
    }
}