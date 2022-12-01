package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
    }

    public void onClickTryAgain(View view) {
        Intent gameIntent = new Intent(GameOver.this, Game.class);
        GameOver.this.startActivity(gameIntent);
    }
}