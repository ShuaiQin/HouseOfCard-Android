package com.example.houseofcard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.butterknifelite.annotations.OnClick;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.listeners.ItemRemovedListener;
import com.example.test.swipe.TinderCard;
import com.mindorks.test.R;

public class ActivityTinder extends AppCompatActivity {

    private static final String TAG = "ActivityTinder";

    @BindView(R.id.swipeView)
    private SwipePlaceHolderView mSwipView;

    String[][] strArray = {
            {"1","a"},
            {"2","b"},
            {"3","c"},
            {"4","d"},
            {"5","e"},
            {"6","f"},
            {"7","g"},
            {"8","h"},
            {"9","i"},
            {"10","j"},
            {"11","k"},
            {"12","l"},
            {"13","m"},
            {"14","n"},
            {"15","o"},
            {"16","p"},
            {"17","q"},
            {"18","r"},
            {"19","s"},
            {"20","t"},
            {"21","u"},
            {"22","v"},
            {"23","w"},
            {"24","x"},
            {"25","y"},
            {"26","z"},
    };
    int size = strArray.length;
    int times = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinder_swipe);
        ButterKnifeLite.bind(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipView.disableTouchSwipe();

        mSwipView.addItemRemoveListener(new ItemRemovedListener() {
            @Override
            public void onItemRemoved(int count) {
                Log.d(TAG, "onItemRemoved: " + count);
                if(count == 0){
                    Log.d(TAG, "onItemRemoved: " + count);

                    if(times==size){
                        mSwipView.addView(new TinderCard("Done",""));
                    }
                    else{                            // next 10 cards
                        mSwipView.addView(new TinderCard(strArray[times][0],strArray[times][1]));
                        times++;
                    }
                }
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
        
        mSwipView.addView(new TinderCard(strArray[times][0],strArray[times][1]));
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

}
