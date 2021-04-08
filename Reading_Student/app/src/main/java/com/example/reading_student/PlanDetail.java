package com.example.reading_student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class PlanDetail extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    private TextView textViewNazev,textViewInstructions, textViewPages;
    private Button addRecordButton;
    private String text;
    private ArrayList<LeaderboardItem> leaderboardItems=new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);
        getSupportActionBar().setTitle("");

        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("plan_data");

        try {
            JSONObject object = new JSONObject(jsonArray);
            textViewNazev = findViewById(R.id.nazev_pole);
            textViewInstructions = findViewById(R.id.instrukce_pole);
            textViewPages = findViewById(R.id.strany_pole);
            textViewNazev.setText(object.getString("nazev"));
            textViewInstructions.setText(object.getString("popis"));
            textViewPages.setText(object.getString("strany"));
            text = object.getString(("plan_id"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerView = (RecyclerView) findViewById(R.id.leaderboard_recycler);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new LeaderboardAdapter(leaderboardItems);
        recyclerView.setAdapter(adapter);

        getLeaderboard();

        Log.d("tohle je text",text);
    addRecordButton = (Button) findViewById(R.id.add_pages_button);
        addRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

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
                params.put("plan_id",text);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }

    private void openDialog() {
        DialogAddRecord dialogAddRecord = new DialogAddRecord();
        Bundle bundle = new Bundle();
        bundle.putString("plan_id",text);
        dialogAddRecord.setArguments(bundle);
        dialogAddRecord.show(getSupportFragmentManager(),"example dialog");
    }
}