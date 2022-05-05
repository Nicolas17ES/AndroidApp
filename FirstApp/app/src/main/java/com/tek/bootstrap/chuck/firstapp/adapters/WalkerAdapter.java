package com.tek.bootstrap.chuck.firstapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tek.bootstrap.chuck.firstapp.R;
import com.tek.bootstrap.chuck.firstapp.ui.model.Dog;
import com.tek.bootstrap.chuck.firstapp.ui.model.User;

import java.util.ArrayList;
import java.util.List;

public class WalkerAdapter extends RecyclerView.Adapter<WalkerAdapter.ViewHolder> implements  View.OnClickListener {
    LayoutInflater inflater;
    ArrayList<User> model;
    int id;
    String name;
    String city;
    String image;


    //listener
    private View.OnClickListener listener;

    public WalkerAdapter(Context context, ArrayList<User> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;

    }

    @NonNull
    @Override
    public WalkerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  inflater.inflate(R.layout.walkers_list, parent, false);
        view.setOnClickListener(this);


        return new WalkerAdapter.ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        name = model.get(position).getName();
        String email = model.get(position).getEmail();
        city = model.get(position).getCity();
        id = model.get(position).getUser_id();
        image = model.get(position).getImage();
        String url = "http://192.168.1.35:3001/images/upload_images/" + image;

        //set in view
        holder.names.setText(name);
        holder.cities.setText(city);
        Picasso.get().load(url).placeholder(R.drawable.ic_dog_in_front_of_a_man_svgrepo_com).into(holder.images, new Callback() {
            @Override
            public void onSuccess() {
                Log.d("devErrors", "succes");
            }

            @Override
            public void onError(Exception e) {
                Log.d("devErrors", "errors is: " + e);

            }
        });
        Log.d("devErrors", "image is " +url);
        Picasso.get().setLoggingEnabled(true);
    }


    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView names, types, dates, locationText, cities;
        ImageView images, locationIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            names = itemView.findViewById(R.id.walkerName);
            cities =  itemView.findViewById(R.id.walkerLocationCity);
            images = itemView.findViewById(R.id.walkerImage);
            locationIcon = itemView.findViewById(R.id.locationIcon);
            locationText = itemView.findViewById(R.id.locationText);

        }
    }
}
