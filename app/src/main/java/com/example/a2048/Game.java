package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

public class Game extends AppCompatActivity {
    private Grid grid;
    Button newGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        grid = findViewById(R.id.grid);
        newGame = findViewById(R.id.newGame);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid.onSizeChanged(1125, 1537, 0 ,0);
            }
        });
    }

}