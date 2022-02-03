package com.tek.bootstrap.chuck.firstapp.animalsFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.tek.bootstrap.chuck.firstapp.bottomNavFragments.MapsActivity;
import com.tek.bootstrap.chuck.firstapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FullAnimalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FullAnimalFragment extends Fragment {;


    int id;
    String name;
    String description;
    String type;
    String breed;
    Double lat;
    Double lng;
    String email;
    int phone;
    String city;
    String image;
    int user_id;
    String street;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FullAnimalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FullAnimalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FullAnimalFragment newInstance(String param1, String param2) {
        FullAnimalFragment fragment = new FullAnimalFragment();
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
        Bundle args = getArguments();
        id = (int) args.get("id");
        name = (String) args.get("name");
        description = (String) args.get("description");
        type = (String) args.get("type");
        breed = (String) args.get("breed");
        lat = (Double) args.get("lat");
        lng = (Double) args.get("lng");
        email = (String) args.get("email");
        phone = (int) args.get("phone");
        city = (String) args.get("city");
        image = (String) args.get("image");
        user_id = (int) args.get("user_id");
        street = (String) args.get("street");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_full_animal, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        //set animal data//

        TextView name1 = getView().findViewById(R.id.animalName);
        name1.setText(name);
        TextView type1 = (TextView) getView().findViewById(R.id.animalType);
        type1.setText(type);
        TextView breed1 = (TextView) getView().findViewById(R.id.animalBreed);
        breed1.setText(breed);
        TextView description1 = (TextView) getView().findViewById(R.id.animalDescription);
        description1.setText(description);
        TextView email1 = (TextView) getView().findViewById(R.id.animalEmail);
        email1.setText(email);
        TextView city1 = (TextView) getView().findViewById(R.id.animalCity);
        city1.setText(city);
        ImageView image1 = (ImageView) getView().findViewById(R.id.animalImage);
        //
        TextView street1 = (TextView) getView().findViewById(R.id.animalStreet);
        street1.setText(street);

        //redirect to maps//
        ImageView icon = (ImageView) getView().findViewById(R.id.locationIcon);
        TextView mapsText = (TextView) getView().findViewById(R.id.locationText);

        mapsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng fromPosition = new LatLng(lat, lng);
                Bundle args = new Bundle();
                args.putParcelable("longLat_dataProvider", fromPosition);
                Intent i = new Intent(getActivity(), MapsActivity.class);
                i.putExtras(args);
                startActivity(i);

            }
        });
    }
}