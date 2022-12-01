package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickNewGame(View view) {
        Intent gameIntent = new Intent(MainActivity.this, Game.class);
        MainActivity.this.startActivity(gameIntent);
    }
}