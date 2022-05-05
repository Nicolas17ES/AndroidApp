package com.tek.bootstrap.chuck.firstapp.registration.regFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.tek.bootstrap.chuck.firstapp.MainActivity;
import com.tek.bootstrap.chuck.firstapp.R;
import com.tek.bootstrap.chuck.firstapp.UploadImageUser;
import com.tek.bootstrap.chuck.firstapp.Upload_image;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {
    TextInputEditText textInputEditTextName, textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail, textInputEditTextCity, textInputEditTextCountry;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;
    TextView DisplayText;
    TextView display, infoPublic;
    CheckBox walker, publicProfile;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final String url = "http://192.168.1.35:3001/auth/register";

        textInputEditTextName = getView().findViewById(R.id.fullname);
        textInputEditTextUsername = getView().findViewById(R.id.username);
        textInputEditTextEmail = getView().findViewById(R.id.email);
        textInputEditTextPassword = getView().findViewById(R.id.password);
        textInputEditTextCity = getView().findViewById(R.id.city);
        textInputEditTextCountry = getView().findViewById(R.id.country);
        walker = getView().findViewById(R.id.checkbox_true);
        publicProfile = getView().findViewById(R.id.checkbox_public);
        buttonSignUp = getView().findViewById(R.id.buttonSignUp);
        textViewLogin = getView().findViewById(R.id.loginText);
        progressBar = getView().findViewById(R.id.progress);
        display = getView().findViewById(R.id.displayAlert);

        publicProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FancyGifDialog.Builder(getActivity())
                        .setTitle("This will allow you to connect with other users by sending and receiving friend requests. Share information on lost animals or receive information from other users if your animal gets lost. ** Find more information on your profile settings **") // You can also send title like R.string.from_resources
                        .setMessage("** Only username and city location will be shared with other users.")
                        .setTitleTextColor(R.color.greenSplash)
                        .setDescriptionTextColor(R.color.green)
                        .setNegativeBtnText("Cancel") // or pass it like android.R.string.cancel
                        .setPositiveBtnBackground(R.color.beige)
                        .setPositiveBtnText("Ok") // or pass it like android.R.string.ok
                        .setNegativeBtnBackground(R.color.beige)
                        .setGifResource(R.drawable.ic_users_friends_svgrepo_com)   //Pass your Gif here
                        .isCancellable(true)
                        .OnPositiveClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                            }
                        })
                        .OnNegativeClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                              if(publicProfile.isChecked()){
                                  publicProfile.setChecked(false);
                              }
                            }
                        }).build();
            }
        });

        walker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FancyGifDialog.Builder(getActivity())
                        .setTitle("Walker profile will allow other users to contact you whenever they need a Dog Walker service.")
                        .setMessage("**Only your username, rating and city location will be shared with other users.")
                        .setTitleTextColor(R.color.greenSplash)
                        .setDescriptionTextColor(R.color.green)
                        .setNegativeBtnText("Cancel") // or pass it like android.R.string.cancel
                        .setPositiveBtnBackground(R.color.beige)
                        .setPositiveBtnText("Ok") // or pass it like android.R.string.ok
                        .setNegativeBtnBackground(R.color.beige)
                        .setGifResource(R.drawable.ic_dog_in_front_of_a_man_svgrepo_com)   //Pass your Gif here
                        .isCancellable(true)
                        .OnPositiveClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {

                            }
                        })
                        .OnNegativeClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                                if(walker.isChecked()){
                                    walker.setChecked(false);
                                }
                            }
                        }).build();
            }
        });


        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, username, password, email, city, country;
                Boolean isWalker, isPublic;
                name = String.valueOf(textInputEditTextName.getText());
                username = String.valueOf(textInputEditTextUsername.getText());
                email = String.valueOf(textInputEditTextEmail.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                city = String.valueOf(textInputEditTextCity.getText());
                country = String.valueOf(textInputEditTextCountry.getText());
                isWalker = walker.isChecked();
                isPublic = publicProfile.isChecked();
                Log.d("devErrors", "is walker = " + isWalker);
                String walkerString = Boolean.toString(isWalker);
                String publicString = Boolean.toString(isPublic);

                if(name.matches("") || username.matches("") || email.matches("") || password.matches("") || city.matches("") || country.matches("")){
                    display.setText("Please fill in all the information");
                } else {
                    HashMap<String, String> params = new HashMap<String,String>();

                    params.put("name", name);
                    params.put("username", username);
                    params.put("email", email);
                    params.put("password", password);
                    params.put("city", city);
                    params.put("country", country);
                    params.put("walker", walkerString);
                    params.put("publicProfile", publicString);

                    JsonObjectRequest jsObjRequest = new
                            JsonObjectRequest(Request.Method.POST,
                            url,
                            new JSONObject(params),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    String id = null;
                                    try {
                                        id = response.getString("user_id");
                                        Intent intent = new Intent(getActivity(), UploadImageUser.class);
                                        intent.putExtra("email", email);
                                        intent.putExtra("user_id", id);
                                        startActivity(intent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            display.setText("Error in credentials");
                            Log.d("dev", "doesnt works");
                        }
                    });
                    queue.add(jsObjRequest);
                }
            }
        });

        TextView button = (TextView) view.findViewById(R.id.loginText);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null);
                fragmentTransaction.replace(R.id.fragmentContainer, new LoginFragment());
                fragmentTransaction.commit();
            }
        });
    }
}