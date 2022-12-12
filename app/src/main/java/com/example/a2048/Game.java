package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Locale;

public class Game extends AppCompatActivity {
    private Grid grid;
    Button newGame;

    public static Game game = null;
    GameScoreModel model;
    TextView currScore, highScore;
    String newCurrScore, newHighScore;
    public Game() {
        game = this;
    }


    // Initialize currentScore to 0 at the start of the game...? Might use diff var.
    int currentScore = 0;
    public static Game getGame() {
        return game;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        currScore = findViewById(R.id.currScore_view);
        highScore = findViewById(R.id.bestScore_view);

        grid = findViewById(R.id.grid);
        newGame = findViewById(R.id.newGame);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid.onSizeChanged(1125, 1537, 0 ,0);
            }
        });

    }

    public void updateScore(int i){
    currentScore =  i;
    currScore.setText(Integer.toString(currentScore));
    }
    /**
     * Update the currentScore stored in the GameScoreModel
     * and in SharedPreferences when the score changes during Game.
     * @param currentScore
     */
    public void updateScoreData(int currentScore){
        // Update currentScore
        model.getCurrentScore().setValue(currentScore);
        newCurrScore = String.format(Locale.getDefault(), "%d", currentScore);
        model.getCurrScoreStr().setValue(newCurrScore);

        // If currentScore > highScore, update the highScore
        if(currentScore > model.getHighScore().getValue()){
            model.getHighScore().setValue(currentScore); // --> Make sure this is set to the correct var that tracks score
            newHighScore = String.format(Locale.getDefault(),"%d", currentScore);
            model.getHighScoreStr().setValue(newHighScore);
        }
    }

    /**
     * When the app is reloaded, the values in GameScoreModel are updated
     * based on what is stored in SharedPreferences.
     */
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        // Access SharedPreferences to see persisted score values
//        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
//        int storedHighScore = sh.getInt("finalHighScore", 0);
//
//        // Update MainScoreModel to values from SharedPreferences
//        model.getHighScore().setValue(storedHighScore);
//    }

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