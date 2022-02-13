package com.tek.bootstrap.chuck.firstapp.ui.model;

public class Dog {
    private int id;
    private String name;
    private String description;
    private String type;
    private String breed;
    private double lat;
    private double lng;
    private String contactEmail;
    private int contactPhone;
    private String city;
    private String street;
    private String date;
    private String image;
    private int user_id;
    private String color;

    public Dog(String name, String description, String type, String breed, String city, String street, String contactEmail, int contactPhone, String date, int user_id){
        this.name = name;
        this.description = description;
        this.type = type;
        this.breed = breed;
        //this.lat = lat;
        // this.lng = lng;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.city = city;
        this.street = street;
        this.date = date;
       // this.image = image;
        this.user_id = user_id;
    }
    public Dog(){}

    public int getId() { return id; }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getType() { return type; }

    public void setType(String type) {
        this.type = type;
    }



    public String getBreed() { return breed; }

    public void setBreed(String breed) {
        this.breed = breed;
    }



    //lng lat

    public double getLat() { return lat; }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() { return lng; }

    public void setLng(double lng) {
        this.lng = lng;
    }

    //street city


    public String getStreet() { return street; }

    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }

    public void setCity(String city) {
        this.city = city;
    }



    public String getContactEmail() { return contactEmail; }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }



    public int getContactPhone() { return contactPhone; }

    public void setContactPhone(int contactPhone) {
        this.contactPhone = contactPhone;
    }



    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }



    public String getImage() { return image; }

    public void setImage(String image) {
        this.image = image;
    }



    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    //color
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
