package com.tek.bootstrap.chuck.firstapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tek.bootstrap.chuck.firstapp.FriendsFragment;
import com.tek.bootstrap.chuck.firstapp.NotificationsFragment;
import com.tek.bootstrap.chuck.firstapp.R;
import com.tek.bootstrap.chuck.firstapp.ui.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> implements  View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<User> model;
    int id;
    String name;
    Button accept, reject;
    NotificationsFragment notificationsFragment;
    RequestQueue queue;
    String user_id_one;
    String user_id_two;



    //listener
    private View.OnClickListener listener;

    public NotificationsAdapter(Context context, ArrayList<User> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;

    }

    @NonNull
    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  inflater.inflate(R.layout.notifications_list, parent, false);
        view.setOnClickListener(this);


        return new NotificationsAdapter.ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.ViewHolder holder, int position) {


        name = model.get(position).getName();
        id = model.get(position).getUser_id();

        //set in view
        holder.names.setText(name);

        //Accept friend request LISTENER//
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("devErrors", "heeey");
                SharedPreferences preferences = holder.accept.getContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                user_id_two = preferences.getString("user_id", "null");
                queue = Volley.newRequestQueue(holder.accept.getContext().getApplicationContext());
                final String url = "http://192.168.1.35:3001/friends/accept";

                HashMap<String, String> params = new HashMap<String,String>();

                user_id_one = String.valueOf(id);

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
                                    Toast toast = Toast.makeText(holder.accept.getContext(),"Friend request accepted",Toast. LENGTH_SHORT);
                                    toast.show();
                                    AppCompatActivity appCompatActivity = (AppCompatActivity) holder.accept.getContext();
                                    FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.main_container, new FriendsFragment());
                                    fragmentTransaction.commit();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast toast = Toast.makeText(holder.accept.getContext(),"Friend request accepted",Toast. LENGTH_SHORT);
                        toast.show();
                        AppCompatActivity appCompatActivity = (AppCompatActivity) holder.accept.getContext();
                        FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new FriendsFragment());
                        fragmentTransaction.commit();
                    }
                });
                queue.add(jsObjRequest);
            }
        });

        //REJECT FRIEND REQUEST LISTENER//
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("devErrors", "heeey");
                SharedPreferences preferences = holder.reject.getContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                user_id_two = preferences.getString("user_id", "null");
                queue = Volley.newRequestQueue(holder.reject.getContext().getApplicationContext());
                final String url = "http://192.168.1.35:3001/friends/reject";

                HashMap<String, String> params = new HashMap<String,String>();

                user_id_one = String.valueOf(id);

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
                                    Toast toast = Toast.makeText(holder.reject.getContext(),"Friend request rejected",Toast. LENGTH_SHORT);
                                    toast.show();
                                    AppCompatActivity appCompatActivity = (AppCompatActivity) holder.reject.getContext();
                                    FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.main_container, new NotificationsFragment());
                                    fragmentTransaction.commit();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast toast = Toast.makeText(holder.reject.getContext(),"Friend request rejected",Toast. LENGTH_SHORT);
                        toast.show();
                        AppCompatActivity appCompatActivity = (AppCompatActivity) holder.reject.getContext();
                        FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new NotificationsFragment());
                        fragmentTransaction.commit();
                    }
                });
                queue.add(jsObjRequest);
            }
        });


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
        TextView names;
        Button accept, reject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            names = itemView.findViewById(R.id.requestName);
            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);

        }
    }


}
