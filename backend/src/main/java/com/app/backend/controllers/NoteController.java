package com.app.backend.controllers;

import com.app.backend.models.Note;
import com.app.backend.services.NoteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestParam Long userId, @Valid @RequestBody Note note) {
        Note createdNote = noteService.create(userId, note);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note note) {
        return ResponseEntity.ok(noteService.update(id, note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.delete(id);
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
                        LocalDateTime.parse(startDate),
                        LocalDateTime.parse(endDate)
                )
        );
    }
}