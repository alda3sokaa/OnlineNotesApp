package com.app.backend.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.backend.models.NoteShare;

public interface NoteShareRepository extends JpaRepository<NoteShare, Long> {
    List<NoteShare> findByNoteId(Long noteId);
    
    List<NoteShare> findBySharedWithUserId(Long sharedWithUserId);
    
    Optional<NoteShare> findByNoteIdAndSharedWithUserId(Long noteId, Long sharedWithUserId);
    
    boolean existsByNoteIdAndSharedWithUserId(Long noteId, Long sharedWithUserId);
}