package com.example.houseofcard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mindorks.test.R;

import static java.lang.Thread.sleep;

public class StudyFinishActivity extends AppCompatActivity {

    private static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_finish);

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
