package com.app.backend.repositories;

import com.app.backend.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserIdOrderByCreatedAtDesc(Long userId);
}
