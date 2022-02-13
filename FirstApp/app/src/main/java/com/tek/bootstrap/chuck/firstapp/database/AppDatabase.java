package com.tek.bootstrap.chuck.firstapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tek.bootstrap.chuck.firstapp.database.dao.UserDAO;
import com.tek.bootstrap.chuck.firstapp.database.entity.User2;

@Database(entities = {
        User2.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase INSTANCE;

    public abstract UserDAO userDAO();

    public static AppDatabase getInstance(Context context) {
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "Dasboard")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return INSTANCE;
    }

}
