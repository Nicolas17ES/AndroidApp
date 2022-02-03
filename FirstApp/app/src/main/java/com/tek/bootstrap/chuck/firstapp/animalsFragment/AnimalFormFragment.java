package com.tek.bootstrap.chuck.firstapp.animalsFragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.tek.bootstrap.chuck.firstapp.bottomNavFragments.HomeFragment;
import com.tek.bootstrap.chuck.firstapp.R;
import com.tek.bootstrap.chuck.firstapp.ui.viewModel.DogViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnimalFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnimalFormFragment extends Fragment {
    TextInputEditText textInputEditTextName, textInputEditTextDescription, textInputEditTextType, textInputEditTextBreed, textInputEditTextCity, textInputEditTextAddress, textInputEditTextEmail, textInputEditTextPhone, textInputEditTextImage;
    private Button postDataBtn;
    DogViewModel dogViewModel;
    int id;
    int args;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       if(getArguments() != null){
           id = getArguments().getInt("user_id");
       } else {

       }



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
        textInputEditTextPhone = getView().findViewById(R.id.phone);
        textInputEditTextImage = getView().findViewById(R.id.image);
        postDataBtn = getView().findViewById(R.id.buttonSubmit);

        dogViewModel = new DogViewModel();

        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = String.valueOf(textInputEditTextName.getText());
                String description = String.valueOf(textInputEditTextDescription.getText());
                String type = String.valueOf(textInputEditTextType.getText());
                String breed = String.valueOf(textInputEditTextBreed.getText());
                String city = String.valueOf(textInputEditTextCity.getText());
                String street = String.valueOf(textInputEditTextAddress.getText());
                String email = String.valueOf(textInputEditTextEmail.getText());
                int phone = Integer.parseInt(textInputEditTextPhone.getText().toString());
                String image = String.valueOf(textInputEditTextImage.getText());
                int user_id = id;

                dogViewModel.createLostDog(name, description, type, breed, city, street, email, phone, image, user_id);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new HomeFragment());
                fragmentTransaction.commit();


            }
        });
    }
}