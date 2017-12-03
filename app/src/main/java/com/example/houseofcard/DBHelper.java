package com.example.houseofcard;

/**
 * Created by zhangyi on 2017/12/1.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "crud.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        //db.execSQL("DROP TABLE IF EXISTS " + CardStore.TABLE);

        String CREATE_TABLE_CARDS = "CREATE TABLE IF NOT EXISTS " + CardStore.TABLE  + "(" //IF NOT EXISTS
                + CardStore.UNIQUE + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + CardStore.HOUSE + " TEXT,"
                + CardStore.KEY_CARD  + " TEXT,"
                + CardStore.VALUE + " TEXT, "
                + CardStore.PROGRESS + " INTEGER )";
                //+ "PRIMARY KEY(" + CardStore.HOUSE + "," + CardStore.KEY_CARD +"))";

        db.execSQL(CREATE_TABLE_CARDS);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        //db.execSQL("DROP TABLE IF EXISTS " + CardStore.TABLE);

        // Create tables again
        onCreate(db);

    }

}
