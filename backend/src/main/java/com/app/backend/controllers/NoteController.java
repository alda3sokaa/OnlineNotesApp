package com.app.backend.controllers;

import com.app.backend.models.Note;
import com.app.backend.services.NoteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	    @PostMapping("/{id}/share")
	    public ResponseEntity<NoteShare> shareNote(
	            @PathVariable Long id,
	            @RequestParam Long userId,
	            @RequestParam Long sharedWithId,
	            @RequestParam String role) {

	        NoteShare share = noteService.shareNote(
	                id,
	                userId,
	                sharedWithId,
	                role
	        );

	        return ResponseEntity.status(HttpStatus.CREATED).body(share);
	    }
	}
