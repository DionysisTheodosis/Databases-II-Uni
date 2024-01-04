package com.icsd.controller;

import com.icsd.util.ImageUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SmallPictureController {

    @FXML 
    private ImageView pictureReviewS;
    private String username;

    @FXML
    void initialize() {
        
    }

    public void setPicture(String image_url) {
        this.pictureReviewS.setImage(ImageUtils.initImageFromExternal(image_url));
    }

    public void setPicture(Image image) {
        this.pictureReviewS.setImage(image);
    }

    public Image getPicture() {
        return this.pictureReviewS.getImage();
    }
    
    public void setUserUpload(String uploader_Username){
        this.username=uploader_Username;
    }
    
     public String getUserUpload(){
        return this.username;
    }
     
}
