package com.icsd.dao;

public class Review {
    private String attractionNameS;
    private String usernameS;
    private Attraction attractionName;
    private User username;
    private double rating;
    private String comment;
    private String creationTime;

    public Review() {
    }
    public Review(String attractionNameS, String usernameS, Attraction attractionName, User username, double rating, String comment, String creationTime) {
        this.attractionNameS = attractionNameS;
        this.usernameS = usernameS;
        this.attractionName = attractionName;
        this.username = username;
        this.rating = rating;
        this.comment = comment;
        this.creationTime = creationTime;
    }

    public String getAttractionNameS() {
        return attractionNameS;
    }

    public void setAttractionNameS(String attractionNameS) {
        this.attractionNameS = attractionNameS;
    }

    public String getUsernameS() {
        return usernameS;
    }

    public void setUsernameS(String usernameS) {
        this.usernameS = usernameS;
    }

    public Attraction getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(Attraction attractionName) {
        this.attractionName = attractionName;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
    
}
