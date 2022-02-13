package com.tek.bootstrap.chuck.firstapp.repository;

import com.tek.bootstrap.chuck.firstapp.database.entity.User2;

public interface User2Repository {

    User2 getOne(int user_id);
    void insert(User2 user2);
}
