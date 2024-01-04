package com.icsd.dao;

public class AttractionReviews {

    private String attractionName;
    private String creationTime;
    private String comment;
    private double rating;
    private String username;
    private String userPicture;
    public AttractionReviews() {
    }

    public AttractionReviews(String attractionName, String creationTime, String comment, double rating, String username, String userPicture) {
        this.attractionName = attractionName;
        this.creationTime = creationTime;
        this.comment = comment;
        this.rating = rating;
        this.username = username;
        this.userPicture = userPicture;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }
    
}
