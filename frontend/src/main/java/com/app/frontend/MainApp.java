package com.app.frontend;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.app.frontend.components.Sidebar;
import com.app.frontend.components.Editor;
import javafx.scene.layout.BorderPane;
import java.util.Objects;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        // 1.OBJECTS //
        Sidebar sidebar = new Sidebar();
        Editor editor = new Editor();

        // UI DESIGN //
        BorderPane root = new BorderPane();
        root.setLeft(sidebar.getView());
        root.setCenter(editor.getView());

        // APPLICATION ICON //
        Image icon = new Image(getClass().getResourceAsStream("/NoteAppIcon.png"));
        stage.getIcons().add(icon);
        ImageView imageView = new ImageView(icon);

        // STAGE//
        Scene scene = new Scene (root,700,700,Color.rgb(10,10,20));
        stage.setTitle("Notes Applaciton");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}