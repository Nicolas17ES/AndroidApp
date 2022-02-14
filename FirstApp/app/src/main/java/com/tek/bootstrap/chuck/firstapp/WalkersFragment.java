package com.tek.bootstrap.chuck.firstapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tek.bootstrap.chuck.firstapp.ui.model.Dog;
import com.tek.bootstrap.chuck.firstapp.ui.model.User;
import com.tek.bootstrap.chuck.firstapp.ui.viewModel.DogViewModel;
import com.tek.bootstrap.chuck.firstapp.ui.viewModel.UserViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalkersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalkersFragment extends Fragment {

    UserViewModel userViewModel;
    List<User>  walkerList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WalkersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalkersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WalkersFragment newInstance(String param1, String param2) {
        WalkersFragment fragment = new WalkersFragment();
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
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getWalkers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_walkers, container, false);

        userViewModel.users.observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                walkerList = userViewModel.users.getValue();
                Log.d("devErrors", "walkers are: " + walkerList);

            }
        });

        return view;
    }
}