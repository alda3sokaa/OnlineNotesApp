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
        if (note.getUserId().equals(userId)) return;

        boolean isShared = noteShareRepository.existsByNoteIdAndSharedWithUserId(note.getId(), userId);
        if (!isShared) {
            throw new UnauthorizedException("you do not have permission to view this note");
        }
    }

    public void checkEditPermission(Note note, Long userId) {
        if (note.getUserId().equals(userId)) return;

        var shareRecord = noteShareRepository.findByNoteIdAndSharedWithUserId(note.getId(), userId)
                .orElseThrow(() -> new UnauthorizedException("you do not have permission to edit this note"));

        if (shareRecord.getRole() != ShareRole.EDITOR) {
            throw new UnauthorizedException("you only have permission to view the not , u cant edit it");
        }
    }

    public void checkDeletePermission(Note note, Long userId) {
        if (!note.getUserId().equals(userId)) {
            throw new UnauthorizedException(" only the (Owner) can delete the note ");
        }
    }
}