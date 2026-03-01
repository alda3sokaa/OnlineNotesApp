package com.app.frontend.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker; // 👈 مكتبة الألوان
import javafx.scene.control.IndexRange;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color; // 👈 مكتبة الألوان
import org.fxmisc.richtext.InlineCssTextArea;

public class AppToolbar extends HBox {
    private Editor editor;
    private InlineCssTextArea textArea;

    public AppToolbar(Editor editor) {
        this.editor =  editor;
        this.textArea =  new InlineCssTextArea();

        // 1. زر التسطير (Underline)
        Button underlineBtn = new Button("U");
        underlineBtn.setStyle("-fx-underline: true; -fx-background-color: #333333; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold;");
        underlineBtn.setOnAction(e -> editor.applyUnderLine());

        // 2. زر الخط العريض (Bold)
        Button boldBtn = new Button("B");
        boldBtn.setStyle("-fx-font-weight: bold; -fx-background-color: #333333; -fx-text-fill: white; -fx-cursor: hand;");
        boldBtn.setOnAction(e -> editor.applyBold());

        // 3. زر الخط المائل (Italic)
        Button italicBtn = new Button("I");
        italicBtn.setStyle("-fx-font-style: italic; -fx-background-color: #333333; -fx-text-fill: white; -fx-cursor: hand;");
        italicBtn.setOnAction(e -> editor.applyItalic());

        // 4. باليتة الألوان (Color Picker) 🎨
        ColorPicker colorPicker = new ColorPicker(Color.WHITE); // اللون الافتراضي أبيض
        colorPicker.setStyle("-fx-background-color: #333333; -fx-color-label-visible: false; -fx-cursor: hand;");
        colorPicker.setOnAction(e -> {
            Color c = colorPicker.getValue();
            // تحويل اللون لصيغة Hex عشان الـ CSS يفهمه (مثال: #FF0000)
            String hexColor = String.format("#%02X%02X%02X",
                    (int) (c.getRed() * 255),
                    (int) (c.getGreen() * 255),
                    (int) (c.getBlue() * 255));
            editor.applyColor(hexColor);
        });

        // 5. زر إضافة مهمة (Task Checkbox)
        Button taskBtn = new Button("□ Task");
        taskBtn.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-cursor: hand;");
        taskBtn.setOnAction(e -> editor.insertTaskBox());

        // 6. زر التنقيط التلقائي (Auto-Bullet Toggle)
        ToggleButton autoBulletBtn = new ToggleButton("• Auto");
        autoBulletBtn.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold;");
        autoBulletBtn.setOnAction(e -> {
            editor.setAutoBulletEnabled(autoBulletBtn.isSelected());
            if (autoBulletBtn.isSelected()) {
                autoBulletBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold;");
            } else {
                autoBulletBtn.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold;");
            }
        });

        // إضافة كل الأزرار للشريط (رتبناهم: B, I, U, Color, Auto, Task)
        this.getChildren().addAll(boldBtn, italicBtn, underlineBtn, colorPicker, autoBulletBtn, taskBtn);
        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.setStyle("-fx-background-color: #1e1e1e; -fx-border-color: #333333; -fx-border-width: 0 0 1 0;");
    }
    private void applyUnderline() {
        IndexRange selection = textArea.getSelection();
        if (selection.getLength() == 0) return;

        textArea.setStyle(
                selection.getStart(),
                selection.getEnd(),
                "-fx-underline: true; -fx-fill: white;"
        );
    }
}