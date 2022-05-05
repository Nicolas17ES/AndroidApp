package com.tek.bootstrap.chuck.firstapp.ui.viewModel;

import android.util.Log;
import android.widget.Button;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.textfield.TextInputEditText;
import com.tek.bootstrap.chuck.firstapp.ui.interfaces.UserApi;
import com.tek.bootstrap.chuck.firstapp.ui.model.Dog;
import com.tek.bootstrap.chuck.firstapp.ui.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogViewModel extends ViewModel {
    public String name;
    public MutableLiveData<List<Dog>> dogs = new MutableLiveData<>();
    public MutableLiveData<List<Dog>> userDogs = new MutableLiveData<>();
    String filePath = "";



    public void getDogs(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.35:3001/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi userApi = retrofit.create(UserApi.class);

        Call<List<Dog>> call = userApi.getDogs();

        call.enqueue(new Callback<List<Dog>>() {
            @Override
            public void onResponse(Call<List<Dog>> call, Response<List<Dog>> response) {
                if (!response.isSuccessful()){
                    Log.d("devErrors", "Codigo" + response.code());
                    return;
                }
                List<Dog> dog = response.body();
                dogs.setValue(dog);
            }

            @Override
            public void onFailure(Call<List<Dog>> call, Throwable t) {
                Log.d("devErrors", "failure" + t.getMessage());
            }
        });

    }

    public void getDogsByUserId(String id){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.35:3001/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi userApi = retrofit.create(UserApi.class);

        Call<List<Dog>> call = userApi.getDogsByUserId(id);

        call.enqueue(new Callback<List<Dog>>() {
            @Override
            public void onResponse(Call<List<Dog>> call, Response<List<Dog>> response) {
                if (!response.isSuccessful()){
                    Log.d("devErrors", "Codigo" + response.code());
                    return;
                }
                List<Dog> userDog = response.body();
                userDogs.setValue(userDog);
            }

            @Override
            public void onFailure(Call<List<Dog>> call, Throwable t) {
                Log.d("devErrors", "failure" + t.getMessage());
            }
        });

    }

    public void createLostDog(String name, String description, String type, String breed, String city, String street, String contactEmail, int contactPhone, String date, int user_id){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.35:3001/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi userApi = retrofit.create(UserApi.class);

        Dog dog = new Dog( name, description, type, breed, city, street, contactEmail, contactPhone, date, user_id);

        Call <Dog> call = userApi.createLostDog(dog);

        call.enqueue(new Callback<Dog>() {
            @Override
            public void onResponse(Call<Dog> call, Response<Dog>response) {
                if (!response.isSuccessful()){
                    Log.d("devErrors", "Codigo" + response.code());
                    return;
                }

                Log.d("devErrors", "Dog created babyyy");

            }

            @Override
            public void onFailure(Call<Dog> call, Throwable t) {
                Log.d("devErrors", "failure" + t.getMessage());
            }
        });

    }


}
