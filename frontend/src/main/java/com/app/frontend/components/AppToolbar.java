package com.app.frontend.components;

import javafx.scene.control.Button;
import javafx.scene.control.IndexRange;
import javafx.scene.layout.HBox;
import org.fxmisc.richtext.InlineCssTextArea;

public class AppToolbar extends HBox {

    private InlineCssTextArea textArea;

    public AppToolbar(InlineCssTextArea textArea) {
        this.textArea = textArea;

        // UNDERLINE BUTTON
        Button underlineBtn = new Button("U");

        underlineBtn.setOnAction(e -> applyUnderline());

        this.getChildren().add(underlineBtn);
        this.setSpacing(10);
    }

    private void applyUnderline() {
        IndexRange selection = textArea.getSelection();

        if (selection.getLength() == 0) return;

        // Seçili alana underline uygula
        textArea.setStyle(
                selection.getStart(),
                selection.getEnd(),
                "-fx-underline: true;"
        );
    }
}