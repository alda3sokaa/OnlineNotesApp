package com.app.backend.exceptions;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(Long noteId) {
        super("Note with id " + noteId + " was not found.");
    }
}