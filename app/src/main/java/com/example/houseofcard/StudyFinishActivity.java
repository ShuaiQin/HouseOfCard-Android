package com.example.houseofcard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mindorks.test.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.Thread.sleep;

public class StudyFinishActivity extends AppCompatActivity {

    private static String houseName;
    private static String userID;
    private static TextView showCongrats;
    private static Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_finish);

        ctx = getApplicationContext();

        showCongrats = (TextView) findViewById(R.id.textView9);
        showCongrats.setVisibility(View.INVISIBLE);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "http://manage-dot-pigeoncard.appspot.com/checkschedulefinish?pigeon_id=" +
                        userID + "&house_id=" + houseName,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response
                        // mTextView.setText(response.toString());
                        // Process the JSON
                        try{
                            boolean isFinished = response.getBoolean("is_finished");
                            if (isFinished) {
                                showCongrats.setVisibility(View.VISIBLE);

                                ///////////////////////////////////

                                RequestQueue requestQueue = Volley.newRequestQueue(ctx);

                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                        Request.Method.GET,
                                        "http://manage-dot-pigeoncard.appspot.com/" +
                                                "service-deletesubscription?user_id=" +
                                                userID + "&delete_sub_string=" + houseName,
                                        null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                // Do something with response
                                                // mTextView.setText(response.toString());
                                                // Process the JSON
                                                try{
                                                    response.getBoolean("status");




                                                    RequestQueue requestQueue2 = Volley.newRequestQueue(ctx);

                                                    JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(
                                                            Request.Method.GET,
                                                            "http://manage-dot-pigeoncard.appspot.com/" +
                                                                    "service-createsubscription?user_id=" +
                                                                    userID + "&house_name=" + houseName,
                                                            null,
                                                            new Response.Listener<JSONObject>() {
                                                                @Override
                                                                public void onResponse(JSONObject response) {
                                                                    // Do something with response
                                                                    // mTextView.setText(response.toString());
                                                                    // Process the JSON
                                                                    try{
                                                                        response.getBoolean("status");
                                                                    }catch (JSONException e){
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            },
                                                            new Response.ErrorListener(){
                                                                @Override
                                                                // if error, set text for debug
                                                                public void onErrorResponse(VolleyError error){
                                                                    // do nothing
                                                                }
                                                            }
                                                    );

                                                    // Add JsonObjectRequest to the RequestQueue

                                                    requestQueue2.add(jsonObjectRequest2);




                                                }catch (JSONException e){
                                                    e.printStackTrace();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener(){
                                            @Override
                                            // if error, set text for debug
                                            public void onErrorResponse(VolleyError error){
                                                // do nothing
                                            }
                                        }
                                );
                                // Add JsonObjectRequest to the RequestQueue
                                requestQueue.add(jsonObjectRequest);

                                ///////////////////////////////////
                            }
                            else showCongrats.setVisibility(View.INVISIBLE);
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

        // showCongrats.setText("sssss");
        Button button=(Button) findViewById(R.id.button2);

        View.OnClickListener mFan = new View.OnClickListener(){
            public void onClick(View v) {
                ShowSubscriptionActivity.actionStart(StudyFinishActivity.this, userID);
            }
        };

        button.setOnClickListener(mFan);

//        try {
//            sleep(4000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        ShowSubscriptionActivity.actionStart(StudyFinishActivity.this, userID);
    }

    public static void actionStart(Context ctx, String user, String house){
        Intent studyFinishIntent = new Intent(ctx, StudyFinishActivity.class);
        userID = user;
        houseName = house;
        ctx.startActivity(studyFinishIntent);
    }

//    public void backToSub() {
//        finish();
//        ShowSubscriptionActivity.actionStart(StudyFinishActivity.this, userID);
//    }
}
