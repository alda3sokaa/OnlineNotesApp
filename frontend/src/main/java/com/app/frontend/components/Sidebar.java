package main.java.com.app.frontend.components;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Sidebar {
    public static class NoteItem {
        private String id;
        private String title;

        public NoteItem(String id, String title) {
            this.id = id;
            this.title = title;
        }
        public String getId() {
            return id;
        }

        @Override
        public String toString() {
            return title;
        }
    }
    private BorderPane root;
    private TextField searchField;
    private VBox notesListContainer;
    //private ObservableList<String>notes;
    private ListView<NoteItem> listView;
    private NoteSelectionListener listener;

    public void setNoteSelectionListener(NoteSelectionListener listener) {
        this.listener = listener;
    }

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

        searchField=new TextField();
        searchField.setPromptText("Search");

        listView = new ListView<>();
        listView.setPrefHeight(200);
        
        listView.setOnMouseClicked(event->{
            NoteItem SelectedNote =listView.getSelectionModel().getSelectedItem();
        if(SelectedNote !=null && listener!=null) {
            listener.onNoteSelected(SelectedNote.getId());
        }
            });

        VBox noteContent = new VBox(10, searchField, listView);
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
    public TextField getSearchField(){
        return searchField;
    }
    public interface NoteSelectionListener{
        void onNoteSelected(String noteId);
    }
    public void bindNotes(ObservableList<NoteItem>notesFromLogic){
        listView.setItems(notesFromLogic);
    }
}



