# Sync/Logic Team – Documentation and Tasks

## Overview
The Sync/Logic Team is responsible for designing and implementing the rules that control how users collaborate on notes.  
Since this project does not include real-time editing in the first version, the focus is on defining permission rules, update logic, and conflict handling.

This team works closely with the backend team because all sync and permission features are implemented on the server.

---

## Responsibilities (MVP)
1. Define the logic for user permissions:
   - Who can view a note
   - Who can edit a note
   - How the owner shares the note with others
   - What the "view-only" role means
   - What the "edit" role means

2. Implement versioning behavior:
   - Each note has a version number
   - Version increases on each update
   - Version is used to detect conflicting edits

3. Handle update conflicts:
   - If two users edit the same note at the same time
   - Decide how to respond when version numbers do not match
   - Define whether “last save wins” or “reject conflicting save”

4. Assist backend team in writing the correct logic inside:
   - `NoteService`
   - `PermissionService`
   - Controllers for `/notes` and `/share`

5. Create documented rules for all sync behavior:
   - Expected input from frontend
   - Expected output from backend
   - Common error scenarios and how they should be handled

6. Test collaborative flows:
   - Two users editing the same note
   - A user with view-only trying to edit
   - Sharing a note with another user
   - Removing access from a user
   - Invalid requests and permission violations

---

## Recommended Tools
- Java (for reading backend logic)
- Basic understanding of REST APIs
- Working with Spring Services and Controllers
- Writing clear documentation for the backend and frontend teams

---

## Expected Folder Location (inside backend)
Although this team does not have a separate project folder, their work will appear in:

backend/src/main/java/com/app/backend/services/
NoteService.java
PermissionService.java
SyncService.java (if needed)

backend/src/main/java/com/app/backend/controllers/

The Sync Team focuses on logic and rules, not on user interface or database design.

---

## Roadmap
### Week 1
- Define all permission rules clearly
- Define versioning logic
- Write documentation explaining how sync should behave

### Week 2
- Work with backend team to implement permission checks
- Implement versioning in `NoteService`
- Test each endpoint manually with different users

### Week 3
- Handle update conflicts
- Finalize logic for the `/share` endpoint
- Create detailed test cases

### Week 4
- Stress test collaborative features
- Document all behaviors for frontend team
- Final pass on permission and sync rules
