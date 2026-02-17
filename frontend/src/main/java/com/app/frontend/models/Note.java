package com.app.frontend.models;

import java.time.LocalDateTime;

public class Note {
    private String title;
    private String content;
    private LocalDateTime date;

    public Note(String title) {
        this.title = title;
        this.content = content;
        this.date = LocalDateTime.now(); // بياخد وقت النظام الحالي
    }

    public String getTitle() {
        return title;
    }
    public String getContent() {return content;}
    public LocalDateTime getDate() {
        return date;
    }
}