package com.app.backend.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import com.app.backend.models.Note;

public class NoteResponse {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String createdAt; 
    private int version;
    private List<String> tags;

    public NoteResponse(Note note) {
        this.id = note.getId();
        this.userId = note.getUserId();
        this.title = note.getTitle();
        this.content = note.getContent();
        this.version = note.getVersion();
        this.tags = note.getTags();
        
        if (note.getCreatedAt() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            this.createdAt = note.getCreatedAt().format(formatter);
        }
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCreatedAt() { return createdAt; }
    public int getVersion() { return version; }
    public List<String> getTags() { return tags; }
}