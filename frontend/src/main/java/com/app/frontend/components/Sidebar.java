package com.app.frontend.components;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Pos;

import java.util.Collection;


public class Sidebar {
    private BorderPane root;
    private TextField searchFiled;
    private VBox notesListContainer;

    public Sidebar(){
        root=new BorderPane();
        buildTop();
        buildCenter();

    }
    private void buildTop() {
        Label title = new Label("OnlineAppNote");
        VBox topContainer = new VBox(10);
        topContainer.getChildren().add(title);
        topContainer.setPadding(new Insets(10,10,20,10));
        root.setTop(topContainer);
    }
    private void buildCenter(){


        Region spacer =new Region();
        VBox.setVgrow(spacer,Priority.ALWAYS);
        FontAwesomeIconView settingsIcon =
                new FontAwesomeIconView(FontAwesomeIcon.GEAR);

        settingsIcon.setGlyphSize(26);
        settingsIcon.getStyleClass().add("sidebar-icon");

        Button settingsButton = new Button();
        settingsButton.setGraphic(settingsIcon);
        settingsButton.getStyleClass().add("sidebar-button");



        Button noteListBtn=new Button();
        FontAwesomeIconView noteIcon =
                new FontAwesomeIconView(FontAwesomeIcon.FILE.FILE_TEXT);

        noteIcon.setGlyphSize(26);
        noteIcon.getStyleClass().add("sidebar-icon");

        noteListBtn.setGraphic(noteIcon);
        noteListBtn.getStyleClass().add("sidebar-button");


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
        FontAwesomeIconView newIcon =
                new FontAwesomeIconView(FontAwesomeIcon.PLUS);
        newIcon.setGlyphSize(26);
        newIcon.getStyleClass().add("sidebar-icon");

        Button newBtn = new Button();
        newBtn.setGraphic(newIcon);
        newBtn.getStyleClass().add("sidebar-button");


        FontAwesomeIconView saveIcon =
                new FontAwesomeIconView(FontAwesomeIcon.SAVE);
        saveIcon.setGlyphSize(26);
        saveIcon.getStyleClass().add("sidebar-icon");

        Button saveBtn = new Button();
        saveBtn.setGraphic(saveIcon);
        saveBtn.getStyleClass().add("sidebar-button");

        FontAwesomeIconView deleteIcon =
                new FontAwesomeIconView(FontAwesomeIcon.TRASH);
        deleteIcon.setGlyphSize(26);
        deleteIcon.getStyleClass().add("sidebar-icon");

        Button deleteBtn = new Button();
        deleteBtn.setGraphic(deleteIcon);
        deleteBtn.getStyleClass().add("sidebar-button");

        FontAwesomeIconView shareIcon =
                new FontAwesomeIconView(FontAwesomeIcon.SHARE);
        shareIcon.setGlyphSize(26);
        shareIcon.getStyleClass().add("sidebar-icon");

        Button shareBtn = new Button();
        shareBtn.setGraphic(shareIcon);
        shareBtn.getStyleClass().add("sidebar-button");
        VBox buttonBar = new VBox(10,noteListBtn, newBtn, deleteBtn,saveBtn,shareBtn,spacer,settingsButton);
        buttonBar.setPadding(new Insets(10));




        VBox centerBox=new VBox(15);
        centerBox.getChildren().addAll(noteContent,buttonBar);
        root.setCenter(centerBox);
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
