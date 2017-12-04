package com.example.houseofcard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.butterknifelite.annotations.OnClick;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.listeners.ItemRemovedListener;
import com.example.test.swipe.TinderCard;
import com.mindorks.test.R;

public class ActivityTinder extends AppCompatActivity {

    private static final String TAG = "ActivityTinder";
    private static String userID;
    private static String houseName;

    private CardOperataion co;

    @BindView(R.id.swipeView)
    private SwipePlaceHolderView mSwipView;

    String[][] strArray = {
            {"1","a"},
            {"2","b"},
            {"3","c"},
//            {"4","d"},
//            {"5","e"},
//            {"6","f"},
//            {"7","g"},
//            {"8","h"},
//            {"9","i"},
//            {"10","j"},
//            {"11","k"},
//            {"12","l"},
//            {"13","m"},
//            {"14","n"},
//            {"15","o"},
//            {"16","p"},
//            {"17","q"},
//            {"18","r"},
//            {"19","s"},
//            {"20","t"},
//            {"21","u"},
//            {"22","v"},
//            {"23","w"},
//            {"24","x"},
//            {"25","y"},
//            {"26","z"},
    };

    int size = strArray.length;
    int times = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinder_swipe);

        houseName = "test";
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
        Intent tinderIntent = new Intent(ctx, ActivityTinder.class);
        userID = user;
        houseName = house;
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
}
