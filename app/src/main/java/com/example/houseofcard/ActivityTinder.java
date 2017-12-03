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
                    mSwipView.addView(new TinderCard("bias","I have a bias towards food."))
                            .addView(new TinderCard("recital","a famous pianist's recital"))
                            .addView(new TinderCard("swampy","The land is swampy."))
                            .addView(new TinderCard("delectable","These food looks delectable."))
                            .addView(new TinderCard("imitate","The girl imitates her mother."))
                            .addView(new TinderCard("morale","Employee morale is high."))
                            .addView(new TinderCard("decoration","There are many pretty decorations."))
                            .addView(new TinderCard("palette","The major colors on my palette."))
                            .addView(new TinderCard("patron","The restraurant's oldest patrons."))
                            .addView(new TinderCard("barter","He bartered the fruits for the rabbit."));
                }
            }
        });

        mSwipView.getBuilder()
//                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_VERTICAL)
                .setDisplayViewCount(3)
                .setIsUndoEnabled(true)
                .setWidthSwipeDistFactor(4)
                .setHeightSwipeDistFactor(6)
                .setSwipeDecor(new SwipeDecor()
//                        .setMarginTop(300)
//                        .setMarginLeft(100)
//                        .setViewGravity(Gravity.TOP)
                        .setPaddingTop(20)
                        .setSwipeMaxChangeAngle(2f)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));

        mSwipView.addView(new TinderCard("1","I have a bias towards food."))
                .addView(new TinderCard("2","a famous pianist's recital"))
                .addView(new TinderCard("3","The land is swampy."))
                .addView(new TinderCard("4","These food looks delectable."))
                .addView(new TinderCard("5","The girl imitates her mother."))
                .addView(new TinderCard("6","Employee morale is high."))
                .addView(new TinderCard("7","There are many pretty decorations."))
                .addView(new TinderCard("8","The major colors on my palette."))
                .addView(new TinderCard("9","The restraurant's oldest patrons."))
                .addView(new TinderCard("10","He bartered the fruits for the rabbit."));

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Thread.sleep(8000);
                    mSwipView.enableTouchSwipe();
//                    mSwipView.lockViews();
//                    Thread.currentThread().sleep(4000);
//                    mSwipView.unlockViews();
//                    Thread.currentThread().sleep(4000);
//                    mSwipView.lockViews();
//                    Thread.currentThread().sleep(4000);
//                    mSwipView.unlockViews();
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
