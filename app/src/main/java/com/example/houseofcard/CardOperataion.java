package com.example.houseofcard;

/**
 * Created by zhangyi on 2017/12/1.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;

public class CardOperataion {
    private DBHelper dbHelper;
    public CardOperataion(Context context) {
        dbHelper = new DBHelper(context);
        //initialize();
        //get();
    }

    /*
    public static final String TABLE = "Cards";
    public static final String HOUSE = "house_name";
    public static final String KEY_CARD = "card_key";
    public static final String VALUE = "card_value";
    public static final String PROGRESS = "progress";
     */

    public void initialize(String [][] cards, String house){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //String house = "Uber";
        db.delete(CardStore.TABLE,CardStore.HOUSE+"=?", new String[]{house});

        for(int i=0; i<cards.length;i++){
            ContentValues values = new ContentValues();
            values.put(CardStore.HOUSE, house);
            values.put(CardStore.KEY_CARD, cards[i][0]);
            values.put(CardStore.VALUE, cards[i][1]);
            values.put(CardStore.PROGRESS, 50);
            db.insert(CardStore.TABLE, null, values);
        }

        // Inserting Row
        db.close(); // Closing database connection
    }

    public void getTest(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                CardStore.HOUSE + "," +
                CardStore.KEY_CARD + "," +
                CardStore.VALUE + "," +
                CardStore.PROGRESS +
                " FROM " + CardStore.TABLE
                + " WHERE " +
                CardStore.HOUSE + "=?";

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf("Uber") } );

        if (cursor.moveToFirst()) {
            do {
                System.out.println(cursor.getString(cursor.getColumnIndex(CardStore.VALUE)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

    }

    //return number of remaining cards
    public int numberOfCards(String house){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT COUNT (*)" +
                " FROM " + CardStore.TABLE
                + " WHERE " +
                CardStore.HOUSE + "=? and " +
                CardStore.PROGRESS +">?";

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(house) , String.valueOf(0)} );

        int count = 0;
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }

    //get all cards of progress lower than 100
    public String [][] getCards(String house){
        int numberOfCards = numberOfCards(house);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                CardStore.HOUSE + "," +
                CardStore.KEY_CARD + "," +
                CardStore.VALUE + "," +
                CardStore.PROGRESS +
                " FROM " + CardStore.TABLE
                + " WHERE " +
                CardStore.HOUSE + "=? and " +
                CardStore.PROGRESS +">?";

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(house) , String.valueOf(0) } );
        String[][] ret = new String[numberOfCards][3];

        int count = 0;
        if (cursor.moveToFirst()) {
            do {
                ret[count][0] = cursor.getString(cursor.getColumnIndex(CardStore.KEY_CARD));
                ret[count][1] = cursor.getString(cursor.getColumnIndex(CardStore.VALUE));
                ret[count][2] = cursor.getString(cursor.getColumnIndex(CardStore.PROGRESS));
                if(++count >=numberOfCards){
                    break;
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ret;
    }

    /*
    public int getProgress(String house, String card){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                CardStore.KEY_CARD + "," +
                CardStore.PROGRESS +
                " FROM " + CardStore.TABLE
                + " WHERE " +
                CardStore.HOUSE + "=? and " +
                CardStore.KEY_CARD +"=?";

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(house),String.valueOf(card)} );

        int progress = 50;
        if (cursor.moveToFirst()) {
            do {
                progress = cursor.getColumnIndex(CardStore.PROGRESS);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return progress;
    }
    */

    public void updateProgress(String house, String card, int progress){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String whereClause = CardStore.HOUSE+"=? and "+ CardStore.KEY_CARD+"=?";
        ContentValues value = new ContentValues();
        value.put(CardStore.PROGRESS, progress);

        db.update(CardStore.TABLE, value, whereClause, new String[]{house, card} );
        db.close();
        //db.update()
    }

    public void dropTable(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + CardStore.TABLE);
    }

}
