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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.tek.bootstrap.chuck.firstapp.MainActivity;
import com.tek.bootstrap.chuck.firstapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText textInputEditTextPassword, textInputEditTextEmail;
    Button buttonLogIn;
    TextView textViewSignUp;
    TextView display;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final String url = "http://192.168.1.98:3001/auth/login";

        textInputEditTextEmail = getView().findViewById(R.id.email);
        textInputEditTextPassword = getView().findViewById(R.id.password);
        buttonLogIn = getView().findViewById(R.id.btnLogin);
        textViewSignUp = getView().findViewById(R.id.signUpText);
        display = getView().findViewById(R.id.displayAlert);

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password, email;
                email = String.valueOf(textInputEditTextEmail.getText());
                password = String.valueOf(textInputEditTextPassword.getText());

                if(email.matches("") || password.matches("")){
                    display.setText("Please fill in all the information");
                } else {
                    HashMap<String, String> params = new HashMap<String,String>();

                    params.put("email", email);
                    params.put("password", password);
                    JsonObjectRequest jsObjRequest = new
                            JsonObjectRequest(Request.Method.POST,
                            url,
                            new JSONObject(params),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String id = response.getString("user_id");
                                        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                                        intent.putExtra("user_id", id);
                                        startActivity(intent);
                                        Toast toast = Toast.makeText(getActivity(),"Succesfully logged in",Toast. LENGTH_SHORT);
                                        toast.setMargin(50,50);
                                        toast.show();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            display.setText("Error in credentials");
                        }
                    });
                    queue.add(jsObjRequest);
                }
            }
        });

        TextView button = (TextView) view.findViewById(R.id.signUpText);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new SignUpFragment());
                fragmentTransaction.commit();
            }
        });
    }
}