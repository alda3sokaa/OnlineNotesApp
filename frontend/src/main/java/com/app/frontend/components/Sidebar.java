package main.java.com.app.frontend.components;

import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.awt.*;

public class Sidebar {
private BorderPane root;
private TextField searchFiled;
private VBox notesListContainer;

public Sidebar(){
    root=new BorderPane();
    buildTop();
    buildCenter();
    buildBottom();
}
private void buildTop() {

    Label title = new Label("OnlineAppNote");
    searchFiled = new TextField();
    searchFiled.setPromptText("Search");
    VBox topContainer = new VBox(10);
    topContainer.getChildren().addAll(title,searchFiled);
    topContainer.setPadding(new Insets(10,10,20,10));
    root.setTop(topContainer);
}
private void buildCenter(){
    Button newBtn = new Button("New");
    Button editBtn = new Button("Edit");
    Button deleteBtn = new Button("Delete");

    VBox buttonBar = new VBox(10, newBtn, editBtn, deleteBtn);
    buttonBar.setPadding(new Insets(10));

    notesListContainer = new VBox(10);
    notesListContainer.setPadding(new Insets(10));

    ScrollPane scrollPane = new ScrollPane(notesListContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefHeight(200);

    VBox centerBox = new VBox(10, buttonBar, scrollPane);
    root.setCenter(centerBox);
    for (int i = 1; i <= 20; i++) {
        notesListContainer.getChildren().add(new Label("Note " + i));
    }

}
private void buildBottom(){
    Button settingsButton=new Button("Settings");
    root.setBottom(settingsButton);
}
public BorderPane getView(){
    return root;
}
public VBox getNotesListContainer(){
    return notesListContainer;
}
public TextField getSearchFiled(){
    return searchFiled;
}
}