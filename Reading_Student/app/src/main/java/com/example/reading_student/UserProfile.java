package com.example.reading_student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserProfile extends AppCompatActivity {
    private TextView levelTv, pagesTv, timeTv, usernameTv, emailTv;
    private ArrayList<Integer> rewards = new ArrayList<>();
    private ArrayList<RewardsItem> achievements = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().setTitle("");


        levelTv = (TextView) findViewById(R.id.stats_level);
        pagesTv = (TextView) findViewById(R.id.stats_pages);
        timeTv = (TextView)findViewById(R.id.stats_time);
        usernameTv = (TextView)findViewById(R.id.profile_username);
        emailTv = (TextView)findViewById(R.id.profile_email);
        recyclerView = (RecyclerView) findViewById(R.id.reward_recycler);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RewardsAdapter(achievements,this);
        recyclerView.setAdapter(adapter);


        getUserProfile();
    }

    private void getUserProfile() {
        String url = "http://10.0.2.2/RAScripts/Student/UserProfile.php";
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String userID =  sharedPreferences.getString("USER_ID","");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("odznaky");
                    for(int i =0;i<jsonArray.length();i++) {


                        rewards.add(jsonArray.getInt(i));
                    }

                    String[]names = getResources().getStringArray(R.array.reward_names);
                    String[]descs = getResources().getStringArray(R.array.reward_decsc);
                    for (int i =0;i<names.length;i++) {
                        RewardsItem rewardsItem = new RewardsItem();
                        if (rewards.contains(i)) {
                            rewardsItem.setName(names[i]);
                            rewardsItem.setDesc(descs[i]);
                            rewardsItem.setImage(getResources().getIdentifier("obr"+i,"drawable",getPackageName()));
                            achievements.add(rewardsItem);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    levelTv.setText(jsonObject.getString("level"));
                    pagesTv.setText(jsonObject.getString("strany"));
                    timeTv.setText(jsonObject.getString("doba"));
                    usernameTv.setText(jsonObject.getString("jmeno"));
                    emailTv.setText(jsonObject.getString("email"));



                } catch (JSONException e) {
                    e.printStackTrace();
                }
Log.d("odmeny",rewards.toString());
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userID);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }
}