package com.app.frontend.components;

import com.app.frontend.models.Note;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class NoteCard {


    public Node createCard(Note note) {
        Label titleLabel = new Label(note.getTitle());


        Label dateLabel = new Label(note.getDate().toString());

        VBox cardLayout = new VBox(5);
        cardLayout.getChildren().addAll(titleLabel, dateLabel);

        return cardLayout;
    }
}