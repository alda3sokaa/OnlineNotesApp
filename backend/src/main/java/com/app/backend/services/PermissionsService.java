package com.app.backend.services;

import com.app.backend.exceptions.UnauthorizedException;
import com.app.backend.models.Note;
import com.app.backend.models.ShareRole;
import com.app.backend.repositories.NoteShareRepository;
import org.springframework.stereotype.Service;

@Service
public class PermissionsService {

    private final NoteShareRepository noteShareRepository;

    public PermissionsService(NoteShareRepository noteShareRepository) {
        this.noteShareRepository = noteShareRepository;
    }

    public void checkViewPermission(Note note, Long userId) {
        // ضفنا حماية: لو الـ userId مو موجود، أو إذا كان هو المالك، بيمر
        if (userId == null || note.getUserId().equals(userId)) return;

        boolean isShared = noteShareRepository.existsByNoteIdAndSharedWithUserId(note.getId(), userId);
        if (!isShared) {
            throw new UnauthorizedException("You do not have permission to view this note.");
        }
    }

    public void checkEditPermission(Note note, Long userId) {
        if (userId == null || note.getUserId().equals(userId)) return;

        var shareRecord = noteShareRepository
                .findByNoteIdAndSharedWithUserId(note.getId(), userId)
                .orElseThrow(() -> new UnauthorizedException("You do not have permission to edit this note."));

        if (shareRecord.getRole() != ShareRole.EDITOR) {
            throw new UnauthorizedException("You only have permission to view the note, you can't edit it.");
        }
    }

    public void checkDeletePermission(Note note, Long userId) {
        if (userId == null || !note.getUserId().equals(userId)) {
            throw new UnauthorizedException("Only the owner can delete the note.");
        }
    }

    public void checkSharePermission(Note note, Long userId) {
        if (userId == null || !note.getUserId().equals(userId)) {
            throw new UnauthorizedException("Only the owner can share this note.");
        }
    }
}