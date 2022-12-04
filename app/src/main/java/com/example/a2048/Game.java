package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Game extends AppCompatActivity {
    private Grid grid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        grid = findViewById(R.id.grid);
    }

}