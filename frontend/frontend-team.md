# Frontend Team - Documentation and Tasks

## Overview
The frontend team is responsible for building the desktop application using Java (preferably JavaFX).  
This part represents the user interface: the layout, buttons, note editor, and communication with the backend.

The frontend will retrieve and update data through HTTP requests sent to the backend.

---

## Responsibilities (MVP)
1. Build the main UI:
   - Sidebar for displaying the list of notes
   - Main editor area for writing and editing notes
   - Buttons for New, Save, Delete, and Share

2. Implement user interactions:
   - Selecting notes
   - Editing note content
   - Saving changes

3. Connect to backend endpoints:
   - `GET /notes` - load all notes
   - `GET /notes/{id}` - open a specific note
   - `POST /notes` - create a new note
   - `PUT /notes/{id}` - update an existing note

4. Create a simple login screen using a username only.

5. Prepare the app to be packaged as an executable (.exe) when completed.

---

## Recommended Tools
- Java
- JavaFX  
- Eclipse IDE  
- Java `HttpURLConnection` or `HttpClient` for API calls

---

## Expected Folder Structure
frontend/
src/main/java/com/app/frontend/
Main.java
ApiClient.java
controllers/
ui/
src/main/resources/

---

## Roadmap
### Week 1
- Build a basic JavaFX window  
- Create layout: sidebar + editor  
- Add placeholder buttons  

### Week 2
- Connect to backend `/hello`  
- Load real notes from backend  
- Display selected note content  

### Week 3
- Implement New, Save, Delete  
- Implement Create Note  
- Add simple login  

### Week 4
- Add the note sharing UI  
- Improve layout and styling  
- Prepare for `.exe` packaging  
