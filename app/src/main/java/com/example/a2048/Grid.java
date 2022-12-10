package com.example.a2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;


public class Grid extends GridLayout {

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
        emptyPoints.clear();
        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 4; ++x) {
                if (cards[x][y].getCount() == 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }
        Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
        cards[p.x][p.y].setCount(Math.random() > 0.1 ? 2 : 4);
    }

    public static void startGame() {
        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 4; ++x) {
                cards[x][y].setCount(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }

    private void swipeLeft() {
        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 3; ++x) {
                for (int x1 = x + 1; x1 < 4; ++x1) {
                    if (cards[x1][y].getCount() > 0) {
                        if (cards[x][y].getCount() == 0) {
                            cards[x][y].setCount(cards[x1][y].getCount());
                            cards[x1][y].setCount(0);
                            --x;
                            addRandomNum();
                            checkGameOver();
                        } else if (cards[x][y].equals(cards[x1][y])) {
                            cards[x][y].setCount(cards[x][y].getCount() * 2);
                            cards[x1][y].setCount(0);
//                            MainActivity.getMainActivity().addScore(cards[x][y].getCount());
                            addRandomNum();
                            checkGameOver();
                        }
                        break;
                    }
                }
            }
        }
    }

    private void swipeRight() {
        boolean b = false;
        for (int y = 0; y < 4; ++y) {
            for (int x = 3; x > 0; --x) {
                for (int x1 = x - 1; x1 >= 0; --x1) {
                    if (cards[x1][y].getCount() > 0) {
                        if (cards[x][y].getCount() == 0) {
                            cards[x][y].setCount(cards[x1][y].getCount());
                            cards[x1][y].setCount(0);
                            ++x;
                            addRandomNum();
                            checkGameOver();
                        } else if (cards[x][y].equals(cards[x1][y])) {
                            cards[x][y].setCount(cards[x][y].getCount() * 2);
                            cards[x1][y].setCount(0);
//                            MainActivity.getMainActivity().addScore(cards[x][y].getCount());
                            addRandomNum();
                            checkGameOver();
                        }
                        break;
                    }
                }
            }
        }
    }

    private void swipeUp() {
        boolean b = false;
        for (int x = 0; x < 4; ++x) {
            for (int y = 0; y < 3; ++y) {
                for (int y1 = y + 1; y1 < 4; ++y1) {
                    if (cards[x][y1].getCount() > 0) {
                        if (cards[x][y].getCount() == 0) {
                            cards[x][y].setCount(cards[x][y1].getCount());
                            cards[x][y1].setCount(0);
                            --y;
                            addRandomNum();
                            checkGameOver();
                        } else if (cards[x][y].equals(cards[x][y1])) {
                            cards[x][y].setCount(cards[x][y].getCount() * 2);
                            cards[x][y1].setCount(0);
//                            MainActivity.getMainActivity().addScore(cards[x][y].getCount());
                            addRandomNum();
                            checkGameOver();
                        }
                        break;
                    }
                }
            }
        }
    }

    private void swipeDown() {
        boolean b = false;
        for (int x = 0; x < 4; ++x) {
            for (int y = 3; y > 0; --y) {
                for (int y1 = y - 1; y1 >= 0; --y1) {
                    if (cards[x][y1].getCount() > 0) {
                        if (cards[x][y].getCount() == 0) {
                            cards[x][y].setCount(cards[x][y1].getCount());
                            cards[x][y1].setCount(0);
                            ++y;
                            addRandomNum();
                            checkGameOver();
                        } else if (cards[x][y].equals(cards[x][y1])) {
                            cards[x][y].setCount(cards[x][y].getCount() * 2);
                            cards[x][y1].setCount(0);
//                            MainActivity.getMainActivity().addScore(cards[x][y].getCount());
                            addRandomNum();
                            checkGameOver();
                        }
                        break;
                    }
                }
            }
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
            new AlertDialog.Builder(getContext()).setTitle("Hey").setMessage("There" + "Score Here" + "！").setPositiveButton("Hey", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startGame();
                }
            }).show();
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
