package com.icsd.dao;

public class Attraction {

    private String attractionName;
    private double entranceFee;
    private String website;
    private String attr_ProfilePic;
    private String place;
    private String attraction_type;
    private String[] picturesFReviews;
    private String map;
    private Review[] reviews;
    private AttractionReviews[] attrReviews;
    private String[] telephone;
    private String[] email;
    private int numberOfReviews;
    private double averageRating;
    private PicturesAttrraction[] allPictures;
    private OperationTime[] operationTime;
    public Attraction() {

    }

    @Override
    public String toString() {
        return "Attraction{" + "attractionName=" + attractionName + ", entranceFee=" + entranceFee + ", website=" + website + ", attr_ProfilePic=" + attr_ProfilePic + ", place=" + place + ", attraction_type=" + attraction_type + ", picturesFReviews=" + picturesFReviews + ", map=" + map + ", reviews=" + reviews + ", attrReviews=" + attrReviews + ", telephone=" + telephone + ", email=" + email + ", numberOfReviews=" + numberOfReviews + ", averageRating=" + averageRating + ", allPictures=" + allPictures + ", operationTime=" + operationTime + '}';
    }
    
    public Attraction(String attractionName, double entranceFee, String website, String attr_ProfilePic, String place, String attraction_type, String[] picturesFReviews, String map, Review[] reviews, AttractionReviews[] attrReviews, String[] telephone, String[] email, int numberOfReviews, double averageRating, PicturesAttrraction[] allPictures, OperationTime[] operationTime) {
        this.attractionName = attractionName;
        this.entranceFee = entranceFee;
        this.website = website;
        this.attr_ProfilePic = attr_ProfilePic;
        this.place = place;
        this.attraction_type = attraction_type;
        this.picturesFReviews = picturesFReviews;
        this.map = map;
        this.reviews = reviews;
        this.attrReviews = attrReviews;
        this.telephone = telephone;
        this.email = email;
        this.numberOfReviews = numberOfReviews;
        this.averageRating = averageRating;
        this.allPictures = allPictures;
        this.operationTime = operationTime;
    }

    
    public OperationTime[] getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(OperationTime[] operationTime) {
        this.operationTime = operationTime;
    }

    

    public PicturesAttrraction[] getAllPictures() {
        return allPictures;
    }

    public void setAllPictures(PicturesAttrraction[] allPictures) {
        this.allPictures = allPictures;
    }

   
    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public double getEntranceFee() {
        return entranceFee;
    }

    public void setEntranceFee(double entranceFee) {
        this.entranceFee = entranceFee;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAttr_ProfilePic() {
        return attr_ProfilePic;
    }

    public void setAttr_ProfilePic(String attr_ProfilePic) {
        this.attr_ProfilePic = attr_ProfilePic;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getAttraction_type() {
        return attraction_type;
    }

    public void setAttraction_type(String attraction_type) {
        this.attraction_type = attraction_type;
    }

    public String[] getPicturesFReviews() {
        return picturesFReviews;
    }

    public void setPicturesFReviews(String[] picturesFReviews) {
        this.picturesFReviews = picturesFReviews;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public Review[] getReviews() {
        return reviews;
    }

    public void setReviews(Review[] reviews) {
        this.reviews = reviews;
    }

    public String[] getTelephone() {
        return telephone;
    }

    public void setTelephone(String[] telephone) {
        this.telephone = telephone;
    }

    public String[] getEmail() {
        return email;
    }

    public void setEmail(String[] email) {
        this.email = email;
    }

    public AttractionReviews[] getAttrReviews() {
        return attrReviews;
    }

    public void setAttrReviews(AttractionReviews[] attrReviews) {
        this.attrReviews = attrReviews;
    }

}
