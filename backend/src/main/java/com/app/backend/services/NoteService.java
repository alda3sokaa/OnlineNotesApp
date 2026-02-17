package com.app.backend.services;

import com.app.backend.dto.NoteResponse;
import com.app.backend.models.Note;
import com.app.backend.dto.FilterOptions;
import com.app.backend.exceptions.BusinessException;
import com.app.backend.utils.SortingHelper;
import com.app.backend.utils.IdGenerator;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Service 
public class NoteService {
    private final Map<Long, Note> notes = new ConcurrentHashMap<>();
    
    private final IdGenerator idGenerator;

    public NoteService(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public NoteResponse create(Long userId, Note note) {
        validateNote(note);
        Long id = idGenerator.generateId(); 
        
        note.setId(id);
        note.setUserId(userId);
        note.setVersion(1);
        notes.put(id, note);
        return new NoteResponse(note);
    }

    public List<NoteResponse> getAll(FilterOptions options) {
        List<Note> allNotes = new ArrayList<>(notes.values());
        List<Note> filtered = applyFilters(allNotes, options);

        if (filtered != null && !filtered.isEmpty()) {
            SortingHelper.sortByNewest(filtered);
        }
        return filtered.stream()
                       .map(NoteResponse::new)
                       .toList();
    }

    public NoteResponse getById(Long id) {
        if (!notes.containsKey(id)) {
            throw new BusinessException("Note with ID " + id + " not found.");
        }
        Note note = notes.get(id);
        return new NoteResponse(note);
    }

    public NoteResponse update(Long id, Note update) {
        Note existingNote = notes.get(id);
        
        if (existingNote == null) {
            throw new BusinessException("Cannot update: Note not found.");
        }

        if (update.getTitle() != null && update.getTitle().trim().isEmpty()) {
            throw new BusinessException("Title cannot be blank.");
        }

        if (update.getTitle() != null) existingNote.setTitle(update.getTitle());
        if (update.getContent() != null) existingNote.setContent(update.getContent());
        
        existingNote.setVersion(existingNote.getVersion() + 1);
        
        return new NoteResponse(existingNote);
    }

    public void delete(Long id) {
        if (!notes.containsKey(id)) {
            throw new BusinessException("Cannot delete: Note with ID " + id + " does not exist.");
        }
        notes.remove(id);
    }

    private void validateNote(Note note) {
        if (note.getTitle() == null || note.getTitle().trim().isEmpty()) {
            throw new BusinessException("Note title is required and cannot be empty.");
        }
    }

    private List<Note> applyFilters(List<Note> notes, FilterOptions options) {
        if (options == null) return notes;
        Stream<Note> stream = notes.stream();

        if (options.getSearch() != null && !options.getSearch().isEmpty()) {
            String search = options.getSearch().toLowerCase();
            stream = stream.filter(n ->
                n.getTitle().toLowerCase().contains(search) ||
                n.getContent().toLowerCase().contains(search)
            );
        }

        if (options.getUserId() != null) {
            stream = stream.filter(n -> n.getUserId() != null && n.getUserId().equals(options.getUserId()));
        }

        if (options.getStartDate() != null) {
            stream = stream.filter(n -> n.getCreatedAt().isAfter(options.getStartDate()));
        }

        if (options.getEndDate() != null) {
            stream = stream.filter(n -> n.getCreatedAt().isBefore(options.getEndDate()));
        }
        return stream.toList();
    }
}