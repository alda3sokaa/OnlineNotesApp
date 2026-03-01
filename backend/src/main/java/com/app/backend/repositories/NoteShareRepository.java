package com.app.backend.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.backend.models.NoteShare;

public interface NoteShareRepository extends JpaRepository<NoteShare,Long> {
List<NoteShare> findByNoteId(Long noteId);
List<NoteShare> findByUserId(Long userId);
}