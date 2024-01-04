package com.icsd.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class UserProfileController {

    @FXML
    private VBox mainReviewsVbox;

    @FXML
    private ImageView picImageView;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label user_NameLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    void initialize() {

    }
    
    public Label getUsername(){
        return usernameLabel;
    }

    public Label getName(){
        return  user_NameLabel;
    }
    public Label getLastName(){
        return surnameLabel;
    }
    public ImageView getProfileImageView(){
        
          return picImageView;
    }
    public  VBox getReviewsVbox(){
        return mainReviewsVbox;
    }
}
