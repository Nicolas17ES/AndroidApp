//package com.tek.bootstrap.chuck.firstapp;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//import androidx.lifecycle.ViewModel;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class User {
//    Context context;
//    String name;
//    String user_id;
//    String email;
//    String id;
//    Object user;
//    RequestQueue requestQueue;
//
//
//
//    public User(Context context){
//        this.context = context;
//        requestQueue = Volley.newRequestQueue(context);
//
//    }
//
//    public void setUser(String id){
//
//
//        String url = String.format("http://192.168.1.98:3001/auth/userid/" + id);
//
//        JsonObjectRequest jsObjRequest = new
//                JsonObjectRequest(Request.Method.GET,
//                url,
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            user_id = response.getString("user_id");
//                            name = response.getString("name");
//                            email = response.getString("email");
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                Log.d("dev", "doesn't works");
//            }
//        });
//        requestQueue.add(jsObjRequest);
//    }
//
//    public String getName(){
//        return this.name;
//    }
//
//}


