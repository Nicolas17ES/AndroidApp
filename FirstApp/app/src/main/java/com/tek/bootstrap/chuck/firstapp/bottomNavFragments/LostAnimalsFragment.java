package com.tek.bootstrap.chuck.firstapp.bottomNavFragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tek.bootstrap.chuck.firstapp.R;
import com.tek.bootstrap.chuck.firstapp.adapters.AnimalAdapter;
import com.tek.bootstrap.chuck.firstapp.animalsFragment.FullAnimalFragment;
import com.tek.bootstrap.chuck.firstapp.ui.model.Dog;
import com.tek.bootstrap.chuck.firstapp.ui.viewModel.DogViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LostAnimalsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LostAnimalsFragment extends Fragment {
    AnimalAdapter animalAdapter;
    RecyclerView recyclerViewAnimals;
    ArrayList<Dog> listAnimals;
    DogViewModel dogViewModel;
    List<Dog> dogsList;
    LinearLayout fragment;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LostAnimalsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LostAnimalsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LostAnimalsFragment newInstance(String param1, String param2) {
        LostAnimalsFragment fragment = new LostAnimalsFragment();
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
        // initialize animals model
        dogViewModel = ViewModelProviders.of(this).get(DogViewModel.class);
        dogViewModel.getDogs();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lost_animals, container, false);

        recyclerViewAnimals = view.findViewById(R.id.recyclerview);
        listAnimals = new ArrayList<>();
        //load the list
        loadList();
        return view;
    }

    public void loadList(){
        dogViewModel.dogs.observe(getViewLifecycleOwner(), new Observer<List<Dog>>() {
            @Override
            public void onChanged(List<Dog> dogs) {
                dogsList = dogViewModel.dogs.getValue();

                displayData();
            }
        });
    }

    public void displayData(){
        recyclerViewAnimals.setLayoutManager(new LinearLayoutManager(getContext()));
        animalAdapter = new AnimalAdapter(getContext(), (ArrayList<Dog>) dogsList);
        recyclerViewAnimals.setAdapter(animalAdapter);

        animalAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = dogsList.get(recyclerViewAnimals.getChildAdapterPosition(view)).getId();
                String name = dogsList.get(recyclerViewAnimals.getChildAdapterPosition(view)).getName();
                String type = dogsList.get(recyclerViewAnimals.getChildAdapterPosition(view)).getType();
                String breed = dogsList.get(recyclerViewAnimals.getChildAdapterPosition(view)).getBreed();
                String description = dogsList.get(recyclerViewAnimals.getChildAdapterPosition(view)).getDescription();
                Double lat = dogsList.get(recyclerViewAnimals.getChildAdapterPosition(view)).getLat();
                Double lng = dogsList.get(recyclerViewAnimals.getChildAdapterPosition(view)).getLng();
                String email = dogsList.get(recyclerViewAnimals.getChildAdapterPosition(view)).getContactEmail();
                int phone = dogsList.get(recyclerViewAnimals.getChildAdapterPosition(view)).getContactPhone();
                String city = dogsList.get(recyclerViewAnimals.getChildAdapterPosition(view)).getCity();
                String date = dogsList.get(recyclerViewAnimals.getChildAdapterPosition(view)).getDate();
                String image = dogsList.get(recyclerViewAnimals.getChildAdapterPosition(view)).getImage();
                int user_id = dogsList.get(recyclerViewAnimals.getChildAdapterPosition(view)).getUser_id();
                String street = dogsList.get(recyclerViewAnimals.getChildAdapterPosition(view)).getStreet();


                Bundle bundle = new Bundle();
                bundle.putInt( "id", id);
                bundle.putString("name", name);
                bundle.putString( "type", type);
                bundle.putString( "breed", breed);
                bundle.putString( "description", description);
                bundle.putDouble( "lat", lat);
                bundle.putDouble( "lng", lng);
                bundle.putString( "email", email);
                bundle.putInt( "phone", phone);
                bundle.putString( "city", city);
                bundle.putString( "date", date);
                bundle.putInt( "user_id", user_id);
                bundle.putString( "street", street);
                bundle.putString( "image", image);

                FullAnimalFragment fullAnimalFragment = new FullAnimalFragment();
                fullAnimalFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fullAnimalFragment).addToBackStack(null).commit();
            }
        });

    }

}