package com.example.reading_teacher;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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

public class PlansActivity extends AppCompatActivity implements GroupRecyclerClick {
    ArrayList<PlansListItem>plansListItems = new ArrayList<>();
    ArrayList<String> teacherPlans = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    String groupID;
    Button addPlanButton;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        getSupportActionBar().setTitle("Plány");




        Intent intent = getIntent();
        groupID = intent.getStringExtra("skupina_id");



        recyclerView = (RecyclerView) findViewById(R.id.plans_recycler);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PlansRecyclerAdapter(plansListItems,this);
        recyclerView.setAdapter(adapter);

        getTeacherPlans();

        addPlanButton = (Button) findViewById(R.id.add_plan_button);
        addPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(PlansActivity.this, AddPlan.class);
                intent1.putExtra("skupina_id",groupID);
                startActivity(intent1);
            }
        });
    }

    private void getTeacherPlans() {
        String url = "http://10.0.2.2/RAScripts/Teacher/TeacherPlans.php";
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String userID =  sharedPreferences.getString("USER_ID","");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                plansListItems.clear();

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject planObject = jsonArray.getJSONObject(i);

                            PlansListItem plansListItem = new PlansListItem();
                            plansListItem.setPlanName(planObject.getString("nazev"));
                            plansListItem.setPlanID(planObject.getString("plan_id"));
                            plansListItems.add(plansListItem);
                            teacherPlans.add(planObject.toString());




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
                params.put("skupina_id", groupID);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, PlanActivity.class);
        intent.putExtra("plan_id",plansListItems.get(position).getPlanID());
        intent.putExtra("plan_data",teacherPlans.get(position));
        startActivity(intent);
    }
}