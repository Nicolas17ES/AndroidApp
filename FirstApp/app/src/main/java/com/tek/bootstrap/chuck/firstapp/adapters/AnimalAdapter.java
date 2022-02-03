package com.tek.bootstrap.chuck.firstapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tek.bootstrap.chuck.firstapp.R;
import com.tek.bootstrap.chuck.firstapp.ui.model.Dog;

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
        String description = model.get(position).getDescription();
        id = model.get(position).getId();

        String image = model.get(position).getImage();

        //set in view
        holder.names.setText(name);
        holder.types.setText(type);
        holder.descriptions.setText(description);

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
        TextView names, types, descriptions, locationText;
        ImageView image, locationIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            names = itemView.findViewById(R.id.animalName);
            types = itemView.findViewById(R.id.animalType);
            descriptions = itemView.findViewById(R.id.animalDescription);
            image = itemView.findViewById(R.id.animalImage);

            locationIcon = itemView.findViewById(R.id.locationIcon);
            locationText = itemView.findViewById(R.id.locationText);

        }
    }

}
