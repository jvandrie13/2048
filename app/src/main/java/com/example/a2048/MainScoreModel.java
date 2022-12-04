package com.example.a2048;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel to contain the values used with MainActivity, namely
 * to store the high score and previous score to be displayed on the home screen.
 */
public class MainScoreModel extends ViewModel {
    private MutableLiveData<Integer> highScore;
    private MutableLiveData<Integer> previousScore;

    private MutableLiveData<String> highScoreStr;
    private MutableLiveData<String> prevScoreStr;

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
     * If previousScore is unassigned, initialize and instantiate to 0.
     * Otherwise, just return the value of previousScore.
     */
    public MutableLiveData<Integer> getPreviousScore() {
        if (previousScore == null) {
            previousScore = new MutableLiveData<>();
            previousScore.setValue(0);
        }
            return previousScore;
    }

    /**
     * If highScoreStr is unassigned, initialize and instantiate to
     * the current value of highScore; this will instantiate itself
     * to 0 if previously undeclared.
     * Otherwise, just return the string value of highScoreStr.
     */
    public MutableLiveData<String> getHighScoreStr(){
        if (highScoreStr == null) {
            highScoreStr = new MutableLiveData<>();
            highScoreStr.setValue(highScore.toString());
        }
        return prevScoreStr;
    }

    /**
     * If prevScoreStr is unassigned, initialize and instantiate to
     * the current value of prevScore; this will instantiate itself
     * to 0 if previously undeclared.
     * Otherwise, just return the string value of prevScoreStr.
     */
    public MutableLiveData<String> getPreviousScoreStr(){
        if (prevScoreStr == null) {
            prevScoreStr = new MutableLiveData<>();
            prevScoreStr.setValue(previousScore.toString());
        }
        return prevScoreStr;
    }


}
