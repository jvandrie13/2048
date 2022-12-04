package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reference TextViews in .xml file
        highScoreView = findViewById(R.id.highScore_View);
        prevScoreView = findViewById(R.id.prevScore_View);

        // Get MainScoreModel to obtain score data
        model = new ViewModelProvider(this).get(MainScoreModel.class);
        super.onResume(); // Call to load in values from sharedPreferences
    }

    public void onClickNewGame(View view) {
        Intent gameIntent = new Intent(MainActivity.this, Game.class);
        MainActivity.this.startActivity(gameIntent);
    }

    /**
     * When the app is reloaded, the values in MainScoreModel are updated
     * to those stored in the SharedPreferences.
     */
    @Override
    protected void onResume() {
        super.onResume();

        // Access SharedPreferences to see persisted score values
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        int storedHighScore = sh.getInt("finalHighScore",0);

        // Update MainScoreModel to values from SharedPreferences
        model.getHighScore().setValue(storedHighScore);

    }

    /**
     * When the app is minimized or closed completely,
     * the user's high score value is stored in SharedPreferences.
     */
    @Override
    protected void onPause(){
        super.onPause();

        // Access SharedPreferences to update persisted score values
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();

        // Update finalHighScore in SharedPreferences
        myEdit.putInt("finalHighScore", model.getHighScore().getValue());
        myEdit.apply();

    }
}