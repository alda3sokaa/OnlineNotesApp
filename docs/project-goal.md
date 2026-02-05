# Online Notes App General Documentation

@alda3soka - Hassan Ibrahim

- The goal of this app is to make an Obsidian-like app that takes notes of everything you write, and make it accessible to multiple people at once, more ideas will be added down the line hopefully, like adding flow charts, live note sharing, etc...

But for now we just want to add notes and sharing as a basic idea, once we execute that, we can look at extras.

- The main goal of this app is to take notes as a team and plan for events, or study, it is all down to the user. But we want to make it as user/consumer friendly as possible.

- We will have 3 teams, frontend, backend, and sync teams.

1) - The Frontend Team:

        - Builds the UI (JavaFX recommended, but flexible)
        
        - Makes the layout clean and professional
        
        - Takes UI inspiration from:
        
        - Obsidian
        
        - Phone Notes apps
        
        - Preferably uses a dark theme (easier on the eyes)

JavaFX will preferably be used for the UI but it is up to the team to choose what they want to use as a UI optimization.

2) - The Backend Team:

        - Builds the logic behind note creation, editing, deleting

        - Handles storage and small features needed for sharing

        - Creates the API that the frontend will communicate with

        - Uses Spring Boot + Database (H2 / PostgreSQL)

3) Sync/Logic Team:

        - Designs and writes the logic for:
        
        - Online collaboration
        
        - Permissions (view/edit)
        
        - How notes sync between users (non-real-time)
        
        = Works closely with backend to ensure correct behavior


# Team Documentation
        - Each team will have its own .md file inside docs/ containing:
        
        - Weekly tasks
        
        - What they are responsible for
        
        - What's done / not done
        
        - Notes and explanations

This helps keep progress clear for everyone.

- If someone does not understand a task, ask your team, or do research and learn about the topic more.
The project will become way more flexible if we do this very task.

- I will personally be monitoring every team and what they are doing and try my best to contribute to each tean.