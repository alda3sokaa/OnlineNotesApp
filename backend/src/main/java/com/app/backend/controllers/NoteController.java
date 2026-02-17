package com.app.backend.controllers;

import com.app.backend.models.Note;
import com.app.backend.services.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // 1. GET /notes
    @GetMapping
    public ResponseEntity<Collection<Note>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAll());
    }

    // 2. GET /notes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Note note = noteService.getById(id);
        if (note != null) {
            return ResponseEntity.ok(note);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 3. POST /notes?userId=1
    
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestParam Long userId, @RequestBody Note note) {
        Note createdNote = noteService.create(userId, note);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    // 4. PUT /notes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note note) {
        Note updatedNote = noteService.update(id, note);
        if (updatedNote != null) {
            return ResponseEntity.ok(updatedNote);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE /notes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}