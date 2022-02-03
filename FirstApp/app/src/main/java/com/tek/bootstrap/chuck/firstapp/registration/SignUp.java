package com.tek.bootstrap.chuck.firstapp.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.android.material.textfield.TextInputEditText;
import com.tek.bootstrap.chuck.firstapp.R;
import com.tek.bootstrap.chuck.firstapp.registration.regFragments.SignUpFragment;

public class SignUp extends AppCompatActivity {
    TextInputEditText textInputEditTextName, textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;
    TextView DisplayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SignUpFragment signUpFragment = new SignUpFragment();
        fragmentTransaction.add(R.id.fragmentContainer, signUpFragment);
        fragmentTransaction.commit();

    }
}
