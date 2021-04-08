package com.example.reading_teacher;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;

public class AddPlan extends AppCompatActivity {

    EditText planNameEt, planInstructionsEt, planPagesEt, planTimeEt;
    Button createPlan;
    String groupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        getSupportActionBar().setTitle("Přidání plánu");

        Intent intent =getIntent();
        groupID=intent.getStringExtra("skupina_id");



        planNameEt = (EditText)findViewById(R.id.plan_name_edittext);
        planInstructionsEt = (EditText)findViewById(R.id.plan_instructions_edittext);
        planPagesEt = (EditText)findViewById(R.id.plan_pages_edittext);
        planTimeEt = (EditText)findViewById(R.id.plan_time_edittext);

        createPlan=(Button) findViewById(R.id.create_plan_button);
        createPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPlan();
            }
        });
    }

    private void createPlan() {
        String url = "http://10.0.2.2/RAScripts/Teacher/CreatePlan.php";

        final String planName = this.planNameEt.getText().toString().trim();
        final String planInstructions = this.planInstructionsEt.getText().toString().trim();
        final String planPages = this.planPagesEt.getText().toString().trim();
        final String planTime = this.planTimeEt.getText().toString().trim();




            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    Log.d("response",response);


                        startActivity(new Intent(AddPlan.this, MainActivity.class));




                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error","Problém2"+error.toString());

                        }


                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("nazev", planName);
                    params.put("instrukce", planInstructions);
                    params.put("strany", planPages);
                    params.put("doba", planTime);
                    params.put("skupina_id",groupID);

                    return params;
                }
            };
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}