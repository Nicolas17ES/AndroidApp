package com.tek.bootstrap.chuck.firstapp.ui.interfaces;

import com.tek.bootstrap.chuck.firstapp.ui.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserApi {
    @GET("auth/userid/{id}")
    Call<User> getUser(@Path("id") String id);
}
