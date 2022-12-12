package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    TextView highScore, prevScore;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        highScore = findViewById(R.id.highScore_View);
        prevScore = findViewById(R.id.prevScore_View);

        sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);

        highScore.setText(sharedPref.getString("HIGH_SCORE", "1"));
        prevScore.setText(sharedPref.getString("PREV_SCORE", "1'"));


        MediaPlayer player = MediaPlayer.create(this, R.raw.lose);
        player.start();
    }

    public void onClickTryAgain(View view) {
        Intent gameIntent = new Intent(GameOver.this, Game.class);
        GameOver.this.startActivity(gameIntent);
    }
}