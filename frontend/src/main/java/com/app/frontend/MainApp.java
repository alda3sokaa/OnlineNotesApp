package com.app.frontend;

import com.app.frontend.components.AppToolbar;

import com.app.frontend.components.Editor;
import com.app.frontend.components.LoginView;
import com.app.frontend.components.Sidebar;
import com.app.frontend.components.Editor;
import com.app.frontend.models.NoteResponse;
import com.app.frontend.services.MockNoteApiService;
import com.app.frontend.services.NoteApiService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.util.List;

public class MainApp extends Application {

    private NoteApiService apiService;
    private ObservableList<Sidebar.NoteItem> items;

    @Override
    public void init() throws Exception {
        apiService = new NoteApiService();
    }
    @Override
    public void start(Stage stage) {


        LoginView loginView = new LoginView(() -> showMainApp(stage));
        Scene scene = new Scene(loginView, 400, 400);

        try {
            Image icon = new Image(getClass().getResourceAsStream("/NoteAppIcon.png"));
            stage.getIcons().add(icon);
        } catch (Exception e) {}

        stage.setTitle("NoteTo");
        stage.setScene(scene);
        stage.show();
    }


    public void showMainApp(Stage stage) {


        Editor editor = new Editor();
        Sidebar sidebar = new Sidebar(editor);
        AppToolbar appToolbar = new AppToolbar(editor);


        items = FXCollections.observableArrayList();
        refreshList(sidebar);

        sidebar.getNewBtn().setOnAction(e -> {
            editor.clear();
            System.out.println("Ready to write a new note !!");
        });

        sidebar.getSaveBtn().setOnAction(e -> {
            String title = editor.getTitle();
            String content = editor.getContent();

            if (!title.isEmpty()) {
                apiService.createNote(1L, title, content);
                refreshList(sidebar);
                editor.clear();
                System.out.println("Saved successfully!");
            } else {
                System.out.println("Cannot save a note without a title!");
            }
        });

        sidebar.setNoteSelectionListener(noteId -> {
            List<NoteResponse> allNotes = apiService.getAllNotes();
            for (NoteResponse note : allNotes) {
                if (note.getId().toString().equals(noteId)) {
                    editor.setNote(note.getTitle(), note.getContent());
                    System.out.println("Opened: " + note.getTitle());
                    break;
                }
            }
        });

        // UI DESIGN //
        BorderPane root = new BorderPane();
        root.setTop(appToolbar);
        root.setLeft(sidebar.getView());
        root.setCenter(editor.getView());

        // APPLICATION ICON //
        try {
            Image icon = new Image(getClass().getResourceAsStream("/NoteAppIcon.png"));
            stage.getIcons().add(icon);
        } catch (Exception e) {}

        Scene scene = new Scene (root, 900, 700);
        try {
            scene.getStylesheets().add(MainApp.class.getResource("/style.css").toExternalForm());
        } catch (Exception e) {}

        stage.setTitle("NoteTo");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    private void refreshList(Sidebar sidebar) {
        items.clear();
        for (NoteResponse note : apiService.getAllNotes()) {
            items.add(new Sidebar.NoteItem(note.getId().toString(), note.getTitle()));
        }
        sidebar.bindNotes(items);
    }

    public static void main(String[] args) {
        launch(args);
    }
}