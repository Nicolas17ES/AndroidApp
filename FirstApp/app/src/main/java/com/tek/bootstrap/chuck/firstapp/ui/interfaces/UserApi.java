package com.tek.bootstrap.chuck.firstapp.ui.interfaces;

import com.tek.bootstrap.chuck.firstapp.ui.model.Dog;
import com.tek.bootstrap.chuck.firstapp.ui.model.FileModel;
import com.tek.bootstrap.chuck.firstapp.ui.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {
    @GET("auth/userid/{id}")
    Call<User> getUser(@Path("id") String id);

    @GET("dogs")
    Call <List<Dog>> getDogs();

    @GET("dogs/{id}")
    Call <List<Dog>> getDogsByUserId(@Path("id") String id);

    @POST("dogs")
    Call<Dog> createLostDog(@Body Dog dog);

    @Multipart
    @POST("image")
    Call<FileModel> postImage(@Part MultipartBody.Part image, @Query("name") String name, @Query("email") String email, @Query("user_id") String user_id);
}
