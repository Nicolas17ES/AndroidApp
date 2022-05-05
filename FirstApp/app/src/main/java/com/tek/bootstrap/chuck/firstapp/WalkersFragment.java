package com.tek.bootstrap.chuck.firstapp;

import android.content.Intent;
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

import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.tek.bootstrap.chuck.firstapp.adapters.AnimalAdapter;
import com.tek.bootstrap.chuck.firstapp.adapters.WalkerAdapter;
import com.tek.bootstrap.chuck.firstapp.ui.model.Dog;
import com.tek.bootstrap.chuck.firstapp.ui.model.User;
import com.tek.bootstrap.chuck.firstapp.ui.viewModel.DogViewModel;
import com.tek.bootstrap.chuck.firstapp.ui.viewModel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalkersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalkersFragment extends Fragment {

    UserViewModel userViewModel;
    List<User>  walkerList;
    RecyclerView recyclerViewWalkers;
    ArrayList<User> listWalkers;
    WalkerAdapter walkerAdapter;
    String emailWalker;
    int secondary_user_id;
    String user_id_two;

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
        recyclerViewWalkers = view.findViewById(R.id.recyclerview3);

        listWalkers = new ArrayList<>();

        loadList();
        return view;
    }

    public void loadList(){
        userViewModel.users.observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                walkerList = userViewModel.users.getValue();
                Log.d("devErrors", "walkers are: " + walkerList);
                displayData();

            }
        });
    }

    public void displayData() {
        recyclerViewWalkers.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        walkerAdapter = new WalkerAdapter(getContext(), (ArrayList<User>) walkerList);
        recyclerViewWalkers.setAdapter(walkerAdapter);

        walkerAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailWalker = walkerList.get(recyclerViewWalkers.getChildAdapterPosition(view)).getEmail();
                secondary_user_id = walkerList.get(recyclerViewWalkers.getChildAdapterPosition(view)).getUser_id();

                new FancyGifDialog.Builder(getActivity())
                        .setTitle("")
                        .setMessage("")
                        .setTitleTextColor(R.color.greenSplash)
                        .setDescriptionTextColor(R.color.green)
                        .setNegativeBtnText("Contact Walker") // or pass it like android.R.string.cancel
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
                                bundle.putString("email", emailWalker);

                                ExternUserProfileFragment externUserProfileFragment = new ExternUserProfileFragment();
                                externUserProfileFragment.setArguments(bundle);
                                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_container , externUserProfileFragment).commit();
                            }
                        })
                        .OnNegativeClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ emailWalker});
                                email.putExtra(Intent.EXTRA_SUBJECT, "Walker Services");
                                email.setType("message/rfc822");
                                startActivity(Intent.createChooser(email, "Choose an Email client :"));
                            }
                        }).build();
            }
        });
    }
}