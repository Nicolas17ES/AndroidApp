package com.tek.bootstrap.chuck.firstapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.tek.bootstrap.chuck.firstapp.adapters.FriendAdapter;
import com.tek.bootstrap.chuck.firstapp.adapters.UserAdapter;
import com.tek.bootstrap.chuck.firstapp.ui.model.User;
import com.tek.bootstrap.chuck.firstapp.ui.viewModel.UserViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FriendsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendsFragment extends Fragment {
    UserViewModel userViewModel;
    List<User> userList;
    RecyclerView recyclerViewFriends;
    ArrayList<User> listUsers;
    FriendAdapter friendAdapter;
    String user_id;
    String emailUser;
    int secondary_user_id;
    String user_id_two;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FriendsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FriendsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendsFragment newInstance(String param1, String param2) {
        FriendsFragment fragment = new FriendsFragment();
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

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getFriends(user_id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        recyclerViewFriends = view.findViewById(R.id.recyclerviewFriends);

        listUsers = new ArrayList<>();

        loadList();
        return view;
    }

    public void loadList(){
        userViewModel.friends.observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> friends) {
                userList = userViewModel.friends.getValue();
                displayData();
            }
        });
    }

    public void displayData() {
        recyclerViewFriends.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        friendAdapter = new FriendAdapter(getContext(), (ArrayList<User>) userList);
        recyclerViewFriends.setAdapter(friendAdapter);

        friendAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondary_user_id = userList.get(recyclerViewFriends.getChildAdapterPosition(view)).getUser_id();
                emailUser = userList.get(recyclerViewFriends.getChildAdapterPosition(view)).getEmail();
                Bundle bundle = new Bundle();
                user_id_two = String.valueOf(secondary_user_id);
                bundle.putString("id", user_id_two);
                bundle.putString("email", emailUser);

                ExternUserProfileFragment externUserProfileFragment = new ExternUserProfileFragment();
                externUserProfileFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_container , externUserProfileFragment).commit();
            }
        });

    }
}