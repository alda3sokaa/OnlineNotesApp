# Coding Rules

- We will have rules while writing code so we can avoid as much confusion as possible

# Rule #1:
- Always make variable names obvious and easy for the other developers to read and identify

Example:

int s;
String b; 
    // This is not the best way to initialize variables, as a programmer, i will not understand what s and b are for.

int noteID;
String noteName;
    // With this notation i can intuitively guess what noteID and noteName do, aswell as what purpose they serve.



# Rule #2:
- Leave comments on blocks of code you make and explain what they do so that anyone else can understand what you're doing

Example:

List<Note> notes = noteService.getNotesForUser(userId);
    // Loads all notes belonging to the current user

# Rule #3:
- Add your name infront of blocks of code so we know that you have contributed to the codebase

Example:

@alda3sokaa - Hassan Ibrahim

// This line of code iterates the variable x between 0 and 10

for(x<10)
    {
        /*code*/ 
    }