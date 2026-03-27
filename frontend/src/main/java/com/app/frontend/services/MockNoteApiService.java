package com.app.frontend.services;

import com.app.frontend.models.NoteResponse;
import java.util.ArrayList;
import java.util.List;

public class MockNoteApiService extends NoteApiService {

    private List<NoteResponse> fakeDatabase = new ArrayList<>();
    private long idCounter = 1;

    public MockNoteApiService() {
        // نستخدم الميثود الجديدة بنفس الباراميترات
        createNote(1L, "ملاحظة الجامعة", "توزيع المهام بين أعضاء الفريق");
        createNote(1L, "مشروع الجافا", "الانتهاء من ربط الواجهات اليوم!");
    }

    @Override
    public List<NoteResponse> getAllNotes() {
        return fakeDatabase;
    }

    // 🚀 عدلنا التوقيع ليكون void ويستقبل userId مثل الأب تماماً
    @Override
    public void createNote(Long userId, String title, String content) {
        NoteResponse newNote = new NoteResponse(idCounter++, title, content);
        fakeDatabase.add(newNote);
        System.out.println("Mock: Saved note for user " + userId);
    }

    // إذا كان عندك ميثود التحديث في الأب، لازم تطابقها هون
    public void updateNote(Long id, String title, String content) {
        for (NoteResponse note : fakeDatabase) {
            if (note.id.equals(id)) { // تأكد إن id في NoteResponse هو public
                note.title = title;
                note.content = content;
                break;
            }
        }
    }

    public void deleteNote(Long id) {
        fakeDatabase.removeIf(note -> note.id.equals(id));
    }
}