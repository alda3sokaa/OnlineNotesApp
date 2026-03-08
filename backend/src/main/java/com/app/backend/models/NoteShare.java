package com.app.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(
    name = "note_shares",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"noteId", "sharedWithUserId"})
    }
)
public class NoteShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long noteId;

    @NotNull
    @Column(nullable = false)
    private Long sharedWithUserId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShareRole role;

    @Column(nullable = false, updatable = false)
    private Instant sharedAt;

    public NoteShare() {
        this.sharedAt = Instant.now();
    }

    public NoteShare(Long noteId, Long sharedWithUserId, ShareRole role) {
        this.noteId = noteId;
        this.sharedWithUserId = sharedWithUserId;
        this.role = role;
        this.sharedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public Long getSharedWithUserId() {
        return sharedWithUserId;
    }

    public void setSharedWithUserId(Long sharedWithUserId) {
        this.sharedWithUserId = sharedWithUserId;
    }

    public ShareRole getRole() {
        return role;
    }

    public void setRole(ShareRole role) {
        this.role = role;
    }

    public Instant getSharedAt() {
        return sharedAt;
    }

    public void setSharedAt(Instant sharedAt) {
        this.sharedAt = sharedAt;
    }
}