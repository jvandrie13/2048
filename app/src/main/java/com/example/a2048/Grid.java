package com.example.a2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Grid extends GridLayout {

    private static final int GRID_SIZE = 4;
    public static Card[][] cards = new Card[4][4];
    private static List<Point> emptyPoints = new ArrayList<Point>();
    public int num[][] = new int[4][4];
    public boolean hasTouched = false;


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

    protected void initGrid() {
        setRowCount(4);
        setColumnCount(4);
        setOnTouchListener(new Listener());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth = (Math.min(w, h) - 10) / 4;
        addCards(cardWidth, cardWidth);
        startGame();
    }

    protected void addCards(int cardWidth, int cardHeight) {
        this.removeAllViews();
        Card c;
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                c = new Card(getContext());
                c.setCount(0);
                addView(c, cardWidth, cardHeight);
                cards[row][col] = c;
            }
        }
    }

    private static void addRandomNum() {
        // Create a list of empty positions on the grid
        List<Point> emptyPoints = new ArrayList<>();
        for (int y = 0; y < GRID_SIZE; ++y) {
            for (int x = 0; x < GRID_SIZE; ++x) {
                if (cards[x][y].getCount() == 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }

        // Choose a random position from the list of empty positions
        Random random = new Random();
        Point p = emptyPoints.remove(random.nextInt(emptyPoints.size()));

        // Set the value of the chosen position to either 2 or 4, with a probability of 0.9 and 0.1 respectively
        cards[p.x][p.y].setCount(random.nextDouble() < 0.9 ? 2 : 4);
    }


    public static void startGame() {
        // Reset all cards to 0
        for (int y = 0; y < GRID_SIZE; ++y) {
            for (int x = 0; x < GRID_SIZE; ++x) {
                cards[x][y].setCount(0);
            }
        }

        // Add two random numbers to the grid
        addRandomNum();
        addRandomNum();
    }


    private void swipeLeft() {
        boolean movedOrMerged = false;

        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 3; ++x) {
                for (int x1 = x + 1; x1 < 4; ++x1) {
                    if (cards[x1][y].getCount() > 0) {
                        if (cards[x][y].getCount() == 0) {
                            cards[x][y].setCount(cards[x1][y].getCount());
                            cards[x1][y].setCount(0);
                            --x;
                            movedOrMerged = true;
                        } else if (cards[x][y].equals(cards[x1][y])) {
                            cards[x][y].setCount(cards[x][y].getCount() * 2);
                            cards[x1][y].setCount(0);
                            movedOrMerged = true;
                        }
                        break;
                    }
                }
            }
        }

        if (movedOrMerged) {
            addRandomNum();
            checkGameOver();
        }
    }


    private void swipeRight() {
        boolean movedOrMerged = false;

        for (int y = 0; y < 4; ++y) {
            for (int x = 3; x > 0; --x) {
                for (int x1 = x - 1; x1 >= 0; --x1) {
                    if (cards[x1][y].getCount() > 0) {
                        if (cards[x][y].getCount() == 0) {
                            cards[x][y].setCount(cards[x1][y].getCount());
                            cards[x1][y].setCount(0);
                            ++x;
                            movedOrMerged = true;
                        } else if (cards[x][y].equals(cards[x1][y])) {
                            cards[x][y].setCount(cards[x][y].getCount() * 2);
                            cards[x1][y].setCount(0);
                            movedOrMerged = true;
                        }
                        break;
                    }
                }
            }
        }

        if (movedOrMerged) {
            addRandomNum();
            checkGameOver();
        }
    }


    private void swipeUp() {
        boolean moved = false;
        for (int col = 0; col < 4; ++col) {
            for (int row = 0; row < 3; ++row) {
                if (cards[col][row].getCount() == 0) {
                    int aboveRow = row + 1;
                    while (aboveRow < 4 && cards[col][aboveRow].getCount() == 0) {
                        aboveRow++;
                    }
                    if (aboveRow < 4) {
                        cards[col][row].setCount(cards[col][aboveRow].getCount());
                        cards[col][aboveRow].setCount(0);
                        moved = true;
                    }
                } else {
                    int aboveRow = row + 1;
                    while (aboveRow < 4 && cards[col][aboveRow].getCount() == 0) {
                        aboveRow++;
                    }
                    if (aboveRow < 4 && cards[col][row].equals(cards[col][aboveRow])) {
                        cards[col][row].setCount(cards[col][row].getCount() * 2);
                        cards[col][aboveRow].setCount(0);
                        moved = true;
                    }
                }
            }
        }
        if (moved) {
            addRandomNum();
            checkGameOver();
        }
    }


    private void swipeDown() {
        boolean moved = false;
        for (int col = 0; col < 4; ++col) {
            for (int row = 3; row > 0; --row) {
                if (cards[col][row].getCount() == 0) {
                    int belowRow = row - 1;
                    while (belowRow >= 0 && cards[col][belowRow].getCount() == 0) {
                        belowRow--;
                    }
                    if (belowRow >= 0) {
                        cards[col][row].setCount(cards[col][belowRow].getCount());
                        cards[col][belowRow].setCount(0);
                        moved = true;
                    }
                } else {
                    int belowRow = row - 1;
                    while (belowRow >= 0 && cards[col][belowRow].getCount() == 0) {
                        belowRow--;
                    }
                    if (belowRow >= 0 && cards[col][row].equals(cards[col][belowRow])) {
                        cards[col][row].setCount(cards[col][row].getCount() * 2);
                        cards[col][belowRow].setCount(0);
                        moved = true;
                    }
                }
            }
        }
        if (moved) {
            addRandomNum();
            checkGameOver();
        }
    }



    private void checkGameOver() {
        boolean isOver = true;
        ALL:
        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 4; ++x) {
                if (cards[x][y].getCount() == 0 ||
                        (x <= 2 && cards[x][y].getCount() == cards[x + 1][y].getCount()) ||
                        (y <= 2 && cards[x][y].getCount() == cards[x][y + 1].getCount())) {
                    isOver = false;
                    break ALL;
                }
            }
        }
        if (isOver) {

        }
    }


    class Listener implements View.OnTouchListener {

        private float startX, startY, offsetX, offsetY;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (!hasTouched) {
                hasTouched = true;
            }
//            score = MainActivity.score;
            for (int y = 0; y < 4; ++y) {
                for (int x = 0; x < 4; ++x) {
                    num[y][x] = cards[y][x].getCount();
                }
            }
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = motionEvent.getX();
                    startY = motionEvent.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    offsetX = motionEvent.getX() - startX;
                    offsetY = motionEvent.getY() - startY;

                    if (Math.abs(offsetX) > Math.abs(offsetY)) {
                        if (offsetX < -5) {
                            swipeLeft();
                        } else if (offsetX > 5) {
                            swipeRight();
                        }
                    } else {
                        if (offsetY < -5) {
                            swipeUp();
                        } else if (offsetY > 5) {
                            swipeDown();
                        }
                    }
            }
            return true;
        }

    }
}
