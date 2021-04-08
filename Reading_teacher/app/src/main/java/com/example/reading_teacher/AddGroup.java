package com.example.reading_teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddGroup extends AppCompatActivity implements GroupRecyclerClick {
    ArrayList<StudentAddItem> studentAddItems = new ArrayList<>();
    ArrayList<String> userIDs = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    Button addStudent;
    EditText useremail;
    Button saveGroup;
    EditText groupNameEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        getSupportActionBar().setTitle("Přidání skupiny");


        recyclerView = (RecyclerView) findViewById(R.id.students_add_recycler);

        layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new StudentsAddAdapter(studentAddItems,this);
        recyclerView.setAdapter(adapter);

        useremail = (EditText) findViewById(R.id.student_add_edittext);
        groupNameEt = (EditText)findViewById(R.id.group_name_tv);
        addStudent = (Button)findViewById(R.id.student_add_button);

        
        saveGroup = (Button)findViewById(R.id.save_group_button); 

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
        
        saveGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGroup();
            }
        });
    }



    private void addUser() {
        String url = "http://10.0.2.2/RAScripts/Teacher/UserToGroup.php";
        String studentEmail = useremail.getText().toString().trim();

        if (studentEmail.isEmpty()){
            Toast.makeText(this, "Pole je prázdné", Toast.LENGTH_SHORT).show();
        }else{
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        String userID = object.getString("user_id");
                        if (userIDs.contains(userID)){
                            Toast.makeText(AddGroup.this, "Uživatel už je v seznamu", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            StudentAddItem studentAddItem = new StudentAddItem();
                            studentAddItem.setStudentName(object.getString("jmeno"));
                            studentAddItem.setStudentEmail(object.getString("email"));
                            studentAddItems.add(studentAddItem);
                            userIDs.add(userID);
                            adapter.notifyDataSetChanged();
                            useremail.setText("");              //vymažu původní input
                            Log.d("blbost",userIDs.toString());
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }


                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", studentEmail);

                    return params;
                }
            };
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }

    }
    private void createGroup() {
        String url = "http://10.0.2.2/RAScripts/Teacher/CreateGroup.php";
        String groupName = groupNameEt.getText().toString().trim();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String userID =  sharedPreferences.getString("USER_ID","");

            if (userIDs.isEmpty()){
                Toast.makeText(this, "Zadejte platné údaje o skupině", Toast.LENGTH_SHORT).show();
            }
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    startActivity(new Intent(AddGroup.this,MainActivity.class));

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }


                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("users_array", userIDs.toString());
                    params.put("group_name",groupName);
                    params.put("teacher_id",userID);


                    return params;
                }
            };
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }

    @Override
    public void onItemClick(int position) {
        studentAddItems.remove(position);
        userIDs.remove(position);
        adapter.notifyDataSetChanged();
    }
}
