package com.example.a2048;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel to contain the values used with Game, namely
 * to store/display the high score and current score.
 */
public class GameScoreModel extends ViewModel {
    private MutableLiveData<Integer> highScore;
    private MutableLiveData<Integer> currentScore;

    private MutableLiveData<String> highScoreStr;
    private MutableLiveData<String> currScoreStr;

    /**
     * If highScore is unassigned, initialize and instantiate to 0.
     * Otherwise, just return the value of highScore.
     */
    public MutableLiveData<Integer> getHighScore(){
        if(highScore == null){
            highScore = new MutableLiveData<>();
            highScore.setValue(0);
        }
        return highScore;
    }

    /**
     * If currentScore is unassigned, initialize and instantiate to 0.
     * Otherwise, just return the value of currentScore.
     */
    public MutableLiveData<Integer> getCurrentScore() {
        if (currentScore == null) {
            currentScore = new MutableLiveData<>();
            currentScore.setValue(0);
        }
        return currentScore;
    }

    /**
     * If highScoreStr is unassigned, initialize and instantiate to
     * the current value of highScore; this will instantiate itself
     * to 0 if currently undeclared.
     * Otherwise, just return the string value of highScoreStr.
     */
    public MutableLiveData<String> getHighScoreStr(){
        if (highScoreStr == null) {
            highScoreStr = new MutableLiveData<>();
            highScoreStr.setValue(highScore.toString());
        }
        return currScoreStr;
    }

    /**
     * If currScoreStr is unassigned, initialize and instantiate to
     * the current value of currScore; this will instantiate itself
     * to 0 if currently undeclared.
     * Otherwise, just return the string value of currScoreStr.
     */
    public MutableLiveData<String> getCurrScoreStr(){
        if (currScoreStr == null) {
            currScoreStr = new MutableLiveData<>();
            currScoreStr.setValue(currentScore.toString());
        }
        return currScoreStr;
    }
}

