package com.tek.bootstrap.chuck.firstapp.adapters;

import android.content.Context;
import android.media.Image;
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
import com.squareup.picasso.RequestCreator;
import com.tek.bootstrap.chuck.firstapp.R;
import com.tek.bootstrap.chuck.firstapp.ui.model.Dog;

import java.io.File;
import java.util.ArrayList;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.ViewHolder> implements  View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<Dog> model;
    int id;
    String name;


    //listener
    private View.OnClickListener listener;

    public AnimalAdapter(Context context, ArrayList<Dog> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  inflater.inflate(R.layout.animals_list, parent, false);
        view.setOnClickListener(this);


        return new ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        name = model.get(position).getName();
        String type = model.get(position).getType();
        String date = model.get(position).getDate();
        id = model.get(position).getId();
        String image = model.get(position).getImage();
        String url = "http://192.168.1.98:3001/images/upload_images/" + image;
        Log.d("devErrors", "image is " +url);




        //set in view
        holder.names.setText(name);
        holder.types.setText(type);
        holder.dates.setText(date);
        Picasso.get().load(url).placeholder(R.drawable.ic_dog).into(holder.images, new Callback() {
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

        // holder.images.setImageResource(foto);
       // Picasso.get().load(url).into(holder.images);


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
        TextView names, types, dates, locationText;
        ImageView images, locationIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            names = itemView.findViewById(R.id.animalName);
            types = itemView.findViewById(R.id.animalType);
            dates = itemView.findViewById(R.id.animalDate);
            images = itemView.findViewById(R.id.animalImage);

            locationIcon = itemView.findViewById(R.id.locationIcon);
            locationText = itemView.findViewById(R.id.locationText);

        }
    }

}
