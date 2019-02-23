package com.example.dobra.myapplication;

import android.text.TextUtils;


public class GameplayManager {
    private String keywordSelected;
    private String keywordAnswer;

    GameplayManager(){
        keywordSelected = "";
        keywordAnswer = "";
    }

    public String getKeywordSelected() {
        return keywordSelected;
    }

    public void setKeywordSelected(String keywordSelected) {
        this.keywordSelected = keywordSelected;
    }

    public String getKeywordAnswer() {
        return keywordAnswer;
    }

    public void setKeywordAnswer(String keywordAnswer) {
        this.keywordAnswer = keywordAnswer;
    }

    public boolean isKeyWordSelected(){
        return !TextUtils.isEmpty(keywordSelected);
    }

    public void clear(){
        keywordSelected = "";
        keywordAnswer = "";
    }

    public boolean isCorrectAnswer(){
        return (keywordSelected.equals(keywordAnswer));
    }
}
