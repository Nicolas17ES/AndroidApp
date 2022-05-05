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
import com.tek.bootstrap.chuck.firstapp.ui.model.User;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> implements  View.OnClickListener {
    LayoutInflater inflater;
    ArrayList<User> model;
    public static int id;
    String name;
    String city;
    TextView request;
    String image;


    //listener
    private View.OnClickListener listener;

    public UserAdapter(Context context, ArrayList<User> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;

    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  inflater.inflate(R.layout.users_list, parent, false);
        view.setOnClickListener(this);


        return new UserAdapter.ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        name = model.get(position).getName();
        city = model.get(position).getCity();
        String email = model.get(position).getEmail();
        id = model.get(position).getUser_id();
        image = model.get(position).getImage();
        String url = "http://192.168.1.35:3001/images/upload_images/" + image;

        //set in view
        holder.names.setText(name);
        holder.cities.setText(city);
        Picasso.get().load(url).placeholder(R.drawable.ic_person).into(holder.images, new Callback() {
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

    public int getId() { return id; }


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
        TextView names, cities, types, dates, locationText;
        ImageView images, locationIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            names = itemView.findViewById(R.id.userName);
            cities = itemView.findViewById(R.id.userLocation);
            images = itemView.findViewById(R.id.userImage);
            locationIcon = itemView.findViewById(R.id.locationIcon);
            locationText = itemView.findViewById(R.id.locationText);

        }
    }

}
