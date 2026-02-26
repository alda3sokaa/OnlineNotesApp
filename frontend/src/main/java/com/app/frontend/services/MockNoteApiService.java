package com.app.frontend.services;

import com.app.frontend.models.NoteResponse;
import java.util.ArrayList;
import java.util.List;


public class MockNoteApiService implements NoteApiService {

    private List<NoteResponse> fakeDatabase = new ArrayList<>();
    private long idCounter = 1;

    public MockNoteApiService() {

        createNote(1L, "ملاحظة الجامعة", "توزيع المهام بين أعضاء الفريق");
        createNote(1L, "مشروع الجافا", "الانتهاء من ربط الواجهات اليوم!");
    }

    @Override
    public List<NoteResponse> getAllNotes() {
        return fakeDatabase;
    }

    @Override
    public NoteResponse createNote(Long userId, String title, String content) {
        NoteResponse newNote = new NoteResponse(idCounter++, title, content);
        fakeDatabase.add(newNote);
        return newNote;
    }

    @Override
    public NoteResponse updateNote(Long id, String title, String content) {
        for (NoteResponse note : fakeDatabase) {
            if (note.getId().equals(id)) {
                note.setTitle(title);
                note.setContent(content);
                return note;
            }
        }
        return null;
    }

    @Override
    public void deleteNote(Long id) {
        fakeDatabase.removeIf(note -> note.getId().equals(id));
    }
}