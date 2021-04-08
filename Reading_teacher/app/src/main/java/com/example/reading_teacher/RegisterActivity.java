package com.example.reading_teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextName, editTextEmail, editTextPassword, editTextConfirmPwd;

    String TAG = "HOJ";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Registrace");





        editTextName = (EditText) findViewById(R.id.editText_nickName);
        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPassword = (EditText) findViewById(R.id.editText_password);
        editTextConfirmPwd = (EditText) findViewById(R.id.editText_confirmPassword);

        sharedPreferences = getSharedPreferences("USER_INFO",Context.MODE_PRIVATE);
        boolean islogged = sharedPreferences.getBoolean("ISLOGGEDIN", false);
        if(islogged) {
            validateUser();

        }else {



        findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        findViewById(R.id.textView_login).setOnClickListener(new View.OnClickListener() {               //Přepnutí na přihlašovací aktivitu
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            }
        });

        }
    }

    private void validateUser() {

        String url = "http://10.0.2.2/RAScripts/Teacher/ValidateTeacher.php";

        String userID = sharedPreferences.getString("USER_ID","");
        String authCode = sharedPreferences.getString("RANDOM_NUMBER","");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG,response);
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equals("false")){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("USER_ID");
                        editor.remove("RANDOM_NUMBER");
                        editor.putBoolean("ISLOGGEDIN",false);
                        editor.apply();
                        startActivity(new Intent(RegisterActivity.this, RegisterActivity.class));}
                    if (status.equals("success")){startActivity(new Intent(RegisterActivity.this, MainActivity.class));}







                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"Problém2"+error.toString());

                    }


                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userID);
                params.put("random_number", authCode);

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }



    private void registerUser() {

        String url = "http://10.0.2.2/RAScripts/Teacher/RegisterTeacher.php";

        final String jmeno = this.editTextName.getText().toString().trim();
        final String email = this.editTextEmail.getText().toString().trim();
        final String heslo = this.editTextPassword.getText().toString().trim();
        final String heslo1 = this.editTextConfirmPwd.getText().toString().trim();

        if (!heslo.equals(heslo1)||heslo.isEmpty()) {
            Toast.makeText(this, "Zadejte heslo znovu", Toast.LENGTH_SHORT).show();
        } else {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    Log.d(TAG,response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String userID = object.getString("user_id");
                        String radnomNumber = object.getString("random_number");



                        SharedPreferences.Editor editor = sharedPreferences.edit();                 //Vložit nově vygenerované user id do sp
                        editor.putString("USER_ID",userID);
                        editor.putString("RANDOM_NUMBER",radnomNumber);
                        editor.putBoolean("ISLOGGEDIN",true);
                        editor.apply();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG,"Problém2"+error.toString());

                        }


                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("jmeno", jmeno);
                    params.put("email", email);
                    params.put("heslo", heslo);

                    return params;
                }
            };
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }

    }

}