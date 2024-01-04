package com.icsd.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class AttractionResultController {

    @FXML
    private VBox attrVbox;


    @FXML
    private Label titleAttrResults;

    @FXML
    void initialize() {

    }

    public void setTitle(String title) {
        titleAttrResults.setText(title);
    }
    public VBox getVbox(){
        return attrVbox;
    }
    public void setVbox(AnchorPane attrPane){
        attrVbox.getChildren().add(attrPane);
    }
    
    

}
