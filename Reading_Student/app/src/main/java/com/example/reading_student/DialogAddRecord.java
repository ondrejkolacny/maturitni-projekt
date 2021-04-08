package com.example.reading_student;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaSync;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DialogAddRecord extends AppCompatDialogFragment {
    private EditText editTextPages, editTextTime;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_record,null);




        builder.setView(view).setTitle("Přidání záznamu")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();


                    }
                })
                .setPositiveButton("Přidat", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        insertRecord();
                        dialog.dismiss();
                        startActivity(new Intent(getActivity(),MainActivity.class));


                    }
                });
        return builder.create();
    }


    @Override
    public void onStart() {                 //metoda navíc kvůli tomu aby šlo použít findviewbyid
        super.onStart();
        editTextPages = (EditText) getDialog().findViewById(R.id.pages_read);
        editTextTime = (EditText) getDialog().findViewById(R.id.time_read);
    }

    private void insertRecord() {
        String url = "http://10.0.2.2/RAScripts/Student/InsertRecord.php";
        Bundle bundle = getArguments();                 //získání plan_id
        String planID = bundle.getString("plan_id");


        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String userID =  sharedPreferences.getString("USER_ID","");


        final String pagesRead = editTextPages.getText().toString().trim();
        final String timeRead = editTextTime.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();
                params.put("user_id",userID);
                params.put("plan_id",planID);
                params.put("pages_read",pagesRead);
                params.put("time_reading",timeRead);
                return params;
            }
        }
                ;

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest); //možná špatně

    }
}

