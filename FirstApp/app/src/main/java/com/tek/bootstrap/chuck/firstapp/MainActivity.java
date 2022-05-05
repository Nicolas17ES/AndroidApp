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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.tek.bootstrap.chuck.firstapp.animalsFragment.AnimalFormFragment;
import com.tek.bootstrap.chuck.firstapp.bottomNavFragments.HomeFragment;
import com.tek.bootstrap.chuck.firstapp.bottomNavFragments.MapsActivity;
import com.tek.bootstrap.chuck.firstapp.registration.SignUp;
import com.tek.bootstrap.chuck.firstapp.ui.model.User;
import com.tek.bootstrap.chuck.firstapp.ui.viewModel.UserViewModel;

import java.util.List;

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
    List<User> friendRequestUser;
    FloatingActionButton floatingActionButton;
    String requestName;
    int requestId;
    Menu notify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TOP BAR
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);


        //set click listeners to tip bar elements//
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
       notifications();


       //BOTTOM NAV BAR FLOAT BUTTON
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_container , new HomeFragment()).commit();
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //only accessible if users is logged in//
                if(extras != null || user_id != "null"){
                    Fragment fragment = new AnimalFormFragment();
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_container , fragment).commit();

                } else {
                    startActivity(new Intent(getApplicationContext(), SignUp.class));
                    Toast toast = Toast. makeText(getApplicationContext(),"LogIn to view your profile",Toast. LENGTH_LONG);
                    toast.setMargin(50,50);
                    toast.show();
                    overridePendingTransition(0, 0);
                }
            }
        });
        //BOTTOM NAV BAR FLOAT BUTTON end

        //Set on clikc listeners to bottom navigation bar//

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
                            toast.show();
                            overridePendingTransition(0, 0);
                            return true;
                        }

                   case R.id.notifications:
                       if(extras != null || user_id != "null"){
                           if(friendRequestUser != null){
                               fragment = new NotificationsFragment();
                           } else {
                               fragment = new NotificationsFragment();
                           }
                       } else {
                           Toast toast = Toast. makeText(getApplicationContext(),"LogIn or Register to access notifications",Toast. LENGTH_LONG);
                           toast.show();
                           overridePendingTransition(0, 0);
                           fragment = new HomeFragment();
                       }
                       break;

                   case R.id.map:
                       startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                       overridePendingTransition(0, 0);
                       return true;

                }
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_container, fragment).commit();
                return true;
            }
        });
    }



    //IF USER NOT LOGGED DISPLAY SMTH, OTHERWISE...//

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

    //SAVE USER ID WHEN USERS LOGGS IN//

    private void savePreferences(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if(extras != null){
            if(extras.containsKey("user_id")){
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

    //GET USER ID FROM PREFERENCES TO MAKE SURE USER REMAINS LOGGED IN//

    private void loadPreferences(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        user_id = preferences.getString("user_id", "null");

    }

    //CHECK IF LOGGED USER HAS ANY NOTIFICATIONS AVAILABNLE//
    //DISPLAY ALERT IF THERE ARE NOTIS//

    private void notifications(){
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUserFromRequest(user_id);
        userViewModel.requestFromUser.observe(this, new Observer <List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                friendRequestUser = userViewModel.requestFromUser.getValue();
                if(friendRequestUser != null){
                    bottomNavigationView.getMenu().getItem(1).setIcon(R.drawable.ic_baseline_notifications_active_24);
                    new FancyGifDialog.Builder(MainActivity.this)
                            .setTitle("New friend request")
                            .setTitleTextColor(R.color.greenSplash)
                            .setDescriptionTextColor(R.color.green)
                            .setNegativeBtnText("Cancel") // or pass it like android.R.string.cancel
                            .setPositiveBtnBackground(R.color.beige)
                            .setPositiveBtnText("Open") // or pass it like android.R.string.ok
                            .setNegativeBtnBackground(R.color.beige)
                            .setGifResource(R.drawable.ic_dog_in_front_of_a_man_svgrepo_com)   //Pass your Gif here
                            .isCancellable(true)
                            .OnPositiveClicked(new FancyGifDialogListener() {
                                @Override
                                public void OnClick() {
                                    Fragment fragment = new NotificationsFragment();
                                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_container, fragment).commit();
                                }
                            })
                            .OnNegativeClicked(new FancyGifDialogListener() {
                                @Override
                                public void OnClick() {
                                }
                            }).build();
                }
            }
        });
    }


}



