package com.app.backend.models;

public class Note {

    private Long id;
    private String title;
    private String content;
    private int version;

    public Note() {
    }

    public Note(Long id, String title, String content, int version) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}