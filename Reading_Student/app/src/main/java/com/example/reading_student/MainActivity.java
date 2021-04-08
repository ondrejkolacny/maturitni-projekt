package com.example.reading_student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener {
    ArrayList<DataToList> nazvy = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;
String TAG = "Main";
private ArrayList<String> planDatas = new ArrayList<>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Zadané plány");

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);


        recyclerView = (RecyclerView) findViewById(R.id.Recycler_View);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapter(nazvy,this);
        recyclerView.setAdapter(adapter);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);

        

        getData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });



        findViewById(R.id.logoutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("USER_ID");
                editor.putBoolean("ISLOGGEDIN",false);
                editor.apply();
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
        findViewById(R.id.start_profil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,UserProfile.class));
            }
        });
    }
public void getData(){              //načítá data z databáze
    String url = "http://10.0.2.2/RAScripts/Student/StudentPlans.php";
    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
    String userID =  sharedPreferences.getString("USER_ID","");
    Log.d(TAG,userID);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            nazvy.clear();
            planDatas.clear();

            try {
                JSONArray jsonArray = new JSONArray(response);

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject planObject = jsonArray.getJSONObject(i);

                        DataToList dataToList = new DataToList();
                        dataToList.setPlanName(planObject.getString("nazev"));
                        dataToList.setGroupName("skupina_nazev");
                        nazvy.add(dataToList);
                        planDatas.add(planObject.toString());



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);


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

    @Override
    public void onItemClick(int position) {


        Intent intent = new Intent(this, PlanDetail.class);
        intent.putExtra("plan_data",planDatas.get(position));
        startActivity(intent);
        Log.d(TAG, planDatas.get(position));
    }
 }







