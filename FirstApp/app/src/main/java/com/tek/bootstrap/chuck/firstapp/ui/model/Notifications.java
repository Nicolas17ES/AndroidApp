package com.tek.bootstrap.chuck.firstapp.ui.model;

import java.io.Serializable;

public class Notifications implements Serializable {
    public String name;
    public int user_id;

    public Notifications(String name, int user_id){
        this.name = name;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public int getUser_id() {
        return user_id;
    }
}


