package com.example.reading_teacher;

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

public class MainActivity extends AppCompatActivity implements GroupRecyclerClick {
    ArrayList<GroupsListItem> groupsListItems = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    Button profile;


    String TAG = "HOJ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Moje skupiny");



        recyclerView = (RecyclerView) findViewById(R.id.groups_recycler);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new GroupsRecyclerAdapter(groupsListItems,this);
        recyclerView.setAdapter(adapter);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        //potom vymazat - jenom kvůli odhlašovacímu tlačítku  ,
profile = (Button) findViewById(R.id.start_profil);
profile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,AddGroup.class);
        startActivity(intent);
    }
});

        getData();




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

    }
    public void getData(){              //načítá data z databáze
        String url = "http://10.0.2.2/RAScripts/Teacher/TeacherGroups.php";
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String userID =  sharedPreferences.getString("USER_ID","");
        Log.d(TAG,userID);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                groupsListItems.clear();

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject planObject = jsonArray.getJSONObject(i);

                            GroupsListItem groupsListItem = new GroupsListItem();
                            groupsListItem.setGroupName(planObject.getString("nazev"));
                            groupsListItem.setGroupID(planObject.getString("skupina_id"));
                            groupsListItems.add(groupsListItem);




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
                params.put("teacher_id", userID);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }

    @Override
    public void onItemClick(int position) {

        // kdybych potřeboval zjistit, které pole bylo vybráno: nazvy.get(position);
        Intent intent = new Intent(this, PlansActivity.class);
        intent.putExtra("skupina_id",groupsListItems.get(position).getGroupID());
        startActivity(intent);
    }
}