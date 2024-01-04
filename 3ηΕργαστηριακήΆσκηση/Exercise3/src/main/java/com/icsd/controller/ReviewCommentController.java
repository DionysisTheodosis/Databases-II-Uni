package com.icsd.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReviewCommentController {

    @FXML 
    private Label reviewComment; 
    @FXML 
    void initialize() {
       

    }
    public void setReviewComment(String comment){
        reviewComment.setText(comment);
    }

}