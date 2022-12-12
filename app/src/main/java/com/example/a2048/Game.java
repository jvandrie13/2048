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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Locale;
import java.util.Set;

/**
 * Primary class for performing game play.
 * Contains methods for updating the score and method calls to generate
 * the grid board.
 */
public class Game extends AppCompatActivity {
    private Grid grid;
    Button newGame;
    boolean popUpShown = false;

    public static Game game = null;
    GameScoreModel model;
    TextView currScore, highScore;

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
        highScore.setText(sharedPref.getString("HIGH_SCORE", "1"));

        grid = findViewById(R.id.grid);
        newGame = findViewById(R.id.newGame);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid.onSizeChanged(1125, 1537, 0, 0);
            }
        });
    }


    public void GameOver(){
        Intent intent = new Intent(this, GameOver.class);
        this.startActivity(intent);
    }

    /**
     * Updates the int value of the user's current score
     * Calls updateScoreData() to reflect score changes in the UI
     * @param i
     */
    public void updateScore(int i){
        currentScore = i;
        currScore.setText(Integer.toString(currentScore));

        // Call to update the stored LiveData score values
        updateScoreData(currentScore);
    }

    /**
     * Update the currentScore stored displayed on the UI, stored in the view model,
     * and in SharedPreferences when the score changes during Game.
     *
     * @param currentScore
     */
    public void updateScoreData(int currentScore) {
        // Update currentScore
        SharedPreferences.Editor scoreUpdate = sharedPref.edit();

        int currHighScore = Integer.valueOf(highScore.getText().toString());

        // If current score > high score
        if(currentScore > currHighScore){ // FIX ME
            scoreUpdate.putString("HIGH_SCORE", (String.valueOf(currentScore)));
            highScore.setText(String.valueOf(currentScore));
        }
        scoreUpdate.putString("PREV_SCORE", String.valueOf(currentScore));
        scoreUpdate.apply();

    }

    /**
     * When the app is minimized or closed completely,
     * the user's high score value is stored in SharedPreferences.
     */
    @Override
    protected void onPause() {
        super.onPause();
        // Access SharedPreferences to update persisted score values
        SharedPreferences.Editor scoreUpdate = sharedPref.edit();
        scoreUpdate.putString("HIGH_SCORE", (String.valueOf(highScore.getText())));
        scoreUpdate.putString("PREV_SCORE", (String.valueOf(currentScore)));
        scoreUpdate.apply();
    }

    public void popUp() {
        if (popUpShown == false) {
            // Create a new instance of the PopupWindow class
            PopupWindow popupWindow = new PopupWindow(this);

// Set the content view of the PopupWindow to a custom layout that contains a TextView and a Button
            View customView = getLayoutInflater().inflate(R.layout.popup, null);
            popupWindow.setContentView(customView);

// Set the width and height of the PopupWindow
            popupWindow.setWidth(500);
            popupWindow.setHeight(500);

// Set the focusable property of the PopupWindow to true
            popupWindow.setFocusable(true);

// Show the PopupWindow
            popupWindow.showAtLocation(grid, Gravity.CENTER, 0, 0);
            popUpShown = true;
        }
    }
}