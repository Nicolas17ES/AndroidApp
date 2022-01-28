package com.tek.bootstrap.chuck.firstapp.ui.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tek.bootstrap.chuck.firstapp.ui.interfaces.UserApi;
import com.tek.bootstrap.chuck.firstapp.ui.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserViewModel extends ViewModel {

    public int user_id;
    public MutableLiveData<String> name = new MutableLiveData<>();
    public String email;

    public void getUser(String id){

        name.setValue("");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.98:3001/")
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
                user_id = authUser.getUser_id();
                name.setValue(authUser.getName());

                email = authUser.getEmail();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("devErrors", "failure" + t.getMessage());
            }
        });

    }
}
