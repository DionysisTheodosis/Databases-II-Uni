package com.icsd.dao;

public class Email_Attraction {
    private String email;
    private Attraction attractionName;

    public Email_Attraction(String email, Attraction attractionName) {
        this.email = email;
        this.attractionName = attractionName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAttractionName(Attraction attractionName) {
        this.attractionName = attractionName;
    }

    public String getEmail() {
        return email;
    }

    public Attraction getAttractionName() {
        return attractionName;
    }
    
    
}
