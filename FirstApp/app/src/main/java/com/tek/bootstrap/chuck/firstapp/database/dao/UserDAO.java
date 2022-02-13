package com.tek.bootstrap.chuck.firstapp.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.tek.bootstrap.chuck.firstapp.database.entity.User2;


@Dao
public interface UserDAO {

    @Query("SELECT * FROM Users2 WHERE user_id = :user_id")
    User2 getOne(int user_id);

    @Insert
    void insert(User2 user2);

}
