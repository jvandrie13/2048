package com.example.a2048;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;


public class Grid extends GridLayout {

    public static Card[][] cards = new Card[4][4];

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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth = (Math.min(w, h)-10)/4;
        addCards(cardWidth, cardWidth);
//        startGame();
        for(int y=0;y<4;++y) {
            for(int x=0;x<4;++x) {
                cards[x][y].setCount(0);
            }
        }

    }

    private void addCards(int cardWidth, int cardHeight) {
        this.removeAllViews();
        Card c;
        for(int col=0;col<4;col++) {
            for(int row = 0;row<4;row++) {
                c = new Card(getContext());
                c.setCount(0);
                addView(c, cardWidth, cardHeight);
                cards[row][col] = c;
            }
        }
    }


    class Listener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;

        }

    }
}
