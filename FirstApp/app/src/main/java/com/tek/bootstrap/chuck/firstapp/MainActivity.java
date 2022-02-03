package com.tek.bootstrap.chuck.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tek.bootstrap.chuck.firstapp.R;
import com.tek.bootstrap.chuck.firstapp.animalsFragment.AnimalFormFragment;
import com.tek.bootstrap.chuck.firstapp.bottomNavFragments.HomeFragment;
import com.tek.bootstrap.chuck.firstapp.bottomNavFragments.LostAnimalsFragment;
import com.tek.bootstrap.chuck.firstapp.bottomNavFragments.MapsActivity;
import com.tek.bootstrap.chuck.firstapp.bottomNavFragments.UserProfileFragment;
import com.tek.bootstrap.chuck.firstapp.registration.SignUp;
import com.tek.bootstrap.chuck.firstapp.ui.model.User;
import com.tek.bootstrap.chuck.firstapp.ui.viewModel.UserViewModel;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    UserViewModel userViewModel;
    String id;
    User user_data;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().getExtras() != null){
            id = getIntent().getExtras().getString("user_id");
        } else{
            id;
        }



        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUser(id);

        userViewModel.user.observe(this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        user_data = userViewModel.user.getValue();
                    }
                });

                bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container , new HomeFragment()).commit();
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("user_id", user_data.getUser_id());
                Fragment fragment = new AnimalFormFragment();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container , fragment).commit();
            }
        });

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

                        if(id != null){
                            Bundle bundle = new Bundle();
                            bundle.putInt("user_id", user_data.getUser_id());
                            fragment = new UserProfileFragment();
                            fragment.setArguments(bundle);
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
}



