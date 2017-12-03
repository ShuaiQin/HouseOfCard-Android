package com.example.houseofcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.mindorks.test.R;

public class DatabaseTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_test);
        dbTest();
    }

    public void dbTest(){
        CardOperataion co = new CardOperataion(this);
        int numberOfNullHouse = co.numberOfCards("houseNotExist");
        System.out.println("Numbers: "+ numberOfNullHouse);

        String[][] cardDeck = new String[][]{
                new String []{"card1", "This is card1"},
                new String []{"card2", "This is card2"},
                new String []{"card3", "This is card3"},
                new String []{"card4", "This is card4"},
                new String []{"card5", "This is card5"},
        };
        co.initialize(cardDeck, "newHouse");
        co.initialize(cardDeck, "newHouse2");
        //
        //get card number
        int number = co.numberOfCards("newHouse");
        System.out.println("Number of cards: "+ number);

        //get card
        String[][] allCards = co.getCards("newHouse");
        for(int i=0;i<number; i++){
            System.out.println(allCards[i][0] +" "+ allCards[i][1]+" "+ allCards[i][2]);
        }
        //set familier
        co.updateProgress("newHouse","card3",0);

        //get card
        number = co.numberOfCards("newHouse");
        int number2 = co.numberOfCards("newHouse2");
        System.out.println("Number of cards after update: "+ number);
        System.out.println("Number of cards after update: "+ number2);
    }
}
