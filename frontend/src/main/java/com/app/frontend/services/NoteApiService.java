package com.app.frontend.services;

import com.app.frontend.models.NoteResponse;
import java.util.List;

// لاحظ هون استبدلنا كلمة class بكلمة interface
public interface NoteApiService {
    List<NoteResponse> getAllNotes();
    NoteResponse createNote(Long userId, String title, String content);
    NoteResponse updateNote(Long id, String title, String content);
    void deleteNote(Long id);
}