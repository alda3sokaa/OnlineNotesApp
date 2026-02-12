
package com.app.frontend.components;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class Editor {
    private TextArea contentArea;

    public Editor(){
        this.contentArea= new TextArea();
        this.contentArea.setWrapText(true);
    }

    public TextArea getView(){
        return contentArea;
    }

    public String getText(){
        return contentArea.getText();
    }

    public void setText(String text){
        contentArea.setText(text);
    }
}
