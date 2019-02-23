package com.example.dobra.myapplication;

import android.text.TextUtils;
import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentParser {
    private static final ContentParser SINGLE_INSTANCE = new ContentParser();
    private List<ContentType> contentWords;

    public final Integer TYPE_NEWLINE = 0;
    public final Integer TYPE_KEYWORD = 1;
    public final Integer TYPE_REGULAR = 2;


    public ContentParser(){

    }

    public class ContentType{
        public String wordContent;
        public String correctWord;
        public Integer wordType;

        ContentType(){
        }

        ContentType(String correctWord, String wordContent, Integer wordType){
            this.correctWord = correctWord;
            this.wordContent = wordContent;
            this.wordType = wordType;
        }

        ContentType(String wordContent, Integer wordType){
            this.wordContent = wordContent;
            this.wordType = wordType;
        }

        ContentType(String wordContent){
            this.wordContent = wordContent;
            this.correctWord = "";
            this.wordType = 2;
        }

        ContentType(Integer wordType){
            this.wordType = wordType;
            this.correctWord = "";
            this.wordContent = "";
        }

        public boolean isSecretWord(){
            return !TextUtils.isEmpty(correctWord);
        }

        public boolean isKeyword(){
            return wordType.equals(TYPE_KEYWORD);
        }

        public boolean isRegularWord(){
            return wordType.equals(TYPE_REGULAR);
        }

        public boolean isNewLine(){
            return wordType.equals(TYPE_NEWLINE);
        }
    }

    public static ContentParser getInstance() {
        return SINGLE_INSTANCE;
    }

    public List<ContentType> processedWords(Level level){
        
        String content = level.getContent();

        contentWords = new ArrayList<>();
        
        if(level.isGreen()){

            boolean newWordFound = false;

            String newWord = "";


            int beginningPositionOfWord = 0;

            for(int i = 0; i < content.length(); i++){
                if(content.charAt(i) == '*' && !newWordFound){
                    newWordFound = true;

                    beginningPositionOfWord = i+1;

                } else if(content.charAt(i) == '*' && newWordFound){
                    newWordFound = false;

                    contentWords.add(new ContentType(newWord, TYPE_KEYWORD));

                } else if(content.charAt(i) == '^' && !newWordFound) {
                    newWordFound = true;

                    beginningPositionOfWord = i+1;
                } else if(content.charAt(i) == '^' && newWordFound) {
                    newWordFound = false;

                    newWord = content.substring(beginningPositionOfWord, i);

                    contentWords.add(new ContentType(newWord));
                } else if(content.charAt(i) == '/'){

                    contentWords.add(new ContentType(TYPE_NEWLINE));
                }
            }
            
        } else if(level.isViolette()){
            boolean newCorrectWordFound = false;

            boolean newWrongWordFound = false;

            String newCorrectWord = "";

            String newWrongWord = "";

            int beginningPositionOfCorrectWord = 0;
            int beginningPositionOfWrongWord = 0;

            for(int i = 0; i < content.length(); i++){
                if(content.charAt(i) == '*' && !newCorrectWordFound){
                    newCorrectWordFound = true;

                    beginningPositionOfCorrectWord = i+1;

                } else if(content.charAt(i) == '&'){
                    newWrongWordFound = true;

                    beginningPositionOfWrongWord = i+1;

                    newCorrectWord = content.substring(beginningPositionOfCorrectWord,i);

                } else if(content.charAt(i) == '*' && newCorrectWordFound && newWrongWordFound){
                    newCorrectWordFound = false;

                    newWrongWordFound = false;

                    newWrongWord = content.substring(beginningPositionOfWrongWord, i);

                    contentWords.add(new ContentType(newCorrectWord, newWrongWord, TYPE_KEYWORD));

                } else if(content.charAt(i) == '*' && newCorrectWordFound && !newWrongWordFound){
                    newCorrectWordFound = false;

                    newCorrectWord = content.substring(beginningPositionOfCorrectWord, i);

                    contentWords.add(new ContentType(newCorrectWord, TYPE_KEYWORD));

                } else if(content.charAt(i) == '^' && !newCorrectWordFound) {
                    newCorrectWordFound = true;

                    beginningPositionOfCorrectWord = i+1;
                } else if(content.charAt(i) == '^' && newCorrectWordFound) {
                    newCorrectWordFound = false;

                    newCorrectWord = content.substring(beginningPositionOfCorrectWord, i);

                    contentWords.add(new ContentType(newCorrectWord));
                } else if(content.charAt(i) == '/'){

                    contentWords.add(new ContentType(TYPE_NEWLINE));
                }
            }
        } else if (level.isRed()){

            boolean newCorrectWordFound = false;

            boolean newWrongWordFound = false;

            String newCorrectWord = "";

            String newWrongWord = "";

            int beginningPositionOfCorrectWord = 0;
            int beginningPositionOfWrongWord = 0;

            for(int i = 0; i < content.length(); i++){
                if(content.charAt(i) == '*' && !newCorrectWordFound){
                    newCorrectWordFound = true;

                    beginningPositionOfCorrectWord = i+1;

                } else if(content.charAt(i) == '&'){
                    newWrongWordFound = true;

                    beginningPositionOfWrongWord = i+1;

                    newCorrectWord = content.substring(beginningPositionOfCorrectWord,i);

                } else if(content.charAt(i) == '*' && newCorrectWordFound && newWrongWordFound){
                    newCorrectWordFound = false;

                    newWrongWordFound = false;

                    newWrongWord = content.substring(beginningPositionOfWrongWord, i);

                    contentWords.add(new ContentType(newCorrectWord, newWrongWord, TYPE_KEYWORD));

                } else if(content.charAt(i) == '*' && newCorrectWordFound && !newWrongWordFound){
                    newCorrectWordFound = false;

                    newCorrectWord = content.substring(beginningPositionOfCorrectWord, i);

                    contentWords.add(new ContentType(newCorrectWord, TYPE_KEYWORD));

                } else if(content.charAt(i) == '^' && !newCorrectWordFound) {
                    newCorrectWordFound = true;

                    beginningPositionOfCorrectWord = i+1;
                } else if(content.charAt(i) == '^' && newCorrectWordFound) {
                    newCorrectWordFound = false;

                    newCorrectWord = content.substring(beginningPositionOfCorrectWord, i);

                    contentWords.add(new ContentType(newCorrectWord));
                } else if(content.charAt(i) == '/'){

                    contentWords.add(new ContentType(TYPE_NEWLINE));
                }
            }
        }

        return contentWords;
    }
}
