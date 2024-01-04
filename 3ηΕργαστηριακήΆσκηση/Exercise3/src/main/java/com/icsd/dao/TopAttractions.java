package com.icsd.dao;

public class TopAttractions {
    private String attractionName;
    private int sumOfReviews;
    private double rating;
    private String picture_url;

    public TopAttractions(String attractionName, int sumOfReviews, double rating, String picture_url) {
        this.attractionName = attractionName;
        this.sumOfReviews = sumOfReviews;
        this.rating = rating;
        this.picture_url = picture_url;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public int getSumOfReviews() {
        return sumOfReviews;
    }

    public void setSumOfReviews(int sumOfReviews) {
        this.sumOfReviews = sumOfReviews;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }    
} 
