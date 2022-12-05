package com.example.a2048;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;


public class Grid extends GridLayout {

    public Grid(Context context) {
        super(context);
        initGrid();
    }

    public Grid(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGrid();
    }

    public Grid(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGrid();
    }

    private void initGrid() {
        setRowCount(4);
        setColumnCount(4);
        setOnTouchListener(new Listener());
    }


    class Listener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;

        }

    }
}
