package com.example.a2048;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
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
import java.util.Set;

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
    SharedPreferences sharedPref;


    // Initialize currentScore to 0 at the start of the game...?
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
        sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);

        model = new ViewModelProvider(this).get(GameScoreModel.class);
        // Set the "BEST" score value
        highScore.setText(sharedPref.getString("HIGH_SCORE", "1")); // This makes it crash :/

        grid = findViewById(R.id.grid);
        newGame = findViewById(R.id.newGame);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid.onSizeChanged(1125, 1537, 0 ,0);
            }
        });
    }

    /**
     * Update the player's score depending on the tiles they have merged
     * @param i
     */
    public void GameOver(){
        Intent intent = new Intent(this, GameOver.class);
        this.startActivity(intent);
    }

    public void updateScore(int i){
        currentScore = i;
        currScore.setText(Integer.toString(currentScore));

        // Call to update the stored LiveData score values
        updateScoreData(currentScore);
    }

    /**
     * Update the currentScore stored in the GameScoreModel
     * and in SharedPreferences when the score changes during Game.
     * @param currentScore
     */
    public void updateScoreData(int currentScore){
        // Update currentScore
        SharedPreferences.Editor scoreUpdate = sharedPref.edit();

        // If current score > high score
        if(currentScore > model.getHighScore().getValue()){
            scoreUpdate.putString("HIGH_SCORE", (String.valueOf(currentScore)));
        }
        scoreUpdate.putString("PREV_SCORE", String.valueOf(currentScore));
        scoreUpdate.apply();

    }

    /**
     * When the app is minimized or closed completely,
     * the user's high score value is stored in SharedPreferences.
     */
    @Override
    protected void onPause(){
        super.onPause();
        // Access SharedPreferences to update persisted score values
        SharedPreferences.Editor scoreUpdate = sharedPref.edit();
        scoreUpdate.putString("HIGH_SCORE", (String.valueOf(highScore)));
        scoreUpdate.putString("PREV_SCORE", (String.valueOf(currentScore)));
        scoreUpdate.apply();
    }
}