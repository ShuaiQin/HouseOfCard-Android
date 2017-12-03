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

                    if(size-times*10<0){
                        mSwipView.addView(new TinderCard("Done",""));
                    }
                    else if(size-times*10<10){      // next x cards (0<x<10)
                        int temp = times*10;
                        for(int i=temp;i<size;i++){
                            mSwipView.addView(new TinderCard(strArray[i][0],strArray[i][1]));
                        }
                        times++;
                    }
                    else{                            // next 10 cards
                        int temp = times*10;
                        for(int i=0;i<10;i++){
                            mSwipView.addView(new TinderCard(strArray[temp+i][0],strArray[temp+i][1]));
                        }
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

        if(size-times*10<10){
            int temp = times*10;
            for(int i=temp;i<size;i++){
                mSwipView.addView(new TinderCard(strArray[i][0],strArray[i][1]));
            }

        }
        else{
            mSwipView.addView(new TinderCard(strArray[0][0],strArray[0][1]));
            mSwipView.addView(new TinderCard(strArray[1][0],strArray[1][1]));
            mSwipView.addView(new TinderCard(strArray[2][0],strArray[2][1]));
            mSwipView.addView(new TinderCard(strArray[3][0],strArray[3][1]));
            mSwipView.addView(new TinderCard(strArray[4][0],strArray[4][1]));
            mSwipView.addView(new TinderCard(strArray[5][0],strArray[5][1]));
            mSwipView.addView(new TinderCard(strArray[6][0],strArray[6][1]));
            mSwipView.addView(new TinderCard(strArray[7][0],strArray[7][1]));
            mSwipView.addView(new TinderCard(strArray[8][0],strArray[8][1]));
            mSwipView.addView(new TinderCard(strArray[9][0],strArray[9][1]));
            times++;
        }


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
