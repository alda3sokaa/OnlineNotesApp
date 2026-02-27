package com.app.backend.services;

import com.app.backend.exceptions.NoteNotFoundException;
import com.app.backend.models.Note;
import com.app.backend.repositories.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    void createShouldRequireUserId() {
        Note note = new Note();
        note.setTitle("Test");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> noteService.create(null, note));

        assertTrue(ex.getMessage().contains("userId"));
    }

    @Test
    void getByIdShouldThrowWhenMissing() {
        when(noteRepository.findById(9L)).thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class, () -> noteService.getById(9L));
    }

    @Test
    void createShouldTrimTitleAndPersist() {
        Note request = new Note();
        request.setTitle("  Hello  ");

        when(noteRepository.save(any(Note.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Note saved = noteService.create(1L, request);

        assertEquals("Hello", saved.getTitle());
        assertEquals(1L, saved.getUserId());
    }
}
