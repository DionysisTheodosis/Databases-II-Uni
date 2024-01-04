package com.icsd.dao;

import javafx.scene.image.Image;

public class PicturesAttrraction {

    private String pictureUrl;
    private Image picture;
    private String username;

    public PicturesAttrraction() {
    }

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }

    public PicturesAttrraction(String pictureUrl, String username) {
        this.pictureUrl = pictureUrl;
        this.username = username;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
   
}
