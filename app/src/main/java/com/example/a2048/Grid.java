package com.example.a2048;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class has the game logic, it is what allows the user to swipe, and generate new cards
 * at a random interval
 */
public class Grid extends GridLayout {

    private static final int GRID_SIZE = 4;
    public static Card[][] cards = new Card[4][4];

    /**
     * An initialization function
     * @param context
     */
    public Grid(Context context) {
        super(context);
        initGrid();
    }

    /**
     * An initialization function
     * @param context
     * @param attrs
     */
    public Grid(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGrid();
    }

    /**
     * An initialization function
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public Grid(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGrid();
    }

    /**
     * This function initializes the grid on the screen
     */
    protected void initGrid() {
        setRowCount(4);
        setColumnCount(4);
        setOnTouchListener(new Listener());
    }

    /**
     * This function starts the game and sets the grid size
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth = (Math.min(w, h) - 10) / 4;
        addCards(cardWidth, cardWidth);
        startGame();
        Game.getGame().updateScore(getCurrentScore(cards));
    }

    /**
     * This function is used to add cards to the grid
     * @param cardWidth
     * @param cardHeight
     */
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

    /**
     * This function is used to chose a random number to generate the cards
     */
    private static void addRandomNum() {
        // Create a list of empty positions on the grid
        List<int[]> emptyPositions = new ArrayList<>();
        for (int y = 0; y < GRID_SIZE; ++y) {
            for (int x = 0; x < GRID_SIZE; ++x) {
                if (cards[x][y].getCount() == 0) {
                    emptyPositions.add(new int[] {x, y});
                }
            }
        }

        // Choose a random position from the list of empty positions
        Random random = new Random();
        int[] chosenPosition = emptyPositions.remove(random.nextInt(emptyPositions.size()));

        // Set the value of the chosen position to either 2 or 4, with a probability of 0.9 and 0.1 respectively
        cards[chosenPosition[0]][chosenPosition[1]].setCount(random.nextDouble() < 0.9 ? 2 : 4);
    }


    /**
     * This function started the game
     */
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

    /**
     * This function is what moves the cards when the user swipes left
     */
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
            Game.getGame().updateScore(getCurrentScore(cards));
            checkGameOver();
        }
    }

    /**
     * This function is what moves the cards when the user swipes right
     */
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
            Game.getGame().updateScore(getCurrentScore(cards));
            checkGameOver();
        }
    }
    /**
     * This function is what moves the cards when the user swipes up
     */

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
            Game.getGame().updateScore(getCurrentScore(cards));
            checkGameOver();
        }
    }

    /**
     * This function is what moves the cards when the user swipes down
     */
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
            Game.getGame().updateScore(getCurrentScore(cards));
            checkGameOver();
        }
    }

    /**
     * This function checks the grid to see if the game over condition is met
     * The game is over when there are no more ways to combine cards on the screen
     * and the grid is full
     */

    private void checkGameOver() {
        // Loop through all the cards in the game
        for(int y=0; y<4; y++) {
            for(int x=0; x<4; x++) {
                // Check if the current card has a count of 0 or a matching
                // adjacent card
                if (cards[x][y].getCount() == 0 ||
                        (x<=2 && cards[x][y].getCount() == cards[x+1][y].getCount()) ||
                        (y<=2 && cards[x][y].getCount() == cards[x][y+1].getCount())) {
                    // If so, the game is not over, so we return early
                    return;
                }
            }
        }

        // If we get to this point, the game must be over.
        System.out.println("Game Over");
        Game.getGame().GameOver();
    }

    /**
     * This function gets the user's score
     * @param gameBoard
     * @return
     */
    public int getCurrentScore(Card[][] gameBoard) {
        int score = 0;

        // Loop through each cell of the game board and add the value of
        // each cell to the score.
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                score += gameBoard[i][j].getCount();
                if (gameBoard[i][j].getCount() == 2048){
                    Game.getGame().popUp();
                }
            }
        }

        return score;
    }


    /**
     * This is used to "listen" for the user's touch
     */


    class Listener implements View.OnTouchListener {

        private float startX, startY, offsetX, offsetY;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
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
