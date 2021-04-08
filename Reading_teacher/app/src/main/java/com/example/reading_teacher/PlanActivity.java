package com.example.reading_teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlanActivity extends AppCompatActivity {
    private ArrayList<LeaderboardItem> leaderboardItems=new ArrayList<>();
    String planID;

    TextView planNazevTv,instrukcePole;


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        getSupportActionBar().setTitle("");

        Intent intent = getIntent();
        planID = intent.getStringExtra("plan_id");
        String planData = intent.getStringExtra("plan_data");
        try {
            JSONObject object = new JSONObject(planData);
            planNazevTv = findViewById(R.id.nazev_pole);
            instrukcePole = findViewById(R.id.instrukce_pole);
            planNazevTv.setText(object.getString("nazev"));
            instrukcePole.setText(object.getString("popis"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerView = (RecyclerView) findViewById(R.id.leaderboard_recycler);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new LeaderboardAdapter(leaderboardItems);
        recyclerView.setAdapter(adapter);



        getLeaderboard();
    }

    private void getLeaderboard() {
        String url = "http://10.0.2.2/RAScripts/LeaderboardPlan.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.d("blbost",response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            LeaderboardItem leaderboardItem = new LeaderboardItem();
                            leaderboardItem.setUser(jsonObject.getString("username"));
                            leaderboardItem.setPercent(jsonObject.getString("percent"));
                            leaderboardItems.add(leaderboardItem);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();


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
                params.put("plan_id",planID);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}