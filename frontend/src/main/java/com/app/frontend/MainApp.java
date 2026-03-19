package com.app.frontend;

import com.app.frontend.components.*;
import com.app.frontend.models.NoteResponse;
import com.app.frontend.services.NoteApiService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.util.List;

public class MainApp extends Application {

    private NoteApiService apiService;
    private ObservableList<Sidebar.NoteItem> items;

    @Override
    public void init() throws Exception {
        this.apiService = new NoteApiService();
    }

    @Override
    public void start(Stage stage) {
        Runnable onAuthSuccess = () -> showMainApp(stage);

        try {
            Image icon = new Image(getClass().getResourceAsStream("/NoteAppIcon.png"));
            if (!stage.getIcons().contains(icon)) {
                stage.getIcons().add(icon);
            }
        } catch (Exception e){}
        showLoginScreen(stage, onAuthSuccess);

    }

    private void showLoginScreen(Stage stage, Runnable onAuthSuccess) {
        LoginView loginView = new LoginView(onAuthSuccess);

        loginView.getRegisterLink().setOnAction(e -> showRegisterScreen(stage, onAuthSuccess));

        Scene scene = new Scene(loginView, 400, 400);
        stage.setScene(scene);
        stage.setTitle("NoteTo - Login");
        stage.show();
    }

    private void showRegisterScreen(Stage stage, Runnable onAuthSuccess) {
        RegisterView registerView = new RegisterView(onAuthSuccess);

        registerView.getLoginLink().setOnAction(e -> showLoginScreen(stage, onAuthSuccess));

        Scene scene = new Scene(registerView, 400, 450);
        stage.setScene(scene);
        stage.setTitle("NoteTo - Register");
    }

    public void showMainApp(Stage stage) {
        Editor editor = new Editor();
        Sidebar sidebar = new Sidebar(editor);
        AppToolbar appToolbar = new AppToolbar(editor);

        items = FXCollections.observableArrayList();
        refreshList(sidebar);

        sidebar.getNewBtn().setOnAction(e -> editor.clear());

        sidebar.getSaveBtn().setOnAction(e -> {
            String title = editor.getTitle();
            String content = editor.getContent();
            if (!title.isEmpty()) {
                apiService.createNote(1L, title, content); // رح نعدل 1L لاحقاً
                refreshList(sidebar);
            }
        });

        sidebar.setNoteSelectionListener(noteId -> {
            List<NoteResponse> allNotes = apiService.getAllNotes();
            for (NoteResponse note : allNotes) {
                if (note.getId().toString().equals(noteId)) {
                    editor.setNote(note.getTitle(), note.getContent());
                    break;
                }
            }
        });

        BorderPane root = new BorderPane();
        root.setTop(appToolbar);
        root.setLeft(sidebar.getView());
        root.setCenter(editor.getView());

        Scene scene = new Scene(root, 1000, 700);
        try {
            Image icon = new Image(getClass().getResourceAsStream("/NoteAppIcon.png"));
            if (!stage.getIcons().contains(icon)) {
                stage.getIcons().add(icon);
            }
        } catch (Exception e){}

        try {
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        } catch (Exception e) {}

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("NoteTo - Your Notes");
    }

    private void refreshList(Sidebar sidebar) {
        new Thread(() -> {
            List<NoteResponse> notesFromServer = apiService.getAllNotes();
            Platform.runLater(() -> {
                items.clear();
                for (NoteResponse note : notesFromServer) {
                    items.add(new Sidebar.NoteItem(note.getId().toString(), note.getTitle()));
                }
                sidebar.bindNotes(items);
            });
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}