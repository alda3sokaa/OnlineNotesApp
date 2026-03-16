package com.app.backend.repositories;

import com.app.backend.models.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository // ضفتلك هاي كأفضل ممارسة (Best Practice)
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUserIdOrderByCreatedAtDesc(Long userId);

    Page<Note> findByUserId(Long userId, Pageable pageable);

    // التعديل صار هون: بسطنا اسم الدالة عشان تطابق الـ NoteService بالضبط وتتجاهل حالة الأحرف
    List<Note> findByUserIdAndTitleContainingIgnoreCase(Long userId, String keyword);

    List<Note> findByUserIdAndCreatedAtBetween(
            Long userId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}