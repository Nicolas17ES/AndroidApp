package com.tek.bootstrap.chuck.firstapp.ui.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tek.bootstrap.chuck.firstapp.ui.interfaces.UserApi;
import com.tek.bootstrap.chuck.firstapp.ui.model.Dog;
import com.tek.bootstrap.chuck.firstapp.ui.model.User;

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
    public String email;

    public void getUser(String id){

        //name.setValue("");

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
                user.setValue(authUser);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("devErrors", "failure" + t.getMessage());
            }
        });

    }

    public void getWalkers(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.98:3001/")
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
}
