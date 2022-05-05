package com.tek.bootstrap.chuck.firstapp;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.tek.bootstrap.chuck.firstapp.ui.model.User;
import com.tek.bootstrap.chuck.firstapp.ui.viewModel.UserViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    Button editProfile, editPassword, editPublic, editWalker;
    String user_id, user_name, user_email, user_country, user_city;
    int user_public, user_walker;
    UserViewModel userViewModel;
    User user_data;
    private String pass1 = "";
    private String pass2 = "";
    private String oldPass = "";
    String walker, publicProfile;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        SharedPreferences preferences = getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        user_id = preferences.getString("user_id", "null");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //get the spinner from the xml.
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUser(user_id);
        userViewModel.user.observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                user_data = userViewModel.user.getValue();
                user_name = user_data.getName();
                user_email = user_data.getEmail();
                user_city = user_data.getCity();
                user_country = user_data.getCountry();
                user_public = user_data.getPublicProfile().intValue();
                Log.d("devErrors", "user is:" + user_public);
                user_walker = user_data.getWalker().intValue();
            }
        });

        editProfile = getView().findViewById(R.id.editButton);
        editPassword = getView().findViewById(R.id.editPassword);
        editPublic = getView().findViewById(R.id.editPublic);
        editWalker = getView().findViewById(R.id.editWalker);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                

            }
        });

        editPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Edit");
                LinearLayout lila1= new LinearLayout(getActivity());
                lila1.setOrientation(LinearLayout.VERTICAL);

                final EditText oldPassword = new EditText(getActivity());
                oldPassword.setHint("Old Password");
                final EditText newPassword = new EditText(getActivity());
                newPassword.setHint("New Password");
                final EditText repeatNewPassword = new EditText(getActivity());
                repeatNewPassword.setHint("Repeat New Password");

                lila1.addView(oldPassword);
                lila1.addView(newPassword);
                lila1.addView(repeatNewPassword);
                builder.setView(lila1);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        oldPass = oldPassword.getText().toString();
                        pass1 = newPassword.getText().toString();
                        pass2 = repeatNewPassword.getText().toString();
                        if(pass1 == "" || pass2 == ""){
                            Toast.makeText(getActivity(), "Fill in all fields", Toast.LENGTH_SHORT).show();
                        } else if (pass1.equals(pass2)){
                            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                            final String url = "http://192.168.1.35:3001/auth/user/update/password";

                            HashMap<String, String> params = new HashMap<String,String>();

                            params.put("user_id", user_id);
                            params.put("email", user_email);
                            params.put("password", oldPass);
                            params.put("newPassword", pass1);
                            JsonObjectRequest jsObjRequest = new
                                    JsonObjectRequest(Request.Method.POST,
                                    url,
                                    new JSONObject(params),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                String id = response.getString("user_id");
                                                Toast toast = Toast.makeText(getActivity(),"Password changed",Toast. LENGTH_SHORT);
                                                toast.show();

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                    Toast toast = Toast.makeText(getActivity(),"Password changed",Toast. LENGTH_SHORT);
                                    toast.show();
                                }
                            });
                            queue.add(jsObjRequest);


                        } else {
                            Toast.makeText(getActivity(), "Passwords are different", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });

        editPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user_public == 1){
                    new FancyGifDialog.Builder(getActivity())
                            .setTitle("Change to private")
                            .setMessage("")
                            .setTitleTextColor(R.color.greenSplash)
                            .setDescriptionTextColor(R.color.green)
                            .setNegativeBtnText("Cancel") // or pass it like android.R.string.cancel
                            .setPositiveBtnBackground(R.color.greenSplash)
                            .setPositiveBtnText("Yes") // or pass it like android.R.string.ok
                            .setNegativeBtnBackground(R.color.red)
                            .setGifResource(R.drawable.ic_alligator)   //Pass your Gif here
                            .isCancellable(true)
                            .OnPositiveClicked(new FancyGifDialogListener() {
                                @Override
                                public void OnClick() {
                                    RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                                    final String url = "http://192.168.1.35:3001/auth/user/update/public&walker";

                                    HashMap<String, String> params = new HashMap<String,String>();
                                    params.put("user_id", user_id);
                                    if(user_walker == 1){
                                        walker = "true";
                                    } else {
                                        walker = "false";
                                    }
                                    params.put("walker", walker);
                                    params.put("publicProfile", "false");

                                    JsonObjectRequest jsObjRequest = new
                                            JsonObjectRequest(Request.Method.POST,
                                            url,
                                            new JSONObject(params),
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        String id = response.getString("user_id");
                                                        Toast toast = Toast.makeText(getActivity(),"Password changed",Toast. LENGTH_SHORT);
                                                        toast.show();

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            Toast toast = Toast.makeText(getActivity(),"Password changed",Toast. LENGTH_SHORT);
                                            toast.show();
                                        }
                                    });
                                    queue.add(jsObjRequest);
                                }
                            })
                            .OnNegativeClicked(new FancyGifDialogListener() {
                                @Override
                                public void OnClick() {
                                }
                            }).build();
                } else {
                    new FancyGifDialog.Builder(getActivity())
                            .setTitle("Change to public")
                            .setMessage("")
                            .setTitleTextColor(R.color.greenSplash)
                            .setDescriptionTextColor(R.color.green)
                            .setNegativeBtnText("Cancel") // or pass it like android.R.string.cancel
                            .setPositiveBtnBackground(R.color.greenSplash)
                            .setPositiveBtnText("Yes") // or pass it like android.R.string.ok
                            .setNegativeBtnBackground(R.color.red)
                            .setGifResource(R.drawable.ic_alligator)   //Pass your Gif here
                            .isCancellable(true)
                            .OnPositiveClicked(new FancyGifDialogListener() {
                                @Override
                                public void OnClick() {
                                    RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                                    final String url = "http://192.168.1.35:3001/auth/user/update/public&walker";

                                    HashMap<String, String> params = new HashMap<String,String>();
                                    params.put("user_id", user_id);
                                    if(user_walker == 1){
                                        walker = "true";
                                    } else {
                                        walker = "false";
                                    }
                                    params.put("walker", walker);
                                    params.put("publicProfile", "true");

                                    JsonObjectRequest jsObjRequest = new
                                            JsonObjectRequest(Request.Method.POST,
                                            url,
                                            new JSONObject(params),
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        String id = response.getString("user_id");
                                                        Toast toast = Toast.makeText(getActivity(),"Password changed",Toast. LENGTH_SHORT);
                                                        toast.show();

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            Toast toast = Toast.makeText(getActivity(),"Password changed",Toast. LENGTH_SHORT);
                                            toast.show();
                                        }
                                    });
                                    queue.add(jsObjRequest);
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

        editWalker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user_walker == 1){
                    new FancyGifDialog.Builder(getActivity())
                            .setTitle("Quit walker profile")
                            .setMessage("")
                            .setTitleTextColor(R.color.greenSplash)
                            .setDescriptionTextColor(R.color.green)
                            .setNegativeBtnText("Cancel") // or pass it like android.R.string.cancel
                            .setPositiveBtnBackground(R.color.greenSplash)
                            .setPositiveBtnText("Yes") // or pass it like android.R.string.ok
                            .setNegativeBtnBackground(R.color.red)
                            .setGifResource(R.drawable.ic_alligator)   //Pass your Gif here
                            .isCancellable(true)
                            .OnPositiveClicked(new FancyGifDialogListener() {
                                @Override
                                public void OnClick() {
                                    RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                                    final String url = "http://192.168.1.35:3001/auth/user/update/public&walker";

                                    HashMap<String, String> params = new HashMap<String,String>();
                                    params.put("user_id", user_id);
                                    if(user_public == 1){
                                        publicProfile = "true";
                                    } else {
                                        publicProfile = "false";
                                    }
                                    params.put("walker", "false");
                                    params.put("publicProfile", publicProfile);

                                    JsonObjectRequest jsObjRequest = new
                                            JsonObjectRequest(Request.Method.POST,
                                            url,
                                            new JSONObject(params),
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        String id = response.getString("user_id");
                                                        Toast toast = Toast.makeText(getActivity(),"Password changed",Toast. LENGTH_SHORT);
                                                        toast.show();

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            Toast toast = Toast.makeText(getActivity(),"Password changed",Toast. LENGTH_SHORT);
                                            toast.show();
                                        }
                                    });
                                    queue.add(jsObjRequest);
                                }
                            })
                            .OnNegativeClicked(new FancyGifDialogListener() {
                                @Override
                                public void OnClick() {
                                }
                            }).build();
                } else {
                    new FancyGifDialog.Builder(getActivity())
                            .setTitle("Change to walker profile")
                            .setMessage("")
                            .setTitleTextColor(R.color.greenSplash)
                            .setDescriptionTextColor(R.color.green)
                            .setNegativeBtnText("Cancel") // or pass it like android.R.string.cancel
                            .setPositiveBtnBackground(R.color.greenSplash)
                            .setPositiveBtnText("Yes") // or pass it like android.R.string.ok
                            .setNegativeBtnBackground(R.color.red)
                            .setGifResource(R.drawable.ic_alligator)   //Pass your Gif here
                            .isCancellable(true)
                            .OnPositiveClicked(new FancyGifDialogListener() {
                                @Override
                                public void OnClick() {
                                    RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                                    final String url = "http://192.168.1.35:3001/auth/user/update/public&walker";

                                    HashMap<String, String> params = new HashMap<String,String>();
                                    params.put("user_id", user_id);
                                    if(user_public == 1){
                                        publicProfile = "true";
                                    } else {
                                        publicProfile = "false";
                                    }
                                    params.put("walker", "true");
                                    params.put("publicProfile", publicProfile);

                                    JsonObjectRequest jsObjRequest = new
                                            JsonObjectRequest(Request.Method.POST,
                                            url,
                                            new JSONObject(params),
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        String id = response.getString("user_id");
                                                        Toast toast = Toast.makeText(getActivity(),"Password changed",Toast. LENGTH_SHORT);
                                                        toast.show();

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            Toast toast = Toast.makeText(getActivity(),"Password changed",Toast. LENGTH_SHORT);
                                            toast.show();
                                        }
                                    });
                                    queue.add(jsObjRequest);
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