package com.example.houseofcard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.butterknifelite.annotations.OnClick;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.listeners.ItemRemovedListener;
import com.example.test.swipe.TinderCard;
import com.mindorks.test.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ActivityTinder extends AppCompatActivity {

    private static final String TAG = "ActivityTinder";
    private static String userID;
    private static String houseName;

    private static String mJSONURLString;

    private CardOperataion co;

    @BindView(R.id.swipeView)
    private SwipePlaceHolderView mSwipView;

    private static String[][] strArray;

    // int size = strArray.length;
    int times = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinder_swipe);

        String a = getIntent().getStringExtra("key_string");

        System.out.println(a);

//        co = new CardOperataion(this);
//        co.initialize(strArray, houseName);

        co = new CardOperataion(this);

        if(co.numberOfCards(houseName)==0)
            co.initialize(strArray, houseName);

        ButterKnifeLite.bind(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipView.disableTouchSwipe();

        mSwipView.addItemRemoveListener(new ItemRemovedListener() {
            @Override
            public void onItemRemoved(int count) {
                Log.d(TAG, "onItemRemoved: sssssssssss" + count);
                //if(count == 0){
                    Log.d(TAG, "onItemRemoved: " + count);



                    if(co.numberOfCards(houseName)!=0){
                        Log.d(TAG, "onItemRemoved:asdasdasdasdasdas " + count);
                        String [][] remainCards = co.getCards(houseName);
                        String [] selectedCard = getRandomCard(remainCards);
                        mSwipView.addView(new TinderCard(selectedCard[0],selectedCard[1], houseName, co));
                    } else {
                        StudyFinishActivity.actionStart(ActivityTinder.this, userID);
                    }

//                    if(times==size){
//                        mSwipView.addView(new TinderCard("Done","", co));
//                        StudyFinishActivity.actionStart(ActivityTinder.this, userID);
//                    }
//                    else{                            // next 10 cards
//                        mSwipView.addView(new TinderCard(strArray[times][0],strArray[times][1], co));
//                        times++;
//                    }
                //}
            }
        });

        mSwipView.getBuilder()
                .setDisplayViewCount(3)
                .setIsUndoEnabled(true)
                .setWidthSwipeDistFactor(4)
                .setHeightSwipeDistFactor(6)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setSwipeMaxChangeAngle(2f)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));

        String [][] remainCards = co.getCards(houseName);
        String [] selectedCard = getRandomCard(remainCards);
        mSwipView.addView(new TinderCard(selectedCard[0],selectedCard[1], houseName, co));
        times++;

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                    mSwipView.enableTouchSwipe();

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @OnClick(R.id.rejectBtn)
    private void onRejectClick(){
        mSwipView.doSwipe(false);
    }

    @OnClick(R.id.acceptBtn)
    private void onAcceptClick(){
        mSwipView.doSwipe(true);
    }

    @SwipeIn
    private void onSwipeIn(){
        Toast.makeText(ActivityTinder.this, "Accepted!!!!",
                Toast.LENGTH_LONG).show();
    }

    public static void actionStart(Context ctx, String user, String house) {

        userID = user;
        houseName = house;

        String stringToSend="Hello";
        Intent tinderIntent = new Intent(ctx, ActivityTinder.class);

        tinderIntent.putExtra("key_string",stringToSend);

        // strArray = message;
        ctx.startActivity(tinderIntent);
    }

    public String[] getRandomCard(String[][] remainCards){
        int[] factors = new int[remainCards.length];

        for (int i = 0; i < remainCards.length; i++) {
            factors[i] = Integer.parseInt(remainCards[i][2]);
        }

        // sum all
        int sum = 0;
        for (int i = 0; i < factors.length; i++) sum += factors[i];

        // random through range
        int pivot = (int) (Math.random() * sum);

        // iterate again find the key
        sum = 0;
        int selectedIndex = 0;
        for (int i = 0; i < factors.length; i++) {
            sum += factors[i];
            if (sum >= pivot) {
                selectedIndex = i;
                break;
            }
        }

        String[] resultingCard = new String[2];
        resultingCard[0] = remainCards[selectedIndex][0];
        resultingCard[1] = remainCards[selectedIndex][1];

        return resultingCard;
    }


    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if(json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }
}
