package com.example.houseofcard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.net.Uri.encode;

public class ShowSubscriptionActivity extends AppCompatActivity {
    private static String USER_ID = "628zhao@gmail.com";
    private static String mJSONURLString = "https://manage-dot-pigeoncard.appspot.com/service-" +
            "manageprofile?user_id=628zhao@gmail.com";
    private TextView mTextView;
    private TextView user;

    private TextView[] textImg = new TextView[4];
    private ImageView[] img = new ImageView[4];

    private Context mContext;
    private JSONArray myJSONArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_subscription);

        mContext = getApplicationContext();

        // Set Pre-Text View
        user = (TextView) findViewById(R.id.user_name);
        user.setText(USER_ID);

        textImg[0] = (TextView) findViewById(R.id.textImg1);
        textImg[1] = (TextView) findViewById(R.id.textImg2);
        textImg[2] = (TextView) findViewById(R.id.textImg3);
        textImg[3] = (TextView) findViewById(R.id.textImg4);

        img[0] = (ImageView) findViewById(R.id.imageView1);
        img[1] = (ImageView) findViewById(R.id.imageView2);
        img[2] = (ImageView) findViewById(R.id.imageView3);
        img[3] = (ImageView) findViewById(R.id.imageView4);


        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        mJSONURLString = "https://manage-dot-pigeoncard.appspot.com/service-" +
                "manageprofile?user_id=" + USER_ID;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                mJSONURLString,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response
                        // mTextView.setText(response.toString());
                        // Process the JSON
                        try{
                            myJSONArray = response.getJSONArray("subed_house_list");
                            // textImg[1].setText("sss");
                            for (int i = 0; i < myJSONArray.length() && i < 4; i++) {
                                textImg[i].setText(myJSONArray.getJSONObject(i).getString("house_name"));
                                String pictURL = myJSONArray.getJSONObject(i).getString("cover_url");
                                Picasso.with(mContext).load(pictURL).resize(100, 100).into(img[i]);
                                final String houseName = myJSONArray.getJSONObject(i).getString("house_name");

                                img[i].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        PreStudyActivity.actionStart(ShowSubscriptionActivity.this,
                                                USER_ID, houseName);
                                    }
                                });
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
                        mTextView.setText(error.toString());
                    }
                }
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);

    }

    public static void actionStart(Context ctx, String user) {
        Intent preStudyIntent = new Intent(ctx, ShowSubscriptionActivity.class);
        USER_ID = user;
        ctx.startActivity(preStudyIntent);
    }
}
