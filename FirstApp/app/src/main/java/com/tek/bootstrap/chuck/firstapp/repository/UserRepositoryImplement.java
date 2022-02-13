package com.tek.bootstrap.chuck.firstapp.repository;

import com.tek.bootstrap.chuck.firstapp.database.dao.UserDAO;
import com.tek.bootstrap.chuck.firstapp.database.entity.User2;

public class UserRepositoryImplement implements User2Repository {

    UserDAO userDAO;
    @Override
    public User2 getOne(int user_id) {
        return userDAO.getOne(user_id);
    }

    @Override
    public void insert(User2 user2) {
         userDAO.insert(user2);
    }
}
