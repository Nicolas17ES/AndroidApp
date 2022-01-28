package com.tek.bootstrap.chuck.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tek.bootstrap.chuck.firstapp.ui.viewModel.UserViewModel;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    UserViewModel userViewModel;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = getIntent().getExtras().getString("user_id");

        TextView welcome = findViewById(R.id.welcome);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUser(id);

        userViewModel.name.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("devErrors", "this is " + userViewModel.name.getValue());
                welcome.setText(userViewModel.name.getValue());
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container , new HomeFragment()).commit();

        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
               switch (item.getItemId()){
                   case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.profile:
                        fragment = new UserFragment();
                        break;
                   case R.id.map:
                       startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                       overridePendingTransition(0, 0);
                       return true;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
                return true;
            }
        });
    }
}



