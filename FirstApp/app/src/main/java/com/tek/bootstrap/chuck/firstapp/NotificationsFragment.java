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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.tek.bootstrap.chuck.firstapp.adapters.FriendAdapter;
import com.tek.bootstrap.chuck.firstapp.adapters.NotificationsAdapter;
import com.tek.bootstrap.chuck.firstapp.ui.model.Notifications;
import com.tek.bootstrap.chuck.firstapp.ui.model.User;
import com.tek.bootstrap.chuck.firstapp.ui.viewModel.UserViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationsFragment extends Fragment {
    String requestName;
    String user_id_two;
    int requestId;
    String user_id_one;
    RecyclerView recyclerViewNotifications;
    ArrayList<Notifications> listNotifications;
    NotificationsAdapter notificationsAdapter;
    UserViewModel userViewModel;
    List<User> userList;
    ArrayList<User> listUsers;
    Button accept;
    RequestQueue queue;
    TextView emptyNotifications;
    boolean notyGang = false;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationsFragment newInstance(String param1, String param2) {
        NotificationsFragment fragment = new NotificationsFragment();
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
        user_id_two = preferences.getString("user_id", "null");
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUserFromRequest(user_id_two);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        //inflate recycler
        recyclerViewNotifications = view.findViewById(R.id.recyclerviewNotifications);
        loadList();

        return view;
    }

    public void loadList(){

        userViewModel.requestFromUser.observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                emptyNotifications.setText(null);
                userList = userViewModel.requestFromUser.getValue();
                displayData();
            }
        });
    }

    public void displayData() {
        recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationsAdapter = new NotificationsAdapter(getContext(), (ArrayList<User>) userList);
        recyclerViewNotifications.setAdapter(notificationsAdapter);
        notificationsAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idFromRequest = userList.get(recyclerViewNotifications.getChildAdapterPosition(view)).getUser_id();
                view.findViewById(R.id.accept).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("devErrors", "obj is:");
                        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                        final String url = "http://192.168.1.35:3001/friends/accept";

                        HashMap<String, String> params = new HashMap<String,String>();

                        user_id_one = String.valueOf(idFromRequest);

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
                                            Toast toast = Toast.makeText(getActivity(),"Friend request accepted",Toast. LENGTH_SHORT);
                                            toast.show();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Toast toast = Toast.makeText(getActivity(),"Friend request accepted",Toast. LENGTH_SHORT);
                                toast.show();
                            }
                        });
                        queue.add(jsObjRequest);
                    }
                });
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emptyNotifications = view.findViewById(R.id.emptyNotifications);
            emptyNotifications.setText("No notifications");
    }

}