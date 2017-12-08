package com.example.test.swipe;

import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.houseofcard.CardOperataion;
import com.example.houseofcard.CardStore;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeHead;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.mindorks.test.R;

/**
 * Created by janisharali on 19/08/16.
 */
@NonReusable
@Layout(R.layout.tinder_card_view)
public class TinderCard {

    private static int count=1;

    private String key_str, value_str, house;

    private CardOperataion co;

    @View(R.id.helloworld)
    private TextView profileTxt;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;

    @Click(R.id.helloworld)
    private void onClick(){
        Log.d("DEBUG", "profileImageView");
    }

    @Resolve
    private void onResolve(){
        nameAgeTxt.setText("Card" + ": " + key_str);
        locationNameTxt.setText("From House: " + house);
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("DEBUG", "onSwipedOut");
        co.updateProgress(house,key_str,false);

    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("DEBUG", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d("DEBUG", "ZZZZZZZZZZZ" + this.key_str + this.value_str);
        co.updateProgress(house,key_str,true);
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("DEBUG", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("DEBUG", "onSwipeOutState");
    }

    @SwipeHead
    private void onSwipeHead() {
        profileTxt.setText(value_str);
        Log.d("DEBUG", "onSwipeHead");
    }

    public TinderCard(String key_str, String value_str, String house , CardOperataion co){
        this.key_str = key_str;
        this.value_str = value_str;
        this.house = house;
        this.co = co;

    }
    public TinderCard(){

    }

}
