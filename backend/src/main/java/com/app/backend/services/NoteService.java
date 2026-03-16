package com.app.backend.services;

import com.app.backend.exceptions.NoteNotFoundException;
import com.app.backend.exceptions.VersionConflictException;
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
        if (userId == null) throw new IllegalArgumentException("userId is required.");
        note.setId(null);
        note.setUserId(userId);
        if (note.getTitle() != null) note.setTitle(note.getTitle().trim());
        return noteRepository.save(note);
    }

    @Transactional
    public Note update(Long id, Long userId, Note update) {
        Note existing = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        permissionsService.checkEditPermission(existing, userId);

        if (existing.getVersion() != update.getVersion()) {
            throw new VersionConflictException("Version conflict detected.");
        }

        if (update.getTitle() != null) {
            String title = update.getTitle().trim();
            if (title.isEmpty()) throw new IllegalArgumentException("Title cannot be blank.");
            existing.setTitle(title);
        }
        if (update.getContent() != null) existing.setContent(update.getContent());

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
        NoteShare share = new NoteShare(noteId, sharedWithId, shareRole);
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
    public void removeShare(Long noteId, Long sharedWithUserId, Long ownerId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException(noteId));

        permissionsService.checkSharePermission(note, ownerId);
        noteShareRepository.deleteByNoteIdAndSharedWithUserId(noteId, sharedWithUserId);
    }
    public org.springframework.data.domain.Page<Note> getUserNotes(Long userId, int page, int size) {
        return noteRepository.findByUserId(userId, org.springframework.data.domain.PageRequest.of(page, size));
    }

    public java.util.List<Note> searchNotes(Long userId, String keyword) {
        return noteRepository.findByUserIdAndTitleContainingIgnoreCase(userId, keyword);
    }

    public java.util.List<Note> getNotesBetweenDates(Long userId, java.time.LocalDateTime start, java.time.LocalDateTime end) {
        return noteRepository.findByUserIdAndCreatedAtBetween(userId, start, end);
    }

    public java.util.List<NoteShare> getNoteShares(Long noteId) {
        return noteShareRepository.findByNoteId(noteId);
    }
}