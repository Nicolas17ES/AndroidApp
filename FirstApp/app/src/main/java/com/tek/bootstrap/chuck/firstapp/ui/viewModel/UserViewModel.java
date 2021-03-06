package com.tek.bootstrap.chuck.firstapp.ui.viewModel;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tek.bootstrap.chuck.firstapp.MainActivity;
import com.tek.bootstrap.chuck.firstapp.ui.interfaces.UserApi;
import com.tek.bootstrap.chuck.firstapp.ui.model.Dog;
import com.tek.bootstrap.chuck.firstapp.ui.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserViewModel extends ViewModel {

    public int user_id;
    public MutableLiveData<User> user = new MutableLiveData<>();
    public MutableLiveData<List<User>> users = new MutableLiveData<>();
    public MutableLiveData<List<User>> publicUsers = new MutableLiveData<>();
    public MutableLiveData<List<User>> friends = new MutableLiveData<>();
    public MutableLiveData<List<User>> requestFromUser = new MutableLiveData<>();
    public String email;

    //Get one users//

    public void getUser(String id){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.35:3001/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi userApi = retrofit.create(UserApi.class);

        Call<User> call = userApi.getUser(id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()){
                    Log.d("devErrors", "Codigo" + response.code());
                    return;
                }
                User authUser = response.body();
                Log.d("devErrors", "User" + authUser.getName());
                user.setValue(authUser);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("devErrors", "failure" + t.getMessage());
            }
        });

    }

    //Users that are public//

    public void getUsers(String id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.35:3001/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi userApi = retrofit.create(UserApi.class);

        Call<List<User>> call = userApi.getUsers(id);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()){
                    Log.d("devErrors", "Codigo" + response.code());
                    return;
                }

                List<User> publicUser = response.body();
                publicUsers.setValue(publicUser);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("devErrors", "failure" + t.getMessage());
            }
        });

    }

//users that are walkers//
    public void getWalkers(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.35:3001/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi userApi = retrofit.create(UserApi.class);

        Call<List<User>> call = userApi.getWalkers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()){
                    Log.d("devErrors", "Codigo" + response.code());
                    return;
                }
                List<User> user = response.body();
                users.setValue(user);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("devErrors", "failure" + t.getMessage());
            }
        });

    }

    //get user from friend request//

    public void getUserFromRequest(String id){
        Log.d("devErrors", "function called");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.35:3001/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi userApi = retrofit.create(UserApi.class);

        Call<List<User>> call = userApi.getUserFromRequest(id);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()){
                    Log.d("devErrors", "Codigo" + response.code());
                    return;
                }
                List<User> requestUser = response.body();
                requestFromUser.setValue(requestUser);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("devErrors", "failure" + t.getMessage());
            }
        });
    }

    //DISPLAY FRIENDS

    public void getFriends(String id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.35:3001/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi userApi = retrofit.create(UserApi.class);

        Call<List<User>> call = userApi.getFriends(id);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()){
                    Log.d("devErrors", "Codigo" + response.code());
                    return;
                }

                List<User> friendsList = response.body();
                Log.d("devErrors", "friends are: " + friendsList);

                friends.setValue(friendsList);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("devErrors", "failure" + t.getMessage());
            }
        });
    }
}
