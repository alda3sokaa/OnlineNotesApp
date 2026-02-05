# Backend Team – Documentation and Tasks

## Overview
The backend team is responsible for building the server side of the application using Spring Boot.  
This includes handling data, business logic, APIs, database operations, and permission rules.

The backend will expose a clean set of REST endpoints that the frontend will interact with.

---

## Responsibilities (MVP)
1. Build the main API for notes:
   - `GET /notes` — list notes available to the user
   - `GET /notes/{id}` — retrieve a note
   - `POST /notes` — create a note
   - `PUT /notes/{id}` — update a note
   - `DELETE /notes/{id}` — delete a note

2. Create the necessary data models:
   - User  
   - Note  
   - NoteShare (permissions)

3. Implement basic permission handling:
   - Each note has an owner  
   - Notes can be shared with specific users  
   - Roles: view-only or edit  

4. Handle versioning for notes:
   - Increment version on each save  
   - Determine how to respond when two users save the same note

5. Connect the backend to a database:
   - Start with H2  
   - Optionally upgrade to PostgreSQL if needed

---

## Recommended Tools
- Java  
- Spring Boot  
- Spring Web  
- Spring Data JPA  
- H2 Database (initial development)

---

## Expected Folder Structure
backend/
src/main/java/com/app/backend/
BackendApplication.java
controllers/
services/
models/
repositories/
src/main/resources/
application.properties
pom.xml

---

## Roadmap
### Week 1
- Set up Spring Boot project  
- Create `/hello` endpoint  
- Create basic Note model  
- Use in-memory or H2 database  

### Week 2
- Build full CRUD for notes  
- Add User and NoteShare models  
- Implement basic permission checks  

### Week 3
- Add versioning logic for notes  
- Improve permission logic  
- Create the `/notes/{id}/share` endpoint  

### Week 4
- Add validation and error handling  
- Improve database structure  
- Final API cleanup and testing  
