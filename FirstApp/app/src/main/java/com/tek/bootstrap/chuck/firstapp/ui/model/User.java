package com.tek.bootstrap.chuck.firstapp.ui.model;

public class User {
    private int user_id;
    private String name;
    private String email;
    private Number walker;
    private String city;
    private String country;
    private Number publicProfile;
    private String image;

    public int getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Number getWalker(){return walker; }

    public String getCity() { return city; }

    public String getCountry() { return country; }

    public Number getPublicProfile() { return publicProfile; }

    public String getImage() { return image; }

    public void setImage(String image) {
        this.image = image;
    }
}
