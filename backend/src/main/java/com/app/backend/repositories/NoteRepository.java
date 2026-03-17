package com.app.backend.repositories;

import com.app.backend.models.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant; // تم التعديل
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUserIdOrderByCreatedAtDesc(Long userId);

    Page<Note> findByUserId(Long userId, Pageable pageable);

    List<Note> findByUserIdAndTitleContainingIgnoreCase(Long userId, String keyword);

    // تم تغيير LocalDateTime إلى Instant
    List<Note> findByUserIdAndCreatedAtBetween(
            Long userId,
            Instant startDate,
            Instant endDate
    );
}