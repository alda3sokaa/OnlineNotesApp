package com.app.frontend.models;

public class Note {
    private Long id;
    private String title;
    private String content;
    private int version;

    // Constructor
    public Note() {}

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }
}