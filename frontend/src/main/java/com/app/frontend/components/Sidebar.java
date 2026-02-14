package main.java.com.app.frontend.components;

import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import javax.swing.*;
import java.util.Collection;


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
        VBox topContainer = new VBox(10);
        topContainer.getChildren().add(title);
        topContainer.setPadding(new Insets(10,10,20,10));
        root.setTop(topContainer);
    }
    private void buildCenter(){

        Button noteListBtn=new Button("Note list");

        TextField searchFiled=new TextField();
        searchFiled.setPromptText("Search");
        notesListContainer=new VBox(10);
        notesListContainer.setPadding(new Insets(10));
        for(int i=1;i<=20;i++){
            notesListContainer.getChildren().add(new Label("Note"+i));
        }
        ScrollPane scrollPane=new ScrollPane(notesListContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(200);

        VBox noteContent=new VBox(10,searchFiled,scrollPane);
        noteContent.setPadding(new Insets(10));

        noteContent.setVisible(false);
        noteContent.setManaged(false);
        noteListBtn.setOnAction(e->{
            boolean isVisible=noteContent.isVisible();
            noteContent.setVisible(!isVisible);
            noteContent.setManaged(!isVisible);
        });
        Button newBtn = new Button("New");
        Button deleteBtn = new Button("Delete");
        Button saveBtn = new Button("Save");
        Button shareBtn = new Button("Share");
        VBox buttonBar = new VBox(10, newBtn, deleteBtn,saveBtn,shareBtn);
        buttonBar.setPadding(new Insets(10));

        VBox centerBox=new VBox(15);
        centerBox.getChildren().addAll(noteListBtn,noteContent,buttonBar);
        root.setCenter(centerBox);
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
//update