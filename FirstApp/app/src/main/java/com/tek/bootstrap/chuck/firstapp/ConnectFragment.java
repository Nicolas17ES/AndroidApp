package com.tek.bootstrap.chuck.firstapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.tek.bootstrap.chuck.firstapp.adapters.UserAdapter;
import com.tek.bootstrap.chuck.firstapp.adapters.WalkerAdapter;
import com.tek.bootstrap.chuck.firstapp.animalsFragment.FullAnimalFragment;
import com.tek.bootstrap.chuck.firstapp.bottomNavFragments.UserProfileFragment;
import com.tek.bootstrap.chuck.firstapp.ui.model.User;
import com.tek.bootstrap.chuck.firstapp.ui.viewModel.UserViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConnectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConnectFragment extends Fragment {

    UserViewModel userViewModel;
    List<User> userList;
    RecyclerView recyclerViewUsers;
    ArrayList<User> listUsers;
    UserAdapter userAdapter;
    ImageView request;
    String url;
    int secondary_user_id;
    String user_id_one, emailUser;
    String user_id_two;
    RequestQueue queue;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConnectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConnectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConnectFragment newInstance(String param1, String param2) {
        ConnectFragment fragment = new ConnectFragment();
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
        user_id_one = preferences.getString("user_id", "null");

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUsers(user_id_one);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connect, container, false);
        recyclerViewUsers = view.findViewById(R.id.recyclerview4);

        listUsers = new ArrayList<>();

        loadList();

        return view;
    }

    public void loadList(){
        userViewModel.publicUsers.observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> publicUsers) {
                userList = userViewModel.publicUsers.getValue();
                Log.d("devErrors", "publicusers are: " + userList);
                displayData();

            }
        });
    }

    public void displayData() {
        recyclerViewUsers.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        userAdapter = new UserAdapter(getContext(), (ArrayList<User>) userList);
        recyclerViewUsers.setAdapter(userAdapter);


                userAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondary_user_id = userList.get(recyclerViewUsers.getChildAdapterPosition(view)).getUser_id();
                emailUser = userList.get(recyclerViewUsers.getChildAdapterPosition(view)).getEmail();
                Log.d("devErrors", "second id is:  " + secondary_user_id);
                new FancyGifDialog.Builder(getActivity())
                        .setTitle("")
                        .setMessage("")
                        .setTitleTextColor(R.color.greenSplash)
                        .setDescriptionTextColor(R.color.green)
                        .setNegativeBtnText("Friend Request") // or pass it like android.R.string.cancel
                        .setPositiveBtnBackground(R.color.beige)
                        .setPositiveBtnText("View Profile") // or pass it like android.R.string.ok
                        .setNegativeBtnBackground(R.color.beige)
                        .setGifResource(R.drawable.ic_alligator)   //Pass your Gif here
                        .isCancellable(true)
                        .OnPositiveClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                                Bundle bundle = new Bundle();
                                user_id_two = String.valueOf(secondary_user_id);
                                bundle.putString("id", user_id_two);
                                bundle.putString("email", emailUser);

                                ExternUserProfileFragment externUserProfileFragment = new ExternUserProfileFragment();
                                externUserProfileFragment.setArguments(bundle);
                                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_container , externUserProfileFragment).commit();
                        }
                        })
                        .OnNegativeClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                                HashMap<String, String> params = new HashMap<String,String>();
                                user_id_two = String.valueOf(secondary_user_id);
                                params.put("user_id_one", user_id_one);
                                params.put("user_id_two", user_id_two);
                                JsonObjectRequest jsObjRequest = new
                                        JsonObjectRequest(Request.Method.POST,
                                        url,
                                        new JSONObject(params),
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    String id = response.getString("user_id");
                                                    Toast toast = Toast.makeText(getActivity(),"Request sended",Toast. LENGTH_SHORT);
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
                                        Toast.makeText(getActivity(),"Request sended",Toast.LENGTH_SHORT).show();

                                    }
                                });
                                queue.add(jsObjRequest);
                               // Toast.makeText(getActivity(),"Ready to connect :)",Toast.LENGTH_SHORT).show();
                            }
                        }).build();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        url = "http://192.168.1.35:3001/friends/send";

    }
}