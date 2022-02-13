package com.tek.bootstrap.chuck.firstapp.animalsFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.tek.bootstrap.chuck.firstapp.Upload_image;
import com.tek.bootstrap.chuck.firstapp.bottomNavFragments.HomeFragment;
import com.tek.bootstrap.chuck.firstapp.R;
import com.tek.bootstrap.chuck.firstapp.registration.SignUp;
import com.tek.bootstrap.chuck.firstapp.ui.viewModel.DogViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnimalFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnimalFormFragment extends Fragment {
    TextInputEditText textInputEditTextName, textInputEditTextDescription, textInputEditTextType, textInputEditTextBreed, textInputEditTextCity, textInputEditTextAddress, textInputEditTextEmail, textInputEditTextPhone, textInputEditTextDate;
    private Button postDataBtn;
    DogViewModel dogViewModel;
    int id;
    String idToChange;
    SharedPreferences preferences;
    TextView display;
    String name;
    String description;
    String type;
    String breed;
    String city;
    String street;
    String email;
    int phone;
    String date;
    int user_id;
    String idToString;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AnimalFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnimalFormFragment newInstance(String param1, String param2) {
        AnimalFormFragment fragment = new AnimalFormFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        preferences = getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        idToChange = preferences.getString("user_id", "null");

        if(idToChange != "null"){
            id = Integer.parseInt(idToChange);

        } else {
            startActivity(new Intent(getActivity(), SignUp.class));
            Toast toast = Toast. makeText(getActivity(),"LogIn to view your profile",Toast. LENGTH_LONG);
            toast.setMargin(50,50);
            toast.show();
            getActivity().overridePendingTransition(0, 0);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_animal_form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textInputEditTextName = getView().findViewById(R.id.AnimalName);
        textInputEditTextDescription = getView().findViewById(R.id.description);
        textInputEditTextType = getView().findViewById(R.id.type);
        textInputEditTextBreed = getView().findViewById(R.id.breed);
        textInputEditTextCity = getView().findViewById(R.id.city);
        textInputEditTextAddress = getView().findViewById(R.id.address);
        textInputEditTextEmail = getView().findViewById(R.id.email);
        textInputEditTextDate = getView().findViewById(R.id.date);
        postDataBtn = getView().findViewById(R.id.buttonSubmit);
        display = getView().findViewById(R.id.displayAlert);

        dogViewModel = new DogViewModel();

        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = String.valueOf(textInputEditTextName.getText());
                description = String.valueOf(textInputEditTextDescription.getText());
                type = String.valueOf(textInputEditTextType.getText());
                breed = String.valueOf(textInputEditTextBreed.getText());
                city = String.valueOf(textInputEditTextCity.getText());
                street = String.valueOf(textInputEditTextAddress.getText());
                email = String.valueOf(textInputEditTextEmail.getText());
                phone = 000;
                date = String.valueOf(textInputEditTextDate.getText());
                user_id = id;
                idToString = String.valueOf(user_id);

                if(name.matches("") || type.matches("") || city.matches("") || street.matches("")){
                    display.setText("Fill in all required fields **");
                    Toast toast = Toast.makeText(getActivity(),"Please fill in all required fields",Toast. LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                } else {
                    dogViewModel.createLostDog(name, description, type, breed, city, street, email, phone, date, user_id);
                    Intent intent = new Intent(getActivity(), Upload_image.class);
                    intent.putExtra("name", name);
                    intent.putExtra("email", email);
                    intent.putExtra("user_id", idToString);
                    startActivity(intent);
                }
            }
        });
    }
}