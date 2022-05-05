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

    @GET("auth/walkers")
    Call <List<User>> getWalkers();

    @GET("auth/public/{id}")
    Call <List<User>> getUsers(@Path("id") String id);

    @GET("friends/received/{id}")
    Call<List<User>> getUserFromRequest(@Path("id") String id);

    @GET("friends/show/{id}")
    Call<List<User>> getFriends(@Path("id") String id);

    @POST("friends/accept")
    Call<User> acceptRequestFromUser(@Body User user);

    @GET("dogs")
    Call <List<Dog>> getDogs();

    @GET("dogs/{id}")
    Call <List<Dog>> getDogsByUserId(@Path("id") String id);

    @POST("dogs")
    Call<Dog> createLostDog(@Body Dog dog);

    @Multipart
    @POST("image")
    Call<FileModel> postImage(@Part MultipartBody.Part image, @Query("name") String name, @Query("email") String email, @Query("user_id") String user_id);

    @Multipart
    @POST("image/user")
    Call<FileModel> postImageUser(@Part MultipartBody.Part image, @Query("email") String email, @Query("user_id") String user_id);
}
