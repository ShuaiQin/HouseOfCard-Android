package com.example.houseofcard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mindorks.test.R;

import static java.lang.Thread.sleep;

public class StudyFinishActivity extends AppCompatActivity {

    private static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_finish);

//        try {
//            sleep(4000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        ShowSubscriptionActivity.actionStart(StudyFinishActivity.this, userID);
    }

    public static void actionStart(Context ctx, String user){
        Intent studyFinishIntent = new Intent(ctx, StudyFinishActivity.class);
        userID = user;
        ctx.startActivity(studyFinishIntent);
    }

//    public void backToSub() {
//        finish();
//        ShowSubscriptionActivity.actionStart(StudyFinishActivity.this, userID);
//    }
}
