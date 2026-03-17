package com.app.backend.controllers;

import com.app.backend.models.Note;
import com.app.backend.models.NoteShare;
import com.app.backend.services.NoteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes(@RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(noteService.getAll(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id, @RequestParam Long userId) {
        return ResponseEntity.ok(noteService.getById(id, userId));
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestParam Long userId, @Valid @RequestBody Note note) {
        Note createdNote = noteService.create(userId, note);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestBody Note note) {
        return ResponseEntity.ok(noteService.update(id, userId, note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id, @RequestParam Long userId) {
        noteService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public ResponseEntity<?> getUserNotesWithPagination(
            @RequestParam Long userId,
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(noteService.getUserNotes(userId, page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Note>> searchNotes(
            @RequestParam Long userId,
            @RequestParam String keyword) {
        // تم تصحيح اسم الدالة هنا من NoteShare إلى searchNotes
        return ResponseEntity.ok(noteService.searchNotes(userId, keyword));
    }


    @GetMapping("/filter")
    public ResponseEntity<List<Note>> filterNotesByDate(
            @RequestParam Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return ResponseEntity.ok(
                noteService.getNotesBetweenDates(
                        userId,
                        Instant.parse(startDate),
                        Instant.parse(endDate)
                )
        );
    }

    @PostMapping("/{id}/share")
    public ResponseEntity<NoteShare> shareNote(
            @PathVariable Long id,
            @RequestParam Long userId,
            @RequestParam Long sharedWithId,
            @RequestParam String role) {

        NoteShare share = noteService.shareNote(id, userId, sharedWithId, role);
        return ResponseEntity.status(HttpStatus.CREATED).body(share);
    }

    @GetMapping("/shared")
    public ResponseEntity<List<Note>> getSharedNotes(@RequestParam Long userId) {
        return ResponseEntity.ok(noteService.getSharedNotes(userId));
    }

    @GetMapping("/{id}/shares")
    public ResponseEntity<List<NoteShare>> getNoteShares(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNoteShares(id));
    }

    @DeleteMapping("/{id}/share/{userId}")
    public ResponseEntity<Void> removeShare(
            @PathVariable Long id,
            @PathVariable Long userId,
            @RequestParam Long ownerId) {

        noteService.removeShare(id, userId, ownerId);
        return ResponseEntity.noContent().build();
    }
}