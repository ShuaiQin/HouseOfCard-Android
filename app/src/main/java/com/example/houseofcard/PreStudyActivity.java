package com.example.houseofcard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import static android.net.Uri.encode;
import static java.lang.Thread.sleep;
import com.mindorks.test.R;

public class PreStudyActivity extends AppCompatActivity {
    private TextView textView1;
    private TextView textView2;

    private static TextView unlearnView;
    private static TextView unfamiliarView;
    private static TextView approxView;

    private static String userID;
    private static String houseName;

    private static String mJSONURLString = "http://manage-dot-pigeoncard.appspot.com/" +
            "checkstudyornot?pigeon_id=" + userID + "&house_id=" + encode(houseName);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_study);
        textView1 = (TextView) findViewById(R.id.preStudyUsername);
        textView2 = (TextView) findViewById(R.id.preStudyHouseName);

        unlearnView = (TextView) findViewById(R.id.num_of_unlearn);
        unfamiliarView = (TextView) findViewById(R.id.num_of_unfamiliar);
        approxView = (TextView) findViewById(R.id.approx_day);

        unlearnView.setText("");
        unfamiliarView.setText("");
        approxView.setText("");


        textView1.setText(userID);
        textView2.setText(houseName);

        // check if study!!!
        mJSONURLString = "http://manage-dot-pigeoncard.appspot.com/" +
                "checkstudyornot?pigeon_id=" + userID + "&house_id=" + houseName;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                mJSONURLString,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response
                        // textView7.setText(response.toString());
                        // Process the JSON
                        try{
                            if (response.getBoolean("is_studied")) {

                                String mJSONURLStringShowProgress = "http://manage-dot-pigeoncard." +
                                        "appspot.com/showprogress?pigeon_id=" + userID + "&house_id=" + houseName;
                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                                // Initialize a new JsonObjectRequest instance
                                // get request
                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                        Request.Method.GET,
                                        mJSONURLStringShowProgress,
                                        null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                // Do something with response
                                                //mTextView.setText(response.toString());
                                                // Process the JSON

                                                try{
                                                    showProgress(response.getInt("num_of_unlearn_key"),
                                                            response.getInt("approx_day_left"),
                                                            response.getInt("num_of_unfamiliar_key"));
                                                }catch (JSONException e){
                                                    e.printStackTrace();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener(){
                                            @Override
                                            // if error, set text for debug
                                            public void onErrorResponse(VolleyError error){

                                            }
                                        }
                                );

                                // Add JsonObjectRequest to the RequestQueue
                                requestQueue.add(jsonObjectRequest);
                            } else {
                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(PreStudyActivity.this);
                                View mView = getLayoutInflater().inflate(R.layout.set_schedule_pop_up, null);
                                final EditText mDay = (EditText) mView.findViewById(R.id.editText);
                                Button mConfirm = (Button) mView.findViewById(R.id.button);



                                mBuilder.setView(mView);
                                AlertDialog dialog = mBuilder.create();
                                dialog.show();

                                mConfirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                        String mJSONURLStringSetSchedule = "http://manage-dot-pigeoncard.appspot.com/setschedule?" +
                                                "pigeon_id=" + userID +
                                                "&house_id=" + houseName +
                                                "&num_per_day=" + String.valueOf(mDay.getText());
                                        // Initialize a new JsonObjectRequest instance
                                        // get request
                                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                                Request.Method.GET,
                                                mJSONURLStringSetSchedule,
                                                null,
                                                new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        // do nothing
                                                    }
                                                },
                                                new Response.ErrorListener(){
                                                    @Override
                                                    // if error, set text for debug
                                                    public void onErrorResponse(VolleyError error){
                                                        // mTextView.setText("Error when View Single Stream!");
                                                    }
                                                }
                                        );

                                        // Add JsonObjectRequest to the RequestQueue
                                        requestQueue.add(jsonObjectRequest);

                                        try {
                                            sleep(2000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        finish();
                                        startActivity(getIntent());
                                    }
                                });

//                                finish();
//                                Intent refresh = new Intent(getApplicationContext(), PreStudyActivity.class);
//                                startActivity(refresh);

                                // set schedule
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    // if error, set text for debug
                    public void onErrorResponse(VolleyError error){
                        unfamiliarView.setText(error.toString());
                    }
                }
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);

    }

    public static void showProgress(int unlearnKey, int approxDay, int unfamiliarKey) {
        unlearnView.setText(String.valueOf(unlearnKey));
        unfamiliarView.setText(String.valueOf(unfamiliarKey));
        approxView.setText(String.valueOf(approxDay));
    }

    public static void actionStart(Context ctx, String user, String house) {
        Intent preStudyIntent = new Intent(ctx, PreStudyActivity.class);
        userID = user;
        houseName = house;
        ctx.startActivity(preStudyIntent);
    }

    public void startStudy(View view) {
        ActivityTinder.actionStart(PreStudyActivity.this, userID, houseName);
    }
}
