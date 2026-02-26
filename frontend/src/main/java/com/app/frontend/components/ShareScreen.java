package com.app.frontend.components;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

public class ShareScreen {
    private TextField title = new TextField();
    private TextArea content = new TextArea();
    private Button shareButton = new Button("Share");

    public VBox createLayout() {
        title.setPromptText("Title");
        content.setPromptText("Write your note...");

        VBox layout = new VBox(10, title, content, shareButton);
        layout.setPadding(new Insets(20));
        return layout;
    }

    public String getTitle() { return title.getText(); }
    public String getContent() { return content.getText(); }
    public Button getShareButton() { return shareButton; }
}