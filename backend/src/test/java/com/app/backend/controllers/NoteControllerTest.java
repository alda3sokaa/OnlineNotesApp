package com.app.backend.controllers;

import com.app.backend.models.Note;
import com.app.backend.services.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoteController.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NoteService noteService;

    @Test
    void getAllShouldReturnOk() throws Exception {
        Note note = new Note();
        note.setId(1L);
        note.setUserId(2L);
        note.setTitle("T");

        when(noteService.getAll(null)).thenReturn(List.of(note));

        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void createShouldReturnCreated() throws Exception {
        Note request = new Note();
        request.setTitle("My note");

        Note created = new Note();
        created.setId(10L);
        created.setUserId(1L);
        created.setTitle("My note");

        when(noteService.create(org.mockito.ArgumentMatchers.eq(1L), org.mockito.ArgumentMatchers.any(Note.class)))
                .thenReturn(created);

        mockMvc.perform(post("/notes")
                        .queryParam("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10));
    }
}
