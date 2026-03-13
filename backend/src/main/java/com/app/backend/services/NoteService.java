package com.app.backend.models;
import jakarta.persistence.*;
package com.app.backend.services;

import com.app.backend.exceptions.NoteNotFoundException;
import com.app.backend.exceptions.VersionConflictException;
import com.app.backend.exceptions.UnauthorizedException;
import com.app.backend.models.Note;
import com.app.backend.models.NoteShare;
import com.app.backend.models.ShareRole;
import com.app.backend.repositories.NoteRepository;
import com.app.backend.repositories.NoteShareRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteShareRepository noteShareRepository;
    private final PermissionsService permissionsService;

    public NoteService(NoteRepository noteRepository,
                       NoteShareRepository noteShareRepository,
                       PermissionsService permissionsService) {
        this.noteRepository = noteRepository;
        this.noteShareRepository = noteShareRepository;
        this.permissionsService = permissionsService;
    }
    public Note getById(Long id, Long userId) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
        permissionsService.checkViewPermission(note, userId);
        return note;
    }

    public List<Note> getAll(Long userId) {
        if (userId != null) {
            return noteRepository.findByUserIdOrderByCreatedAtDesc(userId);
        }
        return noteRepository.findAll();
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
    public Note update(Long id, Long userId, Note update) {
        Note existing = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        permissionsService.checkEditPermission(existing, userId);
        if (existing.getVersion() != update.getVersion()) {
            throw new VersionConflictException("Version conflict detected. The note was updated by someone else.");
        }

        if (update.getTitle() != null) {
            String title = update.getTitle().trim();
            if (title.isEmpty()) throw new IllegalArgumentException("Title cannot be blank.");
            existing.setTitle(title);
        }
        if (update.getContent() != null) {
            existing.setContent(update.getContent());
        }

        return noteRepository.save(existing);
    }
    @Transactional
    public void delete(Long id, Long userId) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        permissionsService.checkDeletePermission(note, userId);

        noteRepository.delete(note);
    }
    @Transactional
    public NoteShare shareNote(Long noteId, Long userId, Long sharedWithId, String roleStr) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException(noteId));

        permissionsService.checkSharePermission(note, userId);
        if (noteShareRepository.existsByNoteIdAndSharedWithUserId(noteId, sharedWithId)) {
            throw new IllegalArgumentException("this note is already shared with this user");
        }

        ShareRole shareRole = ShareRole.valueOf(roleStr.toUpperCase());
        NoteShare share = new NoteShare(noteId,sharedWithId,shareRole);
        return noteShareRepository.save(share);
    }

    public List<Note> getSharedNotes(Long userId) {
        List<NoteShare> shares = noteShareRepository.findBySharedWithUserId(userId);
        return shares.stream()
                .map(share -> noteRepository.findById(share.getNoteId())
                        .orElseThrow(() -> new NoteNotFoundException(share.getNoteId())))
                .toList();
    }

    @Transactional
    public void removeShare(Long noteId,Long sharedWithUserId,Long ownerId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException(noteId));

        permissionsService.checkSharePermission(note,ownerId);

        noteRepository.deleteByNoteIdAndSharedWithUserId(noteId,sharedWithUserId);
    }
}}        }

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
