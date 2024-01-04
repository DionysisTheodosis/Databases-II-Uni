package com.icsd.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class UserProfileReController {

    @FXML
    private Hyperlink attractionNameHyperLink;

    @FXML
    private AnchorPane commentPane;

    @FXML
    private Label creationTimeLabel;

    @FXML
    private AnchorPane imagePane;

    @FXML
    private ImageView ratingImage;

    @FXML
    void initialize() {

    }

    public Hyperlink getAttractionNameHyperLink() {
        return attractionNameHyperLink;
    }

    public AnchorPane getcommentPane() {
        return commentPane;
    }

    public Label getcreationTimeLabel() {
        return creationTimeLabel;
    }

    public ImageView getRatingImageView() {

        return ratingImage;
    }

     public AnchorPane getImagePane() {
        return imagePane;
    }

    

}
