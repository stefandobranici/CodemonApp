package com.example.dobra.myapplication;

import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.addAll;

public class GameStatus {

    private LinkedList<LinkedList<Level>> gameStatus;

    GameStatus(){
        gameStatus = new LinkedList<LinkedList<Level>>();
        for(int i = 0 ; i < 7; i++){
            LinkedList<Level> temp = new LinkedList<Level>();
            gameStatus.add(temp);

        }

    }

    public boolean isEmpty(){
        return gameStatus.size()==0;
    }

    public boolean isChapterEmpty(int chapter_id){
        return gameStatus.get(chapter_id).size() == 0;
    }

    public LinkedList<Level> getChapter(int chapter_id){
        if(!isEmpty()) return gameStatus.get(chapter_id);

        return null;
    }

    public Level getMission(int chapter_id, int mission_id){
        return gameStatus.get(chapter_id).get(mission_id);
    }

    public void addChapter(int chapter_id, LinkedList<Level> chapter_missions){
        gameStatus.get(chapter_id).addAll(chapter_missions);
    }

    public void addMission(int chapter_id, int mission_id, Level mission){
         gameStatus.get(chapter_id).get(mission_id).setValues(mission);
    }
}
