package com.icsd.dao;

import javafx.scene.image.Image;

public class User {
    private String username;
    private String name;
    private String lastname;
    private String email;
    private String profilePic;
    private Image[] photos;

 

    public User() {
    }

     public User(String username, String surname, String lastname, String email, String profilePic, Image[] photos) {
        this.username = username;
        this.name = surname;
        this.lastname = lastname;
        this.email = email;
        this.profilePic = profilePic;
        this.photos = photos;
    }

    public Image[] getPhotos() {
        return photos;
    }

    public void setPhotos(Image[] photos) {
        this.photos = photos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
    

}
