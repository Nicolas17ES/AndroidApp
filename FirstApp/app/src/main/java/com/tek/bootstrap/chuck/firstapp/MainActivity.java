package com.tek.bootstrap.chuck.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tek.bootstrap.chuck.firstapp.animalsFragment.AnimalFormFragment;
import com.tek.bootstrap.chuck.firstapp.bottomNavFragments.HomeFragment;
import com.tek.bootstrap.chuck.firstapp.bottomNavFragments.LostAnimalsFragment;
import com.tek.bootstrap.chuck.firstapp.bottomNavFragments.MapsActivity;
import com.tek.bootstrap.chuck.firstapp.registration.SignUp;
import com.tek.bootstrap.chuck.firstapp.ui.model.User;
import com.tek.bootstrap.chuck.firstapp.ui.viewModel.UserViewModel;

public class MainActivity extends AppCompatActivity {
    Toolbar mToolbar;
    BottomNavigationView bottomNavigationView;
    UserViewModel userViewModel;
    String id;
    String user_id;
    Bundle extras;
    MenuItem logout;
    Menu menu;
    User user_data;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TOP BAR
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);




        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        if(extras != null || user_id != "null"){
                            SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                            preferences.edit().remove("user_id").commit();
                            startActivity(new Intent(getApplicationContext(), SignUp.class));
                            Toast toast = Toast.makeText(getApplicationContext(),"Succesfully logged out",Toast. LENGTH_SHORT);
                            toast.setMargin(50,50);
                            toast.show();
                        } else {
                            startActivity(new Intent(getApplicationContext(), SignUp.class));
                        }

                        overridePendingTransition(0, 0);
                        break;
                }
                return true;
            }
        });

        //SET EXTRAS
        extras = getIntent().getExtras();

       savePreferences();
       loadPreferences();

       //BOTTOM NAV BAR FLOAT BUTTON
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container , new HomeFragment()).commit();
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(extras != null || user_id != "null"){
                    Fragment fragment = new AnimalFormFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container , fragment).commit();

                } else {
                    startActivity(new Intent(getApplicationContext(), SignUp.class));
                    Toast toast = Toast. makeText(getApplicationContext(),"LogIn to view your profile",Toast. LENGTH_LONG);
                    toast.setMargin(50,50);
                    toast.show();
                    overridePendingTransition(0, 0);
                }
            }
        });
        //BOTTOM NAV BAR FLOAT BUTTON

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
                        if(extras != null || user_id != "null"){
                            fragment = new MainUserProfileFragment();
                            break;
                        } else {
                            startActivity(new Intent(getApplicationContext(), SignUp.class));
                            Toast toast = Toast. makeText(getApplicationContext(),"LogIn to view your profile",Toast. LENGTH_LONG);
                            toast.setMargin(50,50);
                            toast.show();
                            overridePendingTransition(0, 0);
                            return true;
                        }

                  case R.id.search:
                       fragment = new LostAnimalsFragment();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_menu, menu);
        menu = mToolbar.getMenu();
        logout = menu.getItem(2);
        if(extras != null || user_id != "null"){
            logout.setTitle("Log Out");
        } else {
            logout.setTitle("Log In");
        }

        return true;
    }

    private void savePreferences(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if(extras != null){
            Log.d("devErrors", "extra snot null");
            if(extras.containsKey("user_id")){
                Log.d("devErrors", "extras has key");
                id = extras.getString("user_id");
                userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
                userViewModel.getUser(id);
                userViewModel.user.observe(this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        user_data = userViewModel.user.getValue();
                    }
                });
                editor.putString("user_id", id);
                editor.commit();
            }
        }
    }


    private void loadPreferences(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        user_id = preferences.getString("user_id", "null");
        Log.d("devErrors", "main id: " + user_id);
    }
}



