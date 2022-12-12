package com.example.a2048;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Main activity/home screen of the 2048 game.
 */
public class MainActivity extends AppCompatActivity {

    MainScoreModel model;
    TextView highScoreView;
    TextView prevScoreView;
    SharedPreferences sharedPref;
    String highScoreStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reference TextViews in .xml file
        highScoreView = findViewById(R.id.highScore_View);
        prevScoreView = findViewById(R.id.prevScore_View);

        // Get MainScoreModel to obtain score data
        model = new ViewModelProvider(this).get(MainScoreModel.class);
        sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);

        super.onResume(); // Call to load in values from sharedPreferences

        // Update UI to reflect stored Highscore and Previous score values
        final Observer<Integer> scoreObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer newScore) {
                //highScoreView.setText(sharedPref.getString("HIGH_SCORE", "1"));
                highScoreView.setText(sharedPref.getString("HIGH_SCORE", "1"));
                prevScoreView.setText(sharedPref.getString("PREV_SCORE", "1"));
            }
        };

        model.getHighScore().observe(this, scoreObserver);
    }

    public void onClickNewGame(View view) {
        Intent gameIntent = new Intent(MainActivity.this, Game.class);
        MainActivity.this.startActivity(gameIntent);
    }

    /**
     * When the app is reloaded, the values in MainScoreModel are updated
     * to those stored in the SharedPreferences.
     */
 /*   @Override
    protected void onResume() {
        super.onResume();

        // Access SharedPreferences to see persisted score values
        //highScoreStr = sharedPref.getString("HIGH_SCORE", "1");
        //prevScoreStr = shared
        //int storedHighScore = Integer.valueOf(highScoreStr);

        // Update MainScoreModel to values from SharedPreferences
        //model.getHighScore().setValue(storedHighScore);

    }*/
}