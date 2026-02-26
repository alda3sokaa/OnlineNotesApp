package com.app.backend.services;

import com.app.backend.exceptions.NoteNotFoundException;
import com.app.backend.models.Note;
import com.app.backend.repositories.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAll(Long userId) {
        if (userId != null) {
            return noteRepository.findByUserIdOrderByCreatedAtDesc(userId);
        }

        return noteRepository.findAll();
    }

    public Note getById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
    }

    @Transactional
    public Note create(Long userId, Note note) {
        if (userId == null) {
            throw new IllegalArgumentException("userId query parameter is required.");
        }

        note.setId(null);
        note.setUserId(userId);

        if (note.getTitle() != null) {
            note.setTitle(note.getTitle().trim());
        }

        return noteRepository.save(note);
    }

    @Transactional
    public Note update(Long id, Note update) {
        Note existing = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        if (update.getTitle() != null) {
            String title = update.getTitle().trim();
            if (title.isEmpty()) {
                throw new IllegalArgumentException("Title cannot be blank.");
            }
            existing.setTitle(title);
        }

        if (update.getContent() != null) {
            existing.setContent(update.getContent());
        }

        return noteRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new NoteNotFoundException(id);
        }
        noteRepository.deleteById(id);
    }
}
