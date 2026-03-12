package com.app.backend.services;

import com.app.backend.exceptions.NoteNotFoundException;
import com.app.backend.exceptions.VersionConflictException;
import com.app.backend.models.Note;
import com.app.backend.repositories.NoteRepository;
import com.app.backend.repositories.NoteShareRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.app.backend.models.NoteShare;
import com.app.backend.models.ShareRole;
import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteShareRepository noteShareRepository;
    public NoteService(NoteRepository noteRepository, NoteShareRepository noteShareRepository) {
        this.noteRepository = noteRepository;
        this.noteShareRepository = noteShareRepository;
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

        if (existing.getVersion() != update.getVersion()) {
            throw new VersionConflictException("Version conflict detected. The note was updated by someone else.");
        }

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
    public NoteShare shareNote(Long noteId, Long userId, Long sharedWithId, String role) {

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException(noteId));

        if (!note.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Only the owner can share this note.");
        }

        ShareRole shareRole = ShareRole.valueOf(role.toUpperCase());

        NoteShare share = new NoteShare(noteId, sharedWithId, shareRole);

        return noteShareRepository.save(share);
    }
}
