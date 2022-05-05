package com.tek.bootstrap.chuck.firstapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tek.bootstrap.chuck.firstapp.bottomNavFragments.UserProfileFragment;
import com.tek.bootstrap.chuck.firstapp.ui.model.User;
import com.tek.bootstrap.chuck.firstapp.ui.viewModel.UserViewModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExternUserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExternUserProfileFragment extends Fragment {
    public User user_data;
    UserViewModel userViewModel;
    String id, emailUser;
    TextView name;
    LinearLayout animals, contact;
    int user_id;
    String user_id_two;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExternUserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExternUserProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static ExternUserProfileFragment newInstance(String param1, String param2) {
        ExternUserProfileFragment fragment = new ExternUserProfileFragment();
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
        Bundle bundle = this.getArguments();

        if(bundle != null){
           id = bundle.getString("id");
           emailUser = bundle.getString("email");
           Log.d("devErrors", "email is " + emailUser);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_extern_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = getView().findViewById(R.id.profileName);
        animals = getView().findViewById(R.id.yourAnimals);
        contact = getView().findViewById(R.id.layoutContact);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUser(id);
        userViewModel.user.observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                user_data = userViewModel.user.getValue();
                user_id = user_data.getUser_id();
                name.setText(user_data.getName());
            }
        });

        animals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                user_id_two = String.valueOf(user_id);
                bundle.putString("id", user_id_two);

                UserProfileFragment userProfileFragment = new UserProfileFragment();
                userProfileFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_container , userProfileFragment).commit();
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailUser});
                email.putExtra(Intent.EXTRA_SUBJECT, "Found It");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
    }

}