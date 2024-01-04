package com.icsd.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class UserReviewPicsController {

    @FXML 
    private HBox HboxPicturesR; 
    @FXML
    void initialize() {

    }

    public void addPicture(String[] images_url) { 
        for (String image_url : images_url) {
            if(image_url!=null){
                try {
                   
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/icsd/main/view/userReviewPic.fxml"));
                    AnchorPane pRoot = loader.load();
                    UserReviewPicController pC = loader.getController();
                    pC.setPicture(image_url);
                    HboxPicturesR.getChildren().add(pRoot);
                    
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
                
            }
        }
    }

}
