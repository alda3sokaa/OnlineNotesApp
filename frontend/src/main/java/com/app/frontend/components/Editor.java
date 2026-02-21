
package com.app.frontend.components;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import javafx.scene.control.ScrollPane;

public class Editor {
    private TextArea contentArea;
    private ScrollPane scrollPane;

    public Editor(){
        this.contentArea= new TextArea();
        this.contentArea.setWrapText(true);

        this.scrollPane = new ScrollPane(contentArea);
        this.scrollPane.setFitToWidth(true);
        this.scrollPane.setFitToHeight(true);

        this.contentArea.textProperty().addListener((observable ,oldValue, newValue) -> {
            syncWithLogic(newValue);
        });
    }
    private void syncWithLogic(String text) {
        System.out.println("Syncing text: " +text);
    }

    public ScrollPane getView() {
        return scrollPane;
    }

    public String getText(){
        return contentArea.getText();
    }

    public void setText(String text){
        contentArea.setText(text);
    }
}
