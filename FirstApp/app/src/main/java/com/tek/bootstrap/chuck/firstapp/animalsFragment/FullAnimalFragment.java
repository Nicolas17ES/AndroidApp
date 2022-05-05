package com.tek.bootstrap.chuck.firstapp.animalsFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tek.bootstrap.chuck.firstapp.bottomNavFragments.MapsActivity;
import com.tek.bootstrap.chuck.firstapp.R;
import com.tek.bootstrap.chuck.firstapp.ui.model.User;
import com.tek.bootstrap.chuck.firstapp.ui.viewModel.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FullAnimalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FullAnimalFragment extends Fragment {;

    UserViewModel userViewModel;
    User user_data;
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
    String date;
    int user_id;
    String street;
    String image;
    String user_email;
    String user_name;

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
        date = (String) args.get("date");
        user_id = (int) args.get("user_id");
        street = (String) args.get("street");
        image = (String) args.get("image");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_full_animal, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //get user data//
        String id = String.valueOf(user_id);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUser(id);
        userViewModel.user.observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                user_data = userViewModel.user.getValue();
                user_email = user_data.getEmail();
                user_name = user_data.getName();
                Log.d("devErrors", "mail is:" + user_data.getEmail());
            }
        });


        //set animal data//

        TextView name1 = getView().findViewById(R.id.animalName);
        name1.setText(name);
        TextView breed1 = (TextView) getView().findViewById(R.id.animalBreed);
        breed1.setText(breed);
        TextView description1 = (TextView) getView().findViewById(R.id.animalDescription);
        description1.setText(description);
        TextView email1 = (TextView) getView().findViewById(R.id.animalEmailText);
        ImageView email2 = (ImageView) getView().findViewById(R.id.emailIcon);
        TextView city1 = (TextView) getView().findViewById(R.id.animalCity);
        city1.setText(city);
        TextView date1 = (TextView) getView().findViewById(R.id.animalDate);
        date1.setText(date);
        ImageView image1 = (ImageView) getView().findViewById(R.id.animalImage);
        String url = "http://192.168.1.35:3001/images/upload_images/" + image;
        Picasso.get().load(url).placeholder(R.drawable.ic_dog).into(image1);

        TextView street1 = (TextView) getView().findViewById(R.id.animalStreet);
        street1.setText(street);

        //redirect to maps//
       // ImageView icon = (ImageView) getView().findViewById(R.id.locationIcon);
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

        email1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();

            }
        });

        email2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();

            }
        });
    }

    public void sendEmail(){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ user_email});
        email.putExtra(Intent.EXTRA_SUBJECT, "Your lost dog");
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client :"));

    }
}