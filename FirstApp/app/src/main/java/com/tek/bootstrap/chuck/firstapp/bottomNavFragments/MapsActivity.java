package com.tek.bootstrap.chuck.firstapp.bottomNavFragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tek.bootstrap.chuck.firstapp.R;
import com.tek.bootstrap.chuck.firstapp.ui.model.Dog;
import com.tek.bootstrap.chuck.firstapp.ui.viewModel.DogViewModel;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    DogViewModel dogViewModel;
    List<Dog> dogsList;
    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();
    MarkerOptions marker;
    Intent i;
    LatLng ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dogViewModel = ViewModelProviders.of(this).get(DogViewModel.class);
        dogViewModel.getDogs();
        i = getIntent();
        ll = i.getParcelableExtra("longLat_dataProvider");


    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.d("devErrors", "dogs ready: " + dogsList);
        mMap = googleMap;

        dogViewModel.dogs.observe(this, new Observer<List<Dog>>() {
            @Override
            public void onChanged(List<Dog> dogs) {

                dogsList = dogViewModel.dogs.getValue();
                for (Dog dog: dogsList){
                    Double lat = dog.getLat();
                    Double lng = dog.getLng();
                    String title = dog.getName();
                    String snippet = dog.getDescription();

                    marker =  new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .title(title)
                            .snippet(snippet);
                    Log.d("devErrors", "markers are: " + marker);
                    mMap.addMarker(marker);
                }
            }
        });


        Log.d("devErrors", "maps data is: " + ll);

        if (i != null && ll != null)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ll, 18), 1500, null);
        } else {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(41.390205, 2.154007), 12), 1500, null);
        }
    }

}


