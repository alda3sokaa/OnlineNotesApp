package com.app.frontend.components;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class NoteCard extends VBox {

    public NoteCard(Sidebar.NoteItem item) {
        // 1. المساحات الداخلية والخارجية
        this.setPadding(new Insets(12, 15, 12, 15));
        this.setSpacing(6); // المسافة بين العنوان والنص الفرعي

        // 2. إعداد ألوان الـ Dark Mode الأساسية
        String defaultStyle = "-fx-background-color: #2b2b2b; " +
                "-fx-background-radius: #2a2a2a; " +
                "-fx-border-color: #a8a4a4; " +
                "-fx-border-radius: 8; " +
                "-fx-border-width: 1;";

        // 3. إعداد لون الكرت لما يمر الماوس فوقه (Hover)
        String hoverStyle = "-fx-background-color: #3a3a3a; " +
                "-fx-background-radius: 8; " +
                "-fx-border-color: #5a5a5a; " +
                "-fx-border-radius: 8; " +
                "-fx-border-width: 1;";

        // تطبيق الستايل الأساسي
        this.setStyle(defaultStyle);
        this.setCursor(Cursor.HAND); // تغيير شكل الماوس

        // برمجة تأثير الماوس (Hover Effects)
        this.setOnMouseEntered(e -> this.setStyle(hoverStyle));
        this.setOnMouseExited(e -> this.setStyle(defaultStyle));

        // 4. تصميم عنوان الملاحظة
        Label titleLabel = new Label(item.toString());
        titleLabel.setStyle("-fx-text-fill: #fafafa; -fx-font-weight: bold; -fx-font-size: 15px;");

        // 5. تصميم النص الفرعي (معلومة إضافية تحت العنوان تعطي شكل احترافي)
        Label idLabel = new Label("ملاحظة رقم: " + item.getId());
        idLabel.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 12px;");

        // إضافة العناصر جوا الكرت
        this.getChildren().addAll(titleLabel, idLabel);
    }
}