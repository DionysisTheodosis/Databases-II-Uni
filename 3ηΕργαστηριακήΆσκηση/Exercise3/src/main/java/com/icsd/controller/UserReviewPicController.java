package com.icsd.controller;

import com.icsd.util.ImageUtils;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class UserReviewPicController {

    @FXML
    private ImageView user_R_Iv;

    @FXML
    void initialize() {

    }

    public void setPicture(String image_url) {
        image_url = image_url.substring(0, image_url.length() - 4);
        image_url += "_93x61.jpg";
        this.user_R_Iv.setImage(ImageUtils.initImageFromExternal(image_url));
    }
}
