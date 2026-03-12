package com.app.backend.repositories;

import com.app.backend.models.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUserIdOrderByCreatedAtDesc(Long userId);

    Page<Note> findByUserId(Long userId, Pageable pageable);

    List<Note> findByUserIdAndTitleContainingOrUserIdAndContentContaining(
            Long userId, String title,
            Long userId2, String content
    );

    List<Note> findByUserIdAndCreatedAtBetween(
            Long userId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

}