package com.app.frontend;

import com.app.frontend.components.AppToolbar;
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
    private ObservableList<Sidebar.NoteItem> items; // نقلناها لهون عشان نقدر نحدثها

    @Override
    public void init() throws Exception {
        apiService = new MockNoteApiService();
    }

    @Override
    public void start(Stage stage) {

        Sidebar sidebar = new Sidebar();
        Editor editor = new Editor();
        AppToolbar appToolbar = new AppToolbar();

        // 1. جلب البيانات عند التشغيل
        items = FXCollections.observableArrayList();
        refreshList(sidebar);

        sidebar.getNewBtn().setOnAction(e -> {
            editor.clear(); // تفريغ المحرر
            System.out.println("📝 جاهز لكتابة ملاحظة جديدة!");
        });

        sidebar.getSaveBtn().setOnAction(e -> {
            String title = editor.getTitle();
            String content = editor.getContent();

            if (!title.isEmpty()) { // التأكد إن العنوان مو فاضي
                apiService.createNote(1L, title, content); // الحفظ بالسيرفر الوهمي
                refreshList(sidebar); // تحديث القائمة الجانبية فوراً
                editor.clear(); // تفريغ المحرر بعد الحفظ
                System.out.println("✅ تم الحفظ بنجاح!");
            } else {
                System.out.println("⚠️ لا يمكن حفظ ملاحظة بدون عنوان!");
            }
        });

        sidebar.setNoteSelectionListener(noteId -> {
            List<NoteResponse> allNotes = apiService.getAllNotes();
            for (NoteResponse note : allNotes) {
                if (note.getId().toString().equals(noteId)) {
                    editor.setNote(note.getTitle(), note.getContent()); // عرضها في المحرر
                    System.out.println("📖 تم فتح: " + note.getTitle());
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

        Scene scene = new Scene (root, 900, 700); // كبرت الشاشة شوي لترتاح بالكتابة
        try {
            scene.getStylesheets().add(MainApp.class.getResource("/style.css").toExternalForm());
        } catch (Exception e) {}

        stage.setTitle("Notes Application");
        stage.setScene(scene);
        stage.show();
    }

    // دالة مساعدة لتحديث القائمة الجانبية بسلاسة
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